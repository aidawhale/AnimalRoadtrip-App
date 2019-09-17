package com.aidawhale.tfmarcore.ui.selectgame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectGameViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SelectGameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is select game fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}