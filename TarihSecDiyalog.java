package com.example.termproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TarihSecDiyalog extends AppCompatDialogFragment {

    public CalendarView takvim;
    public int gun;
    public int ay;
    public int yil;
    public TarihSecDiyalogListener listen;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tarih_sec_layout,null);


        takvim = (CalendarView)view.findViewById(R.id.calendarView);

        takvim.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                gun = dayOfMonth;
                ay = month+1;
                yil = year;

            }
        });

        builder.setView(view)
                .setTitle("Tarih")
                .setNegativeButton("iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gun = 0;
                        ay = 0;
                        yil = 0;

                    }
                })
                .setPositiveButton("tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    listen.tarihAl(gun,ay,yil);

                    }
                });



        return builder.create();


    }


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        try {
            listen = (TarihSecDiyalogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface TarihSecDiyalogListener{
        void tarihAl(int gun,int ay,int yil);


    }



}
