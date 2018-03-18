package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.config.CardConfiguration;
import com.thrashplay.spellspeaker.repository.CardConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
@Component
public class CardFactory {
    private CardConfigurationRepository cardConfigurationRepository;

    @Autowired
    public CardFactory(CardConfigurationRepository cardConfigurationRepository) {
        this.cardConfigurationRepository = cardConfigurationRepository;
    }

    public List<Card> createCards() {
        List<Card> cards = new LinkedList<>();
        for (CardConfiguration cardConfiguration : cardConfigurationRepository.findAll()) {
            for (int i = 0; i < cardConfiguration.getQuantity(); i++) {
                Card card = new Card();
                card.setName(cardConfiguration.getName());
                card.setManaCost(cardConfiguration.getManaCost());
                card.setCastingTime(cardConfiguration.getCastingTime());
                cards.add(card);
            }
        }

        return cards;
    }
}