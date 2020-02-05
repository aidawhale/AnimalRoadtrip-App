package com.aidawhale.tfmarcore.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(
    tableName = "user_info"
)
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    public String userID; // bar code number

    @NonNull
    @ColumnInfo(name = "storage_permission")
    @SerializedName("storage_permission")
    public boolean storagePermission;

    @ColumnInfo(name = "height")
    public int height; // patient height in cm

    @ColumnInfo(name = "difficulty_level")
    public int difficultyLevel; // easy = 1; medium = 2; hard = 2;

    // Constructor
    public User(@NonNull String userID) {
        this.userID = userID;
        this.storagePermission = false; // Storage permission default value
        this.difficultyLevel = 1; // Difficulty level default value
    }

}
