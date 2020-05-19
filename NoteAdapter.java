package com.example.mobilodev;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>{

    private ArrayList<String> kullanicinotlar;

    private Context context ;

    public NoteAdapter(Context context,ArrayList<String> notlar){ // notların gösterildiği adapter
        this.context = context;
        this.kullanicinotlar = notlar;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // notların listelenmesi için recyclerview kullanılmıştır.

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notitem,parent,false);
        NoteAdapter.NoteHolder holder = new NoteAdapter.NoteHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, final int position) {

        final String orgid = kullanicinotlar.get(kullanicinotlar.size()-1);
        if(position!=kullanicinotlar.size()-1){ // kullanıcı id'sini arraylistin sonuna atmıştık. Yazdırmaması için positionun size()-1'e eşit olmaması durumunda notlara erişilecektir.
            holder.not.setText(kullanicinotlar.get(position));
        }
        else{
            holder.not.setText("");
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ChangeNote.class);
                i.putExtra("not",kullanicinotlar.get(position));
                i.putExtra("userid",orgid);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kullanicinotlar.size();
    }

    public static class NoteHolder extends RecyclerView.ViewHolder {
        TextView not;
        RelativeLayout layout;

        public NoteHolder(View v) {
            super(v);
            not = v.findViewById(R.id.not);
            layout = v.findViewById(R.id.layout1);
        }
    }
}
