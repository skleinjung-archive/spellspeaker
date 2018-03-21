package com.thrashplay.spellspeaker.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class PowerDeck {
    private List<Integer> deck = new LinkedList<>();
    private List<Integer> discard = new LinkedList<>();

    public void addPowerCard(int modifier) {
        deck.add(modifier);
    }

    public int draw() {
        if (deck.isEmpty()) {
            reshuffle();
        }

        int result = deck.remove(0);
        discard.add(result);
        return result;
    }

    public void reshuffle() {
        deck.addAll(discard);
        discard.clear();
        Collections.shuffle(deck);
    }

    public int sizeOfDrawPile() {
        return deck.size();
    }

    public int sizeOfDiscardPile() {
        return discard.size();
    }

    public List<Integer> getUsedCards() {
        return discard;
    }
}
