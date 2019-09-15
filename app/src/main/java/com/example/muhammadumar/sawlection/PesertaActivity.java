package com.example.muhammadumar.sawlection;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

public class PesertaActivity extends AppCompatActivity {
    String id_seleksi, id_user;
    CardView cvtutorial, cvkriteria, cvpendaftar, cvperangkingan, cvhasil, cvgrafik;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID_SELEKSI = "id_seleksi";
    public static final String TAG_ID_USER = "id_user";
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ll = (LinearLayout) findViewById(R.id.ll);

        Bundle extras = getIntent().getExtras();
        id_seleksi = extras.getString(TAG_ID_SELEKSI);
        id_user = extras.getString(TAG_ID_USER);


        cvkriteria = (CardView) findViewById(R.id.cvkriteria);
        cvpendaftar = (CardView) findViewById(R.id.cvpendaftar);
        cvhasil = (CardView) findViewById(R.id.cvhasil);
        cvgrafik = (CardView) findViewById(R.id.cvgrafik);

        final Intent intentkriteria = new Intent(this,KriteriaSeleksi.class);
        cvkriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentkriteria.putExtra(TAG_ID_SELEKSI, id_seleksi);
                startActivity(intentkriteria);
            }
        });

        final Intent intentpendaftar = new Intent(this,PendaftarActivity.class);
        cvpendaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentpendaftar.putExtra(TAG_ID_SELEKSI, id_seleksi);
                startActivity(intentpendaftar);
            }
        });

        final Intent intenthasil = new Intent(this,NilaiActivity.class);
        cvhasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenthasil.putExtra(TAG_ID_USER, id_user);
                intenthasil.putExtra(TAG_ID_SELEKSI, id_seleksi);
                startActivity(intenthasil);
            }
        });

        final Intent intentgrafik = new Intent(this,GrafikActivity.class);
        cvgrafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentgrafik.putExtra(TAG_ID_SELEKSI, id_seleksi);
                startActivity(intentgrafik);
            }
        });

    }

}