package com.example.termproject;

import android.net.Uri;

public class AyarlarClass {


    public String zil_sesi_adi;
    public String uri_zil_sesi;
    public Integer hatirlatma_zamani;
    public String hatirlatma_zamani_isim;
    public Integer yinele; //hatırlatma sıklığı
    public String yinele_isim;
    public boolean mod; // karanlık ve ayddınlık mod



    public void ayarlarTanit(){

        System.out.println("Zil sesi adı = "+zil_sesi_adi);
     //   System.out.println("Zil sesi uri = "+uri_zil_sesi);
        System.out.println("Hatırlatma zamanı = "+hatirlatma_zamani);
        System.out.println("Hatırlatma zamanı isim = "+hatirlatma_zamani_isim);
        System.out.println("Yineleme = "+yinele);
        System.out.println("Yineleme ismi = "+yinele_isim);




    }

}
