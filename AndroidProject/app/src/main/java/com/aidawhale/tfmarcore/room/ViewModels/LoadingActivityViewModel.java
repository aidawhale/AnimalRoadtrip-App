package com.aidawhale.tfmarcore.room.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aidawhale.tfmarcore.room.AppRepository;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;

public class LoadingActivityViewModel extends AndroidViewModel {

    /* Loading activity needes info from:
     *
     *   - User:
     *       getDirectUserById()
     *       insert()
     *       update()
     *
     *   - Survey:
     *       getDailySurvey()
     *       insert()
     *
     * */

    private AppRepository repository;

    public LoadingActivityViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
    }

    public LiveData<User> getUserByID(String userid) {
        return repository.getUserById(userid);
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public LiveData<Survey> getDailySurveyByUser(String userid, String date) {
        return repository.getDailySurveyByUser(userid, date);
    }

    public void insert(Survey survey) {
        repository.insert(survey);
    }

}
