package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.Card;
import com.thrashplay.spellspeaker.model.Player;
import com.thrashplay.spellspeaker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class PlayerView {
    // true if the user requesting this view has control of the associated player, false if it is a spectator of that player
    private boolean isControlledByClient;
    private int nextTurnTick;
    private int health;
    private int mana;
    private int numberOfCardsInHand;
    private List<CardView> ritual;

    public PlayerView(User requestingUser, Player player) {
        isControlledByClient = requestingUser != null && requestingUser.getId() == player.getUserId();
        nextTurnTick = player.getNextTurnTick();
        health = player.getHealth();
        mana = player.getMana();
        numberOfCardsInHand = player.getHand().size();
        ritual = convertToCardViews(player.getRitual().getCards());
    }

    public boolean isControlledByClient() {
        return isControlledByClient;
    }

    public int getNextTurnTick() {
        return nextTurnTick;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getNumberOfCardsInHand() {
        return numberOfCardsInHand;
    }

    public List<CardView> getRitual() {
        return ritual;
    }

    private List<CardView> convertToCardViews(List<Card> cards) {
        List<CardView> views = new ArrayList<>(cards.size());
        for (Card card : cards) {
            CardView view = CardView.fromCard(card);
            views.add(view);
        }
        return views;
    }
}
