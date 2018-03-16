package com.thrashplay.spellspeaker.db.repository;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates pseudo-identity values for a specific class.
 * @author Sean Kleinjung
 */
public class IdGenerator {
    private Map<Class, AtomicLong> generators;

    public long getId(Class clazz) {
        AtomicLong generator = generators.get(clazz);
        if (generator == null) {
            generator = new AtomicLong();
            generators.put(clazz, generator);
        }

        return generator.incrementAndGet();
    }
}
