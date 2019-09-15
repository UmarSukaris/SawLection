package com.example.muhammadumar.sawlection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AkunActivity extends AppCompatActivity {
    TextView tvakun;
    Button keluar;
    String id_user, nim, nama;
    SharedPreferences sharedPreferences;
    public static final String TAG_ID_USER = "id_user";
    public static final String TAG_NIM = "nim";
    public static final String TAG_NAMA = "nama";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);



        tvakun = (TextView) findViewById(R.id.tvakun);
        Bundle extra = getIntent().getExtras();
        nama = getIntent().getStringExtra(TAG_NAMA);
        tvakun.setText(nama);

        keluar = (Button) findViewById(R.id.keluar);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AkunActivity.this);
                builder.setMessage("Apakah anda ingin keluar?");
                builder.setCancelable(false);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(LoginActivity.session_status, false);
                        editor.putString(TAG_ID_USER, null);
                        editor.commit();

                        Intent intentkeluar = new Intent(AkunActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intentkeluar);
                    }
                })
                        .setNegativeButton("Tidak", null)
                        .show();


            }
        });

    }

}
