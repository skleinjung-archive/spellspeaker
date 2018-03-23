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

    int getMaximumHandSize();

    int getMaximumAfflictions();

    RitualConstructionRules getRitualConstructionRules();

    int getChillCastingTimeIncrease();

    int getBurningDamage();

    int getShockManaCostIncrease();

    /**
     * Number of chill stacks to clear each phase, -1 == all of them
     */
    int getChillDecayRate();

    /**
     * Number of burning stacks to clear each phase, -1 == all of them
     */
    int getBurningDecayRate();

    /**
     * Number of shock stacks to clear each phase, -1 == all of them
     */
    int getShockDecayRate();
}
