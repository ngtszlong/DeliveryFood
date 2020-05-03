package com.ngtszlong.deliveryfood.Restaurant.Food;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ngtszlong.deliveryfood.Order.Order;
import com.ngtszlong.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> foodArrayList;

    SimpleDateFormat format;
    Date date;
    String str;
    Order order;
    FirebaseAuth fAuth;
    FirebaseUser user;

    public FoodAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        order = new Order();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        SharedPreferences sp = context.getSharedPreferences( "Setting", 0 );
        final Food food = foodArrayList.get(position);
        if (sp.getString("My_Lang", "").equals("zh")){
            holder.txt_foodname.setText(food.getName_chi());
            holder.txt_description.setText(food.getDescription_chi());
            holder.txt_other.setText(food.getOther_chi());
        }else if (sp.getString("My_Lang", "").equals("en")){
            holder.txt_foodname.setText(food.getName_eng());
            holder.txt_description.setText(food.getDescription_eng());
            holder.txt_other.setText(food.getOther_eng());
        }
        holder.txt_price.setText(food.getPrice());
        Picasso.get().load(food.getImage_main()).into(holder.img_foodimage);
        Picasso.get().load(food.getImage_2()).into(holder.img_other);
        if (food.getImage_2().equals("https://upload.wikimedia.org/wikipedia/commons/c/c0/White_color_Page.jpg")) {
            holder.img_other.setVisibility(View.GONE);
        } else {
            holder.img_other.setVisibility(View.VISIBLE);
        }
        holder.cv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fAuth.getCurrentUser() == null) {
                    Toast.makeText(context, R.string.login_first, Toast.LENGTH_SHORT).show();
                }else{
                    getcurrenttime();
                    String uid = user.getUid();
                    order.setRestaurant_No(food.getRestaurant_No());
                    order.setName_chi(food.getName_chi());
                    order.setName_eng(food.getName_eng());
                    order.setUid(uid);
                    order.setPrice(food.getPrice());
                    order.setTime(str);
                    order.setRestaurantname_eng(food.getRestaurant_eng());
                    order.setRestaurantname_chi(food.getRestaurant_chi());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Order");
                    reference.child(uid).child(str).setValue(order);
                    Toast.makeText(holder.itemView.getContext(), R.string.You_have_been_order, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        CardView cv_order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_foodimage = itemView.findViewById(R.id.img_foodimage);
            txt_foodname = itemView.findViewById(R.id.txt_foodname);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_other = itemView.findViewById(R.id.txt_other);
            img_other = itemView.findViewById(R.id.img_other);
            txt_price = itemView.findViewById(R.id.txt_price);
            cv_order = itemView.findViewById(R.id.cv_order);
        }
    }

    private void getcurrenttime() {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date(System.currentTimeMillis());
        str = format.format(date);
    }
}
