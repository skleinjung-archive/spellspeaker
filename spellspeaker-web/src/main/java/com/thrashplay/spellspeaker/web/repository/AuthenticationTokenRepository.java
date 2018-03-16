package com.thrashplay.spellspeaker.web.repository;

import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.repository.Repository;
import com.thrashplay.spellspeaker.web.model.AuthenticationToken;

/**
 * @author Sean Kleinjung
 */
public interface AuthenticationTokenRepository extends Repository<AuthenticationToken, String> {
    AuthenticationToken findByToken(String token);
    AuthenticationToken findByUserId(long userId);

    AuthenticationToken createTokenFor(User user);
    void removeTokenFor(User user);
}
