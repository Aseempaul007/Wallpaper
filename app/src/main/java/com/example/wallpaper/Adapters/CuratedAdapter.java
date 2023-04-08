package com.example.wallpaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpaper.Listners.OnRecyclerClickListner;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CuratedAdapter extends RecyclerView.Adapter<CuratedAdapter.CuratedViewHolder>{

    Context context;
    List<Photo> list;
    OnRecyclerClickListner listner;

    public CuratedAdapter(Context context, List<Photo> list, OnRecyclerClickListner listner) {
        this.context = context;
        this.list = list;
        this.listner = listner;
    }

    @NonNull
    @Override
    public CuratedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_list,parent,false);
        return new CuratedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuratedViewHolder holder, int position) {

        Picasso.get().load(list.get(position).getSrc().getMedium()).placeholder(R.drawable.placeholder_image).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CuratedViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;

        public CuratedViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.home_list_container);
            imageView = itemView.findViewById(R.id.imageView_list);

        }
    }

}
