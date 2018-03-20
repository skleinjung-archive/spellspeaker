package com.thrashplay.spellspeaker.config;

import com.thrashplay.spellspeaker.model.Card;
import com.thrashplay.spellspeaker.model.Ritual;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class RitualConstructionRules implements RitualConstructionRule {
    private List<RitualConstructionRule> rules = new LinkedList<>();

    public RitualConstructionRules addRule(RitualConstructionRule rule) {
        rules.add(rule);
        return this;
    }

    public void validateRitualAddition(Ritual ritual, Card card) {
        for (RitualConstructionRule rule : rules) {
            rule.validateRitualAddition(ritual, card);
        }
    }
}
