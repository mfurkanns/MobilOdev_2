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

public class YineleSecDiyalog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {


    public Integer secim;
    public String deger;
    public Spinner yineleS;

    public YineleSecDiyalogListener listen;



    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.yinele_sec_layout,null);


        yineleS = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.yinele, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yineleS.setAdapter(adapter);
        yineleS.setOnItemSelectedListener(this);


        builder.setView(view)
                .setTitle("Yinele")
                .setNegativeButton("iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listen.yineleAl(secim,deger);
                        Toast.makeText(getContext(), "Hatırlatma sıklığı '"+deger+"' olarak ayarlandı", Toast.LENGTH_SHORT).show();

                    }
                });







        return builder.create();
    }

    public interface YineleSecDiyalogListener{
        int yineleAl(int secim,String deger);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listen = (YineleSecDiyalogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        secim = parent.getSelectedItemPosition();
        deger = yineleS.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
