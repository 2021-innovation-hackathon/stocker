package com.Hackathon.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class settingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public settingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is setting fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}