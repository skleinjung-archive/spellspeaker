package com.thrashplay.spellspeaker.config.rules;

import com.thrashplay.spellspeaker.InvalidInputException;
import com.thrashplay.spellspeaker.config.RitualConstructionRule;
import com.thrashplay.spellspeaker.model.*;

/**
 * @author Sean Kleinjung
 */
public class OnlyOneElementRule implements RitualConstructionRule {
    @Override
    public void validateRitualAddition(Ritual ritual, Card card) {
        if (card.getElement() == Element.None) {
            return;
        }
        if (ritual.getElement() == Element.None) {
            return;
        }
        if (ritual.getElement() != card.getElement()) {
            throw new InvalidInputException("A ritual may only have one element type.");
        }
    }
}
