package com.example.appp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class Agree extends AppCompatActivity {

//    CheckBox cb_agree1, cb_agree2;
//    Button btn_okay,btn_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);


        Button   btn_okay = findViewById(R.id.btn_okay);
        Button  btn_no = findViewById(R.id.btn_no);
        CheckBox  cb_agree1 = findViewById(R.id.cb_agree1);
        CheckBox  cb_agree2 = findViewById(R.id.cb_agree2);


        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Agree.this, Register.class);
                startActivity(intent);

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( Agree.this, Login.class);
                startActivity(intent);

            }
        });
    }
}