package com.example.passthecard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class flagthem extends AppCompatActivity {
    private TextView firstnameft,lastnameft,moodleidft,srnoft;
    private ImageView passit;
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagthem);
        mauth=FirebaseAuth.getInstance();


        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
        String firstname,lastname,moodleid,srno;
        firstnameft=findViewById(R.id.firstnameft);
        lastnameft=findViewById(R.id.lastnameft);
        moodleidft=findViewById(R.id.moodleidft);
        srnoft=findViewById(R.id.srnoft);
        passit=findViewById(R.id.passit);

        passit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mauth.getCurrentUser()!=null)
                {
                    Toast.makeText(flagthem.this, "Card has been passed to the user", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(flagthem.this, "You are not logged in, or you don't have the card to pass", Toast.LENGTH_SHORT).show();
                }


            }
        });





        Bundle extras= getIntent().getExtras();

        if(extras!=null)
        {
            firstnameft.setText(firstname= extras.getString("firstname"));;
            lastnameft.setText(lastname= extras.getString("lastname"));
            moodleidft.setText(moodleid=extras.getString("moodleid"));;
            srnoft.setText(srno= extras.getString("srno"));



        }
    }
}