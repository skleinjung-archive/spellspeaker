package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class Player {
    private long userId;
    private int nextTurnTick;
    private Hand hand;

    public Player(long userId) {
        this.userId = userId;
        hand = new Hand();
    }

    public long getUserId() {
        return userId;
    }

    public int getNextTurnTick() {
        return nextTurnTick;
    }

    public void setNextTurnTick(int nextTurnTick) {
        this.nextTurnTick = nextTurnTick;
    }

    public Hand getHand() {
        return hand;
    }
}
