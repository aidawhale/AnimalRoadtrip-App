package com.aidawhale.tfmarcore.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aidawhale.tfmarcore.utils.GameInfoPerDay;
import com.aidawhale.tfmarcore.utils.UserStepsPerGame;

import java.util.List;

@Dao
public interface GameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Game game);

    @Query("SELECT id FROM game")
    List<Integer> getAllGames();

    @Query("SELECT * FROM game WHERE user LIKE :userid")
    LiveData<List<Game>> getUserGames(String userid);

    @Query("SELECT SUM(steps) FROM game WHERE user LIKE :userid and date LIKE :date")
    LiveData<Integer> getDailyStepCount(String userid, String date);

    @Query("SELECT game_type, SUM(steps) as sumSteps FROM game " +
            "WHERE user LIKE :userid and date LIKE :date " +
            "GROUP BY game_type")
    LiveData<List<UserStepsPerGame>> getDailyUserStepsPerGameType(String userid, String date);

    @Query("SELECT SUM(steps) as sumSteps, SUM(time) as sumTime, date " +
            "FROM game " +
            "WHERE user LIKE :userid " +
            "GROUP BY date")
    LiveData<List<GameInfoPerDay>> getUserStepsAndTimePerDay(String userid);

    @Query("DELETE FROM game WHERE user LIKE :userid")
    void deleteUserGames(String userid);
}
