package com.Hackathon.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel_origin extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel_origin() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calender fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}