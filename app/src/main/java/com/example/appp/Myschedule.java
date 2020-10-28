package com.example.appp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.jar.JarException;

import static com.example.appp.Login.MYid;

import static com.example.appp.Profile.userlist;

public class Myschedule extends AppCompatActivity {


    Button btn_Schedule, btn_Main, btn_setting, btn_Announce,btn_Memory;

    public static final int REQUEST_CODE = 100;
    public static final int REQUEST_CODE1 = 200;

    private String getgetid;
    private String getgetmypicture;

    private ArrayList<AnnounceData> arrayList;
    private AnnounceAdapter announceAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    SharedPreferences sharedPreferences;


//    private ArrayList<Announcedataclass> announcedatalist = new ArrayList<>();

    //    private class Announcedataclass {
//        String Title;
//        String Date;
//        String Money;
//        String Address;
//        String Need;
//        String Time;
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("생명주기", "Myschedule onRestart");

    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
        String json = sharedPreferences.getString("announce", "");

        load();
        Log.d("생명주기", "Myschedule onStart 로드했다"+ json);

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

                break;


            case R.id.menu2 :
                Intent intent = new Intent( Myschedule.this, Applyedschedule.class);
                startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myschedule);


        Log.d("생명주기", "onCreat ");

        recyclerView = (RecyclerView) findViewById(R.id.Recy);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        arrayList = new ArrayList<>();

        announceAdapter = new AnnounceAdapter(arrayList);
        recyclerView.setAdapter(announceAdapter);
        btn_Announce = findViewById(R.id.btn_Announce);
        btn_Main = findViewById(R.id.btn_Main);
        btn_setting = findViewById(R.id.btn_setting);
        btn_Schedule = findViewById(R.id.btn_Schedule);
        btn_Memory = findViewById(R.id.btn_Memory);


        try {

            sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
            String json = sharedPreferences.getString("user", "");

            JSONArray jsonArray = new JSONArray(json);
            Log.d("shared", "" + jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id1 = jsonObject.getString("id");
                String mypicture1 = jsonObject.getString("mypicture");

                Log.d("사진왔다", "원래사진" + mypicture1);

                if (MYid.equals(id1)) {

                    getgetmypicture  = mypicture1;
                    getgetid = id1;
                    Log.d("로그인사진", " " + getgetmypicture);
                    Log.d("로그인아이디", " " + getgetid);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        btn_Memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Myschedule.this, Memory.class);
                startActivity(intent);
            }
        });

        btn_Announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Announce.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        btn_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Myschedule.this, Home.class);
                startActivity(intent);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Myschedule.this, Setting.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        ///// Resume, Start 보다 먼저 실행되어 저장을 함. 그러므로 onStart에 호출을 해주고 onResume 에서 최신 업데이틑 해야된다고 생각.
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("shared", "액티비티 왔다 " +userlist.toString());

