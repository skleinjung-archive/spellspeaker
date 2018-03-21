package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public class FinishedCasting extends StateChange {
    private String player;
    private String card;

    public FinishedCasting(String player, String card) {
        this.player = player;
        this.card = card;
    }

    public String getPlayer() {
        return player;
    }

    public String getCard() {
        return card;
    }

    @Override
    public String getMessage() {
        return String.format("%s finished casting %s.", player, card);
    }
}
