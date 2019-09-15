package com.example.muhammadumar.sawlection;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import com.example.muhammadumar.sawlection.adapter.AdapterKriteria;
import com.example.muhammadumar.sawlection.controller.AppController;
import com.example.muhammadumar.sawlection.data.DataKriteria;
import com.example.muhammadumar.sawlection.util.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KriteriaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataKriteria> itemList = new ArrayList<DataKriteria>();
    AdapterKriteria adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_kriteria, txt_bobot;
    String id, id_seleksi, kriteria, tipe, bobot;
    Spinner tipe_kriteria;

    private static final String TAG = KriteriaActivity.class.getSimpleName();

    private static String url_select     = Server.URL + "kriteria/kriteria-select.php";
    private static String url_insert     = Server.URL + "kriteria/kriteria-insert.php";
    private static String url_edit       = Server.URL + "kriteria/kriteria-edit.php";
    private static String url_update     = Server.URL + "kriteria/kriteria-update.php";
    private static String url_delete     = Server.URL + "kriteria/kriteria-delete.php";

    public static final String TAG_ID       = "id_kriteria";
    public static final String TAG_KRITERIA  = "nama_kriteria";
    public static final String TAG_TIPE   = "tipe_kriteria";
    public static final String TAG_BOBOT   = "bobot_kriteria";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    public final static String TAG_ID_SELEKSI = "id_seleksi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kriteria);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle extra = getIntent().getExtras();
        id_seleksi = extra.getString(TAG_ID_SELEKSI);

        // menghubungkan variablel pada layout dan pada java
        fab     = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterKriteria(KriteriaActivity.this, itemList);
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
                DialogForm("", "", "", "","SIMPAN");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(KriteriaActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                edit(idx);
                                break;
                            case 1:
                                delete(idx);
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
    private void kosong(){
        txt_id.setText(null);
        txt_kriteria.setText(null);
        txt_bobot.setText(null);
    }

    // untuk menampilkan dialog from biodata
    private void DialogForm(String idx, String kriteriax, String tipex, String bobotx, String button) {
        dialog = new AlertDialog.Builder(KriteriaActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_kriteria, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Kriteria");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_kriteria    = (EditText) dialogView.findViewById(R.id.txt_kriteria);
        tipe_kriteria  = (Spinner) dialogView.findViewById(R.id.spin_tipe_kriteria);
        txt_bobot   = (EditText) dialogView.findViewById(R.id.txt_bobot);

        if (!idx.isEmpty()){
            txt_id.setText(idx);
            txt_kriteria.setText(kriteriax);
            txt_bobot.setText(bobotx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id      = txt_id.getText().toString();
                kriteria    = txt_kriteria.getText().toString();
                tipe  = tipe_kriteria.getSelectedItem().toString();
                bobot   = txt_bobot.getText().toString();

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

                        DataKriteria item = new DataKriteria();

                        item.setId(object.getString(TAG_ID));
                        item.setKriteria(object.getString(TAG_KRITERIA));
                        item.setTipe(object.getString(TAG_TIPE));
                        item.setBobot(object.getString(TAG_BOBOT));
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

                        Toast.makeText(KriteriaActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(KriteriaActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(KriteriaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()){
                    params.put("id_seleksi", id_seleksi);
                    params.put("nama_kriteria", kriteria);
                    params.put("tipe_kriteria", tipe);
                    params.put("bobot_kriteria", bobot);
                } else {
                    params.put("id_kriteria", id);
                    params.put("id_seleksi", id_seleksi);
                    params.put("nama_kriteria", kriteria);
                    params.put("tipe_kriteria", tipe);
                    params.put("bobot_kriteria", bobot);
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
                        String idx      = jObj.getString(TAG_ID);
                        String kriteriax    = jObj.getString(TAG_KRITERIA);
                        String tipex  = jObj.getString(TAG_TIPE);
                        String bobotx  = jObj.getString(TAG_BOBOT);

                        DialogForm(idx, kriteriax, tipex, bobotx, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(KriteriaActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(KriteriaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_kriteria", idx);

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

                        Toast.makeText(KriteriaActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(KriteriaActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(KriteriaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_kriteria", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}