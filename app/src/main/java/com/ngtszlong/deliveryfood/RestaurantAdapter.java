package com.ngtszlong.deliveryfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Restaurant> restaurantArrayList;
    private RestaurantAdapter.OnItemClickLister onItemClickListener;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurantArrayList) {
        this.context = context;
        this.restaurantArrayList = restaurantArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rest_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantArrayList.get(position);
        holder.txt_rest.setText(restaurant.getRestaurant_eng());
        Picasso.get().load(restaurant.getImage()).into(holder.img_rest);
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    public interface OnItemClickLister {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(RestaurantActivity listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_rest;
        TextView txt_rest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_rest = itemView.findViewById(R.id.img_rest);
            txt_rest = itemView.findViewById(R.id.txt_restname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
