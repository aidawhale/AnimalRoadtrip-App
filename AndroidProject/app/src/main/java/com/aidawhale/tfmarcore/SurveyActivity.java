package com.aidawhale.tfmarcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity {

    public static HashMap<Integer, String> values = new HashMap<>(); // stores user answers

    private ArrayList<Integer> questionTitles = new ArrayList<>();
    private ArrayList<Integer> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // Generate survey_listitems and add them to the RecyclerView
        loadQuestions();
        initSurveyRecyclerView();

        // FAB Button for sending survey data
        FloatingActionButton fabBtn = findViewById(R.id.fab_send_survey);
        fabBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // Get info sent from MainActivity
                String userID = null;
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    userID = extras.getString("USER_ID");
                }

                // Check if all answers are completed
                for (Integer v : values.keySet()) {
                    if (values.get(v) == null) {
                        Toast.makeText(SurveyActivity.this, "Question without answer: " + getBaseContext().getString(v), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // TODO: send info to DB

                Toast.makeText(SurveyActivity.this, values.toString(), Toast.LENGTH_SHORT).show();

                // Load next activity
                Intent intent = new Intent(getApplicationContext(), UserMenuActivity.class);
                intent.putExtra("USER_ID", userID);
                startActivity(intent);
                finish();  // Don't add this activity to back stack

            }
        });
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
}