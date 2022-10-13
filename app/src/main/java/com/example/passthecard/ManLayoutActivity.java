package com.example.passthecard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;


public class ManLayoutActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    public FirebaseAuth mauth;
    private TextView navlogin;
    private ImageView navloginlogo;
    private TextView dandt,warning,tvusername,logout;
    private ImageView dandtimg,videoproof,audioproof,witness,photoproof,logoutimglogo;
    private FirebaseUser cu;
    int select=0;
    private int VIDEO_REQUEST=101;
    private int IMAGE_REQUEST=100;
    private static Uri videouri,imageuri;
    private static final int STORAGE_PERMISSION_COE=1;




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
        logout=findViewById(R.id.navlogout);
        logoutimglogo=findViewById(R.id.navlogoutlogo);
        if(mauth.getCurrentUser()==null)
        {
            tvusername.setVisibility(View.GONE);
        }
        else
        {
            //cu=FirebaseAuth.getInstance().getCurrentUser();
            //tvusername.setText("Welcome "+cu.getDisplayName().toString()+" !"+cu.getEmail());

            SharedPreferences getshared= getSharedPreferences("idk",MODE_PRIVATE);
            String username=getshared.getString("username","");
            String moodleid=getshared.getString("mid","");
            String div = getshared.getString("div","");
            tvusername.setText("Welcome "+username+" !");
           /* if(div.equals("A"))
            {
                select=0;
            }
            else if(div.equals("B"))
            {
                select=1;
            }
            else {
                select=2;
            }*/



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
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_COE);

        videoproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(mauth.getCurrentUser()==null)
                {
                    Toast.makeText(ManLayoutActivity.this, "You are not  logged in ", Toast.LENGTH_SHORT).show();
                    return;
                }
               else
               {

                   if(ContextCompat.checkSelfPermission(
                           getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED) {
                       Intent videointent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                       startActivityForResult(videointent, VIDEO_REQUEST);
                   }

                   else
                   {
                       Toast.makeText(ManLayoutActivity.this, "Storage permission denied", Toast.LENGTH_SHORT).show();
                   }

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
                else
                {
                    Intent audioplay= new Intent(ManLayoutActivity.this,audioplay.class);
                    startActivity(audioplay);
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
                else
                {
                   if(ContextCompat.checkSelfPermission(
                           getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED)
                    {
                        Intent imageintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(imageintent,IMAGE_REQUEST);

                    }
                   else
                   {
                       Toast.makeText(ManLayoutActivity.this, "Storage permission denied", Toast.LENGTH_SHORT).show();

                   }




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
                Intent i= new Intent(ManLayoutActivity.this, com.example.passthecard.witness.class);
                startActivity(i);
            }
        });










    }

    private void checkPermission(String permission, int requestcode) {

        if (ContextCompat.checkSelfPermission(ManLayoutActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Storage permission needed");
            alertBuilder.setMessage("The application requires storage permission to save the proof on local storage. The application does not gather user data");
            AlertDialog alert = alertBuilder.create();
            alert.show();
            ActivityCompat.requestPermissions(ManLayoutActivity.this, new String[] { permission }, requestcode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_PERMISSION_COE)
        {
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
           if(requestCode==VIDEO_REQUEST)
           {
               if(resultCode==RESULT_OK)
               {
                   videouri=data.getData();
                   Log.i("video_reccord_tag", String.valueOf(videouri));
                   Intent videoplay=new Intent(ManLayoutActivity.this, com.example.passthecard.videoplay.class);
                   videoplay.putExtra("videouri",videouri.toString());
                   startActivity(videoplay);
               }
           }

           else if(requestCode==IMAGE_REQUEST)
           {
               if(resultCode==RESULT_OK)
               {
                   Bundle extras = data.getExtras();
                   Bitmap imageBitmap = (Bitmap) extras.get("data");
                   Uri tempuri=getImageuri(getApplicationContext(),imageBitmap);
                   Log.i("image_tag",String.valueOf(tempuri));
                   Intent imageplay= new Intent(ManLayoutActivity.this, com.example.passthecard.imageplay.class);
                   imageplay.putExtra("imageuri",tempuri.toString());
                   startActivity(imageplay);

               }
           }





    }

    private Uri getImageuri(Context applicationContext, Bitmap imageBitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), imageBitmap, "Title", null);
        return Uri.parse(path);


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
        redirectActivity(this, aboutus.class);


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

