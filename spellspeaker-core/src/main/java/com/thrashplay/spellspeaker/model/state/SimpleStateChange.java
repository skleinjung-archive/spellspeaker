package com.thrashplay.spellspeaker.model.state;

/**
 * @author Sean Kleinjung
 */
public class SimpleStateChange extends StateChange {
    private String type;
    private String message;

    public SimpleStateChange(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
