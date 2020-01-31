package com.aidawhale.tfmarcore.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(
    tableName = "game",
    foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "user_id",
        childColumns = "user",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {
        @Index("user"),
        @Index("date")
    }
)
public class Game {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("game_id")
    public int id;

    @NonNull
    @ColumnInfo(name = "user")
    @SerializedName("game_user_id")
    public String user;

    @NonNull
    @ColumnInfo(name = "game_type")
    @SerializedName("game_type")
    public int game_type; // MarcoPolo = 1; Pieces = 2; Minesweeper = 3

    @NonNull
    @ColumnInfo(name = "time")
    @SerializedName("game_time")
    public int time; // time spent playing this game in seconds

    @NonNull
    @ColumnInfo(name = "steps")
    @SerializedName("game_steps")
    public int steps;

    @NonNull
    @ColumnInfo(name = "date")
    @SerializedName("game_date")
    public String date;

    // Constructor
    public Game(int game_type, String user, int time, int steps, String date) {
        this.game_type = game_type;
        this.user = user;
        this.time = time;
        this.steps = steps;
        this.date = date;
    }

}
