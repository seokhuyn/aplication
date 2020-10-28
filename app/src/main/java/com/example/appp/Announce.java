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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Announce extends AppCompatActivity {


    ImageView im_Gmap;
    TextView tv_Date;
    Spinner spinner_arbtime;

    EditText et_Title, et_Money, et_Address, et_Need;
    Button btn_Ann,btn_no;
    SharedPreferences sharedPreferences;




    @Override
    protected void onStart() {
        super.onStart();
        Log.d("생명주기", "Announce onStart ");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        Log.d("생명주기", "Announce onCreat ");

        et_Title = findViewById(R.id.et_Title);
        tv_Date = findViewById(R.id.tv_Date);
        et_Money = findViewById(R.id.et_Money);
        et_Address = findViewById(R.id.et_Address);
        et_Need = findViewById(R.id.et_Need);
        spinner_arbtime = findViewById(R.id.spinner_arbtime);
        im_Gmap = findViewById(R.id.im_Gmap);

        btn_Ann = findViewById(R.id.btn_Ann);
        btn_no = findViewById(R.id.btn_no);

        im_Gmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Announce.this, Map.class);

                String sub = et_Title.getText().toString();
                String d = tv_Date.getText().toString();
                String m = et_Money.getText().toString();
                String n = et_Need.getText().toString();

                intent.putExtra("sub",sub);
//                intent.putExtra("d",d);
//                intent.putExtra("m",m);
//                intent.putExtra("n",n);


                startActivityForResult(intent, 2);
                //        finish();


            }
        });


        tv_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Announce.this, Myschedule.class);
                startActivity(intent);

            }
        });

        btn_Ann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = et_Title.getText().toString();
                String Date = tv_Date.getText().toString();
                String Money =et_Money.getText().toString();
                String Address = et_Address.getText().toString();
                String Need = et_Need.getText().toString();
                String Time =  (String) spinner_arbtime.getSelectedItem();


                Log.d("shared", "등록" +Title);
                Log.d("shared", "등록" +Date );
                Log.d("shared", "등록" + Money);
                Log.d("shared", "등록" +Address);
                Log.d("shared", "등록" + Need);

                    Intent intent = new Intent( );
                    intent.putExtra("Title",Title);
                    intent.putExtra("Date",Date);
                    intent.putExtra("Money",Money);
                    intent.putExtra("Address",Address);
                    intent.putExtra("Need",Need);
                    intent.putExtra("Time",Time);
                    Log.d("getad", "getAddress" + Address);

                setResult(RESULT_OK,intent);
                Log.d("shared", "리저트 오케이" + Need);
                finish();




            }
        });

    }

//                Announcedataclass announcedataclass = new Announcedataclass();
//                announcedataclass.Title = et_Title.getText().toString();
//                announcedataclass.Date = tv_Date.getText().toString();
//                announcedataclass.Money =et_Money.getText().toString();
//                announcedataclass.Address = et_Address.getText().toString();
//                announcedataclass.Need = et_Need.getText().toString();
//                announcedataclass.Time =  (String) spinner_arbtime.getSelectedItem();
//                announcedatalist.add(announcedataclass);
//                Log.d("getad", "getannouncedatalist" + announcedataclass.toString());
//
//
//                try {
//                    sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
//                    String json = sharedPreferences.getString("announce","");
//                    JSONArray jsonArray = new JSONArray();
//
//                    for (int i= 0; i<announcedatalist.size(); i++ ){
//                        JSONObject jsonObject = new JSONObject();
//
//                        jsonObject.put("Title",announcedatalist.get(i).Title);
//                        jsonObject.put("Date",announcedatalist.get(i).Date);
//                        jsonObject.put("Money",announcedatalist.get(i).Money);
//                        jsonObject.put("Address",announcedatalist.get(i).Address);
//                        jsonObject.put("Need",announcedatalist.get(i).Need);
//                        jsonObject.put("Time",announcedatalist.get(i).Time);
//
//                        jsonArray.put(jsonObject);
//
//                    }
//
//                    sharedPreferences = getSharedPreferences("Useralldata",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("announce",jsonArray.toString());
//                    Log.d("shared", "saveeditor" +jsonArray.toString());
//
//                    editor.apply();


//
//                }catch(JSONException e){
//                    e.printStackTrace();
//                }


        private void init() {

            //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
            final Calendar cal = Calendar.getInstance();

            findViewById(R.id.tv_Date).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatePickerDialog dialog = new DatePickerDialog(Announce.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                            String msg = String.format("%d년 %d월 %d일", year, month + 1, date);
                            makeText(Announce.this, msg, LENGTH_SHORT).show();
                            tv_Date.setText(msg);
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

        if (requestCode ==2 &&resultCode==RESULT_OK){


            String c = data.getStringExtra("address");
            et_Address.setText(c);

        }


    }


    @Override
    protected void onResume() {
        super.onResume();


        Log.d("생명주기" ,"Announce onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("생명주기" ,"Announce onPause 스케줄간다 ");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("생명주기" ,"Announce onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("생명주기" ,"Announce onDestroy ");
    }

}


