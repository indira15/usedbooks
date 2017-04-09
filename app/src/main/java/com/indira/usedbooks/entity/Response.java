package com.indira.usedbooks.entity;

/**
 * Created by jotishsuthar on 10/04/17.
 */

public class Response {

    private int success;
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
