package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.config.CardConfiguration;
import com.thrashplay.spellspeaker.repository.CardConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public List<Card> createBaseCards() {
        return createCards(cardConfigurationRepository.findAllBaseCards(), true);
    }

    public List<Card> createLibraryCards() {
        return createCards(cardConfigurationRepository.findAllLibraryCards(), false);
    }

    private List<Card> createCards(List<CardConfiguration> cardConfigurations, boolean areBaseCards) {
        List<Card> cards = new LinkedList<>();
        for (CardConfiguration cardConfiguration : cardConfigurations) {
            for (int i = 0; i < cardConfiguration.getQuantity(); i++) {
                cards.add(createCard(cardConfiguration, areBaseCards));
            }
        }
        return cards;
    }

    private Card createCard(CardConfiguration cardConfiguration, boolean isBaseCard) {
        Card card = new Card();
        card.setName(cardConfiguration.getName());
        card.setBaseCard(isBaseCard);
        card.setType(cardConfiguration.getType());
        card.setReusable(cardConfiguration.isReusable());
        card.setManaCost(cardConfiguration.getManaCost());
        card.setCastingTime(cardConfiguration.getCastingTime());
        card.setElement(cardConfiguration.getElement());
        card.setPower(cardConfiguration.getPower());
        card.setElementStrength(cardConfiguration.getElementStrength());
        card.setIgnoreAfflictions(cardConfiguration.isIgnoreAfflictions());
        card.setText(cardConfiguration.getText());
        card.setEffectClass(cardConfiguration.getEffectClass());
        card.setParameter(cardConfiguration.getParameter());
        return card;
    }
}