//        Intent intent = getIntent();
//        String id = intent.getStringExtra("id");
//        String mypicture = intent.getStringExtra("mypicture");
//
//        Log.d("홈에서 가져온 아이디", "아이디" + id + mypicture);
//

        if (requestCode == REQUEST_CODE) {


            Log.d("shared", "리퀘스트 왔다 " +userlist.toString());
//
            userlist.clear();                        //// 저장 데이터가 쌓이기 때문에 클리어

            userdataclass userdataclass = new userdataclass();
            userdataclass.Title = data.getStringExtra("Title");
            userdataclass.Date = data.getStringExtra("Date");
            userdataclass.Money = data.getStringExtra("Money");
            userdataclass.Address =data.getStringExtra("Address");
            userdataclass.Need =  data.getStringExtra("Need");
            userdataclass.Time =data.getStringExtra("Time");
            userdataclass.id = getgetid;
            userdataclass.mypicture = getgetmypicture;


            Log.d("shared", "등록왔다" +  userdataclass.Title);
            Log.d("shared", "등록왔다" +userdataclass.Date );
            Log.d("shared", "등록왔다" +   userdataclass.Money);
            Log.d("shared", "등록왔다" + userdataclass.Address);
            Log.d("shared", "등록왔다" +  userdataclass.Need );

            userlist.add(userdataclass);
//
            arrayList.clear();

            String Title = data.getStringExtra("Title");
            String Date = data.getStringExtra("Date");
            String Money = data.getStringExtra("Money");
            String Address = data.getStringExtra("Address");
            String Need = data.getStringExtra("Need");
            String Time = data.getStringExtra("Time");

            Log.d("shared", "등록왔다" +Title);
            Log.d("shared", "등록왔다" +Date );
            Log.d("shared", "등록왔다" + Money);
            Log.d("shared", "등록왔다" +Address);
            Log.d("shared", "등록왔다" + Need);



            AnnounceData announceData = new AnnounceData(getgetmypicture, Title
                    , Money, Date, Address, Need, Time, getgetid, R.drawable.menu);

            Log.d("shared" ,"announcedata " + announceData.getTitle());

            arrayList.add(0, announceData);
            announceAdapter.notifyDataSetChanged();





            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
            String json = sharedPreferences.getString("announce", "");


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

                    Log.d("shared" ,"announcedata " +  userlist.get(i).Title);
//                    jsonObject.put("Title", announceData.getTitle());
//                    jsonObject.put("Date", announceData.getDate());
//                    jsonObject.put("Money", announceData.getMoney());
//                    jsonObject.put("Address", announceData.getAddress());
//                    jsonObject.put("Need", announceData.getNeed());
//                    jsonObject.put("Time", announceData.getArbtime());
//                    jsonObject.put("id", announceData.getId());
//                    jsonObject.put("mypicture", announceData.getIm_profileimage());
//
                    jsonArray.put(jsonObject);

                }

                sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("announce", jsonArray.toString());
                Log.d("shared", "saveeditor" + jsonArray.toString());
                editor.apply();




            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CODE1) {




           // arrayList.clear();   ///로드 데이터가 쌓이기 때문에 클리어 .

            // Log.d("shared", "어레이" + arrayList.get(0).toString());
          //Log.d("쉐어드", "유저데이터 후 " +userlist.toString());


            String Title = data.getStringExtra("Title");
            String Date = data.getStringExtra("Date");
            String Money = data.getStringExtra("Money");
            String Address = data.getStringExtra("Address");
            String Need = data.getStringExtra("Need");
            String Time = data.getStringExtra("Time");
            int position = data.getIntExtra("Position", 0);

            Log.d("shared", "수정또왔다" +Title);
            Log.d("shared", "수정또왔다" +Date );
            Log.d("shared", "수정또왔다" + Money);
            Log.d("shared", "수정또왔다" +Address);
            Log.d("shared", "수정또왔다" + Need);
            Log.d("shared", "수정또왔다" + position);


            AnnounceData announceData = new AnnounceData(getgetmypicture, Title
                    , Money, Date, Address, Need, Time, getgetid, R.drawable.menu);

             sharedPreferences =getSharedPreferences("Userannouncedata", MODE_PRIVATE);
            String json = sharedPreferences.getString("announce", "");


            try{

                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);



                    String id1 = jsonObject.getString("id");

                    if (MYid.equals(id1)) {
                        String getTitle = jsonObject.getString("Title");
                        Log.d("shared1", " 제목" +MYid);
                        Log.d("shared1", " 제목" + id1);

                        if (getTitle.equals(arrayList.get(position).getTitle())){
                            Log.d("shared1", " 제목" + (arrayList.get(position).getTitle()));
                            Log.d("shared1", " 제목" + getTitle);

                            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

//
//                            String Date1 = jsonObject.getString("Date");
//                            String Title1 = jsonObject.getString("Title");
//                            String Money1 = jsonObject.getString("Money");
//                            String Need1 = jsonObject.getString("Need");
//                            String Address1 = jsonObject.getString("Address");
//                            String Time1 = jsonObject.getString("Time");
//                            String id2 = jsonObject.getString("id");
//                            String mypicture1 = jsonObject.getString("mypicture");

//                            JSONObject title = jsonArray.getJSONObject(i).put("Title",Title);
//                            Log.d("shared", "저장하자" +title);
//
//
                            jsonObject.put("Title",Title);
                            jsonObject.put("Date",Date);
                            jsonObject.put("Money",Money);
                            jsonObject.put("Need",Need);
                            jsonObject.put("Address",Address);
                            jsonObject.put("Time",Time);
                            jsonObject.put("id",getgetid);
                            jsonObject.put("mypicture",getgetmypicture);
//

//                            Log.d("shared", "저장하자" +jsonArray.toString());

                            editor.putString("announce", jsonArray.toString());
                            editor.commit();


                            arrayList.set(position, announceData);
                            announceAdapter.notifyDataSetChanged();



                            break;

                        }else {

//                                                       sharedPreferences = view.getContext().getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//                                                       SharedPreferences.Editor editor = sharedPreferences.edit();
//                                                       editor.putString("announce", jsonArray.toString());
//                                                       editor.commit();
                         //   Log.d("shared1", " 다른 제목" + getTitle);
                        }

                    }




                }

                Log.d("shared", "saveeditor" + jsonArray.toString());
                Log.d("shared1", " 나왔다" + MYid);


            } catch (JSONException e) {
                e.printStackTrace();
            }


