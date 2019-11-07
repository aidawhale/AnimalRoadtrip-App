package com.aidawhale.tfmarcore.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private UserDao userDao;
    private SurveyDao surveyDao;
    private GameDao gameDao;

    public AppRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        userDao = db.userDao();
        surveyDao = db.surveyDao();
        gameDao = db.gameDao();
    }

    // Gets
    public LiveData<User> getUserById(String userid) {
        return userDao.getUserById(userid);
    }

    public LiveData<List<Survey>> getUserSurveys(String userid) {
        return surveyDao.getUserSurveys(userid);
    }

    public LiveData<List<Game>> getUserGames(String userid) {
        return gameDao.getUserGames(userid);
    }

    public LiveData<Survey> getDailySurveyByUser(String userid, String date) {
        return surveyDao.getDailySurveyByUser(userid, date);
    }

    public LiveData<Integer> getDailyStepCount(String userid, String date) {
        return gameDao.getDailyStepCount(userid, date);
    }

    // Inserts
    public void insert (User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void insert(Survey survey) {
        new InsertSurveyAsyncTask(surveyDao).execute(survey);
    }

    public void insert(Game game) {
        new InsertGameAsyncTask(gameDao).execute(game);
    }

    // Async tasks
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao asyncTaskDao;

        InsertUserAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class InsertSurveyAsyncTask extends AsyncTask<Survey, Void, Void> {
        private SurveyDao asyncTaskDao;

        InsertSurveyAsyncTask(SurveyDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Survey... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class InsertGameAsyncTask extends AsyncTask<Game, Void, Void> {
        private GameDao asyncTaskDao;

        InsertGameAsyncTask(GameDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Game... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
