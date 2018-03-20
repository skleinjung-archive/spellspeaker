package com.thrashplay.spellspeaker.effect.spell;

import com.thrashplay.spellspeaker.InvalidInputException;
import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Library;
import com.thrashplay.spellspeaker.model.Player;

/**
 * @author Sean Kleinjung
 */
public class Research implements SpellEffect {
    private Library library;
    private Player activePlayer;
    private int manaToSpend;

    @Override
    public void execute() {
        if (manaToSpend < 1) {
            throw new InvalidInputException("You must spend at least one mana.");
        }
        if (manaToSpend > activePlayer.getMana()) {
            throw new InvalidInputException("You do not have enough mana.");
        }

        activePlayer.setMana(activePlayer.getMana() - manaToSpend);
        for (int i = 0; i < manaToSpend; i++) {
            activePlayer.getHand().add(library.draw());
        }
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setManaToSpend(int manaToSpend) {
        this.manaToSpend = manaToSpend;
    }
}
