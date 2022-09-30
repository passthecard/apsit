package com.example.passthecard;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activityb extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArrayList<studentdetailsmodel> studentdetailsmodelArrayList;
    private sdrvadapter sdrvadapter;
    private ProgressDialog progressDialog;
    private com.example.passthecard.sdrvadapter.Recyclerviewlistner listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityb);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));

        setOnclicklistner();





        progressDialog=new ProgressDialog(Activityb.this);
        progressDialog.setCancelable(false);

        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Fetching Data.Please Make sure you have a stable internet connection");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        recyclerView=findViewById(R.id.recylerview);

        recyclerView.setLayoutManager(new GridLayoutManager(Activityb.this,1));
        recyclerView.setHasFixedSize(true);

        studentdetailsmodelArrayList=new ArrayList<>();

        clearall();


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Details of B");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                clearall();
                for(DataSnapshot snapshot: datasnapshot.getChildren()){

                    studentdetailsmodel studentdetailsmmodel= new studentdetailsmodel();

                    studentdetailsmmodel.setFirstname(snapshot.child("first_name").getValue().toString());
                    studentdetailsmmodel.setLastname(snapshot.child("last_name").getValue().toString());
                    studentdetailsmmodel.setMoodleid(snapshot.child("moodle_id").getValue().toString());

                    studentdetailsmmodel.setSrno(snapshot.getKey().toString());

                    studentdetailsmodelArrayList.add(studentdetailsmmodel);
                }

                sdrvadapter=new sdrvadapter(getApplicationContext(),studentdetailsmodelArrayList,listner);

                recyclerView.setAdapter(sdrvadapter);

                sdrvadapter.notifyDataSetChanged();
                progressDialog.dismiss();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressDialog.dismiss();


                Toast.makeText(Activityb.this,"Error"+ error, Toast.LENGTH_LONG).show();


            }
        });


    }

    private void setOnclicklistner() {
        listner=new sdrvadapter.Recyclerviewlistner() {
            @Override
            public void onClick(View v, int position) {

                Intent intent= new Intent(getApplicationContext(),flagthem.class);
                intent.putExtra("firstname",studentdetailsmodelArrayList.get(position).getFirstname());
                intent.putExtra("lastname",studentdetailsmodelArrayList.get(position).getLastname());
                intent.putExtra("moodleid",studentdetailsmodelArrayList.get(position).getMoodleid());
                intent.putExtra("srno",studentdetailsmodelArrayList.get(position).getSrno());
                startActivity(intent);


            }
        };


    }

    private void clearall() {


        if(studentdetailsmodelArrayList!=null)
        {

            studentdetailsmodelArrayList.clear();


            if(sdrvadapter!=null){

                sdrvadapter.notifyDataSetChanged();

            }
        }
        studentdetailsmodelArrayList=new ArrayList<>();
    }

}