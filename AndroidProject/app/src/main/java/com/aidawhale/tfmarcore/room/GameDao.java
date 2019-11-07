package com.aidawhale.tfmarcore.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    @Insert
    void insert(Game game);

    @Query("SELECT id FROM game")
    List<Integer> getAllGames();

    @Query("SELECT * FROM game WHERE user LIKE :userid")
    LiveData<List<Game>> getUserGames(String userid);

    @Query("SELECT SUM(steps) FROM game WHERE user LIKE :userid and date LIKE :date")
    LiveData<Integer> getDailyStepCount(String userid, String date);

}
