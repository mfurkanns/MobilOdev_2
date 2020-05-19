package com.example.termproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class EtkinlikEkle extends AppCompatActivity implements TarihSecDiyalog.TarihSecDiyalogListener, SaatSecDiyalog.SaatSecDiyalogListener,
                                                                YineleSecDiyalog.YineleSecDiyalogListener, HatirlatmaZamaniSecDiyalog.HatirlatmaZamanSecDiyalogListener,
                                                                TamamlaDiyalog.TamamlaDiyalogListener, VazgecDiyalog.VazgecDiyalogListener {

    public int basilan;
    public int basilan_saat;

    public Integer bas_gun;
    public Integer bas_ay;
    public Integer bas_yil;
    public Integer bas_saat;
    public Integer bas_dakika;

    public Integer bit_gun;
    public Integer bit_ay;
    public Integer bit_yil;
    public Integer bit_saat;
    public Integer bit_dakika;

    public TextView bas_tv;
    public TextView bit_tv;
    public ImageView baslangic;
    public ImageView bitis;

    public TextView basSaat_tv;
    public TextView bitSaat_tv;
    public ImageView baslangicSaat;
    public ImageView bitisSaat;

    public TextView yinele;
    public ImageView yineleImage;
    public Integer yinele_deger;
    public String yinele_string_deger;

    public ImageView hatirlat_zaman_Image;
    public static TextView hatirlat_zaman_tv;
    public Integer hatirlat_zaman_deger;
    public String hatirlat_zaman_string_deger;

    public ImageView konum_image;
    public TextView map_tv;
    public String lokasyon_string="";
    public Double lon;
    public Double lat;

    public EditText baslik;
    public EditText detay;

    public ImageView tamamla;
    public ImageView vazgec;

    public SharedPreferences mPrefs;
    public SharedPreferences.Editor prefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_ekle);
        this.setTitle("Etkinlik Oluştur");

        // burada dark mode kontrolu yapılacak.

        mPrefs = getSharedPreferences("data",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();





        baslik = (EditText)findViewById(R.id.editText);
        detay = (EditText)findViewById(R.id.editText2);

        tamamla = (ImageView)findViewById(R.id.imageView4);
        vazgec = (ImageView)findViewById(R.id.imageView5);

        konum_image = (ImageView)findViewById(R.id.mapImageView);
        map_tv = (TextView)findViewById(R.id.textView15);
        Intent i = getIntent();
        if(i.getStringExtra("adres")==null){
            map_tv.setText("");
        }
        else{
            if(i.getStringExtra("adres").length()<30){
                map_tv.setText(i.getStringExtra("adres"));
            }
            else{
                String str = i.getStringExtra("adres").substring(0,20)+"...";
                map_tv.setText(str);
                lokasyon_string = i.getStringExtra("adres");
                lon = i.getDoubleExtra("lon",0.0);
                lat = i.getDoubleExtra("lat",0.0);
                Log.e("a","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }


        }

        hatirlat_zaman_Image = (ImageView)findViewById(R.id.hatirlatImage);
        hatirlat_zaman_tv = (TextView)findViewById(R.id.textView17);

        yinele = (TextView)findViewById(R.id.textView13);
        yineleImage = (ImageView)findViewById(R.id.imageView);

        baslangic = (ImageView)findViewById(R.id.imageView2);
        bitis = (ImageView)findViewById(R.id.imageView3);

        bas_tv = (TextView)findViewById(R.id.textView7);
        bit_tv = (TextView)findViewById(R.id.textView9);

        baslangic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basilan = 0;
                diyalogBaslat();
            }
        });

        bitis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basilan = 1;
                diyalogBaslat();
            }
        });


        basSaat_tv = (TextView)findViewById(R.id.saatBaslangic);
        bitSaat_tv = (TextView)findViewById(R.id.saatBitis);

        baslangicSaat = (ImageView)findViewById(R.id.saat_icon); // baslangıc saat
        bitisSaat = (ImageView)findViewById(R.id.saat_icon1);  // bitiş saat

        baslangicSaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basilan_saat = 0;
                diyalogBaslat1();
            }
        });

        bitisSaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basilan_saat = 1;
                diyalogBaslat1();
            }
        });

        hatirlat_zaman_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hatirlatmaZamanDiyalogBaslat();
            }
        });
        yineleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diyalogBaslat2();
            }
        });

        konum_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EtkinlikEkle.this,Konum.class);
                startActivityForResult(i,11);


            }
        });


        tamamla.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Etkinlik yeni_etkinlik = new Etkinlik();

                if(bit_gun==null || bas_gun==null){
                    Toast.makeText(EtkinlikEkle.this, "Lütfen tarih seçiniz", Toast.LENGTH_SHORT).show();
                }
                else if (bas_saat==null || bit_saat==null){ // saat secimi yapılmalı
                    Toast.makeText(EtkinlikEkle.this, "Lütfen saat seçiniz", Toast.LENGTH_SHORT).show();
                }
                else{
                    yeni_etkinlik.baslangic_tarihi_saat = new Date(bas_yil-1900,bas_ay,bas_gun,bas_saat,bas_dakika); // "2016-09-20"
                    yeni_etkinlik.bitis_tarihi_saat = new Date(bit_yil-1900,bit_ay,bit_gun,bit_saat,bit_dakika);

                    if(yeni_etkinlik.baslangic_tarihi_saat.after(yeni_etkinlik.bitis_tarihi_saat)){
                        Toast.makeText(EtkinlikEkle.this, "Başlangıç tarihi, bitiş tarihinden sonra olamaz", Toast.LENGTH_SHORT).show();
                    }
                    else if(yeni_etkinlik.baslangic_tarihi_saat.before(new Date())){
                        Toast.makeText(EtkinlikEkle.this, "Etkinliğin başlangıcı için o gün artık geç", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        yeni_etkinlik.etkinlik_adi = baslik.getText().toString();
                        yeni_etkinlik.etkinlik_detayi = detay.getText().toString();

                        yeni_etkinlik.yinele = yinele_deger;
                        yeni_etkinlik.hatirlatma_zamani = hatirlat_zaman_deger;
                        yeni_etkinlik.lon = lon;
                        yeni_etkinlik.lat = lat;

                        tamamla_diyalog_baslat(yeni_etkinlik);


                        System.out.println("asdasd");


                    }



                }



            }
        });

        vazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vazgec_diyalog_baslat();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {

                    String adrs = (String) b.getSerializable("adres");
                    if(adrs!=null) {
                        lokasyon_string = adrs;
                        lon = (Double) b.getSerializable("lon");
                        lat = (Double) b.getSerializable("lat");
                        if (adrs.length() < 30) {
                            map_tv.setText(adrs);
                        } else {
                            adrs = adrs.substring(0, 20) + "...";
                            map_tv.setText(adrs);
                        }
                    }
                    else{
                        Toast.makeText(this, "İnternet bağlantınızı tekrar sağlayınız", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void vazgec_diyalog_baslat(){
        VazgecDiyalog s = new VazgecDiyalog();
        s.show(getSupportFragmentManager(),"vazgec");
    }

    public void tamamla_diyalog_baslat(Etkinlik etk){
        TamamlaDiyalog s = new TamamlaDiyalog(etk);
        s.show(getSupportFragmentManager(),"tamamla");
    }

    public void diyalogBaslat(){
        TarihSecDiyalog sec = new TarihSecDiyalog();
        sec.show(getSupportFragmentManager(),"tarih");
    }

    @Override
    public void tarihAl(int gun, int ay, int yil) {
        if(basilan==0){
            bas_ay = ay;
            bas_gun = gun;
            bas_yil = yil;
            String tarih = bas_gun+"/"+(bas_ay)+"/"+bas_yil;
            bas_tv.setText(tarih);
        }
        else if(basilan==1){
            bit_ay = ay;
            bit_gun = gun;
            bit_yil = yil;
            String tarih = bit_gun+"/"+(bit_ay)+"/"+bit_yil;
            bit_tv.setText(tarih);
        }
    }

    public void diyalogBaslat1(){
        SaatSecDiyalog s = new SaatSecDiyalog();
        s.show(getSupportFragmentManager(),"saat");
    }

    public void hatirlatmaZamanDiyalogBaslat(){
        HatirlatmaZamaniSecDiyalog d = new HatirlatmaZamaniSecDiyalog();
        d.show(getSupportFragmentManager(),"hatirlatma_zaman");

    }


    @Override
    public void saatAl(int saat, int dakika) {
        if(basilan_saat==0){ // baslangıc saati alınır alınır
            bas_saat = saat;
            bas_dakika = dakika;
            String s,s1;
            if(bas_saat<10){
                s = "0"+bas_saat;
            }
            else{
                s=bas_saat.toString();
            }
            if(bas_dakika<10){
                s1 = "0"+bas_dakika;
            }
            else{
                s1=bas_dakika.toString();
            }
            String saat1 = s+":"+s1;
            basSaat_tv.setText(saat1);
        }
        else if(basilan_saat==1){ // bitiş saati alınır
            bit_saat = saat;
            bit_dakika = dakika;

            String s,s1;
            if(bit_saat<10){
                s = "0"+bit_saat;
            }
            else{
                s=bit_saat.toString();
            }
            if(bit_dakika<10){
                s1 = "0"+bit_dakika;
            }
            else{
                s1=bit_dakika.toString();
            }
            String saat1 = s+":"+s1;
            bitSaat_tv.setText(saat1);
        }

    }

    public void diyalogBaslat2(){
        YineleSecDiyalog y = new YineleSecDiyalog();
        y.show(getSupportFragmentManager(),"yinele");
    }


    @Override
    public int yineleAl(int secim,String deger) {
        yinele_deger = secim;
        yinele_string_deger = deger;
        yinele.setText(deger);
        return 0;
    }

    @Override
    public void zamanAl(int secim, String deger) {
        hatirlat_zaman_deger = secim;
        hatirlat_zaman_string_deger = deger;
        hatirlat_zaman_tv.setText(deger);
    }

    @Override
    public void tamamlaEtkinlik(Etkinlik etk) {
        // alarm olusturulabilir....
        Toast.makeText(this, "Interface calıstı", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void vazgec() {
        Toast.makeText(this, "Etkinlik iptal edildi", Toast.LENGTH_SHORT).show();
    }
}
