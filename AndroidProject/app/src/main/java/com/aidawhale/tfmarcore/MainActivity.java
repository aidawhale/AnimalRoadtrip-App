package com.aidawhale.tfmarcore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
                // Provisional behaviour
                Intent intent = new Intent(context, UserMenuActivity.class);
                context.startActivity(intent);

                // TODO: open camara and scan QR
                // ...
            }
        });
    }
}
