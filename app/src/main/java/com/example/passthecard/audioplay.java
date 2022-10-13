package com.example.passthecard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class audioplay extends AppCompatActivity {
    private ImageView micimage;
    private Button startbtn,stopbtn,playbtn,proceedbtn;
    private int MICROPHONE_CODE=2;
    private MediaRecorder mr;
    private MediaPlayer mp;
    private String pp,cudiv;
    private int select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));

        setContentView(R.layout.activity_audioplay);
        micimage=findViewById(R.id.micimage);
        startbtn=findViewById(R.id.startbtn);
        stopbtn=findViewById(R.id.stopbtn);
        playbtn=findViewById(R.id.playbtn);
        playbtn.setVisibility(View.GONE);
        proceedbtn=findViewById(R.id.proceedaudio);
        proceedbtn.setVisibility(View.GONE);
        SharedPreferences getshrd= getSharedPreferences("idk",MODE_PRIVATE);
        cudiv=getshrd.getString("div","");

        if(cudiv.equals("A"))
        {
            select=0;
        }
        else if(cudiv.equals("B"))
        {
            select=1;
        }
        else {
            select=2;
        }


        if(ismicpresent())
        {
            grantmicpermission();
        }

    }

    public void playaudio(View view) {

        try {
            mp= new MediaPlayer();
            mp.setDataSource(pp);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            Toast.makeText(this, "No proof available to play"+e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
    public void startrecording(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_DENIED)
        {
            grantmicpermission();
            Toast.makeText(this, "please allow microphone access in app settings", Toast.LENGTH_SHORT).show();
        }
        else
        {

            try {
                mr= new MediaRecorder();
                mr.setAudioSource(MediaRecorder.AudioSource.MIC);
                mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                pp=getpath();
                mr.setOutputFile(pp);
                mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mr.prepare();
                micimage.setImageResource(R.drawable.audioproof);
                Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "someerroroccured"+e.toString(), Toast.LENGTH_SHORT).show();
            }
            mr.start();



        }




    }
    public void stoprecording(View view){
        mr.stop();
        mr.release();
        mr=null;
        micimage.setImageResource(R.drawable.micoff);
        playbtn.setVisibility(View.VISIBLE);
        proceedbtn.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show();



    }
    public void proceed(View view){

    }
    private boolean ismicpresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
        {
            return true;}

            else
            {
                return false;
            }

    }

    private void grantmicpermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_CODE);

        }

    }

    private String getpath(){
        ContextWrapper cw= new ContextWrapper(getBaseContext());
        File md= cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file= new File(md,"proofaudio"+".mp3");
        return file.getPath();
    }

   public void proceedbtnclick(View view)
    {

        if(select==0)
        {
            Intent intent= new Intent(this, activitya.class);
            intent.putExtra("audiopath",pp);
            startActivity(intent);
        }
        else if(select==1) {
            Intent intent = new Intent(this, Activityb.class);
            intent.putExtra("audiopath",pp);
            startActivity(intent);

        }
        else if(select==2)
        {
            Intent intent = new Intent(this, Activityc.class);
            intent.putExtra("audiopath",pp);
            startActivity(intent);

        }






    }






}