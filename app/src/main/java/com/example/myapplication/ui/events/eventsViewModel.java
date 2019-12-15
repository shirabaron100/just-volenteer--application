package com.example.myapplication.ui.events;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class eventsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public eventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }
    protected  void OnCreate (Bundle sa)
    {

    }
    public LiveData<String> getText() {
        return mText;
    }
}