//            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//            String json = sharedPreferences.getString("announce", "");
//
//
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//
//               for (int i = 0; i < arrayList.size(); i++) {
//                    JSONObject jsonObject = new JSONObject();
////
////
////                jsonObject.put("Title", userlist.get(i).Title);
////                jsonObject.put("Date", userlist.get(i).Date);
////                jsonObject.put("Money", userlist.get(i).Money);
////                jsonObject.put("Address", userlist.get(i).Address);
////                jsonObject.put("Need", userlist.get(i).Need);
////                jsonObject.put("Time", userlist.get(i).Time);
////                jsonObject.put("id", userlist.get(i).id);
////                jsonObject.put("mypicture", userlist.get(i).mypicture);
//
//                    jsonObject.put("Title", announceData.getTitle());
//                    jsonObject.put("Date", announceData.getDate());
//                    jsonObject.put("Money", announceData.getMoney());
//                    jsonObject.put("Address", announceData.getAddress());
//                    jsonObject.put("Need", announceData.getNeed());
//                    jsonObject.put("Time", announceData.getArbtime());
//                    jsonObject.put("id", announceData.getId());
//                    jsonObject.put("mypicture", announceData.getIm_profileimage());
//
//                    jsonArray.put(jsonObject);
//                    Log.d("shared","수정데이터"+ jsonArray.toString());
//               }
//
//                sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putString("announce",jsonArray.toString());
//                Log.d("shared", "saveeditor" + jsonArray.toString());
//                editor.apply();
//
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    }

    public  void load() {

        Intent intent = getIntent();
        String getmypicture = intent.getStringExtra("mypicture");
        Log.d("사진왔다", "" + getmypicture);

        try {

            Log.d("로드", "" + arrayList.toString());
            arrayList.clear();   ///로드 데이터가 쌓이기 때문에 클리어 .

            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
            String json = sharedPreferences.getString("announce", "");

            JSONArray jsonArray = new JSONArray(json);
            Log.d("shared", "" + jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String Title = jsonObject.getString("Title");
                String Money = jsonObject.getString("Money");
                String Date = jsonObject.getString("Date");
                String Address = jsonObject.getString("Address");
                String Need = jsonObject.getString("Need");
                String Time = jsonObject.getString("Time");
                String mypicture = jsonObject.getString("mypicture");

                Log.d("사진왔다", "원래사진" + mypicture);

                if (MYid.equals(id)) {
                    Log.d("사진왔다", "원래사진" + mypicture);
                    Log.d("사진왔다", "가져온사진" + getmypicture);
                    if (mypicture.equals(getmypicture)){

                    Log.d("로그인아이디", "" + jsonObject.toString());
                    Log.d("로그인아이디", "" + id);
                    AnnounceData announceData = new AnnounceData(mypicture, Title
                            , Money, Date, Address, Need, Time,id, R.drawable.menu);


                    arrayList.add(0, announceData);
                    announceAdapter.notifyDataSetChanged();

                    }else if (getmypicture == null){

                        AnnounceData announceData = new AnnounceData(mypicture, Title
                                , Money, Date, Address, Need, Time,id, R.drawable.menu);


                        arrayList.add(0, announceData);
                        announceAdapter.notifyDataSetChanged();

                    }else {
                        Log.d("로그인아이디", "" + jsonObject.toString());
                        Log.d("로그인아이디", "" + id);

                        Log.d("사진", "" + getmypicture);
                        AnnounceData announceData = new AnnounceData(getmypicture, Title
                                , Money, Date, Address, Need, Time, id, R.drawable.menu);


                        arrayList.add(0, announceData);
                        announceAdapter.notifyDataSetChanged();

                        jsonObject.put("mypicture", getmypicture);

                        sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("announce", jsonArray.toString() );
                        editor.commit();

                    }


                } else {
//
//                        AnnounceData announceData = new AnnounceData(Mypicture, Title
//                                , Money, Date, Address, Need, Time);
//                        Log.d("announcedata", "" + announceData.getAddress());
//
//                        arrayList.add(0, announceData);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


        Log.d("생명주기" ,"Myschedule onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("생명주기" ,"Myschedule onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("생명주기" ,"Myschedule onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("생명주기" ,"Myschedule onDestroy ");
    }
}
//
//    private void loaddata() {
//        sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//        String json = sharedPreferences.getString("announce", "");
//        Gson gson = new Gson();
//
////        Log.d("Gson","a "+json);
////        Type type = new TypeToken<ArrayList<AnnounceData>>() {
////        }.getType();
////
////        arrayList = gson.fromJson(json,type);
//
//        JsonParser jsonParser = new JsonParser();
//        JsonArray jsonArray =(JsonArray) jsonParser.parse(json);
//
//        for (int i = 0; i <jsonArray.size(); i++){
//            JsonObject object = (JsonObject) jsonArray.get(i);
//            String id = object.get("id").getAsString();
//
//            Log.d("로그인아이디", "" + id);
//            Log.d("로그인아이디", "" + MYid);
//            if (MYid.equals(id)){
//
//
//                String Title = object.get("Title").getAsString();
//                String Money = object.get("Money").getAsString();
//                String Date = object.get("Date").getAsString();
//                String Address = object.get("Address").getAsString();
//                String Need = object.get("Need").getAsString();
//                String Time = object.get("Time").getAsString();
//                String Mypicture = object.get("mypicture").getAsString();
//
//
//                Log.d("로그인아이디", "" + id);
//                AnnounceData announceData = new AnnounceData(Mypicture, Title
//                        , Money, Date, Address, Need, Time,id, R.drawable.menu);
//
//
//                arrayList.add(0, announceData);
//                announceAdapter.notifyDataSetChanged();
//
//
//            }else {
//
//            }
//
//
//        }
//
//
//
//            if (arrayList == null) {
//                arrayList = new ArrayList<>();
//            }
//
//
//
//        }

//            private void savedata(){

//            String Title = data.getStringExtra("Title");
//            String Date = data.getStringExtra("Date");
//            String Money = data.getStringExtra("Money");
//            String Address = data.getStringExtra("Address");
//            String Need = data.getStringExtra("Need");
//            String Time = data.getStringExtra("Time");
//
//            AnnounceData announceData = new AnnounceData(mypicture, Title
//                    , Money, Date, Address, Need, Time, id, R.drawable.menu);
//
//
//            arrayList.add(0, announceData);
//            announceAdapter.notifyDataSetChanged();
//            Log.d("shared", "어레이" + arrayList.get(0).toString());
//
//
//            Gson gson = new Gson();
//            String json = gson.toJson(arrayList);
//            Log.d("json", "a " + json);
//            Log.d("json", "a " + arrayList);
//
//            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("announce", json);
//
//            editor.apply();

//}

