package com.example.passthecard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class witness extends AppCompatActivity {
    private EditText witness1, witness2;
    private String strwitness1,strwitness2;
    private Button witnessproceedbtn;
    private String div;
    private int select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witness);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,245,238));

        witness1= findViewById(R.id.witness1);
        witness2= findViewById(R.id.witness2);
        witnessproceedbtn=findViewById(R.id.witnesproceedbtn);

        witnessproceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp =getSharedPreferences("idk",MODE_PRIVATE);
                div=sp.getString("div","");

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


                strwitness1=witness1.getText().toString().trim();
                strwitness2=witness2.getText().toString().trim();


                if(select==0)
                {
                    if(witness1.getText().toString().isEmpty() || witness2.getText().toString().isEmpty())
                    {
                        witness1.setError("This field is compulsory");
                    }
                    else {
                        Intent intent = new Intent(witness.this, activitya.class);
                        intent.putExtra("witness1", strwitness1);
                        intent.putExtra("witness2", strwitness2);
                        startActivity(intent);
                    }
                }
                else if(select==1) {

                    if(witness1.getText().toString().isEmpty()||witness2.getText().toString().isEmpty())
                    {
                        witness1.setError("This field is compulsory");
                    }
                    else
                    {
                        Intent intent = new Intent(witness.this, Activityb.class);
                        intent.putExtra("witness1",strwitness1);
                        intent.putExtra("witness2",strwitness2);
                        startActivity(intent);


                    }


                }
                else if(select==2)
                {

                    if(witness1.getText().toString().isEmpty() || witness2.getText().toString().isEmpty())
                    {
                        witness1.setError("This field is required");
                    }
                    else
                    {

                        Intent intent = new Intent(witness.this, Activityc.class);
                        intent.putExtra("witness1",strwitness1);
                        intent.putExtra("witness2",strwitness2);
                        startActivity(intent);



                    }

                }

            }
        });









    }
}