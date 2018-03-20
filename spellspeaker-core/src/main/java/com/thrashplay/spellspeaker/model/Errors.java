package com.thrashplay.spellspeaker.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Errors {
    private List<String> messages = new LinkedList<>();

    public boolean hasErrors() {
        return !messages.isEmpty();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void add(String message) {
        messages.add(message);
    }
}
