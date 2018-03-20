package com.thrashplay.spellspeaker.web.model;

/**
 * @author Sean Kleinjung
 */
public class CardsConfigurationMessage {
    private String encodedCardsJson;

    public CardsConfigurationMessage() {
    }

    public CardsConfigurationMessage(String encodedCardsJson) {
        this.encodedCardsJson = encodedCardsJson;
    }

    public String getEncodedCardsJson() {
        return encodedCardsJson;
    }
}
