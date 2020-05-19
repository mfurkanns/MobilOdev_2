package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class Ayarlar extends AppCompatActivity implements HatirlatmaZamaniSecDiyalog.HatirlatmaZamanSecDiyalogListener, YineleSecDiyalog.YineleSecDiyalogListener, AyarlarGuncelleDiyalog.AyarlarGuncelleDiyalogListener {

    public AyarlarClass ayarlarim;

    public TextView zilsesi_tv;
    public String zilses_uri;
    public String zil_sesi_string;
    public ImageView zilsesiImage;

    public TextView hatirlat_zamani_tv;
    public ImageView hatirlatzamaniImage;
    public Integer h_zaman_deger;
    public String h_deger;

    public TextView yinele_tv;
    public ImageView yineleImage;
    public Integer yinele_deger;
    public String yinele_string_deger;

    public Button onaylaButon;
    public Button vazgecButon;

    public SharedPreferences mPrefs;
    public SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        this.setTitle("Ayarlar");

        mPrefs = getSharedPreferences("data",MODE_PRIVATE);

        prefsEditor = mPrefs.edit();



        Gson gson1 = new Gson();
        String json1 = mPrefs.getString("ayarlarim", "");
        AyarlarClass ayr ;
        ayr = gson1.fromJson(json1,AyarlarClass.class);




        //burada ayarlar shared preften okunmalıdır. txtye yazdırılmalıdır.

        onaylaButon = (Button)findViewById(R.id.angry_btn2);
        vazgecButon = (Button)findViewById(R.id.angry_btn);

        yinele_tv = (TextView)findViewById(R.id.textView10);
        yineleImage = (ImageView)findViewById(R.id.imageView7);

        hatirlat_zamani_tv = (TextView)findViewById(R.id.textView11);
        hatirlatzamaniImage = (ImageView)findViewById(R.id.imageView6);

        zilsesi_tv = (TextView)findViewById(R.id.textView18);
        zilsesiImage = (ImageView)findViewById(R.id.imageView5);

        zilsesi_tv.setText(ayr.zil_sesi_adi);
        hatirlat_zamani_tv.setText(ayr.hatirlatma_zamani_isim);
        yinele_tv.setText(ayr.yinele_isim);

        zilsesiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Uri currentTone=RingtoneManager.getActualDefaultRingtoneUri(Ayarlar.this,RingtoneManager.TYPE_ALARM);
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Zil Sesi Seç");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                startActivityForResult(intent, 999);

            }
        });
        hatirlatzamaniImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diyalogBaslat();
            }
        });

        yineleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diyalogBaslat2();
            }
        });

        onaylaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ayarlarGuncelleDiyalogBaslat(ayarlarim);


                AyarlarClass yeni_ayr = new AyarlarClass();

                yeni_ayr.zil_sesi_adi = zil_sesi_string;
                yeni_ayr.uri_zil_sesi = zilses_uri;
                yeni_ayr.hatirlatma_zamani = h_zaman_deger;
                yeni_ayr.hatirlatma_zamani_isim = h_deger;
                yeni_ayr.yinele = yinele_deger;
                yeni_ayr.yinele_isim = yinele_string_deger;
                yeni_ayr.mod = false;


                Gson gson = new Gson();
                String json = gson.toJson(yeni_ayr);
                prefsEditor.putString("ayarlarim", json);
                prefsEditor.apply();

               // ayarlarim.ayarlarTanit();
                // burada ayarlar kaydedilebilir.
                // alarmlar guncellenebilir
            }
        });

        vazgecButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aasda sbaslat
                vazgec_diyalog_baslat();
            }
        });

    }

    public void vazgec_diyalog_baslat(){
        VazgecDiyalog s = new VazgecDiyalog();
        s.show(getSupportFragmentManager(),"vazgec");
    }


    public void ayarlarGuncelleDiyalogBaslat(AyarlarClass ar){
        AyarlarGuncelleDiyalog s = new AyarlarGuncelleDiyalog(ar);
        s.show(getSupportFragmentManager(),"ayarlar_guncelle");

    }

    public void diyalogBaslat(){
        HatirlatmaZamaniSecDiyalog d = new HatirlatmaZamaniSecDiyalog();
        d.show(getSupportFragmentManager(),"hatirlatma_zaman");

    }

    public void diyalogBaslat2(){
        YineleSecDiyalog y = new YineleSecDiyalog();
        y.show(getSupportFragmentManager(),"yinele");

    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
            String title = ringtone.getTitle(this);
            zilsesi_tv.setText(title);
            zil_sesi_string = title;
            zilses_uri = uri.toString();
            System.out.println("uriiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii = "+uri.toString());
            Toast.makeText(this, "Zil sesi '"+title+"' olarak ayarlandı", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void zamanAl(int secim,String deger) {
        h_zaman_deger = secim;
        h_deger = deger;
        hatirlat_zamani_tv.setText(deger);


    }

    @Override
    public int yineleAl(int secim,String deger) {
        yinele_deger = secim;
        yinele_string_deger = deger;
        yinele_tv.setText(yinele_string_deger);
        return 0;
    }

    @Override
    public void ayarlarGuncelle(AyarlarClass ayr) {
        ayarlarim = ayr;
    }
}
