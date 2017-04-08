package com.indira.usedbooks.entity;

/**
 * Created by Manish on 08-04-2017.
 */

public class Image {
    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private  String url;
}
