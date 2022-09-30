package com.example.passthecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class flagthem extends AppCompatActivity {
    private TextView firstnameft,lastnameft,moodleidft,srnoft;
    private ImageView passit;
    private FirebaseAuth mauth;
    private CardView cardloginbtn;
    private Button tologinbtn;
    private String moodleidtopass;
    private FirebaseUser currentuser;
    private String cudiv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagthem);
        mauth=FirebaseAuth.getInstance();
        currentuser=FirebaseAuth.getInstance().getCurrentUser();


        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
        String firstname,lastname,moodleid,srno;
        firstnameft=findViewById(R.id.firstnameft);
        lastnameft=findViewById(R.id.lastnameft);
        moodleidft=findViewById(R.id.moodleidft);
        srnoft=findViewById(R.id.srnoft);
        passit=findViewById(R.id.passit);
        cardloginbtn= findViewById(R.id.cardViewforbutton);
        tologinbtn=findViewById(R.id.tologinbtn);
        moodleidtopass=getIntent().getStringExtra("moodleid").toString();

        Bundle extras= getIntent().getExtras();

        if(extras!=null)
        {
            firstnameft.setText(firstname= extras.getString("firstname"));;
            lastnameft.setText(lastname= extras.getString("lastname"));
            moodleidft.setText(moodleid=extras.getString("moodleid"));;
            srnoft.setText(srno= extras.getString("srno"));




        }






        passit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mauth.getCurrentUser()!=null)
                {
                    cudiv=currentuser.getEmail().toLowerCase();
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("card_"+cudiv).child("current_card");
                    db.setValue(moodleidtopass);
                    Toast.makeText(flagthem.this, "Card has been passed to the user", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(flagthem.this, "You are not logged in, or you don't have the card to pass", Toast.LENGTH_SHORT).show();
                    cardloginbtn.setVisibility(view.VISIBLE);
                }


            }
        });

        tologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openlogin= new Intent(flagthem.this,loginstudent.class);
                startActivity(openlogin);

            }
        });






    }
}