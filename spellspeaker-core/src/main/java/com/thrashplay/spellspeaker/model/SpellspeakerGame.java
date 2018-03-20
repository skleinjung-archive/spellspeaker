package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.model.state.AddedToRitual;
import com.thrashplay.spellspeaker.model.state.BeganCasting;
import com.thrashplay.spellspeaker.model.state.FinishedCasting;
import com.thrashplay.spellspeaker.model.state.StateChange;

import java.util.Iterator;
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

    private ExpectedInput expectedInput;

    private DiscardPile discardPile;
    private Library library;
    private Market market;

    private int currentTick = 0;

    public SpellspeakerGame(GameRules rules, CardFactory cardFactory, long bluePlayerUserId, long redPlayerUserId) {
        this.rules = rules;

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
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void advanceTimeTracker(List<StateChange> stateChangeList) {
        while ((activePlayer = calculateActivePlayer()) == null) {
            currentTick = (currentTick + 1) % rules.getTicksPerPhase();
        }

        resolveActiveCard(stateChangeList, activePlayer);
        expectedInput = ExpectedInput.PlayCardFromHand;
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
            } else {
                // the card is a spell

                // todo: implement spell behavior

                // put reusable cards back in the player's hand, otherwise discard it
                if (card.isReusable()) {
                    player.getHand().add(card);
                } else {
                    discardPile.add(card);
                }
            }

            player.setActiveCard(null);
        }
    }

    public List<StateChange> playFromHand(Errors errors, long userId, String cardName) {
        if (activePlayer.getUserId() != userId) {
            throw new IllegalStateException("It is not your turn!");
        }
        if (expectedInput != ExpectedInput.PlayCardFromHand) {
            throw new IllegalStateException("Not expecting to select a card from hand. expectedInput=" + expectedInput);
        }

        Card card = findCardInHand(activePlayer, cardName);
        if (card == null) {
            throw new IllegalArgumentException("Card not found in active player's hand. (cardName=" + cardName + ", hand=" + activePlayer.getHand().getCards());
        }

        List<StateChange> stateChanges = new LinkedList<>();

        // validate the rune can be added to the ritual
        if (card.getType().isRune()) {
            rules.getRitualConstructionRules().validateRitualAddition(errors, activePlayer.getRitual(), card);
        }

        // skip processing if the rune is invalid
        if (!errors.hasErrors()) {
            activePlayer.getHand().getCards().remove(card);
            spendManaAndTime(activePlayer, card);
            activePlayer.setActiveCard(card);
            stateChanges.add(new BeganCasting(activePlayer.getColor().name(), card.getName()));

            advanceTimeTracker(stateChanges);
        }

        return stateChanges;
    }

    private Card findCardInHand(Player activePlayer, String cardName) {
        for (Card currentCard : activePlayer.getHand().getCards()) {
            if (currentCard.getName().equals(cardName)) {
                return currentCard;
            }
        }
        return null;
    }

    private void spendManaAndTime(Player activePlayer, Card card) {
        int manaCost = Math.max(0, card.getManaCost());
        int castingTime = Math.max(0, card.getCastingTime());

        if (activePlayer.getMana() < manaCost) {
            throw new IllegalStateException("You do not have enough mana to cast '" + card.getName() + "'.");
        }

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

    public ExpectedInput getExpectedInput() {
        return expectedInput;
    }

    public Market getMarket() {
        return market;
    }

    public Library getLibrary() {
        return library;
    }
}
