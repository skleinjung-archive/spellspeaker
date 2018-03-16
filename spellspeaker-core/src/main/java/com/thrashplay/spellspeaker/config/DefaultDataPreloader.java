package com.thrashplay.spellspeaker.config;

import com.thrashplay.spellspeaker.model.CardFactory;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.repository.GameRepository;
import com.thrashplay.spellspeaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author Sean Kleinjung
 */
@Component
public class DefaultDataPreloader {
    private CardFactory cardFactory;
    private GameRepository gameRepository;
    private UserRepository userRepository;

    @Autowired
    public DefaultDataPreloader(CardFactory cardFactory, GameRepository gameRepository, UserRepository userRepository) {
        Assert.notNull(cardFactory, "cardFactory cannot be null");
        Assert.notNull(gameRepository, "gameRepository cannot be null");
        Assert.notNull(userRepository, "userRepository cannot be null");

        this.cardFactory = cardFactory;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void preloadData() {
        if (gameRepository.findOne(101L) == null) {
            SpellspeakerGame game = new SpellspeakerGame(new DefaultGameRules(), cardFactory);
            game.setId(101);
            gameRepository.save(game);
        }

        if (userRepository.findOne(1L) == null) {
            User user = new User();
            user.setId(1);
            user.setUsername("blue");
            user.setPassword("password");
            userRepository.save(user);
        }

        if (userRepository.findOne(2L) == null) {
            User user = new User();
            user.setId(2);
            user.setUsername("red");
            user.setPassword("password");
            userRepository.save(user);
        }
    }
}
