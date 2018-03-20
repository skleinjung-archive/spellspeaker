package com.thrashplay.spellspeaker.effect.inject;

import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.repository.json.CardConfigurationException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Sean Kleinjung
 */
public class RulesPropertyProvider implements PropertyProvider {
    private GameRules rules;

    public RulesPropertyProvider(GameRules rules) {
        this.rules = rules;
    }

    @Override
    public Object getProperty(String propertyName) {
        if (PropertyUtils.isReadable(rules, propertyName)) {
            try {
                return PropertyUtils.getProperty(rules, propertyName);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new CardConfigurationException("Cannot inject rules property '" + propertyName + "': " + e.toString(), e);
            }
        }
        return null;
    }
}
