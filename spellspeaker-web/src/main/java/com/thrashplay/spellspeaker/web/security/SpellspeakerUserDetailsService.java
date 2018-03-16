package com.thrashplay.spellspeaker.web.security;

import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Sean Kleinjung
 */
@Service
public class SpellspeakerUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public SpellspeakerUserDetailsService(UserRepository userRepository) {
        Assert.notNull(userRepository, "userRepository cannot be null");
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new SpellspeakerUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}