package com.thrashplay.spellspeaker.effect.spell;

import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Player;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;

/**
 * @author Sean Kleinjung
 */
public class Noop implements SpellEffect {
    @Override
    public void execute(SpellspeakerGame game, Player castingPlayer) {
        // do nothing
    }
}
