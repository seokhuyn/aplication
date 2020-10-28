package com.example.appp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profilechek extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE =100;
    ImageView im_picture;
    TextView tv_name, tv_number,tv_introduce,tv_instrument ;
    LinearLayout layout_sms,layout_tell;
    SharedPreferences sharedPreferences ;
    Button btn_okay ;
    String getnumber ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilechek);

        im_picture = findViewById(R.id.im_picture);
        tv_name = findViewById(R.id.tv_name);
        tv_number = findViewById(R.id.tv_number);
        tv_introduce = findViewById(R.id.tv_introduce);
        layout_sms = findViewById(R.id.layout_sms);
        layout_tell = findViewById(R.id.layout_tell);
        tv_instrument = findViewById(R.id.tv_instrument);
        btn_okay = findViewById(R.id.btn_okay);

       // permissions();


        Intent intent = getIntent() ;
        String Name = intent.getStringExtra("Name");

        Log.d("profilecheck" ,"" +Name);


        sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
        String json = sharedPreferences.getString("user", "");

        try {
        JSONArray jsonArray = new JSONArray(json);


        for (int i = 0; i < jsonArray.length(); i++) {

           JSONObject jsonObject = jsonArray.getJSONObject(i);


            String getname = jsonObject.getString("name");

            if (Name.equals(getname)) {
                String instrument = jsonObject.getString("instrument");
                String number = jsonObject.getString("number");
                String introduce = jsonObject.getString("introduce");
                String mypicture = jsonObject.getString("mypicture");

                Glide.with(this)
                        .load(mypicture)
                        .override(158,75)
                        .into(im_picture);
                tv_name.setText(Name);
                tv_number.setText(number);
                tv_introduce.setText(introduce);
                tv_instrument.setText(instrument);

                getnumber = number;
                Log.d("profilecheck",""+tv_number.toString());

            }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        Log.d("profilecheck",""+getnumber);
        layout_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("profilecheck",""+getnumber);
                Intent i1 = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + getnumber));
                Log.d("profilecheck",""+tv_number.toString());
                startActivity(i1);

            }
        });


        layout_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+getnumber));
//
//                intent.setAction();
//                intent.setData();




                startActivity(intent);
            }
        });


        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profilechek.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

    }




    public void permissions(){


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);


        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"권한 승인이 필요합니다.",Toast.LENGTH_SHORT).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
            {
                Toast.makeText(this,"전화 권한이 필요합니다.",Toast.LENGTH_SHORT).show();

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}
                        ,MY_PERMISSIONS_REQUEST_CALL_PHONE);

                Toast.makeText(this,"전화 권한이 필요합니다.",Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)   {

            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {

                if(grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"승인이 허가 되었습니다." , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"아직 승인받지 못했습니다." , Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }
}