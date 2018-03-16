package com.thrashplay.spellspeaker.repository;

import com.thrashplay.spellspeaker.model.GameState;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public interface Repository<T, ID extends Serializable> {

    T findOne(ID id);

    List<T> findAll();

    <S extends T> S save(S entity);
}
