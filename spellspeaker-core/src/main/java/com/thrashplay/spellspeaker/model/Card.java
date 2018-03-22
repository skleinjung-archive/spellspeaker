package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.effect.SpellEffect;

/**
 * @author Sean Kleinjung
 */
public class Card {
    private String name;
    private boolean baseCard;
    private CardType type;
    private boolean reusable;
    private int manaCost;
    private int castingTime;
    private int power;
    private Element element;
    private int elementStrength;
    private boolean ignoreAfflictions;
    private String text;
    private Class<? extends SpellEffect> effectClass;
    private CardExecutionParameter parameter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBaseCard() {
        return baseCard;
    }

    public void setBaseCard(boolean baseCard) {
        this.baseCard = baseCard;
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

    public int getElementStrength() {
        return elementStrength;
    }

    public void setElementStrength(int elementStrength) {
        this.elementStrength = elementStrength;
    }

    public boolean isIgnoreAfflictions() {
        return ignoreAfflictions;
    }

    public void setIgnoreAfflictions(boolean ignoreAfflictions) {
        this.ignoreAfflictions = ignoreAfflictions;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Class<? extends SpellEffect> getEffectClass() {
        return effectClass;
    }

    public void setEffectClass(Class<? extends SpellEffect> effectClass) {
        this.effectClass = effectClass;
    }

    public CardExecutionParameter getParameter() {
        return parameter;
    }

    public void setParameter(CardExecutionParameter parameter) {
        this.parameter = parameter;
    }

    public boolean requiresInput() {
        return parameter != null;
    }
}
