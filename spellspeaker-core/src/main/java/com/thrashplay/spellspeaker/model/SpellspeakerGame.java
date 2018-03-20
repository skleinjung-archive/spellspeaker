package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.InvalidInputException;
import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.effect.SpellEffectExecutor;
import com.thrashplay.spellspeaker.model.state.*;
import org.omg.CORBA.DynAnyPackage.Invalid;

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

    public SpellspeakerGame(GameRules rules, CardFactory cardFactory, SpellEffectExecutor spellEffectExecutor, long bluePlayerUserId, long redPlayerUserId) {
        this.rules = rules;
        this.spellEffectExecutor = spellEffectExecutor;

        bluePlayer = new Player(bluePlayerUserId, PlayerColor.Blue);
        bluePlayer.setHealth(rules.getMaximumHealth());
        bluePlayer.setMana(rules.getMaximumMana());

        redPlayer = new Player(redPlayerUserId, PlayerColor.Red);
        redPlayer.setHealth(rules.getMaximumHealth());
        redPlayer.setMana(rules.getMaximumMana());

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
        // for the time being, just add it to the ritual
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
            inputRequest = new InputRequest(InputRequest.InputRequestType.TextEntry, activePlayer.getActiveCard().getParameter().getPrompt());
        } else if (activePlayer.getHand().size() > rules.getMaximumHandSize()) {
            inputRequest = new InputRequest(InputRequest.InputRequestType.SelectCardToDiscard);
        } else {
            inputRequest = new InputRequest(InputRequest.InputRequestType.PlayCardFromHand);
        }
    }

    private void executeSpell(Player player, Card card) {
        spellEffectExecutor.execute(card, this, inputResponse);

        // put reusable cards back in the player's hand, otherwise discard it
        if (card.isReusable()) {
            player.getHand().add(card);
        } else {
            discardPile.add(card);
        }
        player.setActiveCard(null);
        inputResponse = null;
    }

    private void executeRitual(Player player) {
        spellEffectExecutor.execute(player.getRitual(), this);

        // put reusable cards back in the player's hand, otherwise discard them
        player.getRitual().clear(player.getHand(), discardPile);
        inputResponse = null;
    }

    public List<StateChange> playFromHand(long userId, String cardName) {
        assertCurrentUserIsActive(userId);
        if (inputRequest.getType() != InputRequest.InputRequestType.PlayCardFromHand) {
            throw new InvalidInputException("Did not expect a card to be selected from your hand.");
        }

        Card card = findCardInHand(activePlayer, cardName);

        List<StateChange> stateChanges = new LinkedList<>();
        // validate the rune can be added to the ritual
        if (card.getType().isRune()) {
            rules.getRitualConstructionRules().validateRitualAddition(activePlayer.getRitual(), card);
        }

        if (activePlayer.getMana() < card.getManaCost()) {
            throw new InvalidInputException("You do not have enough mana to cast '" + card.getName() + "'.");
        }

        // skip processing if the rune is invalid
        activePlayer.getHand().getCards().remove(card);
        spendManaAndTime(activePlayer, card);
        activePlayer.setActiveCard(card);
        stateChanges.add(new BeganCasting(activePlayer.getColor().name(), card.getName()));

        advanceTimeTracker(stateChanges);
        requestInput();

        return stateChanges;
    }

    public List<StateChange> completeRitual(long userId) {
        assertCurrentUserIsActive(userId);

        List<StateChange> stateChanges = new LinkedList<>();

        executeRitual(activePlayer);
        requestInput();

        return stateChanges;
    }

    public List<StateChange> discardFromHand(long userId, String cardName) {
        assertCurrentUserIsActive(userId);
        if (inputRequest.getType() != InputRequest.InputRequestType.SelectCardToDiscard) {
            throw new InvalidInputException("Did not expect a card to be discarded from your hand.");
        }

        Card card = findCardInHand(activePlayer, cardName);
        if (card.isBaseCard()) {
            throw new InvalidInputException("You cannot discard a base card.");
        }

        List<StateChange> stateChanges = new LinkedList<>();

        activePlayer.getHand().getCards().remove(card);
        stateChanges.add(new Discarded(activePlayer.getColor().name(), card.getName()));

        requestInput();

        return stateChanges;
    }

    public List<StateChange> handleUserInput(User currentUser, String input) {
        assertCurrentUserIsActive(currentUser.getId());

        if (inputRequest.getType() != InputRequest.InputRequestType.TextEntry) {
            throw new InvalidInputException("Not expecting user input.");
        }

        List<StateChange> stateChangeList = new LinkedList<>();

        inputResponse = input;
        resolveActiveCard(stateChangeList, activePlayer);

        requestInput();

        return stateChangeList;
    }

    private void assertCurrentUserIsActive(long currentUserId) {
        if (activePlayer.getUserId() != currentUserId) {
            throw new InvalidInputException("It is not your turn.");
        }
    }

    private Card findCardInHand(Player activePlayer, String cardName) {
        for (Card currentCard : activePlayer.getHand().getCards()) {
            if (currentCard.getName().equals(cardName)) {
                return currentCard;
            }
        }

        throw new InvalidInputException("You do not have that card.");
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

    public Library getLibrary() {
        return library;
    }
}
