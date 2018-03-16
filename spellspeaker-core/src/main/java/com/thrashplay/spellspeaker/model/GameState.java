package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class GameState {
    private long id;
    private int currentTick = 0;

    private Player bluePlayer;
    private Player redPlayer;

    public GameState() {
        bluePlayer = new Player();
        redPlayer = new Player();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void advanceToNextTick() {
        currentTick = currentTick++ % 60;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }
}
