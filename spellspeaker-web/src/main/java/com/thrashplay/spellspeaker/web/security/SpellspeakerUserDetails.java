package com.thrashplay.spellspeaker.web.security;

import com.thrashplay.spellspeaker.model.User;

import java.util.ArrayList;

/**
 * @author Sean Kleinjung
 */
public class SpellspeakerUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public SpellspeakerUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), new ArrayList<>(0));
        this.user = user;
    }

    public User getAppUser() {
        return user;
    }
}
