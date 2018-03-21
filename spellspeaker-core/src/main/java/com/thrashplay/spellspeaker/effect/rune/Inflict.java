package com.thrashplay.spellspeaker.effect.rune;

import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Element;
import com.thrashplay.spellspeaker.model.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class Inflict implements SpellEffect {
    private Player activePlayer;
    private Player opponent;
    private Element attunement;
    private Element element;
    private int power;
    private int elementStrength;

    private List<InflictResultModifier> modifiers = new LinkedList<>();

    public Inflict() {
        modifiers.add(new IceAttunementModifier());
        modifiers.add(new FireAttunementModifier());
        modifiers.add(new LightningAttunementModifier());
        modifiers.add(new PowerCardModifier());
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void setAttunement(Element attunement) {
        this.attunement = attunement;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setElementStrength(int elementStrength) {
        this.elementStrength = elementStrength;
    }

    @Override
    public void execute() {
        InflictResult result = new InflictResult();
        result.getAttacks().add(new InflictResult.Attack(element, power));

        for (InflictResultModifier modifier : modifiers) {
            modifier.modifyResult(result);
        }

        for (InflictResult.Attack attack : result.getAttacks()) {
            if (attack.getPower() > 0) {
                opponent.resolveAttack(attack.getElement(), attack.getPower());
            }
        }
    }

    private boolean isRitualInAttunementTo(Element elementToCheck) {
        return attunement == elementToCheck && element == elementToCheck;
    }

    public interface InflictResultModifier {
        void modifyResult(InflictResult result);
    }

    public class IceAttunementModifier implements InflictResultModifier {
        @Override
        public void modifyResult(InflictResult result) {
            if (isRitualInAttunementTo(Element.Ice)) {
                for (InflictResult.Attack attack : result.getAttacks()) {
                    attack.setPower(attack.getPower() + elementStrength);
                }
            }
        }
    }

    public class FireAttunementModifier implements InflictResultModifier {
        @Override
        public void modifyResult(InflictResult result) {
            if (isRitualInAttunementTo(Element.Fire)) {
                for (InflictResult.Attack attack : result.getAttacks()) {
                    attack.setPower(attack.getPower() + elementStrength);
                }
            }
        }
    }

    public class LightningAttunementModifier implements InflictResultModifier {
        @Override
        public void modifyResult(InflictResult result) {
            if (isRitualInAttunementTo(Element.Lightning)) {
                for (InflictResult.Attack attack : result.getAttacks()) {
                    attack.setPower(attack.getPower() + elementStrength);
                }
            }
        }
    }

    public class PowerCardModifier implements InflictResultModifier {
        @Override
        public void modifyResult(InflictResult result) {
            for (InflictResult.Attack attack : result.getAttacks()) {
                attack.setPower(attack.getPower() + activePlayer.getPowerDeck().draw());
            }
        }
    }
}
