package com.aidawhale.tfmarcore.room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aidawhale.tfmarcore.room.AppRepository;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;

import java.util.List;

public class SurveyActivityViewModel extends AndroidViewModel {

    /* Survey activity needes info from:
     *
     *   - User:
     *       getDirectUserById() -> done in main thread.
     *       insert()
     *
     *   - Survey:
     *       insert()
     *
     * */

    private AppRepository repository;

    public SurveyActivityViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void insert(Survey survey) {
        repository.insert(survey);
    }

}
