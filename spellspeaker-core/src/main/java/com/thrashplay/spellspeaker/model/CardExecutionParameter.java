package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class CardExecutionParameter {
    public enum Type {
        None,
        String,
        Integer;

        public static Type fromName(String type) {
            if ("String".equals(type)) {
                return String;
            } else if ("Integer".equals(type)) {
                return Integer;
            } else {
                return None;
            }
        }
    }

    private String name;
    private Type type;
    private String prompt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
