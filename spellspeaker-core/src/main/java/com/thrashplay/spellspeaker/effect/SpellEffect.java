package com.thrashplay.spellspeaker.effect;

import com.thrashplay.spellspeaker.model.Player;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;

/**
 * @author Sean Kleinjung
 */
public interface SpellEffect {
    void execute(SpellspeakerGame game, Player castingPlayer);
}
