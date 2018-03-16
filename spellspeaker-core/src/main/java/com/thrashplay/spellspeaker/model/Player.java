package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class Player {
    private int nextTurnTick;

    private Hand hand;

    public Player() {
        hand = new Hand();
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
