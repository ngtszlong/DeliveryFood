package com.ngtszlong.deliveryfood.Order;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngtszlong.deliveryfood.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Order> orderArrayList;
    private OrderAdapter.OnItemClickLister onItemClickListener;

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences sp = context.getSharedPreferences( "Setting", 0 );
        Order order = orderArrayList.get(position);
        if (sp.getString("My_Lang", "").equals("zh")){
            holder.txt_restaurant.setText(order.getRestaurantname_chi());
            holder.txt_food.setText(order.getName_chi());
        }else if (sp.getString("My_Lang", "").equals("en")){
            holder.txt_restaurant.setText(order.getRestaurantname_eng());
            holder.txt_food.setText(order.getName_eng());
        }
        holder.txt_time.setText(order.getTime());
        holder.txt_price.setText(order.getPrice());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public interface OnItemClickLister {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OrderActivity listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_restaurant;
        TextView txt_food;
        TextView txt_price;
        TextView txt_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_restaurant = itemView.findViewById(R.id.txt_order_restaurant);
            txt_food = itemView.findViewById(R.id.txt_order_foodname);
            txt_price = itemView.findViewById(R.id.txt_order_price);
            txt_time = itemView.findViewById(R.id.txt_order_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
