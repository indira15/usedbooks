package com.indira.usedbooks.entity;

import java.io.Serializable;

/**
 * Created by Manish on 08-05-2017.
 */

public class Feedback implements Serializable {
    private int id;
    private  String message;
    private int date;
    private User user;

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
