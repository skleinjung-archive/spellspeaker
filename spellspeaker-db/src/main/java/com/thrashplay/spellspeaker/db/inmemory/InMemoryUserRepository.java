package com.thrashplay.spellspeaker.db.inmemory;

import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sean Kleinjung
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<Long, User> users = new HashMap<>();

    public InMemoryUserRepository() {
    }

    @Override
    public User findOne(Long id) {
        return users.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findByUsername(String username) {
        User result = null;
        if (username != null) {
            for (User user : users.values()) {
                if (username.equals(user.getUsername())) {
                    result = user;
                }
            }
        }

        return result;
    }

    @Override
    public <S extends User> S save(S entity) {
        users.put(entity.getId(), entity);
        return entity;
    }
}
