package com.thrashplay.spellspeaker.model;

import java.util.HashMap;
import java.util.Map;

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
    // afflictions
    private Map<Element, Integer> afflictionStacks;

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

        afflictionStacks = new HashMap<>();
        afflictionStacks.put(Element.Ice, 0);
        afflictionStacks.put(Element.Fire, 0);
        afflictionStacks.put(Element.Lightning, 0);
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

    public Shield getShield(Element element) {
        switch (element) {
            case Ice:
                return iceShield;
            case Fire:
                return fireShield;
            case Lightning:
                return lightningShield;
            default:
                return null;
        }
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

    public void addAfflictionStack(Element element) {
        int afflictionCount = getAfflictionStacks(element);
        afflictionCount++;
        afflictionStacks.put(element, afflictionCount);
    }

    public void removeAfflictionStack(Element element) {
        int afflictionCount = getAfflictionStacks(element);
        afflictionCount--;
        afflictionStacks.put(element, afflictionCount);
    }

    public void removeAfflictionStacks(Element element, int numberOfStacksToRemove) {
        int afflictionCount = getAfflictionStacks(element);
        afflictionCount = Math.max(0, afflictionCount - numberOfStacksToRemove);
        afflictionStacks.put(element, afflictionCount);
    }

    public void clearAffliction(Element element) {
        afflictionStacks.put(element, 0);
    }

    public int getAfflictionStacks(Element element) {
        if (!afflictionStacks.containsKey(element)) {
            afflictionStacks.put(element, 0);
        }
        return afflictionStacks.get(element);
    }

    public void resolveAttack(Element element, int power) {
        Shield shield = getShield(element);
        if (shield != null && shield.getStrength() > 0) {
            int strength = shield.getStrength();
            if (strength >= power) {
                // completely blocked
                shield.setStrength(strength - power);
                power = 0;
            } else {
                // some damage got through
                power = power - shield.getStrength();
                shield.setStrength(0);
            }
        }

        health -= power;
    }
}
