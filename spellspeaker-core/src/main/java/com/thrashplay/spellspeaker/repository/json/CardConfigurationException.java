package com.thrashplay.spellspeaker.repository.json;

import com.thrashplay.spellspeaker.SpellspeakerException;

/**
 * @author Sean Kleinjung
 */
public class CardConfigurationException extends SpellspeakerException {
    public CardConfigurationException() {
    }

    public CardConfigurationException(String message) {
        super(message);
    }

    public CardConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardConfigurationException(Throwable cause) {
        super(cause);
    }
}
