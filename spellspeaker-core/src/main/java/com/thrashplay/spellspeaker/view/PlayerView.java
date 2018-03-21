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
    private CardView activeCard;
    private RitualView ritual;
    private int powerCardDrawPileSize;
    private int powerCardDiscardPileSize;
    private List<Integer> usedPowerCards;
    // shields
    private int iceShieldStrength;
    private int fireShieldStrength;
    private int lightningShieldStrength;

    public PlayerView(User requestingUser, Player player) {
        isControlledByClient = requestingUser != null && requestingUser.getId() == player.getUser().getId();
        nextTurnTick = player.getNextTurnTick();
        health = player.getHealth();
        mana = player.getMana();
        numberOfCardsInHand = player.getHand().size();
        activeCard = player.getActiveCard() == null ? null : CardView.fromCard(player.getActiveCard());
        ritual = new RitualView(player.getRitual());
        powerCardDrawPileSize = player.getPowerDeck().sizeOfDrawPile();
        powerCardDiscardPileSize = player.getPowerDeck().sizeOfDiscardPile();
        usedPowerCards = player.getPowerDeck().getUsedCards();
        // shields
        iceShieldStrength = player.getIceShield().getStrength();
        fireShieldStrength = player.getFireShield().getStrength();
        lightningShieldStrength = player.getLightningShield().getStrength();
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

    public int getPowerCardDrawPileSize() {
        return powerCardDrawPileSize;
    }

    public int getPowerCardDiscardPileSize() {
        return powerCardDiscardPileSize;
    }

    public List<Integer> getUsedPowerCards() {
        return usedPowerCards;
    }

    public int getIceShieldStrength() {
        return iceShieldStrength;
    }

    public void setIceShieldStrength(int iceShieldStrength) {
        this.iceShieldStrength = iceShieldStrength;
    }

    public int getFireShieldStrength() {
        return fireShieldStrength;
    }

    public void setFireShieldStrength(int fireShieldStrength) {
        this.fireShieldStrength = fireShieldStrength;
    }

    public int getLightningShieldStrength() {
        return lightningShieldStrength;
    }

    public void setLightningShieldStrength(int lightningShieldStrength) {
        this.lightningShieldStrength = lightningShieldStrength;
    }
}
