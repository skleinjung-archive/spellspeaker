package com.thrashplay.spellspeaker;

/**
 * @author Sean Kleinjung
 */
public class InvalidInputException extends SpellspeakerException {
    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}
