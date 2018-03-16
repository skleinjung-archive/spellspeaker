package com.thrashplay.spellspeaker.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Sean Kleinjung
 */
public class AuthenticationToken {
    private String token;

    @JsonIgnore
    private long userId;
    private long expirationTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
