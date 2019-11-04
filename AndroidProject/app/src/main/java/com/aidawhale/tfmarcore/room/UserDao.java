package com.aidawhale.tfmarcore.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM user_info")
    void deleteAll();

    @Query("DELETE FROM user_info WHERE user_id LIKE :id")
    void deleteUser(String id);

    @Query("SELECT * FROM user_info WHERE user_id LIKE :id")
    LiveData<User> getUserById(String id);

    @Query("SELECT * FROM user_info WHERE user_id LIKE :id")
    User getDirectUserById(String id);

}
