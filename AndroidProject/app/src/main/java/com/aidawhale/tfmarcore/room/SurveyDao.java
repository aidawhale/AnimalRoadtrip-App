package com.aidawhale.tfmarcore.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SurveyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Survey survey);

    @Query("SELECT * FROM survey WHERE user LIKE :userid")
    LiveData<List<Survey>> getUserSurveys(String userid);

    @Query("SELECT * FROM survey WHERE user LIKE :userid and date LIKE :date")
    LiveData<Survey> getDailySurveyByUser(String userid, String date);

    @Query("SELECT * FROM survey WHERE user LIKE :userid and date LIKE :date")
    Survey getDirectDailySurveyByUser(String userid, String date);

}
