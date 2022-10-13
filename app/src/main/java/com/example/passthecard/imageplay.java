package com.example.passthecard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class imageplay extends AppCompatActivity {
    private ImageView iv;
    private String strimageuri;
    private Button proceedimg;
    private String div;
    private int select=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageplay);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
        strimageuri=getIntent().getStringExtra("imageuri");
        iv=findViewById(R.id.iv);
        proceedimg=findViewById(R.id.pimg);
        iv.setImageURI(Uri.parse(strimageuri));





        proceedimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                if(select==0)
                {
                    Intent intent= new Intent(imageplay.this, activitya.class);
                    intent.putExtra("imageuri",strimageuri);
                    startActivity(intent);
                }
                else if(select==1) {
                    Intent intent = new Intent(imageplay.this, Activityb.class);
                    intent.putExtra("imageuri",strimageuri);
                    startActivity(intent);

                }
                else if(select==2)
                {
                    Intent intent = new Intent(imageplay.this, Activityc.class);
                    intent.putExtra("imageuri",strimageuri);
                    startActivity(intent);

                }


            }
        });


    }
}