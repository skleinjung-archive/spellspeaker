package com.thrashplay.spellspeaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class CardContainer {
    protected List<Card> cards = new ArrayList<>(300);

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
