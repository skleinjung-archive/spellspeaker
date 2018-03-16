package com.thrashplay.spellspeaker.repository;

import com.thrashplay.spellspeaker.model.User;

import java.util.List;

/**
 * @author Sean Kleinjung
 */
public interface UserRepository extends Repository<User, Long> {
    User findByUsername(String username);
}
