package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class InputRequest {
    public enum InputRequestType {
        PlayCardFromHand,
        TextEntry
    }

    private InputRequestType type;
    private String prompt;

    public InputRequest(InputRequestType type) {
        this.type = type;
    }

    public InputRequest(InputRequestType type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public InputRequestType getType() {
        return type;
    }

    public void setType(InputRequestType type) {
        this.type = type;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
