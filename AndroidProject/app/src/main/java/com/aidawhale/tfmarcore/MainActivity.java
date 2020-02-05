package com.aidawhale.tfmarcore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.aidawhale.tfmarcore.client.InternetCheck;
import com.aidawhale.tfmarcore.client.OnUserPetitionResponse;
import com.aidawhale.tfmarcore.client.RestService;
import com.aidawhale.tfmarcore.room.AppRoomDatabase;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.SurveyDao;
import com.aidawhale.tfmarcore.room.User;
import com.aidawhale.tfmarcore.room.UserDao;
import com.aidawhale.tfmarcore.utils.DateConverter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Date;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "OnCreate: started.");

        // Button for accesing without scanning QR
        Button btn =  findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserMenuActivity.class);
                context.startActivity(intent);
            }
        });

        // Button for accesing with scanning QR
        ImageButton imgBtn =  findViewById(R.id.imageButton);
        imgBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Zxing: open camara and scan QR
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        // Check shared preferences "appLanguage" to set app locale
        SharedPreferences sharedPreferences = getSharedPreferences("appLanguage", Context.MODE_PRIVATE);
        String lang_to_load = sharedPreferences.getString("language", "null"); // "null" is returned default value if "language" doesn't exist on sharedPreferente
        if(lang_to_load == "null") { // if nothing stored on sharedPreferences
            return; // do nothing
        }
        updateLocale(lang_to_load);
        btn.setText(R.string.access_without_qr); // Update button string because of the updateLocale
    }

    private void updateLocale(String lang_to_load) {

        // Update locale
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration configuration = res.getConfiguration();
        configuration.setLocale(new Locale(lang_to_load.toLowerCase()));
        res.updateConfiguration(configuration, displayMetrics);
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
        // Show dialog with app info and a dropdown to switch language
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        final Spinner spinner = view.findViewById(R.id.dialog_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.i8n_languages));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dialog.setTitle(R.string.app_info);
        dialog.setMessage(R.string.app_info_text);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (spinner.getSelectedItemPosition() != 0) { // Ignore first entry
                    String language_code;

                    switch (spinner.getSelectedItem().toString()) {
                        case "Galego":
                            language_code = "gl";
                            break;
                        case "Castellano":
                            language_code = "es";
                            break;
                        case "English":
                            language_code = "en";
                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + spinner.getSelectedItem().toString());
                    }

                    // Update shared preferences with new locale
                    SharedPreferences sharedPreferences = getSharedPreferences("appLanguage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("language", language_code);
                    editor.commit();

                    updateLocale(language_code);

                    // Reload activity
                    Intent intent = getIntent();
                    overridePendingTransition(0,0); // Don't show animation between transitions
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(intent);
                }
            }
        });

        dialog.setView(view);

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null && result.getContents() != null) {

            // TODO show loading screen
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();

            String userid = result.getContents();

            // Check database and get user info
            AppRoomDatabase db = AppRoomDatabase.getDatabase(context);
            SurveyDao surveyDao = db.surveyDao();
            UserDao userDao = db.userDao();
            User user = userDao.getDirectUserById(userid);

            // Check if internet available
            new InternetCheck(internet -> {
                if(internet) {
                    Log.d("MainActivity InternetCheck", "internet true");
                    // Search USER on RemoteDB
                    RestService.getUserById(userid.trim(), new OnUserPetitionResponse() {
                        @Override
                        public void onPetitionResponse(User remoteUser) { // Found user on remote DB
                            // Check if user exists on local DB
                            if(user == null) {
                                Log.d("MainActivity onPetitionResponse", "remoteUser true user false");
                                // user doesn't exist on local DB, but exists on remote DB
                                login(null, remoteUser, userid, surveyDao);
                            } else {
                                Log.d("MainActivity onPetitionResponse", "remoteUser true user true");
                                // user exists on local and remote DB
                                user.storagePermission = remoteUser.storagePermission;
                                user.height = remoteUser.height;
                                user.difficultyLevel = remoteUser.difficultyLevel;
                                // TODO Update user local data
                                // userDao.updateUser(userid, user.storagePermission, user.difficultyLevel, user.height);

                                // Continue with normal login
                                login(user, remoteUser, userid, surveyDao);
                            }
                        }

                        @Override
                        public void onPetitionResponseNull() { // Didn't find user on remote DB
                            Log.d("MainActivity onPetitionResponse", "remoteUser false user ?");
                            // Continue with normal login
                            login(user, null, userid, surveyDao);
                        }

                        @Override public void onPetitionError() { showErrorMessage(); }
                        @Override public void onPetitionWaiting() { showErrorMessage(); }
                        @Override public void onPetitionFailure() { showErrorMessage(); }
                    });
                } else {
                    Log.d("MainActivity InternetCheck", "internet false");
                    showNoInternetMessage();
                    // Continue with normal login
                    login(user, null, userid, surveyDao);

                }
            });

        } else {
            // Error while scanning code
            Toast.makeText(MainActivity.this, R.string.error_scanning_code, Toast.LENGTH_SHORT).show();
        }
    }

    private void login(User user, User remoteUser, String userid, SurveyDao surveyDao) {
        // NOTE: if user == null, there is no user on local DB
        // NOTE: remoteUser == null means
        //   1) there is no user on remote DB
        //    or
        //   2) there is no internet connection

        // TODO hide loading screen

        // Login process
        if(user == null) {
            if (remoteUser == null) { // First login ever
                Log.d("MainActivity login", "remoteUser false, user false");
                // Load next activity: SurveyActivity
                Intent intent = new Intent(context, SurveyActivity.class);
                intent.putExtra("USER_ID", userid);
                intent.putExtra("REMOTE_USER", "false");
                intent.putExtra("REMOTE_SURVEY", "false");
                context.startActivity(intent);
            } else { // user exists on remote, doesn't exist on local
                if(remoteUser.storagePermission) {
                    Log.d("MainActivity login", "remoteUser true, user false, storage true");
                    boolean doSurvey = true;
                    // TODO check if survey was made on remote
                    if (doSurvey) {
                        // Do survey
                        Log.d("MainActivity login", "remote storage true");// Load next activity: SurveyActivity
                        Intent intent = new Intent(context, SurveyActivity.class);
                        intent.putExtra("USER_ID", userid);
                        intent.putExtra("REMOTE_USER", "true");
                        intent.putExtra("REMOTE_SURVEY", "true");
                        context.startActivity(intent);
                    } else {
                        Log.d("MainActivity login", "remote storage false");
                        // Don't do survey
                        // TODO Save user on local DB (storagePermission = TRUE)
                        User newUser = new User(userid);
                        newUser.storagePermission = true;
                        // newUser.difficultyLevel = remoteUser.difficultyLevel;
                        // newUser.height = remoteUser.height;

                        Log.d("MainActivity login", "load SelectGameFragment");
                        // Load next activity: UserMenuActivity-SelectGameFragment without sending userid
                        Intent intent = new Intent(context, UserMenuActivity.class);
                        context.startActivity(intent);
                    }
                } else {
                    Log.d("MainActivity login", "remoteUser true, user false, storage false");
                    // Don't do survey
                    // TODO Save user on local DB (storagePermission = FALSE)
                    User newUser = new User(userid);
                    newUser.storagePermission = false;

                    Log.d("MainActivity login", "load SelectGameFragment");
                    // Load next activity: UserMenuActivity-SelectGameFragment without sending userid
                    Intent intent = new Intent(context, UserMenuActivity.class);
                    context.startActivity(intent);
                }
            }
        } else { // User was already on the DB
            if(user.storagePermission) { // Allowed data collection
                Log.d("MainActivity login", "remoteUser ?, user true, storage true");
                // Check already made daily survey
                String date = DateConverter.complexDateToSimpleDate(new Date());

                Survey survey = surveyDao.getDirectDailySurveyByUser(userid, date);
                // TODO check if survey was made on remote

                if(survey == null) { // Do daily survey
                    Log.d("MainActivity login", "load SurveyActivity (survey not done yet)");
                    // Load next activity: SurveyActivity
                    Intent intent = new Intent(context, SurveyActivity.class);
                    intent.putExtra("USER_ID", userid);
                    if (remoteUser != null) {
                        intent.putExtra("REMOTE_USER", "true");
                    } else {
                        intent.putExtra("REMOTE_USER", "false");
                    }
                    intent.putExtra("REMOTE_SURVEY", "false");
                    context.startActivity(intent);

                } else { // Daily survey done
                    Log.d("MainActivity login", "load SelectGameFragment (survey already done)");
                    // Load next activity: UserMenuActivity-SelectGameFragment
                    Intent intent = new Intent(context, UserMenuActivity.class);
                    intent.putExtra("USER_ID", userid);
                    context.startActivity(intent);
                }
            } else { // Refused to collect data
                Log.d("MainActivity login", "remoteUser ?, user true, storage false");
                Log.d("MainActivity login", "load SelectGameFragment");
                // Load next activity: UserMenuActivity-SelectGameFragment without sending userid
                Intent intent = new Intent(context, UserMenuActivity.class);
                context.startActivity(intent);
            }
        }
    }

    private void showErrorMessage() {
        Toast.makeText(context, R.string.server_error_message, LENGTH_LONG).show();
    }

    private void showNoInternetMessage() {
        Toast.makeText(context, R.string.no_internet_message, LENGTH_LONG).show();
    }

}