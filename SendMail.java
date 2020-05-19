package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMail extends AppCompatActivity {

    private static final int galeri = 101;
    private EditText kime;
    private EditText baslik;
    private EditText mesaj;
    private String ekdosya;
    private Button gonder;
    private Button ekl;

    private Uri URI = null;
    private int kolon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        gonder = (Button) findViewById(R.id.button13);
        ekl = (Button)findViewById(R.id.button14);
        kime = (EditText) findViewById(R.id.editText15);
        baslik = (EditText) findViewById(R.id.editText17);
        mesaj = (EditText) findViewById(R.id.editText13);

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    String kim = kime.getText().toString();
                    String[] TO = {kim};

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, baslik.getText().toString());
                    emailIntent.putExtra(Intent.EXTRA_TEXT, mesaj.getText().toString());

                    if(URI!=null){
                        emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                    }

                    try {
                        startActivity(Intent.createChooser(emailIntent, "mail"));
                        finish();
                    } catch (Exception ex) {
                        Toast.makeText(SendMail.this, "Mail gönderme başarısız", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SendMail.this, "İnternet bağlantısı bulunamadı", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ekl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == galeri && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData(); // data.getData();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            Cursor cursor = getContentResolver().query(selectedImage,null , path, null, null);
            cursor.moveToFirst();                                   // path
            kolon = cursor.getColumnIndex(path);//cursor.getColumnIndex(path[0]);
            ekdosya = cursor.getString(kolon);
            URI = Uri.parse("file://" + ekdosya);
            System.out.println(ekdosya);
            cursor.close();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void add() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), galeri);
    }
}