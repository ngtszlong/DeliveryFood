package com.ngtszlong.deliveryfood.Setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ngtszlong.deliveryfood.MainActivity;
import com.ngtszlong.deliveryfood.OrderActivity;
import com.ngtszlong.deliveryfood.R;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener {
    Toolbar toolbar;
    CardView account;
    CardView order;
    CardView language;
    CardView login;
    CardView logout;
    CardView register;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadLocale();
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.tb_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        account = findViewById(R.id.Account);
        order = findViewById(R.id.order);
        language = findViewById(R.id.Language);
        login = findViewById(R.id.Login);
        logout = findViewById(R.id.Logout);
        register = findViewById(R.id.Register);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            firebaseUser = mAuth.getCurrentUser();
            String email = firebaseUser.getEmail();
            getSupportActionBar().setSubtitle(email);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingActivity.this, AccountActivity.class);
                    startActivity(intent);

                }
            });
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SettingActivity.this, "You must login first", Toast.LENGTH_SHORT).show();
                }
            });
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SettingActivity.this, "You must login first", Toast.LENGTH_SHORT).show();
                }
            });;

            logout.setVisibility(View.GONE);
        }

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChangeLanguageDialog();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
    }

    private void ShowChangeLanguageDialog() {
        final String[] listItems = {"中文", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("zh");
                    finish();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                }
                if (which == 1) {
                    setLocale("en");
                    finish();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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


    private void openDialog() {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(), "Login Dialog");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void applyText(String email, String password) {

        progressDialog.setMessage("Logging in, Please wait...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SettingActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    System.out.println(task.getException().getMessage());
                }
            }
        });
    }
}
