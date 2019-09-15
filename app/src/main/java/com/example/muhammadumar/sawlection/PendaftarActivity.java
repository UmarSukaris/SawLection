package com.example.muhammadumar.sawlection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import com.example.muhammadumar.sawlection.adapter.AdapterPendaftar;
import com.example.muhammadumar.sawlection.controller.AppController;
import com.example.muhammadumar.sawlection.data.DataPendaftar;
import com.example.muhammadumar.sawlection.util.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendaftarActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataPendaftar> itemList = new ArrayList<DataPendaftar>();
    AdapterPendaftar adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nim, txt_nama;
    String id, id_seleksi, nim, nama;

    private static final String TAG = PendaftarActivity.class.getSimpleName();

    private static String url_select     = Server.URL + "pendaftar/pendaftar-select.php";
    private static String url_insert     = Server.URL + "pendaftar/pendaftar-insert.php";
    private static String url_edit       = Server.URL + "pendaftar/pendaftar-edit.php";
    private static String url_update     = Server.URL + "pendaftar/pendaftar-update.php";
    private static String url_delete     = Server.URL + "pendaftar/pendaftar-delete.php";

    public static final String TAG_ID_PENDAFTAR       = "id_pendaftar";
    public static final String TAG_NIM = "nim";
    public static final String TAG_NAMA  = "nama";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    public final static String TAG_ID_SELEKSI = "id_seleksi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle extra = getIntent().getExtras();
        id_seleksi = extra.getString(TAG_ID_SELEKSI);

        // menghubungkan variablel pada layout dan pada java
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterPendaftar(PendaftarActivity.this, itemList);
        list.setAdapter(adapter);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        // fungsi floating action button memanggil form biodata

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Input Nilai"};
                dialog = new AlertDialog.Builder(PendaftarActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(PendaftarActivity.this, PenilaianActivity.class);

                                intent.putExtra(TAG_ID_SELEKSI, id_seleksi);
                                intent.putExtra(TAG_ID_PENDAFTAR, idx);
                                startActivity(intent);
                        }
                    }
                }).show();
                return false;
            }
        });

    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // untuk mengosongi edittext pada form
    private void kosong(){
        txt_id.setText(null);
        txt_nim.setText(null);
        txt_nama.setText(null);
    }

    // untuk menampilkan dialog from biodata
    private void DialogForm(String idx, String nimx, String namax, String button) {
        dialog = new AlertDialog.Builder(PendaftarActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_pendaftar, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Pendaftar");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_nim     = (EditText) dialogView.findViewById(R.id.txt_nim);
        txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);

        if (!idx.isEmpty()){
            txt_id.setText(idx);
            txt_nim.setText(nimx);
            txt_nama.setText(namax);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id      = txt_id.getText().toString();
                nim     = txt_nim.getText().toString();
                nama    = txt_nama.getText().toString();

                simpan_update();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
    }

    // untuk menampilkan semua data pada listview
    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        StringRequest stringRequest =new StringRequest(Request.Method.POST, url_select, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray values = new JSONArray(response);
                    for (int i = 0; i < values.length(); i++) {
                        JSONObject object=values.getJSONObject(i);

                        DataPendaftar item = new DataPendaftar();

                        item.setId(object.getString(TAG_ID_PENDAFTAR));
                        item.setNim(object.getString(TAG_NIM));
                        item.setNama_pendaftar(object.getString(TAG_NAMA));
                        itemList.add(item);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                HashMap<String,String> params =new HashMap<>();
                params.put("id_seleksi",id_seleksi);
                return params;
            }
        } ;
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id.isEmpty()){
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());

                        callVolley();
                        kosong();

                        Toast.makeText(PendaftarActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(PendaftarActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(PendaftarActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()){
                    params.put("id_seleksi", id_seleksi);
                    params.put("nim", nim);
                    params.put("nama", nama);
                } else {
                    params.put("id_pendaftar", id);
                    params.put("id_seleksi", id_seleksi);
                    params.put("nim", nim);
                    params.put("nama", nama);
                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}