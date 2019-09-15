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



public class PerangkinganSeleksi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    ListView list;
    SwipeRefreshLayout swipe;
    List<DataRangking> itemList = new ArrayList<DataRangking>();
    AdapterRangking adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id_rangking, txt_id_pendaftar, txt_id_kriteria, txt_nilai;
    String id_rangking, id_pendaftar, id_kriteria, nilai, id_seleksi;
    Spinner spinner_id_pendaftar, spinner_id_kriteria;


    private static final String TAG = PerangkinganActivity.class.getSimpleName();
    String[] idpendaftar, nama_pendaftar;
    String[] idkriteria, nama_kriteria;
    private static String url_select = Server.URL + "perangkingan/perangkingan-select.php";
    private static String url_insert = Server.URL + "perangkingan/perangkingan-insert.php";
    private static String url_edit = Server.URL + "perangkingan/perangkingan-edit.php";
    private static String url_update = Server.URL + "perangkingan/perangkingan-update.php";
    private static String url_delete = Server.URL + "perangkingan/perangkingan-delete.php";
    private static String url_select_pendaftar = Server.URL + "pendaftar/pendaftar-select.php";
    private static String url_select_kriteria = Server.URL + "kriteria/kriteria-select.php";
    public static final String TAG_ID_RANGKING = "id_rangking";
    public static final String TAG_ID_PENDAFTAR = "id_pendaftar";
    public static final String TAG_ID_KRITERIA = "id_kriteria";
    public static final String TAG_NILAI = "nilai";
    public static final String TAG_NAMA_PENDAFTAR= "nama";
    public static final String TAG_NAMA_KRITERIA = "nama_kriteria";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    ArrayAdapter<String> spinnerArrayAdapter;
    String tag_json_obj = "json_obj_req";
    public final static String TAG_ID_SELEKSI = "id_seleksi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perangkingan_seleksi);
        Toolbar toolbar_perangkingan = (Toolbar) findViewById(R.id.toolbar_perangkingan);
        toolbar_perangkingan.setTitle("Perangkingan");
        setSupportActionBar(toolbar_perangkingan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle extra = getIntent().getExtras();
        id_seleksi = extra.getString(TAG_ID_SELEKSI);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterRangking(PerangkinganSeleksi.this, itemList);
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

    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

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
                        item.setId_pendaftar(object.getString(TAG_NAMA_PENDAFTAR));
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
                return params;
            }
        } ;
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

    }
}