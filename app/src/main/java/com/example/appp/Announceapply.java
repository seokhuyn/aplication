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

import static com.example.appp.Login.MYid;

import static com.example.appp.Profile.userlist;

public class Announceapply extends AppCompatActivity {

    ImageView im_Gmap;
    TextView et_Title,et_Date, et_Money, et_Address, et_Need,tv_spinner;
    Button btn_Ann,btn_apply;
    SharedPreferences sharedPreferences;

    String getpicture ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announceapply);


        try {

            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
            String json = sharedPreferences.getString("announce", "");

            JSONArray jsonArray = new JSONArray(json);
            Log.d("shared", "" + jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id1 = jsonObject.getString("id");
                String mypicture1 = jsonObject.getString("mypicture");

                Log.d("사진왔다", "원래사진" + mypicture1);

                if (MYid.equals(id1)) {
                    Log.d("사진왔다", "원래사진" + mypicture1);
                    Log.d("사진왔다", "원래사진" + getpicture);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }






        tv_spinner = findViewById(R.id.tv_addresscheck);
        et_Title = findViewById(R.id.et_Title);
        et_Date = findViewById(R.id.tv_Date);
        et_Money = findViewById(R.id.et_Money);
        et_Address = findViewById(R.id.et_Address);
        et_Need = findViewById(R.id.et_Need);
        btn_apply = findViewById(R.id.btn_apply);

        btn_Ann = findViewById(R.id.btn_Ann);
        im_Gmap = findViewById(R.id.im_Gmap);

        final Intent intent = getIntent();
        final String Title = intent.getStringExtra("Title");
        final String Date = intent.getStringExtra("Date");
        final String Money = intent.getStringExtra("Money");
        final String Address = intent.getStringExtra("Address");
        final String Need = intent.getStringExtra("Need");
      //  final String Time = intent.getStringExtra("Time");

        et_Title.setText(Title);
        et_Date.setText(Date);
        et_Money.setText(Money);
        et_Address.setText(Address);
        et_Need.setText(Need);
      //  tv_spinner.setText(Time);


        SharedPreferences Latpref = getSharedPreferences("Latpref",MODE_PRIVATE);
        final float  aa = Latpref.getFloat("latitude",0);
        final float  bb = Latpref.getFloat("longitude",0);





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

        try {

            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
            String json = sharedPreferences.getString("announce", "");

            JSONArray jsonArray = new JSONArray(json);
            Log.d("shared", "" + jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String getTitle = jsonObject.getString("Title");


                if (getTitle.equals(Title)) {

                    String mypicture1 = jsonObject.getString("mypicture");

                    getpicture = mypicture1;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        try {

            sharedPreferences = getSharedPreferences("Userapplyeddata",MODE_PRIVATE);
            String json = sharedPreferences.getString("applydata", "");

            JSONArray jsonArray = new JSONArray(json);
            Log.d("shared", "" + jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id1 = jsonObject.getString("id");
                String Title1 = jsonObject.getString("Title");



                if (  Title1.equals(Title)) {

                    if(MYid.equals(id1)){
                        Toast.makeText(this,"이미 지원하신 공지입니다.",Toast.LENGTH_SHORT).show();
                        break;
                    }else{

                    }

                }else {

                    btn_apply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String applyedcode = "applyed" ;



                            userlist.clear();                        //// 저장 데이터가 쌓이기 때문에 클리어

                            userdataclass userdataclass = new userdataclass();
                            userdataclass.Title = Title;
                            userdataclass.Date = Date;
                            userdataclass.Money = Money;
                            userdataclass.Address = Address;
                            userdataclass.Need =  Need;
                          //  userdataclass.Time =Time;
                            userdataclass.id = MYid;
                            userdataclass.mypicture =getpicture ;
                            userdataclass.applyedcode = applyedcode;



                            Log.d("apply", "등록왔다" +  userdataclass.Title);
                            Log.d("apply", "등록왔다" +userdataclass.Date );
                            Log.d("apply", "등록왔다" +   userdataclass.Money);
                            Log.d("apply", "등록왔다" + userdataclass.Address);
                            Log.d("apply", "등록왔다" +  userdataclass.Need );
                            Log.d("apply", "등록왔다" +  userdataclass.id );
                            Log.d("apply", "저장사진" +  userdataclass.mypicture );

                            userlist.add(userdataclass);
//

                            sharedPreferences = getSharedPreferences("Userapplyeddata",MODE_PRIVATE);
                            String json = sharedPreferences.getString("applydata", "");

                            try {
                                JSONArray jsonArray = new JSONArray(json);

                                for (int i = 0; i < userlist.size(); i++) {
                                    JSONObject jsonObject = new JSONObject();

                                    jsonObject.put("Title", userlist.get(i).Title);
                                    jsonObject.put("Date", userlist.get(i).Date);
                                    jsonObject.put("Money", userlist.get(i).Money);
                                    jsonObject.put("Address", userlist.get(i).Address);
                                    jsonObject.put("Need", userlist.get(i).Need);
                                    jsonObject.put("Time", userlist.get(i).Time);
                                    jsonObject.put("id", userlist.get(i).id);
                                    jsonObject.put("mypicture", userlist.get(i).mypicture);
                                    jsonObject.put("applyedcode", userlist.get(i).applyedcode);

                                    jsonArray.put(jsonObject);

                                }

                                sharedPreferences = getSharedPreferences("Userapplyeddata", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("applydata", jsonArray.toString());
                                Log.d("apply", "save" + jsonArray.toString());
                                editor.apply();


                                Intent intent1 = new Intent(Announceapply.this,Home.class);
                                Toast.makeText(Announceapply.this,"지원하였습니다.", Toast.LENGTH_SHORT).show();
                                startActivity(intent1);

                            }catch (JSONException e){

                            }


                        }
                    });


//
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



//

        btn_Ann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(Announceapply.this,"확인하였습니다.", Toast.LENGTH_SHORT).show();
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