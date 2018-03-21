package com.thrashplay.spellspeaker.command;

import com.thrashplay.spellspeaker.model.InputRequest;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.model.state.StateChange;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class DiscardCardFromHand extends AbstractCommand {
    private String card;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public List<StateChange> execute(User currentUser, SpellspeakerGame game) {
        return game.provideInput(currentUser, InputRequest.InputRequestType.SelectCardToDiscard, card);
    }
}
