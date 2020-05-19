package com.example.mobilodev;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.recyclerview.widget.RecyclerView.*;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private ArrayList<String> fotolar = new ArrayList<String>();
    private ArrayList<String> kullanicilar = new ArrayList<String>();
    private ArrayList<String> parolalar = new ArrayList<String>();
    private Context context ;

    public Adapter( Context context,ArrayList<String> fotolar, ArrayList<String> kullanicilar, ArrayList<String> parolalar) { // diğer kullanıcıların özelliklerinin gösterildiği adapter
        this.fotolar = fotolar;
        this.kullanicilar = kullanicilar;
        this.parolalar = parolalar;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // kullanıcıların listelenmesi için recyclerview kullanılmıştır.

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        Holder holder = new Holder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {

        Glide.with(context).load(decodeBase64(fotolar.get(position))).into(holder.foto); // bitmap alıcak ;
        holder.kullanici.setText(kullanicilar.get(position));
        holder.parola.setText(parolalar.get(position));
    }

    @Override
    public int getItemCount() {
        return kullanicilar.size();
    }

    public static class Holder extends ViewHolder {

        TextView kullanici;
        TextView parola;
        CircleImageView foto;
        RelativeLayout layout;

        public Holder(View v) {
            super(v);
            foto = v.findViewById(R.id.foto);
            kullanici = v.findViewById(R.id.isim7);
            parola = v.findViewById(R.id.sifre);
            layout = v.findViewById(R.id.layout);

        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
