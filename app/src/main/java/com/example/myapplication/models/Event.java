package com.example.myapplication.models;

public class Event {
String nameOfEvent;
String date;
String time;
String location;
String moreInfo;

    public Event () {

    }

    public Event(String nameOfEvent, String date, String time, String location, String moreInfo) {
        this.nameOfEvent = nameOfEvent;
        this.date = date;
        this.time = time;
        this.location = location;
        this.moreInfo = moreInfo;
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
