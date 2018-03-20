package com.thrashplay.spellspeaker.web.view;

import com.thrashplay.spellspeaker.model.Player;
import com.thrashplay.spellspeaker.model.User;

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
    private CardView activeCard;
    private RitualView ritual;

    public PlayerView(User requestingUser, Player player) {
        isControlledByClient = requestingUser != null && requestingUser.getId() == player.getUser().getId();
        nextTurnTick = player.getNextTurnTick();
        health = player.getHealth();
        mana = player.getMana();
        numberOfCardsInHand = player.getHand().size();
        activeCard = player.getActiveCard() == null ? null : CardView.fromCard(player.getActiveCard());
        ritual = new RitualView(player.getRitual());
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

    public CardView getActiveCard() {
        return activeCard;
    }

    public RitualView getRitual() {
        return ritual;
    }
}
