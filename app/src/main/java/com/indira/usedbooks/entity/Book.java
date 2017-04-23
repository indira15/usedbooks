package com.indira.usedbooks.entity;


import java.io.Serializable;

/**
 * Created by Manish on 08-04-2017.
 */

public class Book implements Serializable {

    private int id;
    private String name;
    private String authorName;
    private String edition;
    private User user;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    private double cost;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getEdition() {
        return edition;
    }

    public double getCost() {
        return cost;
    }

}
