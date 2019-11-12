package com.aidawhale.tfmarcore;

import android.os.Bundle;

import com.aidawhale.tfmarcore.utils.LanguageSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class UserMenuActivity extends AppCompatActivity {

    private static String userID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LanguageSettings.updateLocale(getApplicationContext());

        setContentView(R.layout.activity_user_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_selectgame, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Get info sent from SurveyActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("USER_ID");
        } else {
            userID = null;
        }
    }

    public static String getUserID() {
        return userID;
    }
}
