package com.thrashplay.spellspeaker.effect.spell;

import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Player;

/**
 * @author Sean Kleinjung
 */
public class Rejuvenate implements SpellEffect {
    private Player activePlayer;
    private int maximumMana;

    @Override
    public void execute() {
        activePlayer.setMana(maximumMana);
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setMaximumMana(int maximumMana) {
        this.maximumMana = maximumMana;
    }
}
