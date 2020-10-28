package com.example.appp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Addschedule extends AppCompatActivity {

    EditText title , date ;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addschedule);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(Addschedule.this);
                databaseHelper.addText(title .getText().toString().trim()
                        , date.getText().toString().trim());

                Intent intent = new Intent(Addschedule.this,Memory .class);
                startActivity(intent);

            }
        });

    }
}