package com.thrashplay.spellspeaker.view;

import com.thrashplay.spellspeaker.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class CardView {
    private String name;
    private boolean baseCard;
    private String type;
    private boolean reusable;
    private int manaCost;
    private int castingTime;
    private String element;
    private int power;
    private int elementStrength;
    private String text;

    public String getName() {
        return name;
    }

    public boolean isBaseCard() {
        return baseCard;
    }

    public String getType() {
        return type;
    }

    public boolean isReusable() {
        return reusable;
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

    public int getElementStrength() {
        return elementStrength;
    }

    public String getText() {
        return text;
    }

    public static CardView fromCard(Card card) {
        CardView view = new CardView();
        view.name = card.getName();
        view.baseCard = card.isBaseCard();
        view.type = card.getType().getDisplayString();
        view.reusable = card.isReusable();
        view.manaCost = card.getManaCost();
        view.castingTime = card.getCastingTime();
        view.element = card.getElement().name();
        view.power = card.getPower();
        view.elementStrength = card.getElementStrength();
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
