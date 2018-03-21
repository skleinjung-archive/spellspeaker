package com.thrashplay.spellspeaker.effect.spell;

import com.thrashplay.spellspeaker.InvalidInputException;
import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.model.Card;
import com.thrashplay.spellspeaker.model.Market;
import com.thrashplay.spellspeaker.model.Player;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;

/**
 * @author Sean Kleinjung
 */
public class Purchase implements SpellEffect {
    private Market market;
    private Player activePlayer;
    private String cardName;

    public void setMarket(Market market) {
        this.market = market;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public void execute() {
        Card card = null;
        for (Card currentCard : market.getCards()) {
            if (currentCard.getName().equals(cardName)) {
                card = currentCard;
                break;
            }
        }

        if (card == null) {
            throw new InvalidInputException("Card does not exist in the market.");
        }

        market.getCards().remove(card);
        activePlayer.getHand().add(card);
    }
}
