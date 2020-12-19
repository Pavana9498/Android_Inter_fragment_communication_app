package com.example.project_3_a1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.net.URI;

public class MainActivity extends Activity {

    private static final String  PERMISSION = "edu.uic.cs478.sp2020.project3";
    private static final String EXTRAS_NAME ="BUTTON";
    final int REQUEST_CODE = 1;
    String BUTTON_CLICKED = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button restaurants = findViewById(R.id.button1);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You selected Restaurants!",
                        Toast.LENGTH_SHORT)
                        .show();

//                Log.d("click", "here"+R.id.button1);
                BUTTON_CLICKED = "Restaurants";
//                Intent i = new Intent();
                checkPermissionForBroadcast(BUTTON_CLICKED);
//                i.putExtra("BUTTON", "Restaurants");
//                sendBroadcast(i);
            }
        });

        Button attractions = findViewById(R.id.button2);
        attractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You selected Attractions!",
                        Toast.LENGTH_SHORT)
                        .show();

                BUTTON_CLICKED = "Attractions";
//                Log.d("click", "here"+R.id.button2);
                checkPermissionForBroadcast(BUTTON_CLICKED);

//                Intent i = new Intent();
//                i.putExtra("BUTTON", "Attractions");
//                sendBroadcast(i);
            }
        });
    }

    protected void checkPermissionForBroadcast(String button) {
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, PERMISSION);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.i("Permission Click", "granted");
            Intent i = new Intent(PERMISSION);
            i.putExtra(EXTRAS_NAME, button);
            sendBroadcast(i);
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSION},
                    REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    Log.i("Request Perm Click", "Permission requested adn granted");
                    Intent i = new Intent(PERMISSION);
                    i.putExtra(EXTRAS_NAME, BUTTON_CLICKED);
                    sendBroadcast(i, null);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }
        }

    }
}

