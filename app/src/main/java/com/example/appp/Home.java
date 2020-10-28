package com.example.appp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.appp.Login.MYid;
import static com.example.appp.Myschedule.REQUEST_CODE1;

public class Home extends AppCompatActivity {


    public static final int REQUEST_CODE2 = 300;

    Button btn_Schedule, btn_Main, btn_setting,btn_Memory;
    TextView tv_choicedate;

    SharedPreferences sharedPreferences;

    private ArrayList<HomeData> HomearrayList;
    private HomeAdapter HomeAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private String getgetid ;
    private String getgetmypicture ;


    @Override
    protected void onStart() {
        super.onStart();
        load();

        Log.d("filter" ,"onStart 로드했다");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        final String getmypicture = intent.getStringExtra("mypicture");
        final String getid = intent.getStringExtra("id");


        recyclerView = (RecyclerView) findViewById(R.id.Rc);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        HomearrayList = new ArrayList<>();
        HomeAdapter = new HomeAdapter(HomearrayList);
        recyclerView.setAdapter(HomeAdapter);

        btn_Schedule = findViewById(R.id.btn_Schedule);
        btn_Main = findViewById(R.id.btn_Main);
        btn_setting = findViewById(R.id.btn_setting);
     //   tv_choicedate = findViewById(R.id.tv_choicedate);
        btn_Memory = findViewById(R.id.btn_Memory);

//
//        tv_choicedate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//             //   init();
//
//
//            }
//        });


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
                    getgetid = id1 ;
                    Log.d("사진왔다", "원래사진" + mypicture1);
                    getgetmypicture  = mypicture1;
                    Log.d("사진왔다", "원래사진" + getgetmypicture);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        btn_Memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Memory.class);
                intent.putExtra("id",getid);
                intent.putExtra("mypicture",getmypicture);
                startActivity(intent);
            }
        });


        btn_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getSharedPreferences("Useralldata", MODE_PRIVATE);
                String json = sharedPreferences.getString("user", "");
                try {
                    JSONArray jsonArray = new JSONArray(json);

                        for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        Log.d("=jsonarray", "가져온 아이디 " + id);

                        if (MYid.equals(id)) {
                            String mypicture = jsonObject.getString("mypicture");
                            Log.d("로그인에서 가져온 아이디","아이디"+id + mypicture);

                            Intent intent = new Intent(Home.this, Myschedule.class);
                            intent.putExtra("id",id);
                            intent.putExtra("mypicture",mypicture);
                            startActivity(intent);

                        }
                        }

                        } catch (JSONException e) {
                        e.printStackTrace();
                     }
                    }
                });


        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Setting.class);
                intent.putExtra("id",getid);
                intent.putExtra("mypicture",getmypicture);
                startActivity(intent);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE2) {

//            Intent intent = getIntent();
//            final String mypicture = intent.getStringExtra("mypicture");
//            final String id = intent.getStringExtra("id");
//             Log.d("home", "수정" + mypicture);
//            Log.d("home", "수정 " +id);


            String Title = data.getStringExtra("Title");
            String Date = data.getStringExtra("Date");
            String Money = data.getStringExtra("Money");
            String Address = data.getStringExtra("Address");
            String Need = data.getStringExtra("Need");
            String Time = data.getStringExtra("Time");
            int position = data.getIntExtra("Position", 0);

            Log.d("shared", "수정또왔다" + Title);
            Log.d("shared", "수정또왔다" + Date);
            Log.d("shared", "수정또왔다" + Money);
            Log.d("shared", "수정또왔다" + Address);
            Log.d("shared", "수정또왔다" + Need);
            Log.d("shared", "수정또왔다" + position);


            HomeData homeData = new HomeData(getgetmypicture, Title
                    , Money, Date, Address, Need, Time, getgetid, R.drawable.menu);

            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
            String json = sharedPreferences.getString("announce", "");


            try {

                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    String id1 = jsonObject.getString("id");

                    if (MYid.equals(id1)) {
                        String getTitle = jsonObject.getString("Title");
                        Log.d("shared1", " 제목" + MYid);
                        Log.d("shared1", " 제목" + id1);

                        if (getTitle.equals(HomearrayList.get(position).getTitle())) {
                            Log.d("shared1", " 제목" + (HomearrayList.get(position).getTitle()));
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
                            jsonObject.put("Title", Title);
                            jsonObject.put("Date", Date);
                            jsonObject.put("Money", Money);
                            jsonObject.put("Need", Need);
                            jsonObject.put("Address", Address);
                            jsonObject.put("Time", Time);
                            jsonObject.put("id", getgetid);
                            jsonObject.put("mypicture", getgetmypicture);
//

//                            Log.d("shared", "저장하자" +jsonArray.toString());

                            editor.putString("announce", jsonArray.toString());
                            editor.commit();


                            HomearrayList.set(position, homeData);
                            HomeAdapter.notifyDataSetChanged();


                            break;

                        } else {

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

        }
    }

    public  void load() {

        Intent intent = getIntent();
        String getmypicture = intent.getStringExtra("mypicture");
        Log.d("사진왔다", "" + getmypicture);


        try {

            Log.d("로드", "" + HomearrayList.toString());
            HomearrayList.clear();   ///로드 데이터가 쌓이기 때문에 클리어 .

            sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
            String json = sharedPreferences.getString("announce", "");

            JSONArray jsonArray = new JSONArray(json);
            Log.d("제이슨", "" + jsonArray.toString());
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


                if (MYid.equals(id)) {

                    Log.d("사진왔다", "원래사진" + mypicture);
                    Log.d("사진왔다", "가져온사진" + getmypicture);
                    if (mypicture.equals(getmypicture)){

                        Log.d("로그인아이디", "" + jsonObject.toString());
                        Log.d("로그인아이디", "" + id);

                        HomeData HomeData = new HomeData(mypicture, Title
                                , Money, Date, Address, Need, Time,id, R.drawable.menu);


                        HomearrayList.add(0, HomeData);
                        HomeAdapter.notifyDataSetChanged();

                    }else if (getmypicture == null){

                        HomeData HomeData = new HomeData(mypicture, Title
                                , Money, Date, Address, Need, Time,id, R.drawable.menu);


                        HomearrayList.add(0, HomeData);
                        HomeAdapter.notifyDataSetChanged();

                    }else {
                        Log.d("로그인아이디", "" + jsonObject.toString());
                        Log.d("로그인아이디", "" + id);

                        Log.d("사진", "" + getmypicture);

                        HomeData HomeData = new HomeData(getmypicture, Title
                                , Money, Date, Address, Need, Time, id, R.drawable.menu);


                        HomearrayList.add(0, HomeData);
                        HomeAdapter.notifyDataSetChanged();

                        jsonObject.put("mypicture", getmypicture);

                        sharedPreferences = getSharedPreferences("Userannouncedata", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("announce", jsonArray.toString() );
                        editor.commit();

                    }

                } else {

                    HomeData homeData = new HomeData(mypicture, Title
                                , Money, Date, Address, Need, Time );
                        Log.d("announcedata", "" + homeData.getAddress());


                    HomearrayList.add(0, homeData);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu,menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchitem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                load();
                HomeAdapter.getFilter().filter(s);
                Log.d("Home", "filter" + s);
                return false;
            }
        });
        return true;
    }

//
//    private void init() {
//
//        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
//        final Calendar cal = Calendar.getInstance();
//
//        findViewById(R.id.tv_choicedate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DatePickerDialog dialog = new DatePickerDialog(Home.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
//
//                        String msg = String.format("%d년 %d월 %d일", year, month + 1, date);
//                        makeText(Home.this, msg, LENGTH_SHORT).show();
//                        tv_choicedate.setText(msg);
//                    }
//                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//
//                // dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
//                dialog.show();
//
//            }
//        });
//
//    }
    }
