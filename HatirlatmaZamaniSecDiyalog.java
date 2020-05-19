package com.example.termproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class HatirlatmaZamaniSecDiyalog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {


    public Integer hatirlatma_zaman;
    public String deger;
    public Spinner hatirlatmaZamanS;

    public HatirlatmaZamanSecDiyalogListener listen;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.hatirlatma_zaman_sec_layout,null);

        hatirlatmaZamanS = (Spinner)view.findViewById(R.id.spinner2);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.hatirlatma_zaman, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hatirlatmaZamanS.setAdapter(adapter);
        hatirlatmaZamanS.setOnItemSelectedListener(this);


        builder.setView(view)
                .setTitle("Hatırlatma Zamanı")
                .setNegativeButton("iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listen.zamanAl(hatirlatma_zaman,deger);
                        Toast.makeText(getContext(), "Hatırlatma zamanı '"+deger+"' olarak ayarlandı", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listen = (HatirlatmaZamanSecDiyalogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface HatirlatmaZamanSecDiyalogListener{
        void zamanAl(int secim,String deger);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        deger = hatirlatmaZamanS.getSelectedItem().toString();
        hatirlatma_zaman = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        hatirlatma_zaman = 0;

    }
}
