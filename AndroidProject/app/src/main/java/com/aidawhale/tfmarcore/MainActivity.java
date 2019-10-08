package com.aidawhale.tfmarcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mUserColors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "OnCreate: started.");

        InitImageBitmaps();
    }

    private void InitImageBitmaps() {

        // Load data to create the list
        mNames.add("Aida");
        mUserColors.add("Red"); /// doesn't work
        mNames.add("Aida 2");
        mUserColors.add("Blue"); /// doesn't work

        // Add last item
        mNames.add("Add new user");
        mUserColors.add("Black"); /// doesn't work

        InitRecyclerView();

    }
    
    private void InitRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mUserColors);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
