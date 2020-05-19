package com.example.termproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class VazgecDiyalog extends AppCompatDialogFragment  {




    public Etkinlik yeniEtk;

    public TextView tv;

    public TamamlaDiyalog.TamamlaDiyalogListener listen;




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.vazgec_layout,null);

        tv = (TextView) view.findViewById(R.id.textView51);


        builder.setView(view);
        builder.setTitle("Vazgeç");
        builder.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }); builder.setPositiveButton("tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(getContext(),MainActivity.class);
                startActivity(i);

            }
        });
        return builder.create();



    }

    public interface VazgecDiyalogListener {
        void vazgec();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        super.onAttach(context);
        try {
            listen = (TamamlaDiyalog.TamamlaDiyalogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
