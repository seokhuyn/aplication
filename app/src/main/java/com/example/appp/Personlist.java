package com.example.appp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.appp.Login.MYid;




public class Personlist extends AppCompatActivity {

    private ArrayList<Personlistdata> personarrayList ;
    private Personlistadapter personlistadapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private SharedPreferences sharedPreferences;


    private String id ;

    @Override
    protected void onStart() {
        super.onStart();
    load();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personlist);



        Button btn_okay = findViewById(R.id.btn_okay);
        recyclerView = (RecyclerView) findViewById(R.id.Person_Recy);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        personarrayList = new ArrayList<>();
        personlistadapter = new Personlistadapter(personarrayList);
        recyclerView.setAdapter(personlistadapter);


        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Personlist.this, Home.class);
                startActivity(intent);
                finish();
            }
        });


    }

public void load(){
////
////        Intent intent = getIntent();
////        String getmypicture = intent.getStringExtra("mypicture");
////        Log.d("사진왔다", "" + getmypicture);
    final Intent intent = getIntent();
    final String getTitle = intent.getStringExtra("Title");

    try {


        personarrayList.clear();   ///로드 데이터가 쌓이기 때문에 클리어 .

        sharedPreferences = getSharedPreferences("Userapplyeddata", MODE_PRIVATE);
        String json1 = sharedPreferences.getString("applydata", "");

        JSONArray jsonArray1 = new JSONArray(json1);


        for (int j = 0; j < jsonArray1.length(); j++) {
            JSONObject jsonObject1 = jsonArray1.getJSONObject(j);

            String Title = jsonObject1.getString("Title");

            if (Title.equals(getTitle)) {
                String getid = jsonObject1.getString("id");

                id = getid;

                //personarrayList.clear();   ///로드 데이터가 쌓이기 때문에 클리어 .

                sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
                String json = sharedPreferences.getString("user", "");

                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String getgetid = jsonObject.getString("id");

                    if (getgetid.equals(id)) {
                        String mypicture = jsonObject.getString("mypicture");
                        String name = jsonObject.getString("name");
                        String instrument = jsonObject.getString("instrument");

//
                        Log.d("personlist", "등록 아이디" + id);
                        Log.d("personlist", "가져온 아이디 " + getgetid);
                        Log.d("personlist", "사진 " + mypicture);
                        Log.d("personlist", "이름 " + name);
                        Log.d("personlist", "악기 " + instrument);

                        Personlistdata personlistdata = new Personlistdata(
                                mypicture, name, instrument, R.drawable.menu);


                        personarrayList.add(0, personlistdata);
                        personlistadapter.notifyDataSetChanged();
                    }
                }

//
//                    }else if (getmypicture == null){
//
//                        ApplyedData applyedData = new ApplyedData( Title
//                                , Money, Date, Address, Need, id, R.drawable.menu);
//
//                        applyedarrayList.add(0, applyedData);
//                        applyedadapter.notifyDataSetChanged();
//
//                    }else {
//                        Log.d("로그인아이디", "" + jsonObject.toString());
//                        Log.d("로그인아이디", "" + id);
//
//                        Log.d("사진", "" + getmypicture);
//
//                        HomeData HomeData = new HomeData(getmypicture, Title
//                                , Money, Date, Address, Need, Time, id, R.drawable.menu);
//
//
//                        ApplyedData applyedData = new ApplyedData( Title
//                                , Money, Date, Address, Need, id, R.drawable.menu);
//
//                        jsonObject.put("mypicture", getmypicture);
//
//                        sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("announce", jsonArray.toString() );
//                        editor.commit();
//
//                    }

                    } else {

//                    ApplyedData applyedData = new ApplyedData( Title
//                            , Money, Date, Address, Need, id, R.drawable.menu);
//
//                    Log.d("announcedata", "" + applyedData.getAddress());
//
//                    applyedarrayList.add(0, applyedData);
                    }


        }


    } catch (JSONException e) {
        e.printStackTrace();
    }
}
}