package com.example.myapplication.models;

public class User {

    // name, email, tsize, phone
    private String name, email;
    private int phone;
    private String key;
    private Boolean permissions;
    

    public User(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Boolean getAmota() {
        return permissions;
    }

    public void setAmota(Boolean amota) {
        permissions = amota;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    private char ch;
}
