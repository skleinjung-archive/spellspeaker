package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public abstract class StateChange {
    public String getType() {
        return getClass().getName();
    }

    public abstract String getMessage();
}
