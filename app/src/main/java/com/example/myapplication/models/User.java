package com.example.myapplication.models;

public class User {
    public User(String name, String email) {

    }
    // name, email, tsize, phone
    private String name, email;
    private int phone;

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
