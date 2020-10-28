package com.example.appp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Announcecheck extends AppCompatActivity {

    ImageView im_Gmap;
    TextView et_Title,et_Date, et_Money, et_Address, et_Need,tv_addresscheck;
    Button btn_Ann,btn_checkperson;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcecheck);


        tv_addresscheck = findViewById(R.id.tv_addresscheck);
        et_Title = findViewById(R.id.et_Title);
        et_Date = findViewById(R.id.tv_Date);
        et_Money = findViewById(R.id.et_Money);
        et_Address = findViewById(R.id.et_Address);
        et_Need = findViewById(R.id.et_Need);
        btn_checkperson = findViewById(R.id.btn_checkperson);
        im_Gmap = findViewById(R.id.im_Gmap);

        btn_Ann = findViewById(R.id.btn_Ann);

        final Intent intent = getIntent();
        final String Title = intent.getStringExtra("Title");
        final String Date = intent.getStringExtra("Date");
        final String Money = intent.getStringExtra("Money");
        final String Address = intent.getStringExtra("Address");
        final String Need = intent.getStringExtra("Need");
        //final String Time = intent.getStringExtra("Time");


        et_Title.setText(Title);
        et_Date.setText(Date);
        et_Money.setText(Money);
        et_Address.setText(Address);
        et_Need.setText(Need);

        SharedPreferences Latpref = getSharedPreferences("Latpref",MODE_PRIVATE);
        final float  aa = Latpref.getFloat("latitude",0);
        final float  bb = Latpref.getFloat("longitude",0);




        // sharedPreferences = getSharedPreferences("Announce", MODE_PRIVATE);


        im_Gmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    sharedPreferences = getSharedPreferences("getAddress", MODE_PRIVATE);
                    String json = sharedPreferences.getString("address","");

                    JSONArray jsonArray = new JSONArray(json);
                    Log.d("shared", "" + jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Title = jsonObject.getString("Title");

                        if((Title.equals(et_Title.getText().toString()))){
                            String lat = jsonObject.getString("lat");
                            String longi =jsonObject.getString("longi");

                            String uri ="http://maps.google.com/maps?saddr="+aa+","+bb+"&daddr="+lat+","+longi;


                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse(uri));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER );
                            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                            startActivity(intent);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



//
            }
        });

        btn_checkperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Announcecheck.this,Personlist.class);
                intent.putExtra("Title",Title);
                intent.putExtra("Date",Date);
                intent.putExtra("Money",Money);
                intent.putExtra("Address",Address);
                intent.putExtra("Need",Need);
              //  intent.putExtra("Time",Time);

                startActivity(intent);
            }
        });




        btn_Ann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(Announcecheck.this,"확인하였습니다.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


    }
}

/*
    private void showDate (){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

                );
        datePickerDialog.show();

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {

        String date = "month/day/year"+ month+"/"+"/"+ year;
        et_Date.setText(date);

    }
}
/*
                Intent intent = new Intent();
                intent.putExtra("Title",Title);
                intent.putExtra("Date",Date);
                intent.putExtra("Money",Money);
                intent.putExtra("Address",Address);
                intent.putExtra("Need",Need);

                setResult(RESULT_OK,intent);
                finish();
*/