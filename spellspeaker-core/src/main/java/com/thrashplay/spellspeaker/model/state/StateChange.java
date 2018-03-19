package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public abstract class StateChange {
    private StateChangeType type;

    protected StateChange(StateChangeType type) {
        this.type = type;
    }

    public StateChangeType getType() {
        return type;
    }

    public abstract String getMessage();
}
