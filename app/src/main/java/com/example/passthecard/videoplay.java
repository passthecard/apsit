package com.example.passthecard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class videoplay extends AppCompatActivity {
    public VideoView videoview;
    private String strvideouri;
    private Button proceed;
    private String div;
    private int select=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
        strvideouri=getIntent().getStringExtra("videouri");
        videoview=findViewById(R.id.videoview);
        proceed=findViewById(R.id.proceed);
        Uri videouri= Uri.parse(strvideouri);
        videoview.setVideoURI(videouri);
        videoview.start();
        SharedPreferences getshrd= getSharedPreferences("idk",MODE_PRIVATE);
        div=getshrd.getString("div","");

        if(div.equals("A"))
        {
            select=0;
        }
        else if(div.equals("B"))
        {
            select=1;
        }
        else {
            select=2;
        }



        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(select==0)
                {
                    Intent intent= new Intent(videoplay.this, activitya.class);
                    intent.putExtra("videouri",strvideouri);
                    startActivity(intent);
                }
                else if(select==1) {
                    Intent intent = new Intent(videoplay.this, Activityb.class);
                    intent.putExtra("videouri",strvideouri);
                    startActivity(intent);

                }
                else if(select==2)
                {
                    Intent intent = new Intent(videoplay.this, Activityc.class);
                    intent.putExtra("videouri",strvideouri);
                    startActivity(intent);

                }

            }
        });


    }
}