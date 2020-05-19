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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SaatSecDiyalog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    public int saat;
    public int dakika;

    public Spinner saatS;
    public Spinner dakikaS;

    public SaatSecDiyalogListener listen;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.saat_sec_layout,null);

        saatS = view.findViewById(R.id.spinner1);
        dakikaS = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.saat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        saatS.setAdapter(adapter);
        saatS.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.dakika, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dakikaS.setAdapter(adapter1);
        dakikaS.setOnItemSelectedListener(this);


        builder.setView(view)
                .setTitle("Saat")
                .setNegativeButton("iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saat = 0;
                        dakika = 0;

                    }
                })
                .setPositiveButton("tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listen.saatAl(saat,dakika);

                    }
                });



        return builder.create();

    }

    public interface SaatSecDiyalogListener{
        void saatAl(int saat,int dakika);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listen = (SaatSecDiyalogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        saat = Integer.parseInt(saatS.getSelectedItem().toString());
        dakika= Integer.parseInt(dakikaS.getSelectedItem().toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
