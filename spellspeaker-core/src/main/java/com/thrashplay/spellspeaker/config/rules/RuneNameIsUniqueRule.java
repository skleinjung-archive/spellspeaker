package com.thrashplay.spellspeaker.config.rules;

import com.thrashplay.spellspeaker.InvalidInputException;
import com.thrashplay.spellspeaker.config.RitualConstructionRule;
import com.thrashplay.spellspeaker.model.Card;
import com.thrashplay.spellspeaker.model.Errors;
import com.thrashplay.spellspeaker.model.Ritual;

/**
 * @author Sean Kleinjung
 */
public class RuneNameIsUniqueRule implements RitualConstructionRule {
    @Override
    public void validateRitualAddition(Ritual ritual, Card card) {
        for (Card ritualCard : ritual.getCards()) {
            if (ritualCard.getName().equals(card.getName())) {
                throw new InvalidInputException("A ritual may only have one copy of each rune.");
            }
        }
    }
}
