package com.thrashplay.spellspeaker.db.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates pseudo-identity values for a specific class.
 * @author Sean Kleinjung
 */
@Service
public class IdGenerator {
    private Map<Class, AtomicLong> generators = new HashMap<>();

    public long getId(Class clazz) {
        AtomicLong generator = generators.get(clazz);
        if (generator == null) {
            generator = new AtomicLong();
            generators.put(clazz, generator);
        }

        return generator.incrementAndGet();
    }
}
