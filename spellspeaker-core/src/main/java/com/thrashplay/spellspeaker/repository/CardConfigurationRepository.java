package com.thrashplay.spellspeaker.repository;

import com.thrashplay.spellspeaker.config.CardConfiguration;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public interface CardConfigurationRepository extends Repository<CardConfiguration, Long> {
    @Override
    CardConfiguration findOne(Long aLong);

    @Override
    List<CardConfiguration> findAll();
}
