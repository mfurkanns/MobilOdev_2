package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;

public class NewNote extends AppCompatActivity {

    private String orgid;
    private String orgpas;
    private EditText notyeni;
    private Button vazgec;
    private Button kaydet;

    private SharedPreferences.Editor prefsEditor;
    private Insan insan;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        mPrefs = getSharedPreferences("data",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        Intent i = getIntent();

        orgid=i.getStringExtra("userid");
        orgpas = i.getStringExtra("password");

        kaydet = (Button)findViewById(R.id.kaydet);
        notyeni = (EditText)findViewById(R.id.notyeni);
        vazgec = (Button)findViewById(R.id.vazgec);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String not = notyeni.getText().toString();
                Gson gson = new Gson();
                String json = mPrefs.getString(orgid, "");
                insan = (Insan)gson.fromJson(json, Insan.class);

                insan.notlar.add(not);

                String yeni = gson.toJson(insan);
                prefsEditor.putString(orgid, yeni);
                prefsEditor.commit();

                Intent k = new Intent(NewNote.this,Notes.class);
                k.putExtra("userid",orgid);
                k.putExtra("password",orgpas);
                startActivity(k);
            }
        });

        vazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(NewNote.this,Notes.class);
                k.putExtra("userid",orgid);
                k.putExtra("password",orgpas);
                startActivity(k);
            }
        });


    }
}
