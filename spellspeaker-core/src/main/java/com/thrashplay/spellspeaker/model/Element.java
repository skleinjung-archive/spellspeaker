package com.thrashplay.spellspeaker.model;

import javafx.scene.effect.Light;

/**
 * @author Sean Kleinjung
 */
public enum Element {
    None,
    Ice,
    Fire,
    Lightning;

    public static Element fromName(String element) {
        if ("Ice".equals(element)) {
            return Ice;
        }
        if ("Fire".equals(element)) {
            return Fire;
        }
        if ("Lightning".equals(element)) {
            return Lightning;
        }
        return None;
    }
}
