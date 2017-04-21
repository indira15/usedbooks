package com.indira.usedbooks.entity;

/**
 * Created by Manish on 20-04-2017.
 */

public class Request {


        private String operation;
        private User user;

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

