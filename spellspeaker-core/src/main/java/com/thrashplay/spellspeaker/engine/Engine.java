package com.thrashplay.spellspeaker.engine;

import com.thrashplay.spellspeaker.model.GameState;
import com.thrashplay.spellspeaker.config.GameRules;

/**
 * @author Sean Kleinjung
 */
public class Engine {
    private GameRules rules;
    private GameState gameState;

    public Engine(GameRules rules) {
        this.rules = rules;
        this.gameState = new GameState();
    }
}
