package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.Card;

/**
 * @author Sean Kleinjung
 */
public class CardView {
    private String name;
    private int manaCost;
    private int castingTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(int castingTime) {
        this.castingTime = castingTime;
    }

    public static CardView fromCard(Card card) {
        CardView view = new CardView();
        view.name = card.getName();
        view.manaCost = card.getManaCost();
        view.castingTime = card.getCastingTime();
        return view;
    }
}
