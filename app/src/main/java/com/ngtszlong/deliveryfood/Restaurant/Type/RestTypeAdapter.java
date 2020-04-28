package com.ngtszlong.deliveryfood.Restaurant.Type;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngtszlong.deliveryfood.MainActivity;
import com.ngtszlong.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestTypeAdapter extends RecyclerView.Adapter<RestTypeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RestType> restTypeArrayList;
    private RestTypeAdapter.OnItemClickLister onItemClickListener;

    public RestTypeAdapter(Context context, ArrayList<RestType> restTypeArrayList) {
        this.context = context;
        this.restTypeArrayList = restTypeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resttype_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences sp = context.getSharedPreferences( "Setting", 0 );
        RestType restType = restTypeArrayList.get(position);
        if (sp.getString("My_Lang", "").equals("zh")){
            holder.txt_rest.setText(restType.getType_chi());
        }else if (sp.getString("My_Lang", "").equals("en")){
            holder.txt_rest.setText(restType.getType_eng());
        }
        Picasso.get().load(restType.getImage()).into(holder.img_rest);
    }

    @Override
    public int getItemCount() {
        return restTypeArrayList.size();
    }

    public interface OnItemClickLister {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MainActivity listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_rest;
        ImageView img_rest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_rest = itemView.findViewById(R.id.img_resttype);
            txt_rest = itemView.findViewById(R.id.txt_resttype);
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
