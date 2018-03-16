package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.config.GameRules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Market {
    private List<Card> cards;

    private GameRules rules;
    private DiscardPile discardPile;
    private Library library;

    public Market(GameRules rules, DiscardPile discardPile, Library library) {
        this.rules = rules;
        this.discardPile = discardPile;
        this.library = library;

        cards = new ArrayList<>(rules.getMarketSize());
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public int size() {
        return cards.size();
    }

    public void refresh() {
        discardPile.addAll(cards);
        cards.clear();

        for (int i = 0; i < rules.getMarketSize(); i++) {
            cards.add(library.draw());
        }
    }

    public void buy(Card card) {
        cards.remove(card);
    }

    //    public void drawTo(Hand hand) {
//        hand.add(draw());
//    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }
}
