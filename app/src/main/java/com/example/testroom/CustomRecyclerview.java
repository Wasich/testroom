package com.example.testroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomRecyclerview extends RecyclerView.Adapter<CustomRecyclerview.ViewHolder> {

    Context context;
    List<Repo> arrayList;

    public CustomRecyclerview(Context context, List<Repo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repo repo = arrayList.get(position);

        holder.name.setText(repo.getName());
        holder.chef.setText("By " + repo.getChef());
        holder.description.setText(repo.getDescription());
        holder.price.setText("Price: ₹" + repo.getPrice());
        holder.timestamp.setText(repo.getTimestamp());

        Glide.with(context)
                .load(repo.getThumbnail())
                .into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, description, price, chef, timestamp;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            chef = itemView.findViewById(R.id.chef);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }}
