package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.view.PlayerView;

/**
 * @author Sean Kleinjung
 */
public class Player {
    private long userId;
    private PlayerColor color;
    private int nextTurnTick;
    private int health;
    private int mana;
    private Hand hand;
    private Ritual ritual;

    public Player(long userId, PlayerColor color) {
        this.userId = userId;
        this.color = color;
        hand = new Hand();
        ritual = new Ritual();
    }

    public long getUserId() {
        return userId;
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

    public Ritual getRitual() {
        return ritual;
    }
}
