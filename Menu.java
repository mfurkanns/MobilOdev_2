package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    private Intent in;
    private String orgid;
    private String orgpas;

    private TextView tv;
    private Button changeInfo;
    private Button otherUsers;
    private Button sendMail;
    private Button notes;
    private Button sensor;
    private Button cikis;
    private Button download;
    private Button konum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        download = (Button)findViewById(R.id.button16);
        konum = (Button)findViewById(R.id.button17);

        tv = (TextView) findViewById(R.id.textView4);
        in = getIntent();
        tv.setText(in.getStringExtra("userid"));
        orgid = in.getStringExtra("userid");
        orgpas = in.getStringExtra("password");

        changeInfo = (Button) findViewById(R.id.button4);
        otherUsers = (Button)findViewById(R.id.button3);
        sendMail = (Button)findViewById(R.id.button2);
        notes = (Button)findViewById(R.id.button5);
        sensor = (Button)findViewById(R.id.button6);
        cikis = (Button)findViewById(R.id.button12);

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,MainActivity.class);
                startActivity(i);
            }
        });

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Change.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,SendMail.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
            }
        });

        otherUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,ViewUsers.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Notes.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
            }
        });

        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Sensor.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);

            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Download.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
            }
        });

        konum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Location.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);

            }
        });

    }
}
