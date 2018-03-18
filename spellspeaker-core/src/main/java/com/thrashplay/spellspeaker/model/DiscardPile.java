package com.thrashplay.spellspeaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class DiscardPile extends CardContainer {
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
