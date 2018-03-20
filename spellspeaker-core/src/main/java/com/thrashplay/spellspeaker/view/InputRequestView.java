package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.InputRequest;

/**
 * @author Sean Kleinjung
 */
public class InputRequestView {
    private String type;
    private String prompt;

    public InputRequestView(InputRequest inputRequest) {
        type = inputRequest.getType().name();
        prompt = inputRequest.getPrompt();
    }

    public String getType() {
        return type;
    }

    public String getPrompt() {
        return prompt;
    }
}
