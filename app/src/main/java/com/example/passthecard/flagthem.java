package com.example.passthecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class flagthem extends AppCompatActivity {
    private TextView firstnameft,lastnameft,moodleidft,srnoft;
    private ImageView passit;
    private FirebaseAuth mauth;
    private CardView cardloginbtn;
    private Button tologinbtn;
    private String moodleidtopass;
    private FirebaseUser currentuser;
    private String cudiv,spmid;
    private ProgressDialog progressDialog,pg2;
    private String videouri,imageuri,audiopath,witness1,witness2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagthem);
        mauth=FirebaseAuth.getInstance();
        currentuser=FirebaseAuth.getInstance().getCurrentUser();


        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));
        videouri=getIntent().getStringExtra("videouri");
        imageuri=getIntent().getStringExtra("imageuri");
        audiopath=getIntent().getStringExtra("audiopath");
        witness1=getIntent().getStringExtra("witness1");
        witness2=getIntent().getStringExtra("witness2");

        String firstname,lastname,moodleid,srno;
        firstnameft=findViewById(R.id.firstnameft);
        lastnameft=findViewById(R.id.lastnameft);
        moodleidft=findViewById(R.id.moodleidft);
        srnoft=findViewById(R.id.srnoft);
        passit=findViewById(R.id.passit);
        cardloginbtn= findViewById(R.id.cardViewforbutton);
        tologinbtn=findViewById(R.id.tologinbtn);
        moodleidtopass=getIntent().getStringExtra("moodleid").toString();
        progressDialog = new ProgressDialog(flagthem.this);
        pg2= new ProgressDialog(flagthem.this);
        progressDialog.setTitle("Uploading.......");
        progressDialog.setMessage("Card pass is in progress please wait. Make sure you have Stable internet connection ");
        progressDialog.setCanceledOnTouchOutside(false);
        pg2.setTitle("Passing the card");
        pg2.setMessage("Card pass is in progress please wait. Make sure you have Stable internet connection ");



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
                    SharedPreferences gs= getSharedPreferences("idk",MODE_PRIVATE);
                    cudiv=gs.getString("div","");
                    spmid=gs.getString("mid","");

                    uploadaudio();
                    uploadvideo();
                    uploadimage();
                    uploadwitness();


                }
                else
                {
                    Toast.makeText(flagthem.this, "You don't have the card to pass", Toast.LENGTH_SHORT).show();
                    cardloginbtn.setVisibility(view.VISIBLE);
                    return;
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

    private void uploadwitness() {

        if(witness1!=null && witness2!=null)
        {

            pg2.show();

            DatabaseReference db= FirebaseDatabase.getInstance().getReference();
            HashMap<String,String> map= new HashMap<>();
            map.put("prooflink","witness");
            map.put("witness1",witness1);
            map.put("witness2",witness2);
            map.put("from",spmid);
            map.put("to",moodleidtopass);
            db.child("Transaction_"+cudiv).child(String.valueOf(System.currentTimeMillis())).setValue(map);
            db.child("card_"+cudiv).child("current_card").setValue(moodleidtopass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        pg2.dismiss();
                        Toast.makeText(flagthem.this, "Card has been passed to the user", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(flagthem.this, ManLayoutActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mauth.signOut();
                        startActivity(i);

                    }
                    else
                    {
                        Toast.makeText(flagthem.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                    }

                }
            });



        }
    }

    private void uploadaudio() {

        if(audiopath!=null)
        {
            Uri file= Uri.fromFile(new File(audiopath));
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("audio/mpeg")
                    .build();
            final StorageReference reference= FirebaseStorage.getInstance().getReference("File/"+cudiv+System.currentTimeMillis());
            reference.putFile(file,metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());

                    String downloaduri= uriTask.getResult().toString();
                    DatabaseReference db2= FirebaseDatabase.getInstance().getReference();
                    HashMap<String,String> map=  new HashMap<>();
                    map.put("proofLink",downloaduri);
                    map.put("from",spmid);
                    map.put("To",moodleidtopass);
                    db2.child("Transaction_"+cudiv).child(String.valueOf(System.currentTimeMillis())).setValue(map);
                    db2.child("card_"+cudiv).child("current_card").setValue(moodleidtopass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(flagthem.this, "Proof Uploaded", Toast.LENGTH_SHORT).show();
                            Toast.makeText(flagthem.this, "Card has been passed to the user", Toast.LENGTH_LONG).show();


                            Intent i = new Intent(flagthem.this, ManLayoutActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mauth.signOut();
                            startActivity(i);

                        }
                    });




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(flagthem.this, "Failed"+e, Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    double progress= (100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("uploaded "+ (int)progress+"%");
                    progressDialog.show();

                }
            });

        }



    }

    private void uploadimage() {
        if(imageuri!=null)
        {
            final StorageReference reference= FirebaseStorage.getInstance().getReference("File/"+cudiv+System.currentTimeMillis());
            reference.putFile(Uri.parse(imageuri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());

                    String downloaduri= uriTask.getResult().toString();
                    DatabaseReference db2= FirebaseDatabase.getInstance().getReference();
                    HashMap<String,String> map=  new HashMap<>();
                    map.put("proofLink",downloaduri);
                    map.put("from",spmid);
                    map.put("To",moodleidtopass);
                    db2.child("Transaction_"+cudiv).child(String.valueOf(System.currentTimeMillis())).setValue(map);
                    db2.child("card_"+cudiv).child("current_card").setValue(moodleidtopass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(flagthem.this, "Proof Uploaded", Toast.LENGTH_SHORT).show();
                            Toast.makeText(flagthem.this, "Card has been passed to the user", Toast.LENGTH_LONG).show();


                            Intent i = new Intent(flagthem.this, ManLayoutActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mauth.signOut();
                            startActivity(i);

                        }
                    });




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(flagthem.this, "Failed"+e, Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    double progress= (100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("uploaded "+ (int)progress+"%");
                    progressDialog.show();

                }
            });

        }

    }




    private void uploadvideo()
    {
        if(videouri!=null)
        {
            final StorageReference reference= FirebaseStorage.getInstance().getReference("File/"+cudiv+System.currentTimeMillis());
            reference.putFile(Uri.parse(videouri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());

                        String downloaduri= uriTask.getResult().toString();
                        DatabaseReference db2= FirebaseDatabase.getInstance().getReference();
                        HashMap<String,String> map=  new HashMap<>();
                        map.put("proofLink",downloaduri);
                        map.put("from",spmid);
                        map.put("To",moodleidtopass);
                        db2.child("Transaction_"+cudiv).child(String.valueOf(System.currentTimeMillis())).setValue(map);
                        db2.child("card_"+cudiv).child("current_card").setValue(moodleidtopass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(flagthem.this, "Proof Uploaded", Toast.LENGTH_SHORT).show();
                                Toast.makeText(flagthem.this, "Card has been passed to the user", Toast.LENGTH_LONG).show();


                                Intent i = new Intent(flagthem.this, ManLayoutActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mauth.signOut();
                                startActivity(i);

                            }
                        });




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(flagthem.this, "Failed"+e, Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    double progress= (100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("uploaded "+ (int)progress+"%");
                    progressDialog.show();

                }
            });

        }

    }

}