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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class ManLayoutActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    public FirebaseAuth mauth;
    private TextView navlogin;
    private ImageView navloginlogo;
    private TextView dandt,warning,tvusername,logout;
    private ImageView dandtimg,videoproof,audioproof,witness,photoproof,logoutimglogo;
    private String username;
    private FirebaseUser cu;
    int select=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_layout);
        mauth=FirebaseAuth.getInstance();




        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
        navlogin=findViewById(R.id.navlogin);
        navloginlogo=findViewById(R.id.navloginlogo);
        tvusername=findViewById(R.id.username);
        dandt=findViewById(R.id.Dandt);
        dandtimg=findViewById(R.id.dandtimg);
        photoproof=findViewById(R.id.photo);
        videoproof=findViewById(R.id.videoproof);
        witness=findViewById(R.id.witness);
        audioproof=findViewById(R.id.audioproof);
        username=getIntent().getStringExtra("username");
        logout=findViewById(R.id.navlogout);
        logoutimglogo=findViewById(R.id.navlogoutlogo);
        if(mauth.getCurrentUser()==null)
        {
            tvusername.setVisibility(View.GONE);
        }
        else
        {
            cu=FirebaseAuth.getInstance().getCurrentUser();
            tvusername.setText("Welcome "+cu.getDisplayName().toString()+" !"+cu.getEmail());
          /*  if(cu.getEmail().equals("DivisionA"))
            {
                select=0;
            }
            else if(cu.getEmail().equals("DivisionB"))
            {
                select=1;
            }
            else {
                select=2;
            }

           */

        }




        drawerLayout = findViewById(R.id.drawer_layout);

        if(mauth.getCurrentUser()!=null)
        {
            navlogin.setVisibility(View.GONE);
            navloginlogo.setVisibility(View.GONE);
            dandtimg.setVisibility(View.GONE);
            dandt.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            logoutimglogo.setVisibility(View.VISIBLE);


        }

        videoproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mauth.getCurrentUser()==null)
                {
                    Toast.makeText(ManLayoutActivity.this, "You are not  logged in ", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(select==0)
                {
                    Intent intent= new Intent(ManLayoutActivity.this, activitya.class);
                    startActivity(intent);
                }
                else if(select==1) {
                    Intent intent = new Intent(ManLayoutActivity.this, Activityb.class);
                    startActivity(intent);

                }
                else if(select==2)
                {
                    Intent intent = new Intent(ManLayoutActivity.this, Activityc.class);
                    startActivity(intent);

                }
            }
        });

        audioproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mauth.getCurrentUser()==null)
                {
                    Toast.makeText(ManLayoutActivity.this, "You are not logged in ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        photoproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mauth.getCurrentUser()==null)
                {
                    Toast.makeText(ManLayoutActivity.this, "You are not logged  in ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


        witness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mauth.getCurrentUser()==null)
                {
                    Toast.makeText(ManLayoutActivity.this, "You are not logged in ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });










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
        redirectActivity(this,rules.class);


    }
    public void ClickAboutUs (View view){


    }
    public void login (View view){
        redirectActivity(this,loginstudent.class);
    }

    public void logout (View view){
        mauth.signOut();
        finish();
    }

    public void redirectActivity (Activity activity, Class aclass){
        Intent intent = new Intent(activity, aclass);
        activity.startActivity(intent);
    }


    @Override
    protected void onPause () {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }
}

