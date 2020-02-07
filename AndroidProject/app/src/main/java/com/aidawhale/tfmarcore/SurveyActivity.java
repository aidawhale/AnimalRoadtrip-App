package com.aidawhale.tfmarcore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aidawhale.tfmarcore.client.InternetCheck;
import com.aidawhale.tfmarcore.client.RestService;
import com.aidawhale.tfmarcore.room.AppRoomDatabase;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;
import com.aidawhale.tfmarcore.room.UserDao;
import com.aidawhale.tfmarcore.room.ViewModels.SurveyActivityViewModel;
import com.aidawhale.tfmarcore.utils.DateConverter;
import com.aidawhale.tfmarcore.utils.LanguageSettings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity {

    public static HashMap<Integer, String> values = new HashMap<>(); // stores user answers

    private ArrayList<Integer> questionTitles = new ArrayList<>();
    private ArrayList<Integer> questions = new ArrayList<>();

    private SurveyActivityViewModel surveyViewModel;

    private User user;
    private String userID = null;
    private boolean askForPermission;
    private boolean internet;
    private boolean remoteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        LanguageSettings.updateLocale(getApplicationContext());

        // Get a new or existing ViewModel from the ViewModelProvider.
        surveyViewModel = new ViewModelProvider(this).get(SurveyActivityViewModel.class);


        // Generate survey_listitems and add them to the RecyclerView
        loadQuestions();
        initSurveyRecyclerView();

        // Get info sent from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("USER_ID");
            askForPermission = extras.getString("ASK_FOR_PERMISSION") != null &&
                    extras.getString("ASK_FOR_PERMISSION").equals("true");
            internet = extras.getString("INTERNET") != null &&
                    extras.getString("INTERNET").equals("true");
            remoteUser = extras.getString("REMOTE_USER") != null &&
                    extras.getString("REMOTE_USER").equals("true");

            Log.d("SurveyActivity onCreate", "Extras: \nID " + userID +
                    ", askForPermission " + askForPermission + ", internet " + internet +
                    ", remoteUser " + remoteUser);
        } else {
            Log.d("SurveyActivity onCreate", "Extras is null... this should never happen");
        }

        if(askForPermission) { // First login
            Log.d("SurveyActivity onCreate", "remoteUser " + remoteUser + ", first login on local");
            // Ask for permission to collect and save data usage
            showPrivacyDialog();
            // NOTE: Now user exists on DB local and remote (if internet)
        }

        Log.d("SurveyActivity onCreate", "Continue with survey...");

        // FAB Button for sending survey data
        FloatingActionButton fabBtn = findViewById(R.id.fab_send_survey);
        fabBtn.setOnClickListener(view -> handleSurvey());

    }

    private void handleSurvey() {
        Log.d("SurveyActivity handleSurvey", "remoteUser " + remoteUser + ", user " + userID);

        int happiness = 0, food = 0, pain = 0;

        // Check if all answers are completed
        for (Integer v : values.keySet()) {
            if (values.get(v) == null) {
                String s = getString(R.string.question_no_answer) + " " + getBaseContext().getString(v);
                Toast.makeText(SurveyActivity.this, s, Toast.LENGTH_SHORT).show();
                return;
            }

            switch (v) {
                case R.string.happiness:
                    happiness = Integer.parseInt(values.get(v));
                    break;
                case R.string.food:
                    food = Integer.parseInt(values.get(v));
                    break;
                case R.string.pain:
                    pain = Integer.parseInt(values.get(v));
                    break;
                default:
                    Toast.makeText(SurveyActivity.this, "There was a problem saving survey, please try again :(", Toast.LENGTH_SHORT).show();
            }
        }

        // Send info to DB
        Log.d("SurveyActivity handleSurvey", "Save survey on local DB");
        String date = DateConverter.complexDateToSimpleDate(new Date());
        Survey survey = new Survey(date, userID, happiness, food, pain);
        surveyViewModel.insert(survey);
        if(internet) {
            Log.d("SurveyActivity handleSurvey", "Send survey to remote");
            // Send survey to remote
            RestService.sendSurvey(user, survey);
        }

        // Load next activity
        Log.d("SurveyActivity handleSurvey", "Load UserMenuActivity");
        Intent intent = new Intent(getApplicationContext(), UserMenuActivity.class);
        intent.putExtra("USER_ID", userID);
        startActivity(intent);
        finish();  // Don't add this activity to back stack
    }

    private void loadQuestions() {
        questionTitles.add(R.string.happiness);
        questions.add(R.string.happiness_question);

        questionTitles.add(R.string.food);
        questions.add(R.string.food_question);

        questionTitles.add(R.string.pain);
        questions.add(R.string.pain_question);
    }

    private void initSurveyRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.survey_recyclerview);
        SurveyRecyclerViewAdapter adapter = new SurveyRecyclerViewAdapter(getApplicationContext(), questionTitles, questions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void showPrivacyDialog() {
        // Show dialog with privacy info and question
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(R.string.privacy_title);
        dialog.setMessage(getResources().getString(R.string.privacy_info_text) +
                "\n\n" + getResources().getString(R.string.privacy_question));
        dialog.setPositiveButton(R.string.accept, (dialogInterface, i) -> addUser(true));
        dialog.setNegativeButton(R.string.reject, (dialogInterface, i) -> {

            addUser(false);

            // Load next activity
            Log.d("SurveyActivity showPrivacyDialog", "Refused data collection. Load UserMenuActivity");
            Intent intent = new Intent(getApplicationContext(), UserMenuActivity.class);
            startActivity(intent);
            finish();  // Don't add this activity to back stack
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void addUser(boolean permission) {
        Log.d("SurveyActivity addUser", "Save user on local DB");

        User newUser = new User(userID);
        newUser.storagePermission = permission;

        surveyViewModel.insert(newUser);

        if (askForPermission && internet) {
            addRemoteUser(newUser);
        }
    }

    private void addRemoteUser(User user) {
        // Check if internet available
        new InternetCheck(internet -> {
            if (internet) {
                //  Send user to remote DB
                Log.d("SurveyActivity addRemoteUser", "user " + user.userID + ", storage " + user.storagePermission);
                RestService.sendNewUser(user);
            } else {
                Log.d("SurveyActivity addRemoteUser", "Error sending user to remote: no internet connection");
                Toast.makeText(this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
