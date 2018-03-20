package com.thrashplay.spellspeaker.effect.inject;

import com.thrashplay.spellspeaker.repository.json.CardConfigurationException;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class PropertyInjector {
    private List<PropertyProvider> propertyProviders = new LinkedList<>();

    public void addPropertyProvider(PropertyProvider provider) {
        this.propertyProviders.add(provider);
    }

    public void inject(Object target) {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (shouldSkipProperty(propertyName)) {
                continue;
            }

            Object value = getPropertyValue(propertyName);
            if (value == null) {
                throw new CardConfigurationException("Cannot inject SpellEffect parameter '" + propertyName + "'. No provider exists for that name.");
            }

            try {
                PropertyUtils.setProperty(target, propertyName, value);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new CardConfigurationException("Failed to inject SpellEffect parameter '" + propertyName + " with value: " + value, e);
            }
        }
    }

    private boolean shouldSkipProperty(String propertyName) {
        return "class".equals(propertyName);
    }

    private Object getPropertyValue(String propertyName) {
        for (PropertyProvider provider : propertyProviders) {
            Object value = provider.getProperty(propertyName);
            if (value != null) {
                return value;
            }
        }

        return null;
    }
}
