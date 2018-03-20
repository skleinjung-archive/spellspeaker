package com.thrashplay.spellspeaker.effect;

import com.thrashplay.spellspeaker.effect.inject.CardParameterPropertyProvider;
import com.thrashplay.spellspeaker.effect.inject.MapBackedPropertyProvider;
import com.thrashplay.spellspeaker.effect.inject.PropertyInjector;
import com.thrashplay.spellspeaker.effect.inject.RulesPropertyProvider;
import com.thrashplay.spellspeaker.model.Card;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.repository.json.CardConfigurationException;
import org.springframework.stereotype.Component;

/**
 * @author Sean Kleinjung
 */
@Component
public class SpellEffectExecutor {
    public void execute(Card spellCard, SpellspeakerGame game, String parameterValue) {
        try {
            SpellEffect effect = spellCard.getEffectClass().newInstance();

            MapBackedPropertyProvider defaultProperties = new MapBackedPropertyProvider();
            defaultProperties.addPropertyValue("game", game);
            defaultProperties.addPropertyValue("rules", game.getRules());
            defaultProperties.addPropertyValue("library", game.getLibrary());
            defaultProperties.addPropertyValue("activePlayer", game.getActivePlayer());
            defaultProperties.addPropertyValue("opponent", game.getNonActivePlayer());

            PropertyInjector injector = new PropertyInjector();
            injector.addPropertyProvider(defaultProperties);
            injector.addPropertyProvider(new RulesPropertyProvider(game.getRules()));
            if (spellCard.getParameter() != null) {
                injector.addPropertyProvider(new CardParameterPropertyProvider(spellCard.getParameter(), parameterValue));
            }

            injector.inject(effect);

            effect.execute();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CardConfigurationException("Failed to execute spell '" + spellCard.getName() + ": " + e.toString(), e);
        }
    }
}
