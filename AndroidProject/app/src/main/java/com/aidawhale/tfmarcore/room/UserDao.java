package com.aidawhale.tfmarcore.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
    User getUserById(String id);

    @Query("SELECT user_id FROM user_info")
    List<String> getAllUserIDs();

    @Query("SELECT storage_permission FROM user_info WHERE user_id LIKE :userid")
    boolean getStoragePermission(String userid);

    @Query("UPDATE user_info SET storage_permission = :newPermission WHERE user_id LIKE :userid")
    void updateStoragePermission(String userid, boolean newPermission);

}
