package com.example.appp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.appp.Login.MYid;


public class Setting extends AppCompatActivity {

    String mypicture = null;

    Button btn_Schedule, btn_Main, btn_setting,btn_calander;

    TextView profilename, profilenumber, tv_selectedinstru, tv_logout, tv_myprofil;
    ImageView profilepicture;

    SharedPreferences sharedPreferences;




    @Override
    protected void onStart() {
        super.onStart();
       // load();
       // Log.d("SETTING", "onStart ");
    }

    //////////////////////이미지 가져오기 /////////////////////////
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Glide.with(this)  /////어느 화면에 띄울건지
                .load(data.getData()) //// 갤러리에서 인텐트로 받아온 이미지

                .into(profilepicture);///어느 공간에 넣을지

        mypicture = data.getData().toString();
        Log.d(" resetting 사진", "사진 " + mypicture);




            sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
            String json = sharedPreferences.getString("user", "");
            try {
                JSONArray jsonArray = new JSONArray(json);


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String id = jsonObject.getString("id");
                    Log.d("=resetting", "가져온 아이디 " + id);

                    if (MYid.equals(id)) {

                        String getmypicture = jsonObject.getString("mypicture");
                        Log.d("resetting", "원래 사진 " + getmypicture);
                        Log.d("resetting", "가져온 사진 " + mypicture);

                        if (getmypicture.equals(mypicture)){



                        }else{
                            jsonObject.put("mypicture", mypicture );
                            Log.d("resetting", "가져온 사진 " + mypicture);



                            String profile = jsonArray.toString();
                            sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user", profile);
                            editor.commit();

                        }

                        break;

                    }else {
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        Intent intent = getIntent();
        final String getmypicture = intent.getStringExtra("mypicture");
        final String getid = intent.getStringExtra("id");



        btn_Schedule = findViewById(R.id.btn_Schedule);
        btn_Main = findViewById(R.id.btn_Main);
        btn_setting = findViewById(R.id.btn_setting);

        profilename = findViewById(R.id.profilename);
        profilenumber = findViewById(R.id.profilenumber);
        tv_selectedinstru = findViewById(R.id.tv_selectedinstru);
        profilepicture = findViewById(R.id.profilepicture);
        tv_logout = findViewById(R.id.tv_logout);
        tv_myprofil = findViewById(R.id.tv_myprofil);
        btn_calander = findViewById(R.id.btn_Memory);
        load();

        Log.d("사진", "사진 " + profilepicture.toString());


        profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });



        btn_calander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, Memory.class);
                intent.putExtra("id",getid);
                intent.putExtra("mypicture",getmypicture);
                startActivity(intent);
            }
        });



        btn_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
//                String json = sharedPreferences.getString("user", "");
//                try {
//                    JSONArray jsonArray = new JSONArray(json);
//
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                        String id = jsonObject.getString("id");
//                        Log.d("=jsonarray", "가져온 아이디 " + id);
//
//                        if (MYid.equals(id)) {
//                           String mypicture = jsonObject.getString("mypicture");


                            Intent intent = new Intent(Setting.this, Myschedule.class);

                            intent.putExtra("mypicture" ,mypicture);
                            Log.d("사진간다", "사진 " + mypicture);
                            startActivity(intent);

//                       }
//                    }



//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


            }
        });

        btn_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, Home.class);
                intent.putExtra("mypicture" ,mypicture);
                Log.d("사진간다", "사진 " + mypicture);
                startActivity(intent);
            }
        });


    }

  private void load(){
      Log.d(" resetting 로드", "로드시작 ");

      sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
      String json = sharedPreferences.getString("user", "");
      try {
          JSONArray jsonArray = new JSONArray(json);


          for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);

              String id = jsonObject.getString("id");
              Log.d("=jsonarray", "가져온 아이디 " + id);

              if (MYid.equals(id)) {
                  String number = jsonObject.getString("number");
                  String instrument = jsonObject.getString("instrument");
                  String mypicture = jsonObject.getString("mypicture");
                  String name = jsonObject.getString("name");
                  Log.d("아이디", "아이디 " + number);
                  Log.d(" resetting 사진", "사진 " + mypicture);


                  profilename.setText(name);
                  profilenumber.setText(number);
                  tv_selectedinstru.setText(instrument);

                  Glide.with(this)
                          .load(Uri.parse(mypicture))
                          .into(profilepicture);

                  break;
              }else {
              }

          }


      } catch (JSONException e) {
          e.printStackTrace();
      }


  }


}