package com.example.passthecard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class activitya extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitya);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));


    }
}