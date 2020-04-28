package com.ngtszlong.deliveryfood;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ngtszlong.deliveryfood.Map.MapsActivity;
import com.ngtszlong.deliveryfood.Restaurant.Restaurant;
import com.ngtszlong.deliveryfood.Restaurant.RestaurantActivity;
import com.ngtszlong.deliveryfood.Restaurant.RestaurantAdapter;
import com.ngtszlong.deliveryfood.Restaurant.RestaurantInfoActivity;
import com.ngtszlong.deliveryfood.Restaurant.Type.RestType;
import com.ngtszlong.deliveryfood.Restaurant.Type.RestTypeAdapter;
import com.ngtszlong.deliveryfood.Setting.SettingActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener, RestTypeAdapter.OnItemClickLister, RestaurantAdapter.OnItemClickLister {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    Toolbar toolbar;
    SearchView searchView;
    TextView find_location;
    TextView txt_popular;
    CardView cv_location;
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

    double latitude_sendback;
    double longitude_sendback;

    ImageButton btn_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        LoadLocale();
        SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firststart = pref.getBoolean("firstStart", true);
        if (firststart) {
            showStartDialog();
        }
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        latitude_sendback = intent.getDoubleExtra("latitude", 0.0);
        longitude_sendback = intent.getDoubleExtra("longitude", 0.0);

        txt_popular = findViewById(R.id.txt_featured);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchView = findViewById(R.id.searchview);
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

        find_location = findViewById(R.id.location);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        if (latitude_sendback != 0 || longitude_sendback != 0) {
            getLocationNoGPS();
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            onLocationChanged(location);
            getlocationname(location);
        }

        cv_location = findViewById(R.id.cv_delivery);
        cv_location.setOnClickListener(new View.OnClickListener() {
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

        btn_voice = findViewById(R.id.btn_voice_search);
        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void search(String newText) {
        if (!newText.equals("")) {
            rv_resttype.setVisibility(View.GONE);
            txt_popular.setText(R.string.Searchresult);
        } else {
            rv_resttype.setVisibility(View.VISIBLE);
            txt_popular.setText(R.string.popular_restaurant);
        }
        ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
        for (Restaurant object : restaurants) {
            if (object.getRestaurant_eng().toLowerCase().contains(newText.toLowerCase()) || object.getRestaurant_chi().contains(newText)) {
                restaurantArrayList.add(object);
            }
        }
        RestaurantAdapter restaurantAdapter1 = new RestaurantAdapter(this, restaurantArrayList);
        rv_rest.setAdapter(restaurantAdapter1);
        restaurantAdapter1.setOnItemClickListener(MainActivity.this);
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.Speak_something);
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                searchView.setQuery(result.get(0), false);
            }
        }
    }

    private void showStartDialog() {
        final String[] listItems = {"中文", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("選擇語言 Choose Language");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("zh");
                    finish();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    firststart();
                }
                if (which == 1) {
                    setLocale("en");
                    finish();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    firststart();
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void firststart() {
        SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void getrest() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("restaurant");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurants = new ArrayList<>();
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
        if (item.getItemId() == R.id.person_detail) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
            List<Address> addressList;
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
            List<Address> addressList;
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
        SharedPreferences sp = getSharedPreferences( "Setting", 0 );
        if (sp.getString("My_Lang", "").equals("zh")){
            intent.putExtra("type", restType.getType_chi());
        }else if (sp.getString("My_Lang", "").equals("en")){
            intent.putExtra("type", restType.getType_eng());
        }
        startActivity(intent);
    }

    public void getfoodtype() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("resttype");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restTypes = new ArrayList<>();
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

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void LoadLocale() {
        SharedPreferences preferences = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }

    @Override
    public void restonItemClick(int position) {
        Restaurant restaurant = restaurants.get(position);
        Intent intent = new Intent(this, RestaurantInfoActivity.class);
        intent.putExtra("type", restaurant.getRestaurant_No());
        startActivity(intent);
    }

    public void checkPermission() {
        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }
        };
        TedPermission.with(MainActivity.this).setPermissionListener(permissionListener).setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).setGotoSettingButton(true).check();
    }
}