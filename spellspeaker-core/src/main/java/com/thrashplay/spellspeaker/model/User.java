package com.thrashplay.spellspeaker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Sean Kleinjung
 */
public class User {
    private long id;

    private String username;

    @JsonIgnore
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
