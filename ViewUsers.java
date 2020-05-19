package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ViewUsers extends AppCompatActivity {

    private RecyclerView mRv;
    private RecyclerView.Adapter mAdap;
    private RecyclerView.LayoutManager mLmanager;

    private ArrayList<String> kullanicilarID = new ArrayList<String>();
    private ArrayList<String> kullanicilarParola = new ArrayList<String>();
    private ArrayList<String> fotoBitmapString = new ArrayList<String>();

    private SharedPreferences mPrefs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        mPrefs = getSharedPreferences("data",MODE_PRIVATE);
        initElements();
        mRv = (RecyclerView)findViewById(R.id.recyclerView);
        mAdap = new Adapter(this,fotoBitmapString,kullanicilarID,kullanicilarParola); // diğer kullanıcıların listelendiği yer.
        mRv.setAdapter(mAdap);

        mLmanager = new LinearLayoutManager(this);
        mRv.setLayoutManager(mLmanager);
    }

    public void initElements(){ //
        Gson gson1 = new Gson();
        String json1 = mPrefs.getString("userlar", "");
        ArrayList<String> arr = (ArrayList<String>)gson1.fromJson(json1,java.util.ArrayList.class); // bu arraylist sisteme kayıtlı olan kullanıcıların id'lerini tutmaktadır.

        for(String i : arr){
            Insan insan = new Insan();
            Gson gson = new Gson();
            String json = mPrefs.getString(i, ""); // sharedpref'ten kullanıcıların bilgileri tek tek çekilmektedir.
            insan = (Insan)gson.fromJson(json, Insan.class);

            kullanicilarID.add(insan.userid);
            kullanicilarParola.add(insan.parola);
            fotoBitmapString.add(insan.foto);
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
