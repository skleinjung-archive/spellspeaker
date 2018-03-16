package com.thrashplay.spellspeaker.db.inmemory;

import com.thrashplay.spellspeaker.config.DefaultGameRules;
import com.thrashplay.spellspeaker.model.CardFactory;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.repository.GameRepository;
import com.thrashplay.spellspeaker.model.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sean Kleinjung
 */
@Repository
public class InMemoryGameRepository implements GameRepository {

    private Map<Long, SpellspeakerGame> games = new HashMap<>();

    public InMemoryGameRepository() {
    }

    public SpellspeakerGame findOne(Long id) {
        return games.get(id);
    }

    public List<SpellspeakerGame> findAll() {
        return new ArrayList<>(games.values());
    }

    @Override
    public <S extends SpellspeakerGame> S save(S entity) {
        games.put(entity.getId(), entity);
        return entity;
    }
}
