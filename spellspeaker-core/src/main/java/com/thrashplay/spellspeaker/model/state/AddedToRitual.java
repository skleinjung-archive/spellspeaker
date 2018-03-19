package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public class AddedToRitual extends StateChange {
    private String player;
    private String card;

    public AddedToRitual(String player, String card) {
        super(StateChangeType.AddedToRitual);
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
        return String.format("Added %s to %s's ritual.", card, player);
    }
}
