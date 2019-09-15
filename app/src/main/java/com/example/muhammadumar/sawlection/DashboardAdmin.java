package com.example.muhammadumar.sawlection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class DashboardAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String id_user, nim, nama;
    public static final String my_shared_preferences = "my_shared_preferences";
    SharedPreferences sharedpreferences;
    public static final String TAG_ID_USER = "id_user";
    public static final String TAG_NIM = "nim";
    public static final String TAG_NAMA = "nama";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id_user = getIntent().getStringExtra(TAG_ID_USER);
        nim = getIntent().getStringExtra(TAG_NIM);
        nama = getIntent().getStringExtra(TAG_NAMA);
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_panitia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_seleksi) {
            Intent intentseleksi = new Intent(DashboardAdmin.this, SeleksiActivity.class);
            intentseleksi.putExtra(TAG_ID_USER, id_user);
            startActivity(intentseleksi);

        } else if (id == R.id.nav_akun) {
            Intent intentakun = new Intent(DashboardAdmin.this, AkunActivity.class);
            intentakun.putExtra(TAG_ID_USER, id_user);
            intentakun.putExtra(TAG_NIM, nim);
            intentakun.putExtra(TAG_NAMA, nama);
            startActivity(intentakun);
        }else if (id == R.id.nav_exit) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardAdmin.this);
            builder.setMessage("Apakah anda ingin keluar?");
            builder.setCancelable(false);
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(LoginActivity.session_status, false);
                    editor.putString(TAG_ID_USER, null);
                    editor.commit();

                    Intent intentkeluar = new Intent(DashboardAdmin.this, LoginActivity.class);
                    finish();
                    startActivity(intentkeluar);
                }
            })
                    .setNegativeButton("Tidak", null)
                    .show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
