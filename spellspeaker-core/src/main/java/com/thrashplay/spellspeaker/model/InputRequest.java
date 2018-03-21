package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class InputRequest {

    private InputType type;
    private String prompt;

    public InputRequest(InputType type) {
        this.type = type;
    }

    public InputRequest(InputType type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
