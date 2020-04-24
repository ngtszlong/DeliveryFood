package com.ngtszlong.deliveryfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edt_name;
    EditText edt_email;
    EditText edt_password;
    EditText edt_phone;
    Button submit;

    private FirebaseAuth mAuth;
    Profile profile;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.tb_register);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_phone = findViewById(R.id.edt_phone);
        submit = findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        profile = new Profile();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edt_name.getText().toString().trim();
                final String email = edt_email.getText().toString().trim();
                final String password = edt_password.getText().toString().trim();
                final String phone = edt_phone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    edt_name.setError("You need to input your username");
                } else if (TextUtils.isEmpty(email)) {
                    edt_email.setError("You need to input your email");
                } else if (TextUtils.isEmpty(password)) {
                    edt_phone.setError("You need to input your phone");
                } else if (TextUtils.isEmpty(phone)) {
                    edt_phone.setError("You need to input your phone");
                } else {
                    progressDialog.setMessage("Registering Please Wait...");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();
                                profile.setUid(uid);
                                profile.setEmail(email);
                                profile.setName(name);
                                profile.setPassword(password);
                                profile.setPhone(phone);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(profile);
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                System.out.println(task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
