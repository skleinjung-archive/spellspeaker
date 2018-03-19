package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class CardView {
    private String name;
    private String type;
    private int manaCost;
    private int castingTime;
    private String element;
    private int power;
    private String text;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
        view.type = card.getType().name();
        view.manaCost = card.getManaCost();
        view.castingTime = card.getCastingTime();
        view.element = card.getElement().name();
        view.power = card.getPower();
        view.text = card.getText();
        return view;
    }

    public static List<CardView> fromCards(List<Card> cards) {
        List<CardView> views = new ArrayList<>(cards.size());
        for (Card card : cards) {
            CardView view = CardView.fromCard(card);
            views.add(view);
        }
        return views;
    }
}
