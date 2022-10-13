package com.example.passthecard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class newactivity extends AppCompatActivity {
    TextView demomobile;
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button verifybtn;
    ProgressDialog progressDialog;
    private String botp;
    private String enteredotp;
    private String username;
    private FirebaseAuth mauth;
    private FirebaseUser currentuser;
    private String mid,div,newdiv,rolno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newactivity);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));




        progressDialog = new ProgressDialog(newactivity.this);
        progressDialog.setTitle("Otp verification");
        progressDialog.setMessage("Verifying please wait.......");
        progressDialog.setCanceledOnTouchOutside(false);

        verifybtn=findViewById(R.id.verifybtn);
        demomobile=findViewById(R.id.demomobile);
        demomobile.setText(String.format("+91-%s", getIntent().getStringExtra("mobno")));
        botp=getIntent().getStringExtra("botp");
        username=getIntent().getStringExtra("username");
        mid=getIntent().getStringExtra("mid");
        div=getIntent().getStringExtra("div");
        rolno=getIntent().getStringExtra("rolno");

        newdiv="Division"+div;





        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);


        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (!otp1.getText().toString().trim().isEmpty() && !otp2.getText().toString().trim().isEmpty() && !otp3.getText().toString().trim().isEmpty() && !otp4.getText().toString().trim().isEmpty() && !otp5.getText().toString().trim().isEmpty() && !otp6.getText().toString().trim().isEmpty()) {
                    String enteredotp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();



                    if (botp != null) {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                botp, enteredotp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(newactivity.this, "Welcome "+username, Toast.LENGTH_SHORT).show();

                                    currentuser=FirebaseAuth.getInstance().getCurrentUser();
                                    UserProfileChangeRequest up=new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username)
                                            .build();
                                    currentuser.updateProfile(up);
                                    currentuser.updateEmail(newdiv).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {


                                                Toast.makeText(newactivity.this, "your email is"+ currentuser.getEmail(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });




                                    Intent intent = new Intent(newactivity.this, ManLayoutActivity.class);
                                    SharedPreferences shrd= getSharedPreferences("idk",MODE_PRIVATE);
                                    SharedPreferences.Editor editor= shrd.edit();
                                    editor.putString("username",username);
                                    editor.putString("mid",mid);
                                    editor.putString("div",div);
                                    editor.putString("rollno",rolno);
                                    editor.apply();

                                    intent.putExtra("username",username);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();


                                } else {
                                    Toast.makeText(newactivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                                }


                            }
                        });


                    }
                }
                else
                {
                    Toast.makeText(newactivity.this, "Please enter a valid otp", Toast.LENGTH_LONG).show();
                    return;


                }












                }
        });

        numberotpmove();


    }

    private void numberotpmove() {


        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }





    }
