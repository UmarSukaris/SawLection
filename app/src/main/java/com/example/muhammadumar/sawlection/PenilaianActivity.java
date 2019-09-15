package com.example.muhammadumar.sawlection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.muhammadumar.sawlection.adapter.AdapterRangking;
import com.example.muhammadumar.sawlection.controller.AppController;
import com.example.muhammadumar.sawlection.data.DataRangking;
import com.example.muhammadumar.sawlection.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class PenilaianActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataRangking> itemList = new ArrayList<DataRangking>();
    AdapterRangking adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id_rangking, txt_id_kriteria, txt_nilai;
    String id_rangking, id_pendaftar, id_kriteria, nilai, id_seleksi;
    Spinner spinner_id_kriteria;


    private static final String TAG = PenilaianActivity.class.getSimpleName();

    String[] idkriteria, nama_kriteria;
    private static String url_select = Server.URL + "perangkingan/perangkingan-pendaftar-select.php";
    private static String url_insert = Server.URL + "perangkingan/perangkingan-insert.php";
    private static String url_edit = Server.URL + "perangkingan/perangkingan-edit.php";
    private static String url_update = Server.URL + "perangkingan/perangkingan-pendaftar-update.php";
    private static String url_select_kriteria = Server.URL + "kriteria/kriteria-select.php";
    public static final String TAG_ID_RANGKING = "id_rangking";
    public static final String TAG_ID_PENDAFTAR = "id_pendaftar";
    public static final String TAG_ID_KRITERIA = "id_kriteria";
    public static final String TAG_NILAI = "nilai";
    public static final String TAG_NAMA_PENDAFTAR= "nama_pendaftar";
    public static final String TAG_NAMA_KRITERIA = "nama_kriteria";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    ArrayAdapter<String> spinnerArrayAdapter;
    String tag_json_obj = "json_obj_req";
    public final static String TAG_ID_SELEKSI = "id_seleksi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle extra = getIntent().getExtras();
        id_seleksi = extra.getString(TAG_ID_SELEKSI);
        id_pendaftar = extra.getString(TAG_ID_PENDAFTAR);

        // menghubungkan variablel pada layout dan pada java
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterRangking(PenilaianActivity.this, itemList);
        list.setAdapter(adapter);

        // menampilkan widget refresh
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

        // fungsi floating action button memanggil form tambah buku
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("", "", "", "SIMPAN");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                // TODO Auto-generated method stub
                final String id_rangkingx = itemList.get(position).getId_rangking();
                final CharSequence[] dialogitem = {"Ubah"};
                dialog = new AlertDialog.Builder(PenilaianActivity.this);
                dialog.setCancelable(true);

                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                edit(id_rangkingx);
                                break;
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
    private void kosong() {
        txt_id_rangking.setText(null);
        spinner_id_kriteria.setAdapter(null);
        txt_nilai.setText(null);
    }

    // untuk menampilkan dialog from pengguna
    private void DialogForm(String id_rangkingx, String id_kriteriax, String nilaix, String button) {
        ambil_kriteria(id_kriteriax);
        dialog = new AlertDialog.Builder(PenilaianActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_penilaian, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Penilaian");
        txt_id_rangking = (EditText) dialogView.findViewById(R.id.txt_id_rangking);
        txt_id_kriteria = (EditText) dialogView.findViewById(R.id.txt_id_kriteria);
        spinner_id_kriteria = (Spinner) dialogView.findViewById(R.id.spin_id_kriteria);
        txt_nilai = (EditText) dialogView.findViewById(R.id.txt_nilai);
        if (!id_rangkingx.isEmpty()) {
            txt_id_rangking.setText(id_rangkingx);
            txt_nilai.setText(nilaix);
        } else {
            kosong();
        }
        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id_rangking = txt_id_rangking.getText().toString();
                id_kriteria = idkriteria[spinner_id_kriteria.getSelectedItemPosition()] + "";
                nilai = txt_nilai.getText().toString();

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

    private void ambil_kriteria(final String id_kriteria) {
        final List<String> temp_idkriteria, temp_nama_kriteria;
        temp_idkriteria = new ArrayList<String>();
        temp_nama_kriteria = new ArrayList<String>();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url_select_kriteria, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Parsing json
                try {
                    JSONArray values = new JSONArray(response);
                    idkriteria = new String [values.length()];
                    nama_kriteria = new String[values.length()];
                    for (int i = 0; i < values.length(); i++) {
                        JSONObject obj=values.getJSONObject(i);
                        temp_idkriteria.add(obj.getString("id_kriteria"));
                        temp_nama_kriteria.add(obj.getString("nama_kriteria"));

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                temp_idkriteria.toArray(idkriteria);
                temp_nama_kriteria.toArray(nama_kriteria);
                // notifikasi adanya perubahan data pada adapter
                spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_kriteria, nama_kriteria);
                spinner_id_kriteria.setAdapter(spinnerArrayAdapter);
                if (!id_kriteria.isEmpty()) {
                    spinner_id_kriteria.setSelection(spinnerArrayAdapter.getPosition(id_kriteria));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                HashMap<String,String> params =new HashMap<>();
                params.put("id_seleksi",id_seleksi);
                return params;
            }
        };

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
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

                        DataRangking item = new DataRangking();
                        item.setId_rangking(object.getString(TAG_ID_RANGKING));
                        item.setId_kriteria(object.getString(TAG_NAMA_KRITERIA));
                        item.setNilai(object.getString(TAG_NILAI));

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
                params.put("id_pendaftar", id_pendaftar);
                return params;
            }
        } ;
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id_rangking.isEmpty()) {
            url = url_insert;
        } else {
            url = url_update;
        }
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            // Cek error node pada json
                            if (success == 1) {
                                Log.d("Add/update", jObj.toString());
                                callVolley();
                                kosong();
                                Toast.makeText(PenilaianActivity.this,
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(PenilaianActivity.this,
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(PenilaianActivity.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id_rangking.isEmpty()) {
                    params.put("id_pendaftar", id_pendaftar);
                    params.put("id_kriteria", id_kriteria);
                    params.put("nilai", nilai);
                } else {
                    params.put("id_rangking", id_rangking);
                    params.put("id_pendaftar", id_pendaftar);
                    params.put("id_kriteria", id_kriteria);
                    params.put("nilai", nilai);
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk get id_rangking untuk edit/update data
    private void edit(final String id_rangkingx) {
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
                        String id_rangkingx = jObj.getString(TAG_ID_RANGKING);
                        String id_kriteriax = jObj.getString(TAG_ID_KRITERIA);
                        String nilaix = jObj.getString(TAG_NILAI);

                        DialogForm(id_rangkingx, id_kriteriax, nilaix, "UPDATE");
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(PenilaianActivity.this,
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(PenilaianActivity.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_rangking", id_rangkingx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}