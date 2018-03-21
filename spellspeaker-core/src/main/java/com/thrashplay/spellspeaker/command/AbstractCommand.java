package com.thrashplay.spellspeaker.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.model.state.StateChange;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
              include = JsonTypeInfo.As.PROPERTY,
              property = "action")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayCardFromHand.class, name = "PlayCardFromHand"),
        @JsonSubTypes.Type(value = DiscardCardFromHand.class, name = "DiscardCardFromHand"),
        @JsonSubTypes.Type(value = SelectCardFromMarket.class, name = "SelectCardFromMarket"),
        @JsonSubTypes.Type(value = SubmitUserInput.class, name = "SubmitUserInput"),
        @JsonSubTypes.Type(value = CompleteRitual.class, name = "CompleteRitual")
})
public abstract class AbstractCommand {
    public abstract List<StateChange> execute(User currentUser, SpellspeakerGame game);
}
