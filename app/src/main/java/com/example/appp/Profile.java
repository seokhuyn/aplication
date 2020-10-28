package com.example.appp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import static com.example.appp.Login.MYid;





public class Profile extends AppCompatActivity {


    private final int GET_GALLERY_IMAGE = 200;

    String mypicture = null;
    ImageView iv_Mypicture;

    EditText et_profilenumber, et_introduce;

    Spinner instrumentspinner;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> spinnerAdapter;

    Button btn_Profilfinish, btn_camera;

    SharedPreferences sharedPreferences;

    SharedPreferences getSharedPreferences;
    static ArrayList<userdataclass> userlist = new ArrayList<>();

//    ArrayList<userdataclass> profilelist = new ArrayList<>();
//
//    class Profiledata{
//        String number;
//        String introduce;
//        String instrument;
//        String mypicture;
//        String Myid;
//
//    }


    //////////////////////이미지 가져오기 /////////////////////////
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {


            Glide.with(this)  /////어느 화면에 띄울건지
                    .load(data.getData()) //// 갤러리에서 인텐트로 받아온 이미지

                    .into(iv_Mypicture);///어느 공간에 넣을지

            mypicture = data.getData().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        Log.d("아이디", "아이디 " + MYid);
        checkSelfPermission();
        load();


        btn_Profilfinish = findViewById(R.id.btn_Profilfinish);
        iv_Mypicture = findViewById(R.id.iv_Mypicture);


        et_profilenumber = findViewById(R.id.et_profilenumber);
        et_introduce = findViewById(R.id.et_introduce);

        instrumentspinner = findViewById(R.id.instrumentspinner);
        spinnerList = new ArrayList<>();
        spinnerList.add("악기선택");
        spinnerList.add("바이올린");
        spinnerList.add("첼로");
        spinnerList.add("비올라");
        spinnerList.add("피아노");
        spinnerList.add("콘트라베이스");
        spinnerList.add("호른");
        spinnerList.add("바순");
        spinnerList.add("클라리넷");
        spinnerList.add("플룻");
        spinnerList.add("오보에");
        spinnerList.add("트럼본");
        spinnerList.add("트럼펫");
        spinnerList.add("튜바");



        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, spinnerList);

        instrumentspinner.setAdapter(spinnerAdapter);
        instrumentspinner.setSelection(0);






        Intent intent= getIntent();
        final String name = intent.getStringExtra("name");
        final String id = intent.getStringExtra("id");
        Log.d("getid", "아이디 " + id+name);


//        final String pass = intent.getStringExtra("pass");
//        final String email = intent.getStringExtra("email");
//        final String gender = intent.getStringExtra("gender");



        instrumentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (instrumentspinner.getSelectedItem() == "악기선택") {


                } else {
                    Toast.makeText(Profile.this, spinnerList.get(i) + "가(이) 선택되었습니다.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        btn_Profilfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String number = et_profilenumber.getText().toString();
                String introduce = et_introduce.getText().toString();
                String spinner = (String) instrumentspinner.getSelectedItem();



                if (number.length() == 0) {
                    Toast.makeText(Profile.this, "전화번호 입력은 필수 입니다.", Toast.LENGTH_SHORT).show();
                    et_profilenumber.requestFocus();
                    return;
                  } else if (spinner.equals("악기선택")) {
                    Toast.makeText(Profile.this, "악기선택은 필수 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                  } else if (introduce.length() == 0) {
                    Toast.makeText(Profile.this, "자기소개 입력은 필수 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                  }

                userdataclass userdataclass = new userdataclass();
                userdataclass.number = et_profilenumber.getText().toString();;
                userdataclass.introduce = et_introduce.getText().toString();
                userdataclass.instrument = spinner;
                userdataclass.mypicture = mypicture;
                userdataclass.id = id;
                userdataclass.name = name;


                userlist.add(userdataclass);



                try {

                    sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
                    String json = sharedPreferences.getString("user", "");
                    JSONArray jsonArray = new JSONArray(json);



                    for (int i = 0; i < userlist.size(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("number", userlist.get(i).number);
                        jsonObject.put("introduce", userlist.get(i).introduce);
                        jsonObject.put("instrument", userlist.get(i).instrument);
                        jsonObject.put("mypicture", userlist.get(i).mypicture);
                        jsonObject.put("name", userlist.get(i).name);
                        jsonObject.put("id", userlist.get(i).id);
//                        jsonObject.put("pass", userlist.get(i).pass);
//                        jsonObject.put("email", userlist.get(i).email);
//                        jsonObject.put("gender", userlist.get(i).gender);

                        Log.d("userdataclass","name "+userlist.get(i).id);

                        jsonArray.put(jsonObject);
                        Log.d("array", "e" + jsonArray.toString());

                    }


                    String profile = jsonArray.toString();
                    sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", profile);

                    Log.d("shared", "내 프로필" + profile);


                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(Profile.this, Home.class);
                intent.putExtra("id",userdataclass.id);
                intent.putExtra("mypicture",userdataclass.mypicture);

                startActivity(intent);
                finish();
            }
        });


        iv_Mypicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GET_GALLERY_IMAGE);

                //Action_Pick : 데이터베이스에서 하나의 항목을 선택할 수 있게 해달라고 요청하는 함수,

                //Midea_Provider : 단말기에 저장된 이미지, 동영상 , 오디오 파일의 정보를 제공하는 프로바이더.

                //MediaStore: Media_Provider API의 묶음 . 즉 단말기의 파일들을 접근할 수 있도록 해주는 메소드.

                //External_content_uri : 외부 저장소


            }
        });

    }


        //        Uri selectedImageUri = data.getData();
//        imageView.setImageURI(selectedImageUri);
//        if (GET_GALLERY_IMAGE == 101 && resultCode == RESULT_OK) {
//            try {
//                InputStream is = getContentResolver().openInputStream(data.getData());
//                Bitmap bm = BitmapFactory.decodeStream(is);
//                is.close();
//                iv_Mypicture.setImageBitmap(bm);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == 101 && resultCode == RESULT_CANCELED) {
//            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
//        }
//    }




///////////////////////////// 메니페스트 퍼미션 권한 요청 /////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode ==1){
            int length = permissions.length;
            for(int i =0; i<length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){

                    //동의의
                }
            }
        }

    }

    public void checkSelfPermission() {
        String temp = ""; //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(this
                    , temp.trim().split(" "),1);
        } else { // 모두 허용 상태
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show(); }
    }





    private void load() {
        sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
        String json = sharedPreferences.getString("user", "");

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String number = jsonObject.getString("number");

                if(MYid.equals(id)) {
                    if (number==null){


                    }else{
                        Intent intent = new Intent(Profile.this, Home.class);

                        startActivity(intent);

                        finish();
                    }


                }


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    }

