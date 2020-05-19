package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class Download extends AppCompatActivity {
    private ProgressBar pgsBar;
    private Integer i = 0;
    private TextView txtView;
    private Handler hdlr = new Handler();
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Button btn = (Button)findViewById(R.id.button);
        Button geri = (Button)findViewById(R.id.button18);
        pgsBar = (ProgressBar)findViewById(R.id.progressBar);
        tv = (TextView)findViewById(R.id.textView);

        pgsBar.setMax(100);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample a = new AsyncTaskExample();
                a.execute();
            }
        });

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                String orgid = in.getStringExtra("userid");
                String orgpas = in.getStringExtra("password");

                Intent i = new Intent(Download.this,Menu.class);
                i.putExtra("userid",orgid);
                i.putExtra("password",orgpas);
                startActivity(i);
            }
        });
    }
    public class AsyncTaskExample extends AsyncTask<String,String,String> {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            i = pgsBar.getProgress();
            i=0;
            Random random = new Random();
            while (i < 100) {
                i += random.nextInt(10);
                hdlr.post(new Runnable() {
                    public void run() {
                        pgsBar.setProgress(i);
                        tv.setText("%"+i.toString());
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(Download.this, AlarmReceiveActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(Download.this,12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am =(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("İndiriliyor...");
        }
        @Override
        protected void onPostExecute(String bitmap) {
            super.onPostExecute(bitmap);
            tv.setText("İndirildi");
            Toast.makeText(Download.this, "İndirildi", Toast.LENGTH_SHORT).show();
        }
    }
}
