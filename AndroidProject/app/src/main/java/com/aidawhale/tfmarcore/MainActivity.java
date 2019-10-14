package com.aidawhale.tfmarcore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
            if (result.getContents() != null){
                // Load next activity
                Intent intent = new Intent(context, UserMenuActivity.class);
                intent.putExtra("USER_ID", result.getContents());
                context.startActivity(intent);
            }else{
                // Error while scanning code
                Toast.makeText(MainActivity.this, "Error while scanning code. Please, try again.", Toast.LENGTH_SHORT).show();
            }
    }
}
