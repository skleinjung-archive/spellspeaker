package com.thrashplay.spellspeaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Library {
    private List<Card> cards = new ArrayList<>(300);
    private DiscardPile discardPile;

    public Library(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public int size() {
        return cards.size();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public Card draw() {
        if (cards.size() < 1) {
            cards.addAll(discardPile.reshuffle());
        }

        // still no cards after reshuffling the discard pile
        if (cards.size() < 1) {
            throw new IllegalStateException("There are no cards left to draw.");
        }

        return cards.remove(0);
    }

//    public void drawTo(Hand hand) {
//        hand.add(draw());
//    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }
}
