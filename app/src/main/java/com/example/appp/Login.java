package com.example.appp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {


    public  static String MYid;

    Button btn_Login;
    TextView tv_FindId, tv_FindPass ,tv_Register;
    ImageView  im_Logo;
    EditText et_Id, et_Pass;
    CheckBox cb_autologin;

    SharedPreferences sharedPreferences ;
    SharedPreferences idpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_Login = findViewById(R.id.btn_Login);
        tv_FindId = findViewById(R.id.tv_FindId);
        tv_Register = findViewById(R.id.tv_Register);
        im_Logo = findViewById(R.id.im_Logo);
        et_Id = findViewById(R.id.et_Id);
        et_Pass = findViewById(R.id.et_Pass);
        tv_FindPass = findViewById(R.id.tv_FindPass);
        cb_autologin = findViewById(R.id.cb_autologin);


        idpref = getSharedPreferences("Appdata", 0);
        Boolean savelogindata = idpref.getBoolean("SAVE_LOGIN_DATA", false);
        String Id1 = idpref.getString("Id1", "");
        String Password1 = idpref.getString("Password1", "");


        if (savelogindata) {
            et_Id.setText(Id1);
            et_Pass.setText(Password1);
            cb_autologin.setChecked(savelogindata);
        }


//        Intent intent = getIntent();
//        final String name = intent.getStringExtra("name");
//        final String id = intent.getStringExtra("id");
//        final String pass = intent.getStringExtra("pass");
//        final String email = intent.getStringExtra("email");
//        final String gender = intent.getStringExtra("gender");


        tv_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this,Agree.class);
                startActivity(intent);

            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                sharedPreferences = getSharedPreferences("Userregisterdata", MODE_PRIVATE);
                String json = sharedPreferences.getString("register", "");

                try {
                    JSONArray jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String getid = jsonObject.getString("id");
                        String getname =jsonObject.getString("name");
                        //String pass = jsonObject.getString("pass");
                        //                   Log.d("dd","id "+id);

                        String loginid = et_Id.getText().toString();
                        String loginpw = et_Pass.getText().toString();



                        if (loginid.equals(getid)) {
                            String getpass = jsonObject.getString("pass");
                            if (loginpw.equals(getpass)) {
                                Toast.makeText(Login.this, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Login.this, Profile.class);
                                MYid = loginid;
                                intent.putExtra("name",getname);
                                intent.putExtra("id",getid);
//                                intent.putExtra("pass",pass);
//                                intent.putExtra("email",email);
//                                intent.putExtra("gender",gender);

                                startActivity(intent);

                                saveid();
                                finish();

                                Log.d("생명주기" ,"finish()" );

                            } else {
                                Toast.makeText(Login.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();

                                break;
                            }

                        } else {
//                            Toast.makeText(Login.this, "아이디가 다릅니다.", Toast.LENGTH_SHORT).show();
                            continue;
                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        tv_FindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this,FindId.class);
                startActivity(intent);


            }
        });

        tv_FindPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,FindPass.class );
                startActivity(intent);

            }
        });


    }
    private void saveid() {
        SharedPreferences.Editor editor = idpref.edit();
        editor.putBoolean("SAVE_LOGIN_DATA", cb_autologin.isChecked());
        editor.putString("Id1", et_Id.getText().toString().trim());
        editor.putString("Password1", et_Pass.getText().toString().trim());
        editor.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();


        Log.d("생명주기" ,"onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("생명주기" ,"onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("생명주기" ,"onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("생명주기" ,"onDestroy ");
    }
}