package com.app.pagination;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * CREATED BY SANJAIKUMAR On 10-06-2020
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<MainData> dataArrayList;
    private Context mContext;

    public MainAdapter(Context mContext, ArrayList<MainData> dataArrayList) {
        this.dataArrayList = dataArrayList;
        this.mContext = mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            text = itemView.findViewById(R.id.text_view);
            card = itemView.findViewById(R.id.card_view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainData data = dataArrayList.get(position);
        Glide.with(mContext)
                .load(data.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
        holder.text.setText(data.getName());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Detal.class);
                i.putExtra("name",data.getName());
                i.putExtra("URL",data.getImage());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

}
