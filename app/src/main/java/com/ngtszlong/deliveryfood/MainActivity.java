package com.ngtszlong.deliveryfood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngtszlong.deliveryfood.Restaurant.RestType;
import com.ngtszlong.deliveryfood.Restaurant.RestTypeAdapter;
import com.ngtszlong.deliveryfood.Restaurant.Restaurant;
import com.ngtszlong.deliveryfood.Restaurant.RestaurantActivity;
import com.ngtszlong.deliveryfood.Restaurant.RestaurantAdapter;
import com.ngtszlong.deliveryfood.Setting.SettingActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener, RestTypeAdapter.OnItemClickLister, RestaurantAdapter.OnItemClickLister {
    Toolbar toolbar;
    SearchView searchView;
    TextView find_location;
    TextView txt_location;
    LocationManager locationManager;
    int REQUEST_LOCATION = 123;
    double latitude;
    double longitude;

    RecyclerView rv_resttype;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    public static ArrayList<RestType> restTypes;
    private RestTypeAdapter restTypeAdapter;

    RecyclerView rv_rest;
    ArrayList<Restaurant> restaurants;
    private RestaurantAdapter restaurantAdapter;
    int PLACE_PICKER_REQUEST = 1;

    double latitude_sendback;
    double longitude_sendback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        latitude_sendback = intent.getDoubleExtra("latitude", 0.0);
        longitude_sendback = intent.getDoubleExtra("longitude", 0.0);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchView = findViewById(R.id.searchview);
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(true);

        find_location = findViewById(R.id.location);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        if (latitude_sendback != 0 || longitude_sendback != 0) {
            getLocationNoGPS();
        } else {
            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            onLocationChanged(location);
            getlocationname(location);
        }

        txt_location = findViewById(R.id.searchlocation);
        txt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        rv_resttype = findViewById(R.id.rv_resttype);
        rv_resttype.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_resttype.setLayoutManager(layoutManager);
        getfoodtype();

        rv_rest = findViewById(R.id.rv_main_rest);
        rv_rest.setHasFixedSize(true);
        rv_rest.setLayoutManager(new LinearLayoutManager(this));
        rv_rest.setLayoutManager(new GridLayoutManager(this, 3));
        getrest();
    }

    private void getrest() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("restaurant");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurants = new ArrayList<Restaurant>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Restaurant l = dataSnapshot1.getValue(Restaurant.class);
                    restaurants.add(l);
                }
                restaurantAdapter = new RestaurantAdapter(MainActivity.this, restaurants);
                rv_rest.setAdapter(restaurantAdapter);
                restaurantAdapter.setOnItemClickListener(MainActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.keepSynced(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_detail:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void getlocationname(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = null;
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                addressList = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addressList.get(0).getFeatureName();
                find_location.setText(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLocationNoGPS() {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = null;
            addressList = geocoder.getFromLocation(latitude_sendback, longitude_sendback, 1);
            String address = addressList.get(0).getFeatureName();
            find_location.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        RestType restType = restTypes.get(position);
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("type", restType.getType_eng());
        startActivity(intent);
    }

    public void getfoodtype() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("resttype");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restTypes = new ArrayList<RestType>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RestType l = dataSnapshot1.getValue(RestType.class);
                    restTypes.add(l);
                }
                restTypeAdapter = new RestTypeAdapter(MainActivity.this, restTypes);
                rv_resttype.setAdapter(restTypeAdapter);
                restTypeAdapter.setOnItemClickListener(MainActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.keepSynced(true);
    }
}
