package com.thrashplay.spellspeaker.db.inmemory;

import com.thrashplay.spellspeaker.config.DefaultGameRules;
import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.db.repository.IdGenerator;
import com.thrashplay.spellspeaker.effect.SpellEffectExecutor;
import com.thrashplay.spellspeaker.model.CardFactory;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.repository.GameRepository;
import com.thrashplay.spellspeaker.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sean Kleinjung
 */
@Repository
public class InMemoryGameRepository implements GameRepository {

    private GameRules rules;
    private RandomService randomNumberService;
    private CardFactory cardFactory;
    private SpellEffectExecutor spellEffectExecutor;
    private IdGenerator idGenerator;
    private Map<Long, SpellspeakerGame> games = new HashMap<>();

    @Autowired
    public InMemoryGameRepository(IdGenerator idGenerator, GameRules rules, RandomService randomNumberService, CardFactory cardFactory, SpellEffectExecutor spellEffectExecutor) {
        Assert.notNull(idGenerator, "idGenerator cannot be null");
        this.idGenerator = idGenerator;

        Assert.notNull(rules, "rules cannot be null");
        this.rules = rules;

        Assert.notNull(randomNumberService, "randomNumberService cannot be null");
        this.randomNumberService = randomNumberService;

        Assert.notNull(cardFactory, "cardFactory cannot be null");
        this.cardFactory = cardFactory;

        Assert.notNull(spellEffectExecutor, "spellEffectExecutor cannot be null");
        this.spellEffectExecutor = spellEffectExecutor;
    }

    @Override
    public Long createNewGame(User blueUser, User redUser) {
        SpellspeakerGame game = new SpellspeakerGame(rules, randomNumberService, cardFactory, spellEffectExecutor, blueUser, redUser);
        game.setId(idGenerator.getId(SpellspeakerGame.class));
        save(game);
        return game.getId();
    }

    @Override
    public void deleteAll() {
        games.clear();
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
