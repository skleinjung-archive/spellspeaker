package com.thrashplay.spellspeaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Ritual extends CardContainer {
    public Card getEffectRune() {
        for (Card card : cards) {
            if (card.getType() == CardType.EffectRune) {
                return card;
            }
        }
        return null;
    }

    public String getEffect() {
        Card effectRune = getEffectRune();
        return effectRune != null ? effectRune.getName() : "None";
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

    public void clear(Hand hand, DiscardPile discardPile) {
        for (Card card : getCards()) {
            if (card.isBaseCard()) {
                hand.add(card);
            } else {
                discardPile.add(card);
            }
        }

        getCards().clear();
    }
}
