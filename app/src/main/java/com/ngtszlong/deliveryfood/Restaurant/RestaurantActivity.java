package com.ngtszlong.deliveryfood.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngtszlong.deliveryfood.R;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity  implements RestaurantAdapter.OnItemClickLister{
    String type;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    public static ArrayList<Restaurant> restaurants;
    private RestaurantAdapter restaurantAdapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        recyclerView = findViewById(R.id.rv_rest);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getrestaurant();

        toolbar = findViewById(R.id.tb_rest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(type);
    }

    private void getrestaurant() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("restaurant");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurants = new ArrayList<Restaurant>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Restaurant l = dataSnapshot1.getValue(Restaurant.class);
                    if (l.getType_eng().equals(type)){
                        restaurants.add(l);
                    }
                }
                restaurantAdapter = new RestaurantAdapter(RestaurantActivity.this, restaurants);
                recyclerView.setAdapter(restaurantAdapter);
                restaurantAdapter.setOnItemClickListener(RestaurantActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.keepSynced(true);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
