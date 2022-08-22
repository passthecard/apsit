package com.example.passthecard;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class Activityb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityb);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
    }
}