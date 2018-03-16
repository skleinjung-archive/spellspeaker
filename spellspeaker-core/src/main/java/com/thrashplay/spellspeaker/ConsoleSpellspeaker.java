package com.thrashplay.spellspeaker;

import com.thrashplay.spellspeaker.engine.Engine;
import com.thrashplay.spellspeaker.config.DefaultGameRules;

/**
 * @author Sean Kleinjung
 */
public class ConsoleSpellspeaker {
    public static void main(String[] args) {
        Engine engine = new Engine(new DefaultGameRules());
//        while (engine.step()) {
//
//        }
    }
}
