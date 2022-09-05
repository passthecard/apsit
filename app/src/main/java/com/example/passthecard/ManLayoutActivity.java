package com.example.passthecard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class ManLayoutActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_layout);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));

        drawerLayout = findViewById(R.id.drawer_layout);


    }

    public void ClickMenu (View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer (DrawerLayout drawerLayout){
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo (View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer (DrawerLayout drawerLayout){
        //closedrawer layout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);


        }

    }
    public void ClickHome (View view){
        //recreate activity
        recreate();

    }


    public void Division (View view){
        redirectActivity(this, MainActivitycompose.class);


    }
    public void Rules (View view){


    }
    public void ClickAboutUs (View view){


    }
    public void clicksupport (View view){

    }

    public void redirectActivity (Activity activity, Class aclass){
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    @Override
    protected void onPause () {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    }

