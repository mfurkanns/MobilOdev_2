package com.example.termproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AyarlarGuncelleDiyalog extends AppCompatDialogFragment {

    public TextView tv;

    public AyarlarClass ayarlar;

    public AyarlarGuncelleDiyalogListener listen;

    public AyarlarGuncelleDiyalog(AyarlarClass a){
        ayarlar = a;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ayarlar_guncelle_layout,null);

        tv = (TextView) view.findViewById(R.id.textView5);

        builder.setView(view);
        builder.setTitle("Ayarları Güncelle");
        builder.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getContext(),MainActivity.class);
                startActivity(i);
            }
        }); builder.setPositiveButton("tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listen.ayarlarGuncelle(ayarlar);


                // yeni ayarlar olusturulucak ozellikler guncellenecek
                // burada etkinlik shared prefle telefona kaydedilecek ve alarm olusturulacak.
                Toast.makeText(getContext(), "Ayarlar Güncellendi", Toast.LENGTH_SHORT).show();

                Intent j = new Intent(getContext(),MainActivity.class);
                startActivity(j);

            }
        });
        return builder.create();

    }

    public interface AyarlarGuncelleDiyalogListener {
        void ayarlarGuncelle(AyarlarClass ayr);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        super.onAttach(context);
        try {
            listen = (AyarlarGuncelleDiyalogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
