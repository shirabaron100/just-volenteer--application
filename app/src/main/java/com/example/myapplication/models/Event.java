package com.example.myapplication.models;

import java.io.Serializable;

public class Event implements Serializable {
    private String nameOfEvent;
    private String date;
    private String time;
    private String location;
    private String moreInfo;
    private String key;

    public Event () {

    }

    public Event(String nameOfEvent, String date, String time, String location, String moreInfo) {
        this.nameOfEvent = nameOfEvent;
        this.date = date;
        this.time = time;
        this.location = location;
        this.moreInfo = moreInfo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Override
    public String toString() {
        return "(" + this.getNameOfEvent() + "," + this.getDate() + ")";
    }
}
