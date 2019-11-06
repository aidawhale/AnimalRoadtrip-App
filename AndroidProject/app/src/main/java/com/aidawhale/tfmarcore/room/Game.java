package com.aidawhale.tfmarcore.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
    public int id;

    @NonNull
    @ColumnInfo(name = "user")
    public String user;

    @NonNull
    @ColumnInfo(name = "game_type")
    public int game_type; // MarcoPolo = 1; Pieces = 2; Minesweeper = 3

    @NonNull
    @ColumnInfo(name = "time")
    public int time; // time spent playing this game in seconds

    @NonNull
    @ColumnInfo(name = "steps")
    public int steps;

    @NonNull
    @ColumnInfo(name = "date")
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