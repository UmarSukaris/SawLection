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

public class KriteriaSeleksi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

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

    private static final String TAG = KriteriaSeleksi.class.getSimpleName();

    private static String url_select     = Server.URL + "kriteria/kriteria-select.php";

    public static final String TAG_ID       = "id_kriteria";
    public static final String TAG_KRITERIA  = "nama_kriteria";
    public static final String TAG_TIPE   = "tipe_kriteria";
    public static final String TAG_BOBOT   = "bobot_kriteria";

    String tag_json_obj = "json_obj_req";
    public final static String TAG_ID_SELEKSI = "id_seleksi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kriteria_seleksi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle extra = getIntent().getExtras();
        id_seleksi = extra.getString(TAG_ID_SELEKSI);

        // menghubungkan variablel pada layout dan pada java
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterKriteria(KriteriaSeleksi.this, itemList);
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

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // untuk menampilkan dialog from biodata
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


}