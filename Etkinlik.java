package com.example.termproject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Etkinlik implements Comparable<Etkinlik>{

    public String etkinlik_adi;
    public String etkinlik_detayi;
    public Date baslangic_tarihi_saat;
    public Date bitis_tarihi_saat;

    public Integer yinele;
    public Integer hatirlatma_zamani;

    public Double lon;
    public Double lat;



    public void etkinlikTanit(){

        System.out.println("Etkinlik adı = "+etkinlik_adi);
        System.out.println("Etkinlik detayı = "+etkinlik_adi);
        System.out.println("Etkinlik başlangıç saati = "+baslangic_tarihi_saat);
        System.out.println("Etkinlik bitiş saati = "+bitis_tarihi_saat);
        System.out.println("Etkinlik yineleme durumu = "+yinele);
        System.out.println("Etkinlik hatırlatma zamanı = "+hatirlatma_zamani);
        System.out.println("Etkinlik konum = "+lon+"/"+lat);


    }


    @Override
    public int compareTo(Etkinlik o) {
        return baslangic_tarihi_saat.compareTo(o.baslangic_tarihi_saat);
    }
}
