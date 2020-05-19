package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Location extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    Button btnGetLocation;
    Button btnSendLocation;
    Button geri;
    Double lon=0.0;
    Double lat=0.0;
    TextView showLocation;
    String latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btnSendLocation = (Button)findViewById(R.id.button2);
        btnGetLocation = (Button)findViewById(R.id.button);
        showLocation = (TextView)findViewById(R.id.tv);
        geri = (Button)findViewById(R.id.button3);

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                String orgid = in.getStringExtra("userid");
                String orgpas = in.getStringExtra("password");

                Intent i = new Intent(Location.this,Menu.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
                startActivity(i);
            }
        });

        btnSendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pm=getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "Lokasyon = "+lon.toString()+" - "+lat.toString();
                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    waIntent.putExtra("lon",longitude);
                    waIntent.putExtra("lat",latitude);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        if (location != null) {
                            lon = location.getLongitude();
                            lat = location.getLatitude();
                        }
                    }
                });

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(lon==0 && lat ==0)){
                    showLocation.setText(lon.toString()+" "+lat.toString());
                }
                else{
                    Toast.makeText(Location.this, "Lokasyan bilgisi alınamadı. Tekrar deneyiniz", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
