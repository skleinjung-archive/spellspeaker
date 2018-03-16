package com.thrashplay.spellspeaker.config;

/**
 * @author Sean Kleinjung
 */
public interface GameRules {
    int getTicksPerPhase();

    int getMarketSize();

    int getInitialHandSize();
}
