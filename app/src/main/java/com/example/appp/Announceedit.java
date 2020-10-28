package com.example.appp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Announceedit extends AppCompatActivity {
    Spinner spinner_arbtime;

    TextView tv_Date;
    EditText et_Title, et_Money,et_Address,et_Need;
    Button btn_Ann;
    SharedPreferences sharedPreferences;
    ImageView im_Gmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announceedit);

        tv_Date = findViewById(R.id.tv_Date);

        et_Title =findViewById(R.id.et_Title);
        et_Money =findViewById(R.id.et_Money);
        et_Address =findViewById(R.id.et_Address);
        et_Need =findViewById(R.id.et_Need);

        btn_Ann =findViewById(R.id.btn_Ann);
        spinner_arbtime =findViewById(R.id.spinner_arbtime);
        im_Gmap = findViewById(R.id.im_Gmap);

        Intent intent =getIntent();
        String Title = intent.getStringExtra("Title");
        String Date = intent.getStringExtra("Date");
        String Money = intent.getStringExtra("Money");
        String Address = intent.getStringExtra("Address");
        String Need = intent.getStringExtra("Need");
        String Time = intent.getStringExtra("Time");
        final int Position = intent.getIntExtra("Position",0);
        Log.d("DATE","fsdf"+Date);

        Log.d("shared", "수정왔다" +Title);
        Log.d("shared", "수정왔다" +Date );
        Log.d("shared", "수정왔다" + Money);
        Log.d("shared", "수정왔다" +Address);
        Log.d("shared", "수정왔다" + Need);

        Log.d("shared", "수정왔다" + Position);



        et_Title.setText(Title);
        tv_Date.setText(Date);
        et_Money.setText(Money);
        et_Address.setText(Address);
        et_Need.setText(Need);
        spinner_arbtime.setSelection(Position);


        tv_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        im_Gmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Announceedit.this, Map.class);

                String sub = et_Title.getText().toString();
                String d = tv_Date.getText().toString();
                String m = et_Money.getText().toString();
                String n = et_Need.getText().toString();
                intent.putExtra("sub",sub);
                intent.putExtra("d",d);
                intent.putExtra("m",m);
                intent.putExtra("n",n);


                startActivityForResult(intent, 3);
                //        finish();


            }
        });



        btn_Ann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(Announceedit.this,"수정하였습니다.", Toast.LENGTH_SHORT).show();
//
//                sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//                String json = sharedPreferences.getString("announce", "");

                Intent intent = new Intent();

                String Title = et_Title.getText().toString();
                String Date = tv_Date.getText().toString();
                String Money = et_Money.getText().toString();
                String Address = et_Address.getText().toString();
                String Need = et_Need.getText().toString();
                String Time = (String)spinner_arbtime.getSelectedItem();




                intent.putExtra("Title",Title);
                intent.putExtra("Date",Date);
                intent.putExtra("Money",Money);
                intent.putExtra("Address",Address);
                intent.putExtra("Need",Need);
                intent.putExtra("Time",Time);
                intent.putExtra("Position",Position);

                Log.d("shared", "수정왔다" +Title);
                Log.d("shared", "수정왔다" +Date );
                Log.d("shared", "수정왔다" + Money);
                Log.d("shared", "수정왔다" +Address);
                Log.d("shared", "수정왔다" + Need);

                Log.d("shared", "수정왔다" + Position);

                setResult(RESULT_OK,intent);

                finish();

            }
        });

    }
    private void init() {

        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        final Calendar cal = Calendar.getInstance();


        //DATE PICKER DIALOG
        findViewById(R.id.tv_Date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(Announceedit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d년 %d월 %d일", year, month + 1, date);
                        makeText(Announceedit.this, msg, LENGTH_SHORT).show();

                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                // dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == RESULT_OK) {


            String c = data.getStringExtra("getaddress");
            et_Address.setText(c);

        }
    }
    }