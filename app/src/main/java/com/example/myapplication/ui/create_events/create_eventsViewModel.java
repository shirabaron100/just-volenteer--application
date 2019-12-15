package com.example.myapplication.ui.create_events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class create_eventsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public create_eventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("SELECT DATE");
    }

    public LiveData<String> getText() {
        return mText;
    }
}