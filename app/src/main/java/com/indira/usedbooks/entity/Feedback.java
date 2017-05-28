package com.indira.usedbooks.entity;

import java.io.Serializable;

/**
 * Created by Manish on 08-05-2017.
 */

public class Feedback implements Serializable {
    private int fedid;
    private  String message;
    private String date;
    private User user;

    public int getFedid() {
        return fedid;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public void setFedid(int fedid) {
        this.fedid = fedid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
