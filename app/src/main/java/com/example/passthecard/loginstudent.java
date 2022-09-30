package com.example.passthecard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class loginstudent extends AppCompatActivity {

    private Button loginbtn;
    private String midserver;
    private String mid;
    private String mobno;
    private EditText moodleidlogin,divisionlogin,rolnologin;
    private String div;
    private String rolno;
    ProgressDialog progressDialog;
    private String username;
    private String currentcardholder;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginstudent);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
        loginbtn=findViewById(R.id.logbtn);
        moodleidlogin=findViewById(R.id.moodleidlogin);
        divisionlogin=findViewById(R.id.Divisionlogin);
        rolnologin=findViewById(R.id.Rollnologin);
        progressDialog = new ProgressDialog(loginstudent.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Generating OTP");
        progressDialog.setMessage("Processing Please Wait");




        loginbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog.show();

                        mid=moodleidlogin.getText().toString().trim();
                        div=divisionlogin.getText().toString().toUpperCase().trim();
                        rolno=rolnologin.getText().toString().trim();
                        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Details of "+div).child(rolno);
                        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("card_"+div.toLowerCase(Locale.ROOT));

                        dr.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                try{

                                    currentcardholder=snapshot.child("current_card").getValue().toString().trim();
                                    if(!currentcardholder.equals(mid))
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(loginstudent.this, "Can't login. You don't have the card :(", Toast.LENGTH_SHORT).show();
                                        return;

                                    }

                                    else
                                    {

                                        db.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                try {
                                                    mobno=snapshot.child("phone_number").getValue().toString();
                                                    midserver=snapshot.child("moodle_id").getValue().toString().trim();
                                                    username=snapshot.child("first_name").getValue().toString().trim();


                                                    if(!midserver.equals(mid)) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(loginstudent.this, "Moodle Id, Roll no or division did not match :(", Toast.LENGTH_LONG).show();
                                                    }

                                                    else {

                                                        // Toast.makeText(loginstudent.this, "number is" + mobno, Toast.LENGTH_LONG).show();

                                                        //code to send verification

                                                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                                "+91"+mobno,
                                                                60,
                                                                TimeUnit.SECONDS,
                                                                loginstudent.this,
                                                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                                                                {
                                                                    @Override
                                                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                                                    }

                                                                    @Override
                                                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(loginstudent.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                                    }

                                                                    @Override
                                                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                                        progressDialog.dismiss();
                                                                        Intent intent = new Intent(loginstudent.this, newactivity.class);
                                                                        intent.putExtra("mobno", mobno);
                                                                        intent.putExtra("botp", backendotp);
                                                                        intent.putExtra("username",username);
                                                                        intent.putExtra("mid",mid);
                                                                        intent.putExtra("div",div);
                                                                        startActivity(intent);


                                                                    }
                                                                }
                                                        );







                                                    }

                                                }
                                                catch ( Exception e)
                                                {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(loginstudent.this, "Something went wrong. Please add valid details", Toast.LENGTH_SHORT).show();
                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                progressDialog.dismiss();

                                                Toast.makeText(loginstudent.this, error.toString(), Toast.LENGTH_SHORT).show();
                                                return;

                                            }
                                        });
                                    }

                                }
                                catch (Exception e)
                                {

                                    Toast.makeText(loginstudent.this, e.toString(), Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Toast.makeText(loginstudent.this, error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });
















                    }
                }
        );
    }
}