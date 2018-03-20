package com.thrashplay.spellspeaker.repository;

import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.model.User;

/**
 * @author Sean Kleinjung
 */
public interface GameRepository extends Repository<SpellspeakerGame, Long> {
    Long createNewGame(User blueUser, User redUser);
}
