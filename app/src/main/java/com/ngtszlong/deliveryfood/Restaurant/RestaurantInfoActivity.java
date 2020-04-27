package com.ngtszlong.deliveryfood.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngtszlong.deliveryfood.Map.ViewMapsActivity;
import com.ngtszlong.deliveryfood.R;
import com.ngtszlong.deliveryfood.Restaurant.Food.Food;
import com.ngtszlong.deliveryfood.Restaurant.Food.FoodAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantInfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    String no;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    ImageView img_restinfo;
    TextView txt_restinfoname;
    TextView txt_restinfotype;
    TextView txt_location;
    TextView btn_restlocation;
    TextView txt_time;

    RecyclerView rv_foodlist;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Food> foods;
    FoodAdapter foodAdapter;

    RecyclerView rv_foodimage;
    ArrayList<Image> images;
    ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        img_restinfo = findViewById(R.id.img_restinfo);
        txt_restinfoname = findViewById(R.id.txt_restinfoname);
        txt_restinfotype = findViewById(R.id.txt_restinfotype);
        txt_location = findViewById(R.id.txt_restinfolocation);
        txt_time = findViewById(R.id.txt_time);
        btn_restlocation = findViewById(R.id.btn_restinfolocate);

        toolbar = findViewById(R.id.tb_restinfo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Restaurant Information");

        Intent intent = getIntent();
        no = intent.getStringExtra("type");

        getRestaurantInfo();

        btn_restlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RestaurantInfoActivity.this, ViewMapsActivity.class);
                intent1.putExtra("Location", txt_location.getText());
                startActivity(intent1);
            }
        });

        rv_foodlist = findViewById(R.id.rv_foodlist);
        rv_foodlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_foodlist.setLayoutManager(layoutManager);
        getFoodInfo();

        rv_foodimage = findViewById(R.id.rv_foodimage);
        rv_foodimage.setHasFixedSize(true);
        rv_foodimage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        getFoodImage();
    }

    private void getFoodImage() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Image").child(no);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                images = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Image l = dataSnapshot1.getValue(Image.class);
                    images.add(l);
                }
                imageAdapter = new ImageAdapter(RestaurantInfoActivity.this, images);
                rv_foodimage.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.keepSynced(true);
    }

    private void getFoodInfo() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("food");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foods = new ArrayList<Food>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Food l = dataSnapshot1.getValue(Food.class);
                    if (no.equals(l.getRestaurant_No())) {
                        foods.add(l);
                    }
                }
                foodAdapter = new FoodAdapter(RestaurantInfoActivity.this, foods);
                rv_foodlist.setAdapter(foodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.keepSynced(true);
    }

    private void getRestaurantInfo() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("restaurant");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Restaurant l = dataSnapshot1.getValue(Restaurant.class);
                    if (no.equals(l.getRestaurant_No())) {
                        Picasso.get().load(l.getImage()).into(img_restinfo);
                        txt_restinfoname.setText(l.getRestaurant_eng());
                        txt_restinfotype.setText(l.getType_eng());
                        txt_location.setText(l.getLocation_eng());
                        txt_time.setText(l.getDelivery_time());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.keepSynced(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
