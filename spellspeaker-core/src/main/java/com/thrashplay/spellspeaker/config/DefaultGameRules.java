package com.thrashplay.spellspeaker.config;


import org.springframework.stereotype.Component;

/**
 * @author Sean Kleinjung
 */
@Component
public class DefaultGameRules implements GameRules {
    @Override
    public int getMaximumHealth() {
        return 40;
    }

    @Override
    public int getMaximumMana() {
        return 30;
    }

    @Override
    public int getTicksPerPhase() {
        return 30;
    }

    @Override
    public int getMarketSize() {
        return 5;
    }

    @Override
    public int getInitialHandSize() {
        return 5;
    }
}
