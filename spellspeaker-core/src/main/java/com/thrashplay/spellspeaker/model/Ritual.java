package com.thrashplay.spellspeaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Ritual extends CardContainer {
    public String getEffect() {
        return "None";
    }

    public Element getElement() {
        for (Card card : cards) {
            if (Element.None != card.getElement()) {
                return card.getElement();
            }
        }
        return Element.None;
    }

    public int getPower() {
        int power = 0;
        for (Card card : cards) {
            power += card.getPower();
        }
        return power;
    }
}
