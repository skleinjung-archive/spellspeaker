package com.thrashplay.spellspeaker.web.model;

import com.thrashplay.spellspeaker.model.state.StateChange;
import com.thrashplay.spellspeaker.view.GameView;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class ActionResult {
    private GameView game;
    private List<StateChange> stateChanges;

    public ActionResult(GameView game, List<StateChange> stateChanges) {
        this.game = game;
        this.stateChanges = stateChanges;
    }

    public GameView getGame() {
        return game;
    }

    public List<StateChange> getStateChanges() {
        return stateChanges;
    }
}
