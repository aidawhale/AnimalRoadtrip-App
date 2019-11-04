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

            String userid = result.getContents();

            // Check database and get user info
            AppRoomDatabase db = AppRoomDatabase.getDatabase(context);
            SurveyDao surveyDao = db.surveyDao();
            UserDao userDao = db.userDao();
            User user = userDao.getDirectUserById(userid);

            if(user == null) { // First login
                // Load next activity: SurveyActivity
                Intent intent = new Intent(context, SurveyActivity.class);
                intent.putExtra("USER_ID", userid);
                context.startActivity(intent);

            } else { // User was already on the DB

                if(user.storagePermission) { // Allowed data collection
                    // Check already made daily survey
                    String date = DateConverter.complexDateToSimpleDate(new Date());

                    Survey survey = surveyDao.getDirectDailySurveyByUser(userid, date);

                    if(survey == null) { // Do daily survey
                        // Load next activity: SurveyActivity
                        Intent intent = new Intent(context, SurveyActivity.class);
                        intent.putExtra("USER_ID", userid);
                        context.startActivity(intent);

                    } else { // Daily survey done
                        // Load next activity: UserMenuActivity-SelectGameFragment
                        Intent intent = new Intent(context, UserMenuActivity.class);
                        intent.putExtra("USER_ID", userid);
                        context.startActivity(intent);
                    }

                } else { // Refused to collect data
                    // Load next activity: UserMenuActivity-SelectGameFragment without sending userid
                    Intent intent = new Intent(context, UserMenuActivity.class);
                    context.startActivity(intent);
                }

            }

        } else {
            // Error while scanning code
            Toast.makeText(MainActivity.this, R.string.error_scanning_code, Toast.LENGTH_SHORT).show();
        }
    }
}