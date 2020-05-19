package com.example.mobilodev;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import java.util.ArrayList;

public class ChangeNote extends AppCompatActivity {

    private SharedPreferences.Editor prefsEditor;
    private Insan insan;
    private String orgid;
    private String eskinot;
    private  EditText yeninot;
    private Button kaydet;
    private Button sil;

     private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_note);

        mPrefs = getSharedPreferences("data",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        yeninot = (EditText)findViewById(R.id.yeninot);
        kaydet = (Button)findViewById(R.id.button9);
        sil = (Button)findViewById(R.id.button15);

        Intent i = getIntent();
        orgid = i.getStringExtra("userid");
        eskinot = i.getStringExtra("not");
        yeninot.setText(eskinot);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = mPrefs.getString(orgid, "");
                insan = (Insan)gson.fromJson(json, Insan.class);

                ArrayList<String> eskinotlar = insan.notlar;

                eskinotlar.remove(orgid);
                eskinotlar.set(eskinotlar.indexOf(eskinot),yeninot.getText().toString());
                insan.notlar = eskinotlar;

                String jsonY = gson.toJson(insan);
                prefsEditor.putString(orgid, jsonY);
                prefsEditor.commit();

                Intent k = new Intent(ChangeNote.this,Notes.class);
                k.putExtra("userid",orgid);
                startActivity(k);

            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = mPrefs.getString(orgid, "");
                insan = (Insan)gson.fromJson(json, Insan.class);

                ArrayList<String> eskinotlar = insan.notlar;

                eskinotlar.remove(orgid); // kullancının id'sini arraylistin sonuna atmıştık şimdi çıkartıyoruz.
                eskinotlar.remove(eskinotlar.indexOf(eskinot));
                insan.notlar = eskinotlar;

                String jsonY = gson.toJson(insan);
                prefsEditor.putString(orgid, jsonY);
                prefsEditor.commit();

                Intent k = new Intent(ChangeNote.this,Notes.class);
                k.putExtra("userid",orgid);
                startActivity(k);

            }
        });


    }
}
