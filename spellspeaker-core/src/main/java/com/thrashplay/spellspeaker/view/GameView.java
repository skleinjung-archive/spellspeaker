package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class GameView {
    private long id;
    private int numberOfCardsInLibrary;
    private int currentTick;

    private PlayerView bluePlayer;
    private PlayerView redPlayer;
    private String activePlayerColor;
    private String currentUserColor;

    private String expectedInput;

    private List<CardView> market;
    private List<CardView> hand;

    public GameView(User user, SpellspeakerGame game) {
        final Player currentUserPlayer;
        Player bluePlayer = game.getBluePlayer();
        Player redPlayer = game.getRedPlayer();

        if (user != null && user.getId() == bluePlayer.getUserId()) {
            currentUserPlayer = bluePlayer;
            currentUserColor = PlayerColor.Blue.name();
        } else if (user != null && user.getId() == redPlayer.getUserId()) {
            currentUserPlayer = redPlayer;
            currentUserColor = PlayerColor.Red.name();
        } else {
            currentUserPlayer = null;
        }

        id = game.getId();
        numberOfCardsInLibrary = game.getLibrary().size();
        currentTick = game.getCurrentTick();
        this.bluePlayer = new PlayerView(user, bluePlayer);
        this.redPlayer = new PlayerView(user, redPlayer);
        activePlayerColor = game.getActivePlayer() == null
                ? null
                : game.getActivePlayer().getColor().name();
        expectedInput = game.getExpectedInput() == null
                ? null
                : game.getExpectedInput().name();
        market = CardView.fromCards(game.getMarket().getCards());
        hand = currentUserPlayer == null
                ? null
                : CardView.fromCards(currentUserPlayer.getHand().getCards());
    }

    public long getId() {
        return id;
    }

    public int getNumberOfCardsInLibrary() {
        return numberOfCardsInLibrary;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public PlayerView getBluePlayer() {
        return bluePlayer;
    }

    public PlayerView getRedPlayer() {
        return redPlayer;
    }

    public String getActivePlayerColor() {
        return activePlayerColor;
    }

    public String getCurrentUserColor() {
        return currentUserColor;
    }

    public String getExpectedInput() {
        return expectedInput;
    }

    public List<CardView> getMarket() {
        return market;
    }

    public List<CardView> getHand() {
        return hand;
    }
}
