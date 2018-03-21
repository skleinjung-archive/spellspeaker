package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public class BeganCasting extends StateChange {
    private String player;
    private String card;

    public BeganCasting(String player, String card) {
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
        return String.format("%s began casting %s.", player, card);
    }
}
