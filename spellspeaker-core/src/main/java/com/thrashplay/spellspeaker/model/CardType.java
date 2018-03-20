package com.thrashplay.spellspeaker.model;

/**
 * @author Sean Kleinjung
 */
public enum CardType {
    None,
    EffectRune,
    Rune,
    Spell;

    public boolean isSpell() {
        return this == Spell;
    }

    public boolean isRune() {
        return this == EffectRune || this == Rune;
    }

    public String getDisplayString() {
        switch (this) {
            case EffectRune:
                return "Effect Rune";
            default:
                return name();
        }
    }

    public static CardType fromName(String type) {
        if ("EffectRune".equals(type)) {
            return EffectRune;
        }
        if ("Rune".equals(type)) {
            return Rune;
        }
        if ("Spell".equals(type)) {
            return Spell;
        }
        return None;
    }
}
