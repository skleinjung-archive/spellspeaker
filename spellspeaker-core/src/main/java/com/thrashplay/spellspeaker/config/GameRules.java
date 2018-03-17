package com.thrashplay.spellspeaker.config;

/**
 * @author Sean Kleinjung
 */
public interface GameRules {
    int getMaximumHealth();

    int getMaximumMana();

    int getTicksPerPhase();

    int getMarketSize();

    int getInitialHandSize();
}
