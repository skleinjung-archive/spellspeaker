package com.thrashplay.spellspeaker.web.controller;

import com.thrashplay.spellspeaker.repository.GameRepository;
import com.thrashplay.spellspeaker.repository.json.JsonCardConfigurationRepository;
import com.thrashplay.spellspeaker.web.model.CardsConfigurationMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author Sean Kleinjung
 */
@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    private GameRepository gameRepository;
    private JsonCardConfigurationRepository jsonCardConfigurationRepository;

    public ConfigurationController(GameRepository gameRepository, JsonCardConfigurationRepository jsonCardConfigurationRepository) {
        Assert.notNull(gameRepository, "gameRepository cannot be null");
        this.gameRepository = gameRepository;

        Assert.notNull(jsonCardConfigurationRepository, "jsonCardConfigurationRepository cannot be null");
        this.jsonCardConfigurationRepository = jsonCardConfigurationRepository;
    }

    @GetMapping("/cards")
    public @ResponseBody
    CardsConfigurationMessage getCards() {
        return new CardsConfigurationMessage(Base64Utils.encodeToString(jsonCardConfigurationRepository.getCardsJson().getBytes()));
    }

    @PostMapping("/cards")
    public @ResponseBody
    ResponseEntity<Object> updateCards(@RequestBody CardsConfigurationMessage request) throws UnsupportedEncodingException {
        String newJson = new String(Base64Utils.decodeFromString(request.getEncodedCardsJson()));
        jsonCardConfigurationRepository.setCardsJson(newJson);
        // remove all games using old rules to prevent confusion
        gameRepository.deleteAll();
        return ResponseEntity.status(200).build();
    }
}
