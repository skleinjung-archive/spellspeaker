package com.thrashplay.spellspeaker.config;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public interface GameRules {
    int getMaximumHealth();

    int getMaximumMana();

    int getTicksPerPhase();

    int getMarketSize();

    int getInitialHandSize();

    RitualConstructionRules getRitualConstructionRules();
}
