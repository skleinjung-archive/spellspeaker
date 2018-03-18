package com.thrashplay.spellspeaker.engine;

import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;

/**
 * @author Sean Kleinjung
 */
public class Engine {
    private GameRules rules;

    public Engine(GameRules rules, SpellspeakerGame spellspeakerGame) {
        this.rules = rules;
    }
}
