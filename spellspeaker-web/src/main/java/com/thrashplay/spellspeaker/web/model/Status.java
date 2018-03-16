package com.thrashplay.spellspeaker.web.model;

/**
 * @author Sean Kleinjung
 */
public class Status {
    public static final Status SUCCESS = new Status("success");

    private String status;

    public Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
