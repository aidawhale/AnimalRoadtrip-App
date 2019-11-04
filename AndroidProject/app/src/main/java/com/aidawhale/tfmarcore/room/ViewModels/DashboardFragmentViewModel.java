package com.aidawhale.tfmarcore.room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aidawhale.tfmarcore.room.AppRepository;
import com.aidawhale.tfmarcore.room.Game;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;

import java.util.List;

public class DashboardFragmentViewModel extends AndroidViewModel {

    /* Dashboard fragment needs info from:
     *
     *   - Survey:
     *       getUserSurveys()
     *
     *   - Game:
     *       getUserGames()
     *
     * */

    private AppRepository repository;
    private LiveData<List<Survey>> userSurveys;
    private String userid = "1234";

    public DashboardFragmentViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
        userSurveys = repository.getUserSurveys(userid);

    }

    public LiveData<List<Survey>> getUserSurveys(String userid) {
        return repository.getUserSurveys(userid);
    }

    public LiveData<List<Game>> getUserGames(String userid) {
        return repository.getUserGames(userid);
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void insert(Survey survey) {
        repository.insert(survey);
    }

    public void insert(Game game) {
        repository.insert(game);
    }
}
