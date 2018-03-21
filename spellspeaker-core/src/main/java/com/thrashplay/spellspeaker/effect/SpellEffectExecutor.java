package com.thrashplay.spellspeaker.effect;

import com.thrashplay.spellspeaker.effect.inject.CardParameterPropertyProvider;
import com.thrashplay.spellspeaker.effect.inject.MapBackedPropertyProvider;
import com.thrashplay.spellspeaker.effect.inject.PropertyInjector;
import com.thrashplay.spellspeaker.effect.inject.RulesPropertyProvider;
import com.thrashplay.spellspeaker.model.Card;
import com.thrashplay.spellspeaker.model.Ritual;
import com.thrashplay.spellspeaker.model.SpellspeakerGame;
import com.thrashplay.spellspeaker.repository.json.CardConfigurationException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

/**
 * @author Sean Kleinjung
 */
@Component
public class SpellEffectExecutor {
    public void execute(Card spellCard, SpellspeakerGame game, String parameterValue) {
        try {
            SpellEffect effect = spellCard.getEffectClass().newInstance();

            MapBackedPropertyProvider spellPropertyProvider = new MapBackedPropertyProvider();
            spellPropertyProvider.addPropertyValue("element", spellCard.getElement());
            spellPropertyProvider.addPropertyValue("power", spellCard.getPower());

            PropertyInjector injector = createDefaultPropertyInjector(game);
            injector.addPropertyProvider(spellPropertyProvider);
            if (spellCard.getParameter() != null) {
                injector.addPropertyProvider(new CardParameterPropertyProvider(spellCard.getParameter(), parameterValue));
            }
            injector.inject(effect);

            effect.execute();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CardConfigurationException("Failed to execute spell '" + spellCard.getName() + ": " + e.toString(), e);
        }
    }

    public void execute(Ritual ritual, SpellspeakerGame game) {
        try {
            SpellEffect effect = ritual.getEffectRune().getEffectClass().newInstance();

            MapBackedPropertyProvider ritualPropertyProvider = new MapBackedPropertyProvider();
            ritualPropertyProvider.addPropertyValue("ritual", ritual);
            ritualPropertyProvider.addPropertyValue("element", ritual.getElement());
            ritualPropertyProvider.addPropertyValue("power", ritual.getPower());
            ritualPropertyProvider.addPropertyValue("elementStrength", ritual.getElementStrength());

            PropertyInjector injector = createDefaultPropertyInjector(game);
            injector.addPropertyProvider(ritualPropertyProvider);
            injector.inject(effect);

            effect.execute();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CardConfigurationException("Failed to execute ritual with effect rune '" + ritual.getEffectRune().getName() + ": " + e.toString(), e);
        }
    }

    private PropertyInjector createDefaultPropertyInjector(SpellspeakerGame game) {
        MapBackedPropertyProvider defaultProperties = new MapBackedPropertyProvider();
        defaultProperties.addPropertyValue("game", game);
        defaultProperties.addPropertyValue("attunement", game.getAttunement());
        defaultProperties.addPropertyValue("rules", game.getRules());
        defaultProperties.addPropertyValue("library", game.getLibrary());
        defaultProperties.addPropertyValue("market", game.getMarket());
        defaultProperties.addPropertyValue("discardPile", game.getDiscardPile());
        defaultProperties.addPropertyValue("activePlayer", game.getActivePlayer());
        defaultProperties.addPropertyValue("activeCard", game.getActivePlayer().getActiveCard());
        defaultProperties.addPropertyValue("opponent", game.getNonActivePlayer());

        PropertyInjector injector = new PropertyInjector();
        injector.addPropertyProvider(defaultProperties);
        injector.addPropertyProvider(new RulesPropertyProvider(game.getRules()));
        return injector;
    }
}
