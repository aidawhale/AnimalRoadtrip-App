package com.aidawhale.tfmarcore.room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.aidawhale.tfmarcore.room.AppRepository;
import com.aidawhale.tfmarcore.room.Game;

public class SelectGameFragmentViewModel extends AndroidViewModel {

    /* Survey activity needs info from:
     *
     *   - Game:
     *       insert()
     *
     * */

    private AppRepository repository;

    public SelectGameFragmentViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
    }

    public void insert(Game game) {
        repository.insert(game);
    }
}
