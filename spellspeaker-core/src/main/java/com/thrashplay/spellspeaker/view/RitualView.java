package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.Ritual;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class RitualView {
    private List<CardView> cards;
    private String effect;
    private String element;
    private int power;
    private int elementStrength;

    public RitualView(Ritual ritual) {
        cards = CardView.fromCards(ritual.getCards());
        effect = ritual.getEffect();
        element = ritual.getElement().name();
        power = ritual.getPower();
        elementStrength = ritual.getElementStrength();
    }

    public List<CardView> getCards() {
        return cards;
    }

    public String getEffect() {
        return effect;
    }

    public String getElement() {
        return element;
    }

    public int getPower() {
        return power;
    }

    public int getElementStrength() {
        return elementStrength;
    }
}
