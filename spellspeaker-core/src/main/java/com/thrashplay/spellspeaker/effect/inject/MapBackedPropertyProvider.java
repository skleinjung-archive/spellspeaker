package com.thrashplay.spellspeaker.effect.inject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sean Kleinjung
 */
public class MapBackedPropertyProvider implements PropertyProvider {
    Map<String, Object> properties = new HashMap<>();

    public void addPropertyValue(String propertyName, Object value) {
        properties.put(propertyName, value);
    }

    @Override
    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }
}
