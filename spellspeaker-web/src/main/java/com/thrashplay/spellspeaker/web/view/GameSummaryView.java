package com.thrashplay.spellspeaker.web.view;

import com.thrashplay.spellspeaker.model.SpellspeakerGame;

/**
 * @author Sean Kleinjung
 */
public class GameSummaryView {
    private long id;
    private String bluePlayerName;
    private String redPlayerName;
    private int bluePlayerHealth;
    private int redPlayerHealth;

    public GameSummaryView(SpellspeakerGame game) {
        this.id = game.getId();
        this.bluePlayerName = game.getBluePlayer().getUser().getUsername();
        this.redPlayerName = game.getRedPlayer().getUser().getUsername();
        this.bluePlayerHealth = game.getBluePlayer().getHealth();
        this.redPlayerHealth = game.getRedPlayer().getHealth();
    }

    public long getId() {
        return id;
    }

    public String getBluePlayerName() {
        return bluePlayerName;
    }

    public String getRedPlayerName() {
        return redPlayerName;
    }

    public int getBluePlayerHealth() {
        return bluePlayerHealth;
    }

    public int getRedPlayerHealth() {
        return redPlayerHealth;
    }
}
