package com.example.appp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.appp.Login.MYid;

public class Applyedschedule extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private ArrayList<ApplyedData> applyedarrayList ;
    private Applyedadapter applyedadapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyedschedule);

        recyclerView = (RecyclerView) findViewById(R.id.applyRc);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        applyedarrayList = new ArrayList<>();
        applyedadapter = new Applyedadapter(applyedarrayList);
        recyclerView.setAdapter(applyedadapter);

        load();


    }

    public  void load() {
//
        Intent intent = getIntent();
        String getmypicture = intent.getStringExtra("mypicture");
        Log.d("사진왔다", "" + getmypicture);



        try {

            Log.d("로드", "" + applyedarrayList.toString());
            applyedarrayList.clear();   ///로드 데이터가 쌓이기 때문에 클리어 .

            sharedPreferences = getSharedPreferences("Userapplyeddata",MODE_PRIVATE);
            String json = sharedPreferences.getString("applydata", "");

            JSONArray jsonArray = new JSONArray(json);
            Log.d("제이슨", "" + jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                if (MYid.equals(id)) {
                    String mypicture = jsonObject.getString("mypicture");
                    String Title = jsonObject.getString("Title");
                    String Money = jsonObject.getString("Money");
                    String Date = jsonObject.getString("Date");
                    String Address = jsonObject.getString("Address");
                    String Need = jsonObject.getString("Need");

//
//                    Log.d("사진왔다", "원래사진" + mypicture);
//                    Log.d("사진왔다", "가져온사진" + getmypicture);
//                    if (mypicture.equals(getmypicture)){
//
                        Log.d("로그인아이디", "" + jsonObject.toString());
                        Log.d("로그인아이디", "" + id);

                        ApplyedData applyedData = new ApplyedData(mypicture,  Title
                                , Money, Date, Address, Need, id, R.drawable.menu);


                        applyedarrayList.add(0, applyedData);
                        applyedadapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);;


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.menu1 :
                Intent intent = new Intent( Applyedschedule.this, Myschedule.class);
                startActivity(intent);
                break;


            case R.id.menu2 :


        }

        return super.onOptionsItemSelected(item);
    }


}