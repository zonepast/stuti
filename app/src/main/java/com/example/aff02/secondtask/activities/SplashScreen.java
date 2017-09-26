package com.example.aff02.secondtask.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.example.aff02.secondtask.R;
import com.example.aff02.secondtask.database.DatabaseHelper;

public class SplashScreen extends AppCompatActivity {

    public ImageView mSplash;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    public static final String PREFS_NAME = "MyPrefs";
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().setTitle("Teacher - Student");

        mSplash = (ImageView)findViewById(R.id.imgSplash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

            }
        }, SPLASH_DISPLAY_LENGTH);

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }
    }
    }
