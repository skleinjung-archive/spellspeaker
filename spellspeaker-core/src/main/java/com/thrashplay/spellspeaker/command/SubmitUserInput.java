package com.thrashplay.spellspeaker.command;

import com.thrashplay.spellspeaker.model.InputRequest;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.model.state.StateChange;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class SubmitUserInput extends AbstractCommand {
    private String userInput;

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public List<StateChange> execute(User currentUser, SpellspeakerGame game) {
        return game.provideInput(currentUser, InputRequest.InputRequestType.TextEntry, userInput);
    }
}
