package com.thrashplay.spellspeaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class DiscardPile {
    private List<Card> cards = new ArrayList<>(300);

    public List<Card> getCards() {
        return cards;
    }

    public void add(Card card) {
        cards.add(card);
    }

    public void addAll(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public List<? extends Card> reshuffle() {
        List<Card> result = new ArrayList<>(cards);
        Collections.shuffle(result);
        cards.clear();
        return result;
    }
}
