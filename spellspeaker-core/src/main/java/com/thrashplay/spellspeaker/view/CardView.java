package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.Card;

/**
 * @author Sean Kleinjung
 */
public class CardView {
    private String name;
    private int manaCost;
    private int castingTime;
    private String element;
    private int power;
    private String text;

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getCastingTime() {
        return castingTime;
    }

    public String getElement() {
        return element;
    }

    public int getPower() {
        return power;
    }

    public String getText() {
        return text;
    }

    public static CardView fromCard(Card card) {
        CardView view = new CardView();
        view.name = card.getName();
        view.manaCost = card.getManaCost();
        view.castingTime = card.getCastingTime();
        view.element = card.getElement().name();
        view.power = card.getPower();
        view.text = card.getText();
        return view;
    }
}
