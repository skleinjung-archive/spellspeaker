package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.config.GameRules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Market extends CardContainer {
    private GameRules rules;
    private DiscardPile discardPile;
    private Library library;

    public Market(GameRules rules, DiscardPile discardPile, Library library) {
        this.rules = rules;
        this.discardPile = discardPile;
        this.library = library;

        cards = new ArrayList<>(rules.getMarketSize());
    }

    public void refresh() {
        discardPile.addAll(cards);
        cards.clear();

        for (int i = 0; i < rules.getMarketSize(); i++) {
            cards.add(library.draw());
        }
    }
}
