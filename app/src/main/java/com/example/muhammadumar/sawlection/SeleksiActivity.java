package com.example.muhammadumar.sawlection;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import com.example.muhammadumar.sawlection.adapter.AdapterSeleksi;
import com.example.muhammadumar.sawlection.controller.AppController;
import com.example.muhammadumar.sawlection.data.DataSeleksi;
import com.example.muhammadumar.sawlection.util.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeleksiActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataSeleksi> itemList = new ArrayList<DataSeleksi>();
    AdapterSeleksi adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nama_seleksi, txt_id_laboratorium, txt_tahun_ajaran, txt_kuota, txt_deskripsi;
    String id, nama_seleksi, id_laboratorium, tahun_ajaran, kuota, deskripsi;
    Spinner spinner_id_laboratorium;

    private static final String TAG = SeleksiActivity.class.getSimpleName();
    String[] idlaboratorium, nama_laboratorium;

    private static String url_select     = Server.URL + "seleksi/seleksi-select.php";
    private static String url_insert     = Server.URL + "seleksi/seleksi-insert.php";
    private static String url_edit       = Server.URL + "seleksi/seleksi-edit.php";
    private static String url_update     = Server.URL + "seleksi/seleksi-update.php";
    private static String url_delete     = Server.URL + "seleksi/seleksi-delete.php";
    private static String url_select_laboratorium = Server.URL + "laboratorium/laboratorium-select.php";

    public static final String TAG_ID_SELEKSI       = "id_seleksi";
    public static final String TAG_ID_LABORATORIUM  = "id_lab";
    public static final String TAG_SELEKSI  = "nama_seleksi";
    public static final String TAG_LABORATORIUM  = "nama_lab";
    public static final String TAG_TAHUN_AJARAN  = "tahun_ajaran";
    public static final String TAG_KUOTA  = "kuota";
    public static final String TAG_DESKRIPSI = "deskripsi";

    ArrayAdapter<String> spinnerArrayAdapter;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleksi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // menghubungkan variablel pada layout dan pada java
        fab     = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterSeleksi(SeleksiActivity.this, itemList);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("", "", "", "", "", "", "SIMPAN");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, final long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Lihat", "Edit", "Delete"};
                dialog = new AlertDialog.Builder(SeleksiActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(SeleksiActivity.this, AdminActivity.class);
                                intent.putExtra(TAG_ID_SELEKSI, idx);
                                startActivity(intent);
                                break;
                            case 1:
                                edit(idx);
                                break;
                            case 2:
                                delete(idx);
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
        txt_nama_seleksi.setText(null);
        spinner_id_laboratorium.setAdapter(null);
        txt_tahun_ajaran.setText(null);
        txt_kuota.setText(null);
        txt_deskripsi.setText(null);
    }

    // untuk menampilkan dialog from biodata
    private void DialogForm(String idx, String nama_seleksix, String id_laboratoriumx, String tahun_ajaranx, String kuotax, String deskripsix, String button) {
        pilih_laboratorium(id_laboratoriumx);
        dialog = new AlertDialog.Builder(SeleksiActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_seleksi, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form seleksi");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_nama_seleksi = (EditText) dialogView.findViewById(R.id.txt_nama_seleksi);
        txt_id_laboratorium = (EditText) dialogView.findViewById(R.id.txt_id_laboratorium);
        spinner_id_laboratorium = (Spinner) dialogView.findViewById(R.id.spin_id_laboratorim);
        txt_tahun_ajaran = (EditText) dialogView.findViewById(R.id.txt_tahun_ajaran);
        txt_kuota = (EditText) dialogView.findViewById(R.id.txt_kuota);
        txt_deskripsi = (EditText) dialogView.findViewById(R.id.txt_deskripsi);

        if (!idx.isEmpty()){
            txt_id.setText(idx);
            txt_nama_seleksi.setText(nama_seleksix);
            txt_tahun_ajaran.setText(tahun_ajaranx);
            txt_kuota.setText(kuotax);
            txt_deskripsi.setText(deskripsix);

        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = txt_id.getText().toString();
                nama_seleksi = txt_nama_seleksi.getText().toString();
                id_laboratorium = idlaboratorium[spinner_id_laboratorium.getSelectedItemPosition()] + "";
                tahun_ajaran = txt_tahun_ajaran.getText().toString();
                kuota = txt_kuota.getText().toString();
                deskripsi = txt_deskripsi.getText().toString();

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

    private void pilih_laboratorium(final String id_laboratorium) {
        // membuat request JSON
        final List<String> temp_idlaboratorium, temp_nama_laboratorium;
        temp_idlaboratorium = new ArrayList<String>();
        temp_nama_laboratorium = new ArrayList<String>();
        JsonArrayRequest jArr = new JsonArrayRequest(url_select_laboratorium, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                try {
                    idlaboratorium = new String [response.length()];
                    nama_laboratorium = new String[response.length()];

                        JSONObject obj=response.getJSONObject(i);
                        temp_idlaboratorium.add(obj.getString("id_lab"));
                        temp_nama_laboratorium.add(obj.getString("nama_lab"));

                    }catch (JSONException e) {
                    e.printStackTrace();
                    }

                }
                temp_idlaboratorium.toArray(idlaboratorium);
                temp_nama_laboratorium.toArray(nama_laboratorium);

                // notifikasi adanya perubahan data pada adapter
                spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_laboratorium, nama_laboratorium);
                spinner_id_laboratorium.setAdapter(spinnerArrayAdapter);
                if (!id_laboratorium.isEmpty()) {
                    spinner_id_laboratorium.setSelection(spinnerArrayAdapter.getPosition(id_laboratorium));
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    // untuk menampilkan semua data pada listview
    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataSeleksi item = new DataSeleksi();

                        item.setId(obj.getString(TAG_ID_SELEKSI));
                        item.setNama_seleksi(obj.getString(TAG_SELEKSI));
                        item.setId_laboratorium(obj.getString(TAG_LABORATORIUM));
                        item.setTahun_ajaran(obj.getString(TAG_TAHUN_AJARAN));
                        item.setKuota(obj.getString(TAG_KUOTA));
                        item.setDeskripsi(obj.getString(TAG_DESKRIPSI));
                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
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

                        Toast.makeText(SeleksiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(SeleksiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(SeleksiActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()){
                    params.put("nama_seleksi", nama_seleksi);
                    params.put("id_laboratorium", id_laboratorium);
                    params.put("tahun_ajaran", tahun_ajaran);
                    params.put("kuota", kuota);
                    params.put("deskripsi", deskripsi);
                } else {
                    params.put("id_seleksi", id);
                    params.put("nama_seleksi", nama_seleksi);
                    params.put("id_laboratorium", id_laboratorium);
                    params.put("tahun_ajaran", tahun_ajaran);
                    params.put("kuota", kuota);
                    params.put("deskripsi", deskripsi);
                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk get edit data
    private void edit(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idx      = jObj.getString(TAG_ID_SELEKSI);
                        String nama_seleksix = jObj.getString(TAG_SELEKSI);
                        String id_laboratoriumx = jObj.getString(TAG_ID_LABORATORIUM);
                        String tahun_ajaranx = jObj.getString(TAG_TAHUN_AJARAN);
                        String kuotax = jObj.getString(TAG_KUOTA);
                        String deskripsix = jObj.getString(TAG_DESKRIPSI);

                        DialogForm(idx, nama_seleksix, id_laboratoriumx, tahun_ajaranx, kuotax, deskripsix, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(SeleksiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(SeleksiActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_seleksi", idx);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk menghapus
    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        callVolley();

                        Toast.makeText(SeleksiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(SeleksiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(SeleksiActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_seleksi", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}