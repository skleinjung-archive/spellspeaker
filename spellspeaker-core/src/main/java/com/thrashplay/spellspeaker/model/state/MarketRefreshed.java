package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public class MarketRefreshed extends StateChange {
    @Override
    public String getMessage() {
        return "The market was refreshed.";
    }
}
