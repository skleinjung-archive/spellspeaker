package com.thrashplay.spellspeaker;

/**
 * @author Sean Kleinjung
 */
public class SpellspeakerException extends RuntimeException {
    public SpellspeakerException() {
    }

    public SpellspeakerException(String message) {
        super(message);
    }

    public SpellspeakerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpellspeakerException(Throwable cause) {
        super(cause);
    }
}
