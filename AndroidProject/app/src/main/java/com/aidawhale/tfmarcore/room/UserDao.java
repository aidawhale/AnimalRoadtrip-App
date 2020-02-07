package com.aidawhale.tfmarcore.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM user_info")
    void deleteAll();

    @Query("DELETE FROM user_info WHERE user_id LIKE :id")
    void deleteUser(String id);

    @Query("SELECT * FROM user_info WHERE user_id LIKE :id")
    LiveData<User> getUserById(String id);

    @Query("SELECT * FROM user_info WHERE user_id LIKE :id")
    User getDirectUserById(String id);

    @Query("UPDATE user_info SET " +
            "storage_permission= :storagePermission, " +
            "difficulty_level= :difficultyLevel, " +
            "height= :height " +
            "WHERE user_id LIKE :userid")
    void updateUser(String userid, boolean storagePermission, int difficultyLevel, int height);


}
