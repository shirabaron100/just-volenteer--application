package com.example.myapplication.ui;

public class Event {
    String date;
    String time;
    String nameOfEvent;
    String moreInfo;
    String place;

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public String getPlace() {
        return place;
    }

    public Event(String date, String time, String nameOfEvent, String moreInfo, String place) {
        this.date = date;
        this.time = time;
        this.nameOfEvent = nameOfEvent;
        this.moreInfo = moreInfo;
        this.place = place;
    }
}
