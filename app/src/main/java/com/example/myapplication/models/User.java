package com.example.myapplication.models;

public class User {

    // name, email, tsize, phone
    private String name, email;
    private int phone;

    public User(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
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
