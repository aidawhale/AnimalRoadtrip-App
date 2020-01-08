package com.aidawhale.tfmarcore.ui.selectgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.aidawhale.tfmarcore.MapAssistantActivity;
import com.aidawhale.tfmarcore.MarcopoloActivity;
import com.aidawhale.tfmarcore.R;
import com.aidawhale.tfmarcore.UserMenuActivity;
import com.aidawhale.tfmarcore.room.Game;
import com.aidawhale.tfmarcore.room.ViewModels.SelectGameFragmentViewModel;
import com.aidawhale.tfmarcore.utils.DateConverter;
import com.aidawhale.tfmarcore.utils.LanguageSettings;
import com.unity3d.player.UnityPlayerActivity;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SelectGameFragment extends Fragment implements SensorEventListener {

    private SelectGameViewModel sgViewModel;
    private String userID = "dumb";

    private SelectGameFragmentViewModel selectGameFragmentViewModel;

    private SensorManager sensorManager;
    private Sensor sensor;

    private static final int MARCO_POLO_GAME = 1111;
    private static final int COLLECT_PIECES_GAME = 2222;
    private static final int TREASURE_SEARCH_GAME = 3333;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sgViewModel =
                ViewModelProviders.of(this).get(SelectGameViewModel.class);

        LanguageSettings.updateLocale(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_selectgame);

        View root = inflater.inflate(R.layout.fragment_selectgame, container, false);

        // Get a new or existing ViewModel from the ViewModelProvider.
        selectGameFragmentViewModel = new ViewModelProvider(this).get(SelectGameFragmentViewModel.class);

        // Set step count sensor
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(getContext(), "No step sensor found :(", Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        CardView cv1 = getView().findViewById(R.id.first_game_cardview);
        CardView cv2 = getView().findViewById(R.id.second_game_cardview);
        CardView cv3 = getView().findViewById(R.id.third_game_cardview);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current step count and time
                sgViewModel.preGameDate = new Date();
                sgViewModel.preGameSteps = sgViewModel.steps;

                // Init game with corresponding MarcopoloActivity
                Intent intent = new Intent(getContext(), MarcopoloActivity.class);
                startActivityForResult(intent, MARCO_POLO_GAME);
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current step count and time
                sgViewModel.preGameDate = new Date();
                sgViewModel.preGameSteps = sgViewModel.steps;

                // Init game with corresponding UnityActivity
                Intent intent = new Intent(getContext(), UnityPlayerActivity.class);
                intent.putExtra("SCENE", "1");
                startActivityForResult(intent, COLLECT_PIECES_GAME);
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current step count and time
                sgViewModel.preGameDate = new Date();
                sgViewModel.preGameSteps = sgViewModel.steps;

                // Dialog. Ask to launch MAP or CLUES
                showDialogMapClues();
            }
        });

        userID = UserMenuActivity.getUserID();

        TextView gridHeader = getView().findViewById(R.id.grid_header);
        if(userID != null) {
            gridHeader.setText("Login " + userID);
        } else {
            gridHeader.setText(R.string.select_game);
        }
    }

    private void showDialogMapClues() {// Show dialog with app info and a dropdown to switch language
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());


        dialog.setTitle(R.string.play_mode);
        dialog.setMessage(R.string.play_mode_info);
        dialog.setPositiveButton(R.string.assistant, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Load assistant activity
                Intent intent = new Intent(getContext(), MapAssistantActivity.class);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton(R.string.explorer, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int randScene = new Random().nextInt(2)+2;
                // Init game with corresponding UnityActivity
                Intent intent = new Intent(getContext(), UnityPlayerActivity.class);
                intent.putExtra("SCENE", String.valueOf(randScene));
                startActivityForResult(intent, TREASURE_SEARCH_GAME);
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(userID == null) {
            // user not logged in
            return;
        }

        int gameType = 0;
        int gameSteps = sgViewModel.steps - sgViewModel.preGameSteps;
        Date currentDate = new Date();
        String date = DateConverter.complexDateToSimpleDate(currentDate);
        long time= currentDate.getTime() - sgViewModel.preGameDate.getTime();
        int gameTime = (int) TimeUnit.MILLISECONDS.toSeconds(time);

        Toast.makeText(getContext(), "Insert game: steps " + gameSteps + ", time " + gameTime, Toast.LENGTH_SHORT).show();

        switch (requestCode) {
            case MARCO_POLO_GAME:
                gameType = 1;
                break;
            case COLLECT_PIECES_GAME:
                gameType = 2;
                break;
            case TREASURE_SEARCH_GAME:
                gameType = 3;
                break;
        }

        Game game = new Game(gameType, userID, gameTime, gameSteps, date);
        selectGameFragmentViewModel.insert(game);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        sgViewModel.steps = (int) sensorEvent.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing
    }
}