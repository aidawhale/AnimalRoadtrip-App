package com.aidawhale.tfmarcore.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {User.class, Game.class, Survey.class},
        version = 1,
        exportSchema = false
)
public abstract class AppRoomDatabase extends androidx.room.RoomDatabase {

    private static volatile AppRoomDatabase INSTANCE;
    private static final String DB_NAME = "my_database";

    public abstract UserDao userDao();
    public abstract GameDao gameDao();
    public abstract SurveyDao surveyDao();

    public static AppRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if(INSTANCE == null) {
                    // Create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            // Allow main thread queries only for simple transactions as gets,
                            // inserts, deletes, etc must be done using ViewModels
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate (@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao userDao;
        private final GameDao gameDao;
        private final SurveyDao surveyDao;

        public PopulateDbAsync(AppRoomDatabase db) {
            userDao = db.userDao();
            gameDao = db.gameDao();
            surveyDao = db.surveyDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If needed, use this method for init db
            return null;
        }
    }

}
