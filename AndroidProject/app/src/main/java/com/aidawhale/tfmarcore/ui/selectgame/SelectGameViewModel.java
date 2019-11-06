package com.aidawhale.tfmarcore.ui.selectgame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

public class SelectGameViewModel extends ViewModel {

    public int steps = 0;
    public int preGameSteps = 0;
    public Date preGameDate;

    private MutableLiveData<String> mText;

    public SelectGameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is select game fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}