package com.indira.usedbooks.entity;

import java.io.Serializable;

/**
 * Created by Manish on 08-04-2017.
 */

public class User implements Serializable{
    private int id;
    private String name;
    private String email;
    private String  address;
    private String phoneno;
    private String phoneno2;
    private String city;
    private String state;
    private String password;


    public void setName(String name) {this.name = name;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneno(String phoneno) { this.phoneno = phoneno; }

    public void setPhoneno2(String phoneno2) { this.phoneno2 = phoneno2;}

    public void setCity(String city) { this.city=city;}

    public void setState(String state) {this.state=state;}

    public void setPassword(String password){this.password=password;}

    public String getName() { return name;}

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getPhoneno2() {
        return phoneno2;
    }

    public  String getCity() { return city;}

    public  String getState() {return state;}

    public String getPassword() {return password;}

    public int getId() {
        return id;
    }
}
