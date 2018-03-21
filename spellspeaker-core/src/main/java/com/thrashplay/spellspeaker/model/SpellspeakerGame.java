package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.InvalidInputException;
import com.thrashplay.spellspeaker.SpellspeakerException;
import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.effect.SpellEffectExecutor;
import com.thrashplay.spellspeaker.model.state.*;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class SpellspeakerGame {

    private long id;

    private GameRules rules;

    private Player bluePlayer;
    private Player redPlayer;

    private Player playerWithInitiative;
    private Player activePlayer;

    private InputRequest inputRequest;
    private String inputResponse;

    private DiscardPile discardPile;
    private Library library;
    private Market market;

    private int currentTick = 0;

    private SpellEffectExecutor spellEffectExecutor;

    public SpellspeakerGame(GameRules rules, CardFactory cardFactory, SpellEffectExecutor spellEffectExecutor, User blueUser, User redUser) {
        this.rules = rules;
        this.spellEffectExecutor = spellEffectExecutor;

        bluePlayer = new Player(blueUser, PlayerColor.Blue);
        bluePlayer.setHealth(rules.getMaximumHealth());
        bluePlayer.setMana(rules.getMaximumMana());

        redPlayer = new Player(redUser, PlayerColor.Red);
        redPlayer.setHealth(rules.getMaximumHealth());
        redPlayer.setMana(rules.getMaximumMana());
        redPlayer.setNextTurnTick(29);      // todo: remove this

        playerWithInitiative = bluePlayer; // todo: randomly determine this

        discardPile = new DiscardPile();

        library = new Library(discardPile);
        List<Card> cards = cardFactory.createLibraryCards();
        for (Card card : cards) {
            library.add(card);
        }
        library.shuffle();

        market = new Market(rules, discardPile, library);
        market.refresh();

        bluePlayer.getHand().addAll(cardFactory.createBaseCards());
        redPlayer.getHand().addAll(cardFactory.createBaseCards());
        for (int i = 0; i < rules.getInitialHandSize(); i++) {
            bluePlayer.getHand().add(library.draw());
            redPlayer.getHand().add(library.draw());
        }

        currentTick = -1;
        advanceTimeTracker(new LinkedList<>());
        requestInput();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GameRules getRules() {
        return rules;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    private void advanceTimeTracker(List<StateChange> stateChangeList) {
        while ((activePlayer = calculateActivePlayer()) == null) {
            currentTick = (currentTick + 1) % rules.getTicksPerPhase();
        }

        resolveActiveCard(stateChangeList, activePlayer);
    }

    private void resolveActiveCard(List<StateChange> stateChangeList, Player player) {
        
        if (player.getActiveCard() != null) {
            Card card = player.getActiveCard();

            stateChangeList.add(new FinishedCasting(player.getColor().name(), card.getName()));

            if (card.getType().isRune()) {
                // add the card to the ritual
                stateChangeList.add(new AddedToRitual(player.getColor().name(), player.getActiveCard().getName()));
                player.getRitual().add(card);

                player.setActiveCard(null);
            } else if (!card.requiresInput() || inputResponse != null && inputResponse.length() > 0) {
                // the card is a spell without input, or we got the input we need - execute it
                executeSpell(activePlayer, card);
            }
        }
    }

    private void requestInput() {
        if (activePlayer.getActiveCard() != null && activePlayer.getActiveCard().requiresInput() && (inputResponse == null || inputResponse.length() == 0)) {
            CardExecutionParameter parameter = activePlayer.getActiveCard().getParameter();
            if (parameter.getType() == CardExecutionParameter.Type.CardFromMarket) {
                inputRequest = new InputRequest(InputRequest.InputRequestType.SelectCardFromMarket, parameter.getPrompt());
            } else {
                inputRequest = new InputRequest(InputRequest.InputRequestType.TextEntry, parameter.getPrompt());
            }
        } else if (activePlayer.getHand().size() > rules.getMaximumHandSize()) {
            inputRequest = new InputRequest(InputRequest.InputRequestType.SelectCardToDiscard);
        } else {
            inputRequest = new InputRequest(InputRequest.InputRequestType.PlayCardFromHand);
        }
    }


    private void executeRitual(Player player) {
        spellEffectExecutor.execute(player.getRitual(), this);

        // put reusable cards back in the player's hand, otherwise discard them
        player.getRitual().clear(player.getHand(), discardPile);
        inputResponse = null;
    }

    private List<StateChange> playFromHand(long userId, String cardName) {
        Card card = findCardIn(activePlayer.getHand(), cardName, "You do not have that card.");

        List<StateChange> stateChanges = new LinkedList<>();
        // validate the rune can be added to the ritual
        if (card.getType().isRune()) {
            rules.getRitualConstructionRules().validateRitualAddition(activePlayer.getRitual(), card);
        }

        if (activePlayer.getMana() < card.getManaCost()) {
            throw new InvalidInputException("You do not have enough mana to cast '" + card.getName() + "'.");
        }

        activePlayer.getHand().getCards().remove(card);
        spendManaAndTime(activePlayer, card);
        activePlayer.setActiveCard(card);
        stateChanges.add(new BeganCasting(activePlayer.getColor().name(), card.getName()));

        advanceTimeTracker(stateChanges);
        requestInput();

        return stateChanges;
    }

    private List<StateChange> selectCardFromMarket(User currentUser, String cardName) {
        Card card = findCardIn(market, cardName, "The market does not contain that card.");
        return handleUserInput(currentUser, card.getName());
    }

    public List<StateChange> completeRitual(long userId) {
        assertCurrentUserIsActive(userId);

        List<StateChange> stateChanges = new LinkedList<>();

        executeRitual(activePlayer);
        requestInput();

        return stateChanges;
    }

    private List<StateChange> discardFromHand(long userId, String cardName) {
        Card card = findCardIn(activePlayer.getHand(), cardName, "You do not have that card.");
        if (card.isBaseCard()) {
            throw new InvalidInputException("You cannot discard a base card.");
        }

        List<StateChange> stateChanges = new LinkedList<>();

        activePlayer.getHand().getCards().remove(card);
        stateChanges.add(new Discarded(activePlayer.getColor().name(), card.getName()));

        requestInput();

        return stateChanges;
    }

    private List<StateChange> handleUserInput(User currentUser, String input) {
        List<StateChange> stateChangeList = new LinkedList<>();

        inputResponse = input;
        resolveActiveCard(stateChangeList, activePlayer);

        requestInput();

        return stateChangeList;
    }

    public List<StateChange> provideInput(User currentUser, InputRequest.InputRequestType type, String value) {
        assertCurrentUserIsActive(currentUser.getId());
        if (type != inputRequest.getType()) {
            switch (type) {
                case PlayCardFromHand:
                case SelectCardToDiscard:
                    throw new InvalidInputException("Did not expect a card to be selected from your hand.");

                case SelectCardFromMarket:
                    throw new InvalidInputException("Did not expect a card to be selected from the market.");

                case TextEntry:
                    throw new InvalidInputException("Was not expecting user text input.");
            }
        }

        switch (type) {
            case PlayCardFromHand:
                return playFromHand(currentUser.getId(), value);

            case SelectCardToDiscard:
                return discardFromHand(currentUser.getId(), value);

            case SelectCardFromMarket:
                return selectCardFromMarket(currentUser, value);

            case TextEntry:
                return handleUserInput(currentUser, value);

            default:
                throw new InvalidInputException("Unknown input type was provided: " + type);
        }
    }

    private void assertCurrentUserIsActive(long currentUserId) {
        if (activePlayer.getUser().getId() != currentUserId) {
            throw new InvalidInputException("It is not your turn.");
        }
    }

    private Card findCardIn(CardContainer cardContainer, String cardName, String notFoundErrorMessage) {
        for (Card currentCard : cardContainer.getCards()) {
            if (currentCard.getName().equals(cardName)) {
                return currentCard;
            }
        }

        throw new InvalidInputException(notFoundErrorMessage);
    }

    private void spendManaAndTime(Player activePlayer, Card card) {
        int manaCost = Math.max(0, card.getManaCost());
        int castingTime = Math.max(0, card.getCastingTime());

        activePlayer.setNextTurnTick((currentTick + castingTime) % rules.getTicksPerPhase());
        activePlayer.setMana(activePlayer.getMana() - manaCost);
    }

    private Player calculateActivePlayer() {
        if (playerWithInitiative.getNextTurnTick() == currentTick) {
            return playerWithInitiative;
        } else if (getPlayerWithoutInitiative().getNextTurnTick() == currentTick) {
            return getPlayerWithoutInitiative();
        } else {
            return null;
        }
    }

    private Player getPlayerWithoutInitiative() {
        return playerWithInitiative == bluePlayer ? redPlayer : bluePlayer;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getNonActivePlayer() {
        if (activePlayer == bluePlayer) {
            return redPlayer;
        } else if (activePlayer == redPlayer) {
            return bluePlayer;
        } else {
            return null;
        }
    }

    public InputRequest getInputRequest() {
        return inputRequest;
    }

    public Market getMarket() {
        return market;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public Library getLibrary() {
        return library;
    }

    private interface TurnStep {
        InputRequest execute(List<StateChange> stateChangeList, String input);
    }

    private class ResolveActiveCardStep implements TurnStep {
        private SpellspeakerGame game;
        private Player player;
        private Card card;

        public ResolveActiveCardStep(SpellspeakerGame game, Player player, Card card) {
            Assert.notNull(game, "game cannot be null");
            Assert.notNull(player, "player cannot be null");
            Assert.notNull(card, "card cannot be null");

            this.game = game;
            this.player = player;
            this.card = card;
        }

        @Override
        public InputRequest execute(List<StateChange> stateChangeList, String input) {
            if (card == null) {
                throw new SpellspeakerException("Cannot resolve the active card, because it is null.");
            }

            if (card.requiresInput() && (input == null || input.length() == 0)) {
                CardExecutionParameter parameter = activePlayer.getActiveCard().getParameter();
                if (parameter.getType() == CardExecutionParameter.Type.CardFromMarket) {
                    return new InputRequest(InputRequest.InputRequestType.SelectCardFromMarket, parameter.getPrompt());
                } else {
                    return new InputRequest(InputRequest.InputRequestType.TextEntry, parameter.getPrompt());
                }
            }

            stateChangeList.add(new FinishedCasting(player.getColor().name(), card.getName()));

            if (card.getType().isRune()) {
                // add the card to the ritual
                stateChangeList.add(new AddedToRitual(player.getColor().name(), card.getName()));
                player.getRitual().add(card);
            } else {
                // the card is a spell without input, or we got the input we need - execute it
                executeSpell(game, activePlayer, card, input);
            }

            player.setActiveCard(null);
            return null;
        }

        private void executeSpell(SpellspeakerGame game, Player player, Card card, String input) {
            spellEffectExecutor.execute(card, game, input);

            // put reusable cards back in the player's hand, otherwise discard it
            if (card.isReusable()) {
                player.getHand().add(card);
            } else {
                discardPile.add(card);
            }
        }
    }
}
