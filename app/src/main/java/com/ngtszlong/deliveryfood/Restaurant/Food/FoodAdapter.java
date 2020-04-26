package com.ngtszlong.deliveryfood.Restaurant.Food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngtszlong.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> foodArrayList;

    public FoodAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodArrayList.get(position);
        holder.txt_foodname.setText(food.getName_eng());
        holder.txt_description.setText(food.getDescription_eng());
        holder.txt_other.setText(food.getOther_eng());
        holder.txt_price.setText(food.getPrice());
        Picasso.get().load(food.getImage_main()).into(holder.img_foodimage);
        Picasso.get().load(food.getImage_2()).into(holder.img_other);
        if (food.getImage_2().equals("https://upload.wikimedia.org/wikipedia/commons/c/c0/White_color_Page.jpg")){
            holder.img_other.setVisibility(View.GONE);
        }else{
            holder.img_other.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_foodimage;
        TextView txt_foodname;
        TextView txt_description;
        TextView txt_other;
        ImageView img_other;
        TextView txt_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_foodimage = itemView.findViewById(R.id.img_foodimage);
            txt_foodname = itemView.findViewById(R.id.txt_foodname);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_other = itemView.findViewById(R.id.txt_other);
            img_other = itemView.findViewById(R.id.img_other);
            txt_price = itemView.findViewById(R.id.txt_price);
        }
    }
}
