package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import java.util.ArrayList;

public class Notes extends AppCompatActivity {

    private RecyclerView mRv;
    private RecyclerView.Adapter mAdap;
    private RecyclerView.LayoutManager mLmanager;

    private String orgid;
    private String orgpas;
    private Button geri;
    private Button yeninot;

    private SharedPreferences mPrefs ;
    private Intent i;

    private ArrayList<String> kullanicinotlar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        geri = (Button)findViewById(R.id.button10);
        yeninot = (Button) findViewById(R.id.button7);

        kullanicinotlar = new ArrayList<String>();

        i = getIntent();
        orgid = i.getStringExtra("userid");
        orgpas = i.getStringExtra("password");

        mPrefs = getSharedPreferences("data",MODE_PRIVATE);
        initNotes();
        kullanicinotlar.add(orgid); // kullanici notlarını tutan arraylistin sonuna kullanıcının id'si eklenir.

        mRv = (RecyclerView)findViewById(R.id.recyclerNotes);
        mAdap = new NoteAdapter(this,kullanicinotlar);
        mRv.setAdapter(mAdap);

        mLmanager = new LinearLayoutManager(this);
        mRv.setLayoutManager(mLmanager);

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insan insan = new Insan();
                Gson gson = new Gson();
                String json = mPrefs.getString(orgid, "");
                insan = (Insan)gson.fromJson(json, Insan.class);
                String tmp = insan.parola;
                orgpas = tmp;

                Intent in = new Intent(Notes.this,Menu.class);

                in.putExtra("userid",orgid);
                in.putExtra("password",orgpas);
                startActivity(in);
            }
        });

        yeninot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insan insan = new Insan();
                Gson gson = new Gson();
                String json = mPrefs.getString(orgid, "");
                insan = (Insan)gson.fromJson(json, Insan.class);
                String tmp = insan.parola;
                orgpas = tmp;

                Intent in = new Intent(Notes.this,NewNote.class);

                in.putExtra("userid",orgid);
                in.putExtra("password",orgpas);
                startActivity(in);
            }
        });
    }

    public void initNotes(){
        Insan insan = new Insan();
        Gson gson = new Gson();
        String json = mPrefs.getString(orgid, "");
        insan = (Insan)gson.fromJson(json, Insan.class);

        ArrayList<String> arr = insan.notlar;

        for(String i : arr){
            kullanicinotlar.add(i);
        }
    }
}
