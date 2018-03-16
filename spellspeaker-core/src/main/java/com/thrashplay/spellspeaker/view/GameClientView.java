package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.Card;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public interface GameClientView {
    long getId();
    int getNumberOfCardsInLibrary();
    int getCurrentTick();
    int getBlueNextTurnTick();
    int getRedNextTurnTick();

    List<CardView> getMarket();

    List<CardView> getHand();
}
