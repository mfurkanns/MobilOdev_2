package com.example.mobilodev;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;


public class Change extends AppCompatActivity {

    private EditText isim;
    private EditText yas;
    private EditText cinsiyet;
    private EditText boy;
    private EditText kilo;
    private EditText dil;
    private EditText kullanicid;
    private CheckBox koyu;
    private CheckBox acik;
    private ImageView iv;
    private TextView tepe;

    private Intent i;
    private String orgid;
    private String orgpas;
    private SharedPreferences.Editor prefsEditor;
    private Insan insan;

    private SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        tepe = (TextView) findViewById(R.id.textView10);
        mPrefs = getSharedPreferences("data",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        isim = (EditText)findViewById(R.id.editText5);
        yas = (EditText)findViewById(R.id.editText6);
        cinsiyet = (EditText)findViewById(R.id.editText7);
        boy = (EditText)findViewById(R.id.editText8);
        kilo = (EditText)findViewById(R.id.editText9);
        kullanicid = (EditText) findViewById(R.id.editText11);
        koyu = (CheckBox)findViewById(R.id.checkBox2);
        acik = (CheckBox)findViewById(R.id.checkBox);
        iv = (ImageView)findViewById(R.id.imageView);


        i = getIntent();
        orgid = i.getStringExtra("userid");
        orgpas = i.getStringExtra("password");
        tepe.setText(orgid);

        final String eskiId = orgid;

        Gson gson = new Gson();
        String json = mPrefs.getString(orgid, "");
        insan = (Insan)gson.fromJson(json, Insan.class);


        iv.setImageBitmap(decodeBase64(insan.foto));
        kullanicid.setText(insan.userid);
        isim.setText(insan.isim);
        yas.setText(insan.yas);
        cinsiyet.setText(insan.cinsiyet);
        boy.setText(insan.boy);
        kilo.setText(insan.kilo);

       if(insan.mod.compareTo("koyu")==0){
            koyu.setChecked(true);
        }
        else if(insan.mod.compareTo("acik")==0){
            acik.setChecked(true);
        }

        acik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("a","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                acik.setChecked(true);
                koyu.setChecked(false);
            }
        });

        koyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                koyu.setChecked(true);
                acik.setChecked(false);
            }
        });

        Button onayla = (Button)findViewById(R.id.button8);

        onayla.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) { // kullanıcı özelliklerinde değişiklik olduysa, bu yeni özelliklere göre yeni bir insan objesi oluşturulur ve kaydedilir.

                Insan yeni = new Insan();

                mPrefs.edit().remove(orgid).apply();

                yeni.userid = kullanicid.getText().toString();
                orgid = yeni.userid;
                yeni.parola = orgpas;
                yeni.isim = isim.getText().toString();
                yeni.cinsiyet = cinsiyet.getText().toString();
                yeni.kilo = kilo.getText().toString();
                yeni.boy = boy.getText().toString();
                yeni.yas = yas.getText().toString();
                yeni.foto = insan.foto;
                yeni.notlar = insan.notlar;

                if(koyu.isChecked() ){
                    yeni.mod="koyu";
                }
                else if(acik.isChecked()){
                    yeni.mod= "acik";
                }

                Gson gson1 = new Gson();
                String json1 = mPrefs.getString("userlar", "");
                ArrayList<String> arr = (ArrayList<String>)gson1.fromJson(json1,java.util.ArrayList.class);

                arr.remove(eskiId);
                arr.add(orgid);
                String jsn = gson1.toJson(arr);
                prefsEditor.putString("userlar",jsn);

                for (String i : arr) {
                    System.out.println(i);
                }

                Gson gson = new Gson();
                String json = gson.toJson(yeni);
                prefsEditor.putString(orgid, json);
                prefsEditor.commit();

                Intent newin = new Intent(Change.this,Menu.class);
                newin.putExtra("userid",orgid);
                newin.putExtra("password",orgpas);

                startActivity(newin);

            }
        });
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
