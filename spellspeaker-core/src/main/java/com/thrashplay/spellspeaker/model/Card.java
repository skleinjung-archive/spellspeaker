package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.effect.SpellEffect;

/**
 * @author Sean Kleinjung
 */
public class Card {
    private String name;
    private CardType type;
    private boolean reusable;
    private int manaCost;
    private int castingTime;
    private int power;
    private Element element;
    private String text;
    private SpellEffect effect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public boolean isReusable() {
        return reusable;
    }

    public void setReusable(boolean reusable) {
        this.reusable = reusable;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SpellEffect getEffect() {
        return effect;
    }

    public void setEffect(SpellEffect effect) {
        this.effect = effect;
    }
}
