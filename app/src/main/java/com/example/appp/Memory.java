package com.example.appp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.appp.Login.MYid;
import static com.example.appp.Profile.userlist;


public class Memory extends AppCompatActivity {
    public static final int REQUEST_CODE13 = 1000;

    FloatingActionButton btn ;
    RecyclerView rc;

    DatabaseHelper databaseHelper;
    ArrayList<String> arrayid, arraytitle , arraydate ;

    Memoryadapter memoryadapter;

    SQLiteDatabase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        rc =findViewById(R.id.rc);
        btn =findViewById(R.id.btn);

        databaseHelper = new DatabaseHelper(Memory.this);
        arrayid = new ArrayList<>();
        arraytitle = new ArrayList<>();
        arraydate = new ArrayList<>();


        memoryadapter = new Memoryadapter(Memory.this, arrayid,arraytitle,arraydate);
        rc.setAdapter(memoryadapter);
        rc.setLayoutManager(new LinearLayoutManager(Memory.this));


        // databaseHelper.onCreate(DB);

        storedataarrays();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Memory.this, Addschedule.class);
                startActivity(intent);
            }
        });

    }
    void storedataarrays(){
        Cursor cursor = databaseHelper.readdate() ;
        if(cursor.getCount() ==0){
            Toast.makeText(this, "데이터 없음", Toast.LENGTH_SHORT).show();

        }else{

            while(cursor.moveToNext()){
                arrayid.add(cursor.getString(0));
                arraytitle.add(cursor.getString(1));
                arraydate.add(cursor.getString(2));
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE13){

            recreate();


        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alldelete,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("삭제"  );
            builder.setMessage("삭제하시겠습니까? ");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    DatabaseHelper databaseHelper = new DatabaseHelper(Memory.this);
                    databaseHelper.deleteAll();
                    recreate();

                }
            });
            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            builder.create().show();


        }

        return super.onOptionsItemSelected(item);
    }

}