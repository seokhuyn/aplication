package com.example.appp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Edit extends AppCompatActivity {

    EditText title2, date2 ;
    Button btn_add2 ,btn_delete;
    String id,text, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title2 = findViewById(R.id.title2);
        date2 = findViewById(R.id.date2);
        btn_add2 = findViewById(R.id.btn_add2);
        btn_delete = findViewById(R.id.btn_delete);

        getintentdata();


        btn_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper databaseHelper = new DatabaseHelper(Edit.this);
                databaseHelper.updatedata(id,title2.getText().toString().trim(),date2.getText().toString().trim());
                Log.d("edit", "" +id );
                Log.d("edit", "" + text );
                Log.d("edit", "" + date );

                setResult(RESULT_OK);

                finish();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmdialog();



            }
        });

    }

    void getintentdata (){
        if(getIntent().hasExtra("id")&&getIntent().hasExtra("title")&&getIntent().hasExtra("date")){

            id = getIntent().getStringExtra("id");
            text = getIntent().getStringExtra("title");
            date = getIntent().getStringExtra("date");

            title2.setText(text);
            date2.setText(date);
        }else{

            Toast.makeText(this, "데이터 없음", Toast.LENGTH_SHORT).show();
        }


    }


    void confirmdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제"  );
        builder.setMessage("삭제하시겠습니까? ");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DatabaseHelper databaseHelper = new DatabaseHelper(Edit.this);
                databaseHelper.delete(id);
                finish();

            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        builder.create().show();
    }
}