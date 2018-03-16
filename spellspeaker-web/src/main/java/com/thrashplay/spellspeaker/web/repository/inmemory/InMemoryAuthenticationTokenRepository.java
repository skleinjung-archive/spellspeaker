package com.thrashplay.spellspeaker.web.repository.inmemory;

import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.web.model.AuthenticationToken;
import com.thrashplay.spellspeaker.web.repository.AuthenticationTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Sean Kleinjung
 */
@Repository
public class InMemoryAuthenticationTokenRepository implements AuthenticationTokenRepository {
    private Map<String, AuthenticationToken> authenticationTokens = new HashMap<>();

    @Override
    public AuthenticationToken findByToken(String token) {
        AuthenticationToken result = null;
        if (token != null) {
            for (AuthenticationToken authenticationToken : authenticationTokens.values()) {
                if (token.equals(authenticationToken.getToken())) {
                    result = authenticationToken;
                }
            }
        }

        return result;
    }

    @Override
    public AuthenticationToken findByUserId(long userId) {
        AuthenticationToken result = null;
        for (AuthenticationToken authenticationToken : authenticationTokens.values()) {
            if (userId == authenticationToken.getUserId()) {
                result = authenticationToken;
            }
        }

        return result;
    }

    @Override
    public AuthenticationToken findOne(String id) {
        return authenticationTokens.get(id);
    }

    @Override
    public List<AuthenticationToken> findAll() {
        return new ArrayList<>(authenticationTokens.values());
    }

    @Override
    public <S extends AuthenticationToken> S save(S entity) {
        authenticationTokens.put(entity.getToken(), entity);
        return entity;
    }

    @Override
    public AuthenticationToken createTokenFor(User user) {
        removeTokenFor(user);

        AuthenticationToken token = new AuthenticationToken();
        token.setToken(user.getUsername() + "-" + String.valueOf(System.currentTimeMillis()));
        token.setUserId(user.getId());
        token.setExpirationTime(Long.MAX_VALUE);
        return save(token);
    }

    @Override
    public void removeTokenFor(User user) {
        Set<Map.Entry<String, AuthenticationToken>> entries = authenticationTokens.entrySet();
        entries.removeIf(entry -> entry.getValue().getUserId() == user.getId());
    }
}
