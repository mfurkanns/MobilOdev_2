package com.example.mobilodev;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ------------ NOT : uygulamaya kullanıcı id : user1   parola : 123456  ile giriş yapabilirsiniz.
    // ------------ Kullanıcı bilgileri shared preference'ta tutulmaktadır. Uygulama ilk açıldığında kullanıcı bilgileri kaydediliyor olacaktır. Bu yüzden uygulama biraz geç açılabilir.
    private EditText pass;
    private EditText userid;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Button button;
    private TextView tv;
    private  TextView tvtepe;

    private int giris = 3;
    private static int olusturuldu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvtepe = (TextView)findViewById(R.id.textView);

        mPrefs = getSharedPreferences("data",MODE_PRIVATE);

        prefsEditor = mPrefs.edit();

        if(olusturuldu!=1){ // kullanıcıların bir daha olusturulmaması icin konulmus bir kontrol.
            initElements(); // 10 adet kullanıcının oluşturulduğu fonksiyondur.
            tvtepe.setText("NOT : id: user1 parola: 123456 ile giriş yapabilirsiniz");
        }

        userid = (EditText) findViewById(R.id.editText1);
        pass = (EditText) findViewById(R.id.editText2);
        tv = (TextView)findViewById(R.id.textView3);
        button = (Button) findViewById(R.id.button);

        userid.setText("");
        pass.setText("");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = pass.getText().toString();
                String id = userid.getText().toString();



                Gson gson = new Gson();
                String json = mPrefs.getString(id, "");
                Insan insan = (Insan)gson.fromJson(json, Insan.class);

                if (insan != null && insan.parola.compareTo(password) == 0 && insan.userid.compareTo(id)== 0) {
                    Intent i = new Intent(MainActivity.this, Menu.class);
                    i.putExtra("userid", id);
                    i.putExtra("password", password);

                    tv = (TextView)findViewById(R.id.textView3);
                    tv.setText("Başarıyla giriş yapıldı.");
                    tv.setTextColor(Color.GREEN);

                    startActivity(i);
                } else if(giris>1){
                    tv = (TextView)findViewById(R.id.textView3);
                    tv.setText("Hatalı kullanıcı adı ya da şifre! "+(giris-1)+" hakkınız kaldı.");
                    tv.setTextColor(Color.RED);
                    giris--;
                }
                else {
                    tv = (TextView)findViewById(R.id.textView3);
                    tv.setText("Hakkınız bitti.");
                    tv.setTextColor(Color.RED);
                    finish();
                }
            }

        });
    }

    public void initElements(){

        for (Integer i=0;i<10;i++) {
            Integer k = i + 1;
            String id = "user" + k.toString();
            String password = "123456";

            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.l); // kullanıcılara aynı fotograflar atanmıstır.

            String kod = encodeTobase64(icon);
            Insan insan = new Insan();

            insan.boy="175";
            insan.isim=id+" adi";
            insan.parola=password;
            insan.yas = "23";
            insan.userid = id;
            insan.kilo = "100";
            insan.cinsiyet = "erkek";
            insan.mod = "Türkiye";
            insan.foto =kod;
            insan.mod = "koyu";
            insan.notlar = new ArrayList<String>();
            insan.notlar.add("deneme not1");

            Gson gson = new Gson();
            String json = gson.toJson(insan);
            prefsEditor.putString(id, json); // kullanıcıların bilgileri shared preference ile kaydedilmektedir.Kullanıcı bilgilerine id'leri ile ulaşılabilir.
        }

        ArrayList<String> userlar = new ArrayList<String>(); // bu arraylist'te kullanıcıların id'leri tutulmaktadır. Toplam 10 tane kullanıcı eklenmiştir.

        userlar.add("user1");
        userlar.add("user2");
        userlar.add("user3");
        userlar.add("user4");
        userlar.add("user5");
        userlar.add("user6");
        userlar.add("user7");
        userlar.add("user8");
        userlar.add("user9");
        userlar.add("user10");

        Gson gson1 = new Gson();
        String json1 = gson1.toJson(userlar);
        prefsEditor.putString("userlar", json1); // kullanıcıların id'lerinin bulunduğu arraylist kaydedilir.
        prefsEditor.commit();

        olusturuldu =1;
    }

    public static String encodeTobase64(Bitmap image) {  // fotoğraflar sharedpref'e kaydetmek için stringe çevrilir.
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {   // fotoğraflar stringden Bitmap'e çevrilir.
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
