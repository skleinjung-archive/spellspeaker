package com.thrashplay.spellspeaker.effect.rune;

import com.thrashplay.spellspeaker.model.Element;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class InflictResult {

    private List<Attack> attacks = new LinkedList<>();

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public static class Attack {
        private Element element;
        private int power;

        public Attack() {
        }

        public Attack(Element element, int power) {
            this.element = element;
            this.power = power;
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
    }
}
