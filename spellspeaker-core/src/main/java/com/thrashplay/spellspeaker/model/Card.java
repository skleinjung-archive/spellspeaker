package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class Card {
    private String name;
    private int manaCost;
    private int castingTime;
    private int power;
    private Element element;
    private String text;

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
}
