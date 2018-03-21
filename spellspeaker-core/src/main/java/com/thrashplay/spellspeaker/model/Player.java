package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.view.PlayerView;

/**
 * @author Sean Kleinjung
 */
public class Player {
    private User user;
    private PlayerColor color;
    private int nextTurnTick;
    private int health;
    private int mana;
    private Hand hand;
    private Card activeCard;
    private Ritual ritual;
    private PowerDeck powerDeck;
    // shields
    private Shield iceShield;
    private Shield fireShield;
    private Shield lightningShield;

    public Player(User user, PlayerColor color) {
        this.user = user;
        this.color = color;
        hand = new Hand();
        ritual = new Ritual();

        iceShield = new Shield();
        iceShield.setElement(Element.Ice);

        fireShield = new Shield();
        fireShield.setElement(Element.Fire);

        lightningShield = new Shield();
        lightningShield.setElement(Element.Lightning);
    }

    public User getUser() {
        return user;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getNextTurnTick() {
        return nextTurnTick;
    }

    public void setNextTurnTick(int nextTurnTick) {
        this.nextTurnTick = nextTurnTick;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Hand getHand() {
        return hand;
    }

    public Card getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(Card activeCard) {
        this.activeCard = activeCard;
    }

    public Ritual getRitual() {
        return ritual;
    }

    public PowerDeck getPowerDeck() {
        return powerDeck;
    }

    public void setPowerDeck(PowerDeck powerDeck) {
        this.powerDeck = powerDeck;
    }

    public Shield getIceShield() {
        return iceShield;
    }

    public void setIceShield(Shield iceShield) {
        this.iceShield = iceShield;
    }

    public Shield getFireShield() {
        return fireShield;
    }

    public void setFireShield(Shield fireShield) {
        this.fireShield = fireShield;
    }

    public Shield getLightningShield() {
        return lightningShield;
    }

    public void setLightningShield(Shield lightningShield) {
        this.lightningShield = lightningShield;
    }
}
