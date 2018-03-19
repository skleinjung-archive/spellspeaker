// Copyright 2018 Sean Kleinjung
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.thrashplay.spellspeaker.web.controller;

import com.thrashplay.spellspeaker.model.*;
import com.thrashplay.spellspeaker.model.state.StateChange;
import com.thrashplay.spellspeaker.repository.GameRepository;
import com.thrashplay.spellspeaker.view.GameView;
import com.thrashplay.spellspeaker.web.model.ActionParameters;
import com.thrashplay.spellspeaker.web.model.ActionResult;
import com.thrashplay.spellspeaker.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository) {
        Assert.notNull(gameRepository, "Must supply a game repository");
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public @ResponseBody
    List<GameView> findAll(/*@CurrentUser User user*/) {
        List<SpellspeakerGame> games = gameRepository.findAll();
        List<GameView> results = new ArrayList<>(games.size());
        for (SpellspeakerGame game : games) {
//            results.add(game.toClientView(user.getId()));
            results.add(new GameView(null, game));
        }
        return results;
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Object> findById(@CurrentUser User user, @PathVariable long id) {
        SpellspeakerGame game = gameRepository.findOne(id);
        if (game != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new GameView(user, game));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{gameId}/actions")
    public @ResponseBody
    ResponseEntity<Object> executeAction(@CurrentUser User user, @PathVariable long gameId, @RequestBody ActionParameters action) {
        SpellspeakerGame game = gameRepository.findOne(gameId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<StateChange> stateChanges = game.playFromHand(user.getId(), action.getCard());
        return ResponseEntity.status(HttpStatus.OK).body(new ActionResult(new GameView(user, game), stateChanges));
    }
}
