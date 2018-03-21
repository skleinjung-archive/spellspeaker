package com.thrashplay.spellspeaker.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Sean Kleinjung
 */
@Service
public class RandomService {
    public int getRandomNumberBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
