package com.aidawhale.tfmarcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aidawhale.tfmarcore.client.InternetCheck;
import com.aidawhale.tfmarcore.client.OnSurveyPetitionResponse;
import com.aidawhale.tfmarcore.client.OnUserPetitionResponse;
import com.aidawhale.tfmarcore.client.RestService;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.User;
import com.aidawhale.tfmarcore.room.ViewModels.LoadingActivityViewModel;
import com.aidawhale.tfmarcore.utils.DateConverter;

import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;

public class LoadingActivity extends AppCompatActivity {

    private String userid = null;

    private LoadingActivityViewModel loadingViewModel;
    private LifecycleOwner lifecycleOwner;

    private Context context;

    private boolean userAlreadyChecked;
    private boolean surveyAlreadyChecked, surveyAlreadyCheckedLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        context = getApplicationContext();

        // Get a new or existing ViewModel from the ViewModelProvider.
        loadingViewModel = new ViewModelProvider(this).get(LoadingActivityViewModel.class);
        lifecycleOwner = this;

        userAlreadyChecked = false;
        surveyAlreadyChecked = false;
        surveyAlreadyCheckedLocal = false;

        // Get info sent from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("USER_ID") != null) {
            userid = extras.getString("USER_ID");
        } else {
            // userid should't be null at this point
            Toast.makeText(context, R.string.error_scanning_code, Toast.LENGTH_SHORT).show();
            finish();
        }

        loadingViewModel.getUserByID(userid).observe(this, user -> {
            if (userAlreadyChecked) {
                return;
            }
            userAlreadyChecked = true;

            // Check if internet available
            new InternetCheck(internet -> {
                if (internet) {
                    Log.d("LoadingActivity InternetCheck", "internet true");
                    onlineLogin(user, userid);
                } else {
                    Log.d("LoadingActivity InternetCheck", "internet false");
                    showNoInternetMessage();
                    // Continue with normal login
                    localLogin(user, userid);
                }
            });
        });

    }

    private void localLogin(User user, String userid) {
        Log.d("LoadingActivity localLogin", "remoteUser false, user ?");

        if (user == null) { // First login
            Log.d("LoadingActivity localLogin", "remoteUser false, user false");

            // Make survey && ask for permission
            Intent intent = new Intent(context, SurveyActivity.class);
            intent.putExtra("USER_ID", userid); // null -> create user
            intent.putExtra("ASK_FOR_PERMISSION", "true");
            intent.putExtra("INTERNET", "false"); // so don't send survey answer online
            intent.putExtra("REMOTE_USER", "false");
            startActivity(intent);
            finish(); // Don't add this activity to back stack
        } else { // User exists on local DB
            Log.d("LoadingActivity localLogin", "remoteUser false, user true");

            if (!user.storagePermission) {
                loadMenuActivityWithoutID();
            }

            // Get daily survey
            String date = DateConverter.complexDateToSimpleDate(new Date());
            loadingViewModel.getDailySurveyByUser(user.userID, date).observe(this, survey -> {
                if(surveyAlreadyCheckedLocal) {
                    return;
                }
                surveyAlreadyCheckedLocal = true;

                if(survey == null) {
                    // Make survey
                    Intent intent = new Intent(context, SurveyActivity.class);
                    intent.putExtra("USER_ID", userid);
                    intent.putExtra("ASK_FOR_PERMISSION", "false");
                    intent.putExtra("INTERNET", "false"); // so don't send survey answer online
                    intent.putExtra("REMOTE_USER", "false");
                    startActivity(intent);
                    finish(); // Don't add this activity to back stack
                } else {
                    // Don't make survey (already done)
                    loadMenuActivityWithID(user.userID);
                }
            });
        }
    }

    private void onlineLogin(User user, String userid) {
        Log.d("LoadingActivity onlineLogin", "get remote user by id " + userid);

        // Search USER on RemoteDB
        RestService.getUserById(userid.trim(), new OnUserPetitionResponse() {
            @Override
            public void onPetitionResponse(User remoteUser) { // Found user on remote DB
                Log.d("LoadingActivity getUserById", "Found user on remote DB");

                // Check if user exists on local DB
                if(user == null) { // user doesn't exist on local DB
                    // Create user on local DB -> user = remoteUser
                    loadingViewModel.insert(remoteUser);
                } else { // user exists on local DB
                    // Update local user -> user = remoteUser
                    loadingViewModel.update(remoteUser);
                }

                // Check storage permission
                if(remoteUser.storagePermission) {

                    /*
                    * Check if daily survey done (locally and/or on remote)
                    *
                    * Check local survey first
                    *   if localSurvey is done: doDailySurvey = false
                    *   else
                    *       if remoteSurvey is done:
                    *           (get survey on petition)
                    *           save remoteSurvey on local
                    *           doDailySurvey = false
                    *       else doDailySurvey = true
                    * */

                    // Get daily survey (local)
                    String date = DateConverter.complexDateToSimpleDate(new Date());
                    loadingViewModel.getDailySurveyByUser(remoteUser.userID, date).observe(lifecycleOwner, survey -> {
                        if(surveyAlreadyChecked) {
                            return;
                        }
                        surveyAlreadyChecked = true;

                        if (survey != null) {
                            Log.d("LoadingActivity getDailySurveyByUser", "Daily survey found on local");
                            // Don't make survey (already done)
                            loadMenuActivityWithID(remoteUser.userID);
                        } else {
                            Log.d("LoadingActivity getDailySurveyByUser", "Daily survey NOT found on local");
                            // Get daily survey (remote)
                            RestService.getTodaysSurvey(remoteUser.userID, new OnSurveyPetitionResponse() {

                                @Override
                                public void onPetitionResponse(Survey survey) { // Remote survey found
                                    Log.d("LoadingActivity getTodaysSurvey", "Daily survey found on REMOTE. Save survey on local.");
                                    // Save on local DB
                                    Survey newSurvey = new Survey(survey.date, remoteUser.userID,
                                            survey.happiness, survey.food, survey.pain);
                                    loadingViewModel.insert(newSurvey);
                                    loadMenuActivityWithID(userid);
                                }

                                @Override
                                public void onPetitionResponseNull() { // Remote survey not found
                                    Log.d("LoadingActivity getTodaysSurvey", "Daily survey NOT found on REMOTE. Load SurveyActivity.");
                                    // Make survey
                                    Intent intent = new Intent(context, SurveyActivity.class);
                                    intent.putExtra("USER_ID", userid);
                                    intent.putExtra("ASK_FOR_PERMISSION", "false");
                                    intent.putExtra("INTERNET", "true"); // send survey answer online
                                    intent.putExtra("REMOTE_USER", "true");
                                    startActivity(intent);
                                    finish(); // Don't add this activity to back stack
                                }

                                @Override public void onPetitionError() { showErrorMessage(); }
                                @Override public void onPetitionWaiting() { showErrorMessage(); }
                                @Override public void onPetitionFailure() { showErrorMessage(); }
                            });
                        }
                    });

                } else {
                    loadMenuActivityWithoutID();
                }
            }

            @Override
            public void onPetitionResponseNull() { // Didn't find user on remote DB
                Log.d("LoadingActivity getUserById", "Didn't find user on remote DB");

                // Check if user exists on local DB
                if (user != null) { // user exists on local DB
                    Log.d("LoadingActivity onPetitionResponseNull", "User exists on local DB");
                    // Send user to remote
                    RestService.sendNewUser(user);
                    if (user.storagePermission) {
                        Log.d("LoadingActivity onPetitionResponseNull", "Make survey (save, send remote survey, show menu)");
                        // Make survey (save, send remote survey, show menu)
                        Intent intent = new Intent(context, SurveyActivity.class);
                        intent.putExtra("USER_ID", user.userID);
                        intent.putExtra("ASK_FOR_PERMISSION", "false");
                        intent.putExtra("INTERNET", "true"); // send survey answer online
                        intent.putExtra("REMOTE_USER", "true"); // user sent a few lines above
                        startActivity(intent);
                        finish(); // Don't add this activity to back stack
                    } else {
                        loadMenuActivityWithoutID();
                    }
                } else { // user doesn't exist on local DB
                    Log.d("LoadingActivity onPetitionResponseNull", "User DOESN'T exist on local DB. Load SurveyActivity.");
                    // Make survey (ask for permission, create user, send user to remote then depending on storage permission show menu or take survey)
                    Intent intent = new Intent(context, SurveyActivity.class);
                    intent.putExtra("USER_ID", userid); // null -> create user
                    intent.putExtra("ASK_FOR_PERMISSION", "true"); // depending on storage permission show menu or take survey
                    intent.putExtra("INTERNET", "true"); // send user + survey answer online
                    intent.putExtra("REMOTE_USER", "false");
                    startActivity(intent);
                    finish(); // Don't add this activity to back stack
                }
            }

            @Override public void onPetitionError() { showErrorMessage(); }
            @Override public void onPetitionWaiting() { showErrorMessage(); }
            @Override public void onPetitionFailure() { showErrorMessage(); }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(context, R.string.server_error_message, LENGTH_LONG).show();
        finish(); // Don't add this activity to back stack
    }

    private void showNoInternetMessage() {
        Toast.makeText(context, R.string.no_internet_message, LENGTH_LONG).show();
    }

    private void loadMenuActivityWithoutID() {
        // Load next activity: UserMenuActivity-SelectGameFragment without sending userid
        Intent intent = new Intent(context, UserMenuActivity.class);
        startActivity(intent);
        finish(); // Don't add this activity to back stack
    }

    private void loadMenuActivityWithID(String userid) {
        // Load next activity: UserMenuActivity-SelectGameFragment sending userid
        Intent intent = new Intent(context, UserMenuActivity.class);
        intent.putExtra("USER_ID", userid);
        startActivity(intent);
        finish(); // Don't add this activity to back stack
    }
}
