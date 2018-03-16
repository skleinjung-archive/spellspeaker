package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public class Card {
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
}
