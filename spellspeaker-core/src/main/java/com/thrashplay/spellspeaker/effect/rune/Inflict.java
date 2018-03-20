package com.thrashplay.spellspeaker.effect.rune;

import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Player;

/**
 * @author Sean Kleinjung
 */
public class Inflict implements SpellEffect {
    private Player opponent;
    private int power;

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public void execute() {
        opponent.setHealth(opponent.getHealth() - power);
    }
}
