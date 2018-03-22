package com.thrashplay.spellspeaker.effect.rune;

import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Element;
import com.thrashplay.spellspeaker.model.Player;

/**
 * @author Sean Kleinjung
 */
public class LesserAffliction implements SpellEffect {
    private Element element;
    private Player opponent;

    public void setElement(Element element) {
        this.element = element;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @Override
    public void execute() {
        opponent.addAfflictionStack(element);
    }
}
