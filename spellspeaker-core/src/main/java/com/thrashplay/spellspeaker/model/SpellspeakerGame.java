package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.InvalidInputException;
import com.thrashplay.spellspeaker.SpellspeakerException;
import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.effect.SpellEffectExecutor;
import com.thrashplay.spellspeaker.model.state.*;
import com.thrashplay.spellspeaker.repository.json.PowerDeckFactory;
import com.thrashplay.spellspeaker.service.RandomService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class SpellspeakerGame {

    private long id;

    private GameRules rules;
    private RandomService randomNumberService;
    private SpellEffectExecutor spellEffectExecutor;

    private int currentTick = 0;

    private DiscardPile discardPile;
    private Library library;
    private Market market;

    private Player bluePlayer;
    private Player redPlayer;
    private Player playerWithInitiative;
    private Player activePlayer;

    private Element attunement;

    private InputRequest inputRequest;
    private InputType inputResponseType;
    private String inputResponse;

    private TurnState turnState;

    private List<String> stateChangeLog = new LinkedList<>();

    public SpellspeakerGame(GameRules rules, RandomService randomNumberService, CardFactory cardFactory, PowerDeckFactory powerDeckFactory, SpellEffectExecutor spellEffectExecutor, User blueUser, User redUser) {
        this.rules = rules;
        this.randomNumberService = randomNumberService;
        this.spellEffectExecutor = spellEffectExecutor;

        bluePlayer = new Player(blueUser, PlayerColor.Blue);
        bluePlayer.setHealth(rules.getMaximumHealth());
        bluePlayer.setMana(rules.getMaximumMana());
        bluePlayer.setPowerDeck(powerDeckFactory.createPowerDeck());

        redPlayer = new Player(redUser, PlayerColor.Red);
        redPlayer.setHealth(rules.getMaximumHealth());
        redPlayer.setMana(rules.getMaximumMana());
        redPlayer.setPowerDeck(powerDeckFactory.createPowerDeck());
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

        bluePlayer.getHand().addAll(cardFactory.createBaseCards());
        redPlayer.getHand().addAll(cardFactory.createBaseCards());
        for (int i = 0; i < rules.getInitialHandSize(); i++) {
            bluePlayer.getHand().add(library.draw());
            redPlayer.getHand().add(library.draw());
        }

        currentTick = -1; // start at -1 because the first thing we do is advance this
        LinkedList<StateChange> stateChanges = new LinkedList<>();
        stateChanges.add(new SimpleStateChange("GameBegins", "The game begins."));
        startNextTurn(stateChanges);
        stepUntilBlocked(stateChanges);

        recordStateChanges(stateChanges);
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

    public Element getAttunement() {
        return attunement;
    }

    public List<String> getStateChangeLog() {
        return stateChangeLog;
    }

    /**
     * Begins a new phase.
     */
    private void beginNewPhase(List<StateChange> stateChanges) {
        market.refresh();
        stateChanges.add(new MarketRefreshed());

        changeAttunement(stateChanges);
    }

    /**
     * Advance the time tracker until the next turn, and setup the game state to begin that turn.
     */
    private void startNextTurn(List<StateChange> stateChanges) {
        advanceTimeTracker(stateChanges);

        Player oldActivePlayer = activePlayer;
        activePlayer = calculateActivePlayer();

        if (oldActivePlayer != activePlayer) {
            stateChanges.add(new SimpleStateChange("TurnBegan", "It is now " + activePlayer.getColor().name() + "'s turn."));
        } else {
            stateChanges.add(new SimpleStateChange("TurnBegan", "It is still " + activePlayer.getColor().name() + "'s turn."));
        }
        turnState = new ResolveActiveCardState();
    }

    /**
     * Advances the time tracker to the next player's turn.
     */
    private void advanceTimeTracker(List<StateChange> stateChanges) {
        while (redPlayer.getNextTurnTick() != currentTick && bluePlayer.getNextTurnTick() != currentTick) {
            currentTick = (currentTick + 1) % rules.getTicksPerPhase();

            if (currentTick == 0) {
                beginNewPhase(stateChanges);
            }
        }
    }

    private void recordStateChanges(List<StateChange> stateChanges) {
        for (StateChange stateChange : stateChanges) {
            stateChangeLog.add(stateChange.getMessage());
        }
    }

    /**
     * Advances through turn states until reaching one that requires user input. If invalid input is detected, the
     * original state is restored.
     */
    private void stepUntilBlocked(List<StateChange> stateChanges) {
        //noinspection StatementWithEmptyBody
        while (step(stateChanges)) {
            /* do nothing */
        }
    }

    /**
     * Executes the current turn state, returning true if another step can be executed and false if input is needed.
     */
    private boolean step(List<StateChange> stateChanges) {
        try {
            turnState.execute(stateChanges, this);
        } finally {
            // set the input request even on an exception, because we use InvalidInputExceptions to signal bad input
            inputRequest = turnState.getInputRequest();
        }
        return inputRequest == null;
    }

    /**
     * Returns true if there is user input pending processing.
     */
    private boolean hasInput() {
        return inputResponse != null && inputResponse.length() > 0;
    }

    /**
     * Clears the pending user input.
     */
    private String consumeInput() {
        String result = inputResponse;
        inputResponse = null;
        inputResponseType = null;
        return result;
    }

    /**
     * Sets a new attunement element.
     */
    private void changeAttunement(List<StateChange> stateChanges) {
        int elementIndex = randomNumberService.getRandomNumberBetween(0, 3);
        if (elementIndex == 0) {
            attunement = Element.Ice;
        } else if (elementIndex == 1) {
            attunement = Element.Fire;
        } else {
            attunement = Element.Lightning;
        }

        stateChanges.add(new AttunementChanged(attunement));
    }

    public List<StateChange> provideInput(User currentUser, InputType type, String value) {
        assertCurrentUserIsActive(currentUser.getId());

        if (inputRequest == null) {
            throw new InvalidInputException("Was not expecting input at this time.");
        }

        if (type == InputType.RitualCompletionRequest) {
            if (inputRequest.getType() != InputType.CardFromHand) {
                throw new InvalidInputException("Cannot confirm ritual completion at this time.");
            }
        } else if (type != inputRequest.getType()) {
            switch (type) {
                case CardFromHand:
                    throw new InvalidInputException("Did not expect a card to be selected from your hand.");

                case CardFromMarket:
                    throw new InvalidInputException("Did not expect a card to be selected from the market.");

                case TextEntry:
                    throw new InvalidInputException("Was not expecting user text input.");
            }
        }

        inputResponse = value;
        inputResponseType = type;

        List<StateChange> stateChanges = new LinkedList<>();
        stepUntilBlocked(stateChanges);
        recordStateChanges(stateChanges);
        return stateChanges;
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

    private Player calculateActivePlayer() {
        if (playerWithInitiative.getNextTurnTick() == currentTick) {
            return playerWithInitiative;
        } else if (getPlayerWithoutInitiative().getNextTurnTick() == currentTick) {
            return getPlayerWithoutInitiative();
        } else {
            throw new SpellspeakerException("There is no active player!");
        }
    }

    private Player getPlayerWithoutInitiative() {
        return playerWithInitiative == bluePlayer ? redPlayer : bluePlayer;
    }

    private interface TurnState {
        void execute(List<StateChange> stateChanges, SpellspeakerGame game);
        InputRequest getInputRequest();
    }

    private class ResolveActiveCardState implements TurnState {
        @Override
        public void execute(List<StateChange> stateChangeList, SpellspeakerGame game) {
            Player player = game.activePlayer;
            Card card = player.getActiveCard();

            // no active card should only happen on first turn, but in any case let's skip it
            if (card != null) {
                try {
                    if (card.requiresInput() && !hasInput()) {
                        waitForCardInput(game, card);
                        return;
                    }

                    if (card.getType().isRune()) {
                        // add the card to the ritual
                        stateChangeList.add(new AddedToRitual(player.getColor().name(), card.getName()));
                        player.getRitual().add(card);
                    } else {
                        executeSpell(game, SpellspeakerGame.this.activePlayer, card, game.consumeInput());
                    }
                } catch (InvalidInputException e) {
                    if (card.requiresInput()) {
                        waitForCardInput(game, card);
                    }
                    throw e;
                }

            }

            player.setActiveCard(null);
            game.turnState = new WaitForCardToPlayOrRitualCompletion();
        }

        private void waitForCardInput(SpellspeakerGame game, Card card) {
            CardExecutionParameter parameter = card.getParameter();
            if (parameter.getType() == CardExecutionParameter.Type.CardFromMarket) {
                game.turnState = new WaitForMarketSelectionCardParameterInputState(parameter);
            } else {
                game.turnState = new WaitForTextCardParameterInputState(parameter);
            }
        }

        @Override
        public InputRequest getInputRequest() {
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

    private class WaitForTextCardParameterInputState implements TurnState {
        private CardExecutionParameter parameter;

        public WaitForTextCardParameterInputState(CardExecutionParameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public void execute(List<StateChange> stateChangeList, SpellspeakerGame game) {
            if (hasInput()) {
                game.turnState = new ResolveActiveCardState();
            }
        }

        @Override
        public InputRequest getInputRequest() {
            return new InputRequest(InputType.TextEntry, parameter.getPrompt());
        }
    }

    private class WaitForMarketSelectionCardParameterInputState implements TurnState {
        private CardExecutionParameter parameter;

        public WaitForMarketSelectionCardParameterInputState(CardExecutionParameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public void execute(List<StateChange> stateChangeList, SpellspeakerGame game) {
            if (hasInput()) {
                game.turnState = new ResolveActiveCardState();
            }
        }

        @Override
        public InputRequest getInputRequest() {
            return new InputRequest(InputType.CardFromMarket, parameter.getPrompt());
        }
    }

    private class PlayCardFromHandState implements TurnState {
        @Override
        public void execute(List<StateChange> stateChanges, SpellspeakerGame game) {
            if (!hasInput()) {
                game.turnState = new WaitForCardToPlayOrRitualCompletion();
            } else {
                try {
                    Card card = findCardIn(activePlayer.getHand(), consumeInput(), "You do not have that card.");

                    // validate the rune can be added to the ritual
                    if (card.getType().isRune()) {
                        rules.getRitualConstructionRules().validateRitualAddition(activePlayer.getRitual(), card);
                    }

                    if (activePlayer.getMana() < card.getManaCost()) {
                        throw new InvalidInputException("You do not have enough mana to cast '" + card.getName() + "'.");
                    }

                    playCard(stateChanges, activePlayer, card);
                    turnState = new EndOfTurnState();
                } catch (InvalidInputException e) {
                    game.turnState = new WaitForCardToPlayOrRitualCompletion();
                    throw e;
                }
            }
        }

        @Override
        public InputRequest getInputRequest() {
            return null;
        }

        private void playCard(List<StateChange> stateChanges, Player activePlayer, Card card) {
            int manaCost = Math.max(0, card.getManaCost());
            int castingTime = Math.max(0, card.getCastingTime());

            activePlayer.setNextTurnTick((currentTick + castingTime) % rules.getTicksPerPhase());
            activePlayer.setMana(activePlayer.getMana() - manaCost);

            activePlayer.getHand().getCards().remove(card);
            activePlayer.setActiveCard(card);
            stateChanges.add(new BeganCasting(activePlayer.getColor().name(), card.getName()));
        }
    }

    private class CompleteRitualState implements TurnState {
        @Override
        public void execute(List<StateChange> stateChanges, SpellspeakerGame game) {
            if (hasInput() && inputResponseType == InputType.RitualCompletionRequest) {
                spellEffectExecutor.execute(activePlayer.getRitual(), game);

                // put reusable cards back in the player's hand, otherwise discard them
                activePlayer.getRitual().clear(activePlayer.getHand(), discardPile);
                inputResponse = null;
            }

            game.turnState = new WaitForCardToPlayOrRitualCompletion();
        }

        @Override
        public InputRequest getInputRequest() {
            return null;
        }
    }

    private class WaitForCardToPlayOrRitualCompletion implements TurnState {
        @Override
        public void execute(List<StateChange> stateChangeList, SpellspeakerGame game) {
            if (hasInput()) {
                game.turnState = inputResponseType == InputType.RitualCompletionRequest
                        ? new CompleteRitualState()
                        : new PlayCardFromHandState();
            }
        }

        @Override
        public InputRequest getInputRequest() {
            return new InputRequest(InputType.CardFromHand, "Select a card to play:");
        }
    }

    private class EndOfTurnState implements TurnState {
        @Override
        public void execute(List<StateChange> stateChanges, SpellspeakerGame game) {
            if (game.getActivePlayer().getHand().size() > rules.getMaximumHandSize()) {
                turnState = new WaitForCardToDiscardState();
            } else {
                startNextTurn(stateChanges);
            }
        }

        @Override
        public InputRequest getInputRequest() {
            return null;
        }
    }

    private class DiscardCardState implements TurnState {
        @Override
        public void execute(List<StateChange> stateChanges, SpellspeakerGame game) {
            if (!hasInput()) {
                game.turnState = new WaitForCardToDiscardState();
            } else {
                try {
                    Card card = findCardIn(activePlayer.getHand(), consumeInput(), "You do not have that card.");
                    if (card.isBaseCard()) {
                        throw new InvalidInputException("You cannot discard a base card.");
                    }

                    activePlayer.getHand().getCards().remove(card);
                    discardPile.add(card);
                    stateChanges.add(new Discarded(activePlayer.getColor().name(), card.getName()));

                    game.turnState = new EndOfTurnState();
                } catch (InvalidInputException e) {
                    game.turnState = new WaitForCardToDiscardState();
                    throw e;
                }
            }
        }

        @Override
        public InputRequest getInputRequest() {
            return null;
        }
    }

    private class WaitForCardToDiscardState implements TurnState {
        @Override
        public void execute(List<StateChange> stateChangeList, SpellspeakerGame game) {
            if (hasInput()) {
                game.turnState = new DiscardCardState();
            }
        }

        @Override
        public InputRequest getInputRequest() {
            return new InputRequest(InputType.CardFromHand, "Select a card to discard:");
        }
    }
}
