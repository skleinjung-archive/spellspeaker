package com.thrashplay.spellspeaker.config;

import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.CardExecutionParameter;
import com.thrashplay.spellspeaker.model.CardType;
import com.thrashplay.spellspeaker.model.Element;

/**
 * @author Sean Kleinjung
 */
public class CardConfiguration {
    private long id;
    private String name;
    private CardType type;
    private boolean reusable;
    private int manaCost;
    private int castingTime;
    private Element element;
    private int power;
    private String text;
    private Class<? extends SpellEffect> effectClass;
    private CardExecutionParameter parameter;
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
