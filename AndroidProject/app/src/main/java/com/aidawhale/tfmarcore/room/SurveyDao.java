package com.aidawhale.tfmarcore.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SurveyDao {

    @Insert
    void insert(Survey survey);

    @Query("SELECT * FROM survey")
    List<Survey> getAllSurveys();

    @Query("SELECT * FROM survey WHERE user LIKE :userid")
    List<Survey> getSurveysByUser(String userid);

    @Query("SELECT * FROM survey WHERE user LIKE :userid and date LIKE :date")
    Survey getDailySurveyByUser(String userid, String date);

}
