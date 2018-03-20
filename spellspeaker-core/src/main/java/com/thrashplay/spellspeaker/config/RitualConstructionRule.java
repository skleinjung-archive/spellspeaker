package com.thrashplay.spellspeaker.config;

import com.thrashplay.spellspeaker.model.Card;
import com.thrashplay.spellspeaker.model.Ritual;

/**
 * @author Sean Kleinjung
 */
public interface RitualConstructionRule {
    /**
     * Returns true if this rule allows the specified card to be added to the ritual, otherwise returns
     * false.
     */
    void validateRitualAddition(Ritual ritual, Card card);
}
