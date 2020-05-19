package com.example.mobilodev;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class Sensor extends AppCompatActivity {


    private String orgid;
    private String orgpas;

    private SensorManager sensorManager ;
    private android.hardware.Sensor light;
    private TriggerEventListener triggerEvent;
    private android.hardware.Sensor accelerometer;

    private int x =0 ;
    private int y = 0;
    private int z = 0;

    private TextView tv ;
    private TextView tv2 ;
    private TextView tv3 ;
    private LinearLayout linearLayout;
    private Button geri;

    private CountDownTimer cdtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Intent k = getIntent();
        orgid = k.getStringExtra("userid");
        orgpas = k.getStringExtra("password");

        tv = (TextView)findViewById(R.id.textView5);
        tv2 = (TextView)findViewById(R.id.textView6);
        tv3 = (TextView)findViewById(R.id.textView7);
        geri = (Button)findViewById(R.id.button11);
        linearLayout = (LinearLayout)findViewById(R.id.llayout);

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdtimer.cancel();
                Intent i = new Intent(Sensor.this,Menu.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
            }
        });

        String list="";
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<android.hardware.Sensor> deviceSensors = sensorManager.getSensorList(android.hardware.Sensor.TYPE_ALL); // cihazdaki sensörleri tutan liste

        for(Integer i=0;i<deviceSensors.size();i++){
            Integer a = i+1;
            String tmp =a.toString()+". ";
            list = list + tmp+deviceSensors.get(i).getName()+"\n";
        }
        tv.setText(list);

        light = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_LIGHT);
        accelerometer = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);

        if(light == null){
            Toast.makeText(this, "Parlaklık sensörü bulunmamaktadır.", Toast.LENGTH_SHORT).show();
        }
        if(accelerometer == null){
            Toast.makeText(this, "İvme ölçer sensörü bulunmamaktadır.", Toast.LENGTH_SHORT).show();
        }

        cdtimer = new CountDownTimer(5000, 1000) { // telefonun 5 saniyeden fazla hareketsiz kalması durumunda kapanması için countdowntimer kullanılmıştır.
            public void onTick(long millisUntilFinished) {
                tv3.setText("Kalan süre : " + millisUntilFinished / 1000+" saniye");
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onFinish() {
                tv3.setText("Kapaniyor !");
                finish();
            }
        };
        cdtimer.start();

        final SensorEventListener sensorListener = new SensorEventListener(){ // sensor eventlerinin kontrol edildiği yer.
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType() == android.hardware.Sensor.TYPE_LIGHT){ // ışık sensörünün 20'den az gösterdiği durumun kontrol edilmesi.
                    if(event.values[0]<20){
                        linearLayout.setBackgroundColor(Color.BLACK);
                        tv.setTextColor(Color.WHITE);
                        tv2.setTextColor(Color.WHITE);
                        tv3.setTextColor(Color.WHITE);
                        geri.setTextColor(Color.BLACK);
                    }
                    else{
                        linearLayout.setBackgroundColor(Color.WHITE);
                        tv.setTextColor(Color.BLACK);
                        tv2.setTextColor(Color.BLACK);
                        tv3.setTextColor(Color.BLACK);
                        geri.setTextColor(Color.BLACK);
                    }
                }
                else if(event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER){
                    if(x-(int)event.values[0]!=0 || y-(int)event.values[1]!=0 || z-(int)event.values[2]!=0){ // hareket sensöründe değişim olduğu durumun kontrol edilmesi.
                        x = (int)event.values[0];
                        y = (int)event.values[1];
                        z = (int)event.values[2];
                        cdtimer.cancel();
                        cdtimer.start();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorListener,light,SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(sensorListener,accelerometer,SensorManager.SENSOR_DELAY_UI);
    }
}
