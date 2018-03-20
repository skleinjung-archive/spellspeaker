package com.thrashplay.spellspeaker.web.model;

/**
 * @author Sean Kleinjung
 */
public class CreateGameResponse {
    private long newGameId;

    public CreateGameResponse(long newGameId) {
        this.newGameId = newGameId;
    }

    public long getNewGameId() {
        return newGameId;
    }
}
