package com.thrashplay.spellspeaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Hand {
    private List<Card> cards = new ArrayList<>(300);

    public Hand() {
    }

    public List<Card> getCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }
}
