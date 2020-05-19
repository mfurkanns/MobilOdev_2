package com.example.termproject;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Konum extends FragmentActivity implements OnMapReadyCallback {

    public String TAG = "mesaj";
    public GoogleMap mMap;
    public FusedLocationProviderClient mFLPC;

    public ImageView arama;
    public EditText eText;
    public ImageView onayla;

    public double lon;
    public double lat;
    public String adres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        onayla = (ImageView)findViewById(R.id.imageView2);

        arama = (ImageView)findViewById(R.id.imageView);
        eText = (EditText)findViewById(R.id.input_search);

        mFLPC = LocationServices.getFusedLocationProviderClient(this);


        mFLPC.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(Konum.this, Locale.getDefault());



                            // Logic to handle location object
                            lon = location.getLongitude();
                            lat = location.getLatitude();
                            try {
                                addresses = geocoder.getFromLocation(lat, lon, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(addresses!=null){
                                LatLng konum = new LatLng(lat, lon);
                                adres = addresses.get(0).getAddressLine(0);
                                mMap.addMarker(new MarkerOptions().position(konum).title(addresses.get(0).getAddressLine(0))); // addresses.get(0).getAddressLine(0))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f));
                            }
                            else{
                                LatLng konum = new LatLng(lat, lon);
                                mMap.addMarker(new MarkerOptions().position(konum).title("Konum")); // addresses.get(0).getAddressLine(0))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f));

                            }
                            LatLng konum = new LatLng(lat, lon);
                            mMap.addMarker(new MarkerOptions().position(konum).title("Konum")); // addresses.get(0).getAddressLine(0))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f));
                        }
                    }
                });

        arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Geocoder coder = new Geocoder(Konum.this);
                List<Address> address = null;
                String searchString = eText.getText().toString();
                double longitude;
                double latitude;
                try {
                    address = coder.getFromLocationName(searchString,1);
                    Address location= address.get(0);

                    Log.e(TAG, "asdasdadsasd: found a location: " + location.toString());

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    lon = longitude;
                    lat = latitude;
                    adres = location.getAddressLine(0);

                    LatLng konum = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(konum).title(location.getAddressLine(0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f));
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
                }


            }
        });


        onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent i = new Intent(Konum.this,EtkinlikEkle.class);

                i.putExtra("lon",lon);
                i.putExtra("lat",lat);
                i.putExtra("adres",adres);*/

                onBackPressed();
           //     startActivity(i);


            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("lon",lon);
        i.putExtra("lat",lat);
        i.putExtra("adres",adres);
        setResult(RESULT_OK, i);

       // finish();
        super.onBackPressed();
    }

    private void init(){
        Log.d(TAG, "init: initializing");

        eText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = eText.getText().toString();

        Geocoder geocoder = new Geocoder(Konum.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.e(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

        }
        else{
            Log.e(TAG, "yok aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        init();
    }







}
