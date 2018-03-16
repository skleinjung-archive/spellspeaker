package com.thrashplay.spellspeaker.web.controller;

import com.thrashplay.spellspeaker.config.DefaultGameRules;
import com.thrashplay.spellspeaker.config.GameRules;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sean Kleinjung
 */
@RestController
@RequestMapping("/api/rules")
public class RulesController {
    @GetMapping
    public @ResponseBody
    GameRules get() {
        return new DefaultGameRules();
    }
}
