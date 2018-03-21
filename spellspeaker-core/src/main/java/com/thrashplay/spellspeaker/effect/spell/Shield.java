package com.thrashplay.spellspeaker.effect.spell;

import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Element;
import com.thrashplay.spellspeaker.model.Player;

/**
 * @author Sean Kleinjung
 */
public class Shield implements SpellEffect {
    private Player activePlayer;
    private Element element;
    private int power;

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public void execute() {
        com.thrashplay.spellspeaker.model.Shield shield = activePlayer.getShield(element);
        if (shield != null) {
            int newValue = shield.getStrength() + power + activePlayer.getPowerDeck().draw();
            if (newValue < 0) {
                newValue = 0;
            }
            shield.setStrength(newValue);
        }
    }
}
