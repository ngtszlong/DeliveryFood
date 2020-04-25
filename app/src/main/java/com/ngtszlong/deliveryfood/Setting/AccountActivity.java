package com.ngtszlong.deliveryfood.Setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngtszlong.deliveryfood.MainActivity;
import com.ngtszlong.deliveryfood.R;

public class AccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edt_name;
    EditText edt_email;
    EditText edt_password;
    EditText edt_phone;
    Button btn_update;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        toolbar = findViewById(R.id.tb_account);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your Information");

        edt_name = findViewById(R.id.edt_disname);
        edt_email = findViewById(R.id.edt_disemail);
        edt_password = findViewById(R.id.edt_dispassword);
        edt_phone = findViewById(R.id.edt_disphone);
        btn_update = findViewById(R.id.btn_account_update);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference().child("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Profile l = dataSnapshot1.getValue(Profile.class);
                    if (firebaseUser.getUid().equals(l.getUid())) {
                        edt_name.setText(l.getName());
                        edt_phone.setText(l.getPhone());
                        edt_email.setText(l.getEmail());
                        edt_password.setText(l.getPassword());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedata();
            }
        });
    }

    private void updatedata() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        mAuth.updateCurrentUser(firebaseUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference().child("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Profile profile = dataSnapshot1.getValue(Profile.class);
                    if (firebaseUser.getUid().equals(profile.getUid())) {
                        profile.setName(edt_name.getText().toString());
                        profile.setPhone(edt_phone.getText().toString());
                        mRef.child(profile.getUid()).setValue(profile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "Data Successfully Updated", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
