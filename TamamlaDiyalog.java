package com.example.termproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class TamamlaDiyalog extends AppCompatDialogFragment {




    public Etkinlik yeniEtk;

    public TextView tv;

    public TamamlaDiyalogListener listen;

    public TamamlaDiyalog(Etkinlik e){
        yeniEtk = e;
    }


    public SharedPreferences mPrefs;
    public SharedPreferences.Editor prefsEditor;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        mPrefs = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tamamla_layout,null);

        tv = (TextView) view.findViewById(R.id.textView5);



        builder.setView(view);
        builder.setTitle("Tamamla");
        builder.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }); builder.setPositiveButton("tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listen.tamamlaEtkinlik(yeniEtk);




                yeniEtk.etkinlikTanit();
                // burada etkinlik shared prefle telefona kaydedilecek ve alarm olusturulacak.









                Toast.makeText(getContext(), "Kaydedildi", Toast.LENGTH_SHORT).show();

                Intent j = new Intent(getContext(),MainActivity.class);
                startActivity(j);

            }
        });
        return builder.create();



    }

    public interface TamamlaDiyalogListener {
        void tamamlaEtkinlik(Etkinlik etk);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        super.onAttach(context);
        try {
            listen = (TamamlaDiyalogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
