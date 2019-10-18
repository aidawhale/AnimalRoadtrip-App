package com.aidawhale.tfmarcore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MarcopoloActivity extends AppCompatActivity {

    private CardView easy; // mode 1
    private CardView medium; // mode 2
    private CardView hard; // mode 3
    private int currentMode;
    private Button startStop;
    private ColorStateList grayColor;
    private ColorStateList easyColor;
    private ColorStateList mediumColor;
    private ColorStateList hardColor;
    private ColorStateList startStopColor;
    private Boolean chronoState = false;
    private Chronometer chronometer;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcopolo);

        getSupportActionBar().setTitle(R.string.marcopolo);

        grayColor = ColorStateList.valueOf(getApplicationContext().getResources().getColor(R.color.colorLightGray, getTheme()));

        easy = findViewById(R.id.easy_mode_cardview);
        easyColor = easy.getBackgroundTintList();
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimerAndButton();
                disableModeButtons();
                currentMode = 1;
                easy.setBackgroundTintList(easyColor);
                stopBeepingAndLoadNewSound();
            }
        });

        medium = findViewById(R.id.medium_mode_cardview);
        mediumColor = medium.getBackgroundTintList();
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimerAndButton();
                disableModeButtons();
                currentMode = 2;
                medium.setBackgroundTintList(mediumColor);
                stopBeepingAndLoadNewSound();
            }
        });

        hard = findViewById(R.id.hard_mode_cardview);
        hardColor = hard.getBackgroundTintList();
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimerAndButton();
                disableModeButtons();
                currentMode = 3;
                hard.setBackgroundTintList(hardColor);
                stopBeepingAndLoadNewSound();
            }
        });

        chronometer = findViewById(R.id.chronometer);

        startStop = findViewById(R.id.stop_button);
        startStopColor = startStop.getBackgroundTintList();
        startStop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chronoState) { // active
                    chronometer.stop();
                    startStop.setText(R.string.start);
                    chronoState = false;
                } else {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    startStop.setText(R.string.stop);
                    chronoState = true;
                }
                startStopBeeping();
            }
        });
        startStop.setBackgroundTintList(grayColor);
        startStop.setEnabled(false);
    }

    private void startStopBeeping() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    private void stopBeepingAndLoadNewSound() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }

        switch (currentMode) {
            case 1:
                mediaPlayer = MediaPlayer.create(MarcopoloActivity.this, R.raw.birds);
                mediaPlayer.setLooping(true);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(MarcopoloActivity.this, R.raw.dog);
                mediaPlayer.setLooping(true);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(MarcopoloActivity.this, R.raw.cat);
                mediaPlayer.setLooping(true);
                break;
        }
    }

    private void resetTimerAndButton() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        startStop.setText(R.string.start);
        chronoState = false;
    }

    private void disableModeButtons() {
        easy.setBackgroundTintList(grayColor);
        medium.setBackgroundTintList(grayColor);
        hard.setBackgroundTintList(grayColor);

        startStop.setEnabled(true);
        startStop.setBackgroundTintList(startStopColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_buttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                showAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialog() {
        // Show dialog with MarcoPolo info
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(R.string.marcopolo_info);
        dialog.setMessage(getResources().getString(R.string.marcopolo_info_text) +
                "\n\n" + getResources().getString(R.string.marcopolo_info_attribuition));
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Close dialog
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
