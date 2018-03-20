package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public class Discarded extends StateChange {
    private String player;
    private String card;

    public Discarded(String player, String card) {
        super(StateChangeType.BeganCasting);
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
        return String.format("%s discarded %s.", player, card);
    }
}
