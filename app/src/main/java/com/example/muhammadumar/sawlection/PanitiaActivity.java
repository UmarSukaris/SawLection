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
import android.widget.ArrayAdapter;
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
import com.example.muhammadumar.sawlection.adapter.AdapterPanitia;
import com.example.muhammadumar.sawlection.controller.AppController;
import com.example.muhammadumar.sawlection.data.DataPanitia;
import com.example.muhammadumar.sawlection.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class PanitiaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataPanitia> itemList = new ArrayList<DataPanitia>();
    AdapterPanitia adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id_panitia, txt_id_user;
    String id_panitia, id_user, id_seleksi;
    Spinner spinner_id_user;
    private static final String TAG = PanitiaActivity.class.getSimpleName();
    String[] iduser, nama_user;
    private static String url_select = Server.URL + "panitia/panitia-select.php";
    private static String url_insert = Server.URL + "panitia/panitia-insert.php";
    private static String url_edit = Server.URL + "panitia/panitia-edit.php";
    private static String url_update = Server.URL + "panitia/panitia-update.php";
    private static String url_delete = Server.URL + "panitia/panitia-delete.php";
    private static String url_select_user = Server.URL + "user-select.php";

    public static final String TAG_ID_PANITIA = "id_panitia";
    public static final String TAG_ID_USER = "id_user";
    public static final String TAG_ID_SELEKSI = "id_seleksi";
    public static final String TAG_NAMA= "nama";
    public static final String TAG_NAMA_SELEKSI = "nama_seleksi";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    ArrayAdapter<String> spinnerArrayAdapter;
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panitia);
        Toolbar toolbar_panitia = (Toolbar) findViewById(R.id.toolbar);
        toolbar_panitia.setTitle("Panitia");
        setSupportActionBar(toolbar_panitia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle extra = getIntent().getExtras();
        id_seleksi = extra.getString(TAG_ID_SELEKSI);

        // menghubungkan variablel pada layout dan pada java
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterPanitia(PanitiaActivity.this, itemList);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("","","SIMPAN");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                // TODO Auto-generated method stub
                final String id_panitiax = itemList.get(position).getId_panitia();
                final CharSequence[] dialogitem = {"Delete"};
                dialog = new AlertDialog.Builder(PanitiaActivity.this);
                dialog.setCancelable(true);

                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                delete(id_panitiax);
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

    // untuk menampilkan dialog from pengguna
    private void DialogForm(String id_panitiax, String id_userx, String button) {
        ambil_user(id_userx);
        dialog = new AlertDialog.Builder(PanitiaActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_panitia, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Pilih Panitia");
        txt_id_panitia = (EditText) dialogView.findViewById(R.id.txt_id_panitia);
        spinner_id_user = (Spinner) dialogView.findViewById(R.id.spin_id_user);

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id_panitia = txt_id_panitia.getText().toString();
                id_user = iduser[spinner_id_user.getSelectedItemPosition()] + "";

                simpan();
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void ambil_user(final String id_user) {
        // membuat request JSON
        final List<String> temp_iduser, temp_nama_user;
        temp_iduser = new ArrayList<String>();
        temp_nama_user = new ArrayList<String>();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url_select_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Parsing json
                try {
                    JSONArray values = new JSONArray(response);
                    iduser = new String [values.length()];
                    nama_user = new String[values.length()];
                    for (int i = 0; i < values.length(); i++) {
                        JSONObject obj=values.getJSONObject(i);
                        temp_iduser.add(obj.getString("id_user"));
                        temp_nama_user.add(obj.getString("nama"));

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                temp_iduser.toArray(iduser);
                temp_nama_user.toArray(nama_user);
                // notifikasi adanya perubahan data pada adapter
                spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_panitia, nama_user);
                spinner_id_user.setAdapter(spinnerArrayAdapter);
                if (!id_user.isEmpty()) {
                    spinner_id_user.setSelection(spinnerArrayAdapter.getPosition(id_user));
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

                        DataPanitia item = new DataPanitia();
                        item.setId_panitia(object.getString(TAG_ID_PANITIA));
                        item.setId_user(object.getString(TAG_NAMA));

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
    private void simpan() {
        String url = url_insert;

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
                                Toast.makeText(PanitiaActivity.this,
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(PanitiaActivity.this,
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
                Toast.makeText(PanitiaActivity.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                    params.put("id_user", id_user);
                    params.put("id_seleksi", id_seleksi);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk menghapus
    private void delete(final String id_panitiax) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete,
                new Response.Listener<String>() {
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
                                Toast.makeText(PanitiaActivity.this,
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(PanitiaActivity.this,
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
                Toast.makeText(PanitiaActivity.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_panitia", id_panitiax);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


}