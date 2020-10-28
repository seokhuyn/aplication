package com.example.appp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.appp.Profile.userlist;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    String b ;

    RelativeLayout relative;
    EditText input_search;
    Button btn_Findcheck,btn_locationokay;
    String sub;
//    String d;
//    String m;
//    String n;
    public static final float DEFAULT_ZOOM = 15f;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        relative = findViewById(R.id.relative);

        input_search = findViewById(R.id.input_search);
        btn_Findcheck = findViewById(R.id.btn_Findcheck);
        btn_locationokay = findViewById(R.id.btn_locationokay);


        Intent intent = getIntent();
        sub = intent.getStringExtra("sub");
        Log.d("MapINTENT", "" +sub );
//

//         d = intent.getStringExtra("d");
//         m = intent.getStringExtra("m");
//         n = intent.getStringExtra("n");
//        Log.d("AA","dd"+sub );



        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.G_map);
        supportMapFragment.getMapAsync(this);

        btn_Findcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                geoLocate();

            }
        });

        btn_locationokay.setOnClickListener(new View.OnClickListener() {
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

                            if((Title.equals(sub))){
                                String address = jsonObject.getString("address");



                                Log.d("getad","get"+address);
                                Intent intent = new Intent();
                                intent.putExtra("address", address);
                                setResult(RESULT_OK,intent);

                                finish();

                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

        });

    }

    private void init() {
        Places.initialize(getApplicationContext(), "AIzaSyBTHxnailQDxZXFQqzr9O8biauqlDw-F2w");
        input_search.setFocusable(false);

        input_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(Map.this);
                startActivityForResult(intent, 100);
            }
        });
//        keyword();


    }


    private void geoLocate() {




        String searchString = input_search.getText().toString();
        Geocoder geocoder = new Geocoder(Map.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);

        } catch (IOException e) {

        }

        if (list.size() > 0) {
            Address address = list.get(0);
            LatLng getaddress = new LatLng(address.getLatitude(), address.getLongitude());

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(getaddress));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));

            userdataclass userdataclass = new userdataclass();
            userdataclass.Title = sub;
            Log.d("Map", "" +userdataclass.Title);
            userdataclass.lat =(float) address.getLatitude();
            userdataclass.longi =  (float) address.getLongitude();
            userdataclass.address = address.getAddressLine(0);


            userlist.add(userdataclass);

              sharedPreferences = getSharedPreferences("getAddress", MODE_PRIVATE);
                      String json = sharedPreferences.getString("address", "");
                      try {
                      JSONArray jsonArray = new JSONArray(json);

                      for (int i = 0; i < userlist.size(); i++) {
                          JSONObject jsonObject = new JSONObject();
                          jsonObject.put("Title",userlist.get(i).Title);
                          jsonObject.put("lat",userlist.get(i).lat);
                          jsonObject.put("longi",userlist.get(i).longi);
                          jsonObject.put("address",userlist.get(i).address);
                          jsonArray.put(jsonObject);

                      }

                          sharedPreferences = getSharedPreferences("getAddress", MODE_PRIVATE);
                          SharedPreferences.Editor editor = sharedPreferences.edit();
                          editor.putString("address", jsonArray.toString());


                          Log.d("Map", "" +address.getLatitude() );

                          editor.commit();



                      } catch (JSONException e) {
          e.printStackTrace();
          }




        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

//        Intent intent = getIntent();
//        double latitude = intent.getDoubleExtra("latitude", 0);
//        double longitude = intent.getDoubleExtra("longitude", 0);
//
//        Log.d("dddd", "dd" + latitude);
//        Log.d("dddd", "dd" + longitude);

        SharedPreferences Latpref = getSharedPreferences("Latpref",MODE_PRIVATE);
        float  aa = Latpref.getFloat("latitude",0);
        float  bb = Latpref.getFloat("longitude",0);

        LatLng latLng = new LatLng(aa, bb);

        init();

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("현재위치");
        googleMap.addMarker(markerOptions);

        CameraUpdate animateCamera = CameraUpdateFactory.zoomTo(15);
        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLng(latLng);
        googleMap.moveCamera(animateCamera);
        googleMap.moveCamera(cameraUpdateFactory);


        init();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);


        } else {
            checkLocationPermissionWithRationale();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Map.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        Log.d("getTitle", "" + title);

        googleMap.clear();
        googleMap.addMarker(options);


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode ==100 &&resultCode ==RESULT_OK){
//
//            Place place = Autocomplete.getPlaceFromIntent(data);
//            Log.d("Place","Place: " +place.getAddress()+place.getName()+place.getId()+place.getLatLng());
//            input_search.setText(place.getAddress());
//
//            tv1.setText(String.format("지역 이름: %s ",place.getName()));
//            tv2.setText(String.format(String.valueOf(place.getLatLng())));
//
//        }else  if (resultCode == AutocompleteActivity.RESULT_ERROR){
//
//            Status status = Autocomplete.getStatusFromIntent(data);
//
//            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
//        }else if (resultCode == RESULT_CANCELED){
//            return;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==100 &&resultCode ==RESULT_OK){
            final Place place = Autocomplete.getPlaceFromIntent(data);



            Log.d("Place","Place: " +place.getAddress()+place.getName()+place.getId()+place.getLatLng());
            input_search.setText(place.getAddress());


        }else  if (resultCode == AutocompleteActivity.RESULT_ERROR){

            Status status = Autocomplete.getStatusFromIntent(data);

            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }else if (resultCode == RESULT_CANCELED){
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}

