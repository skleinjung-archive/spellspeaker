package com.thrashplay.spellspeaker.model.state;

import com.thrashplay.spellspeaker.model.Element;

/**
 * @author Sean Kleinjung
 */
public class AttunementChanged extends StateChange {

    private Element element;

    public AttunementChanged(Element element) {
        this.element = element;
    }

    @Override
    public String getMessage() {
        return String.format("Attunement changed to %s.", element.name());
    }
}
