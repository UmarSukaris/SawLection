package com.example.muhammadumar.sawlection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.muhammadumar.sawlection.controller.AppController;
import com.example.muhammadumar.sawlection.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register, btn_login;
    EditText txt_username, txt_password;
    Intent intent;

    int success, role;
    ConnectivityManager conMgr;

    private String url = Server.URL + "login.php";

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ROLE = "role";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_ID_USER = "id_user";
    public final static String TAG_NIM = "nim";
    public final static String TAG_NAMA = "nama";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, id_user, nim, nama;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id_user = sharedpreferences.getString(TAG_ID_USER, null);
        nim = sharedpreferences.getString(TAG_NIM, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra(TAG_ID_USER, id_user);
            intent.putExtra(TAG_NIM, nim);
            intent.putExtra(TAG_NAMA, nama);
            finish();
            startActivity(intent);
        }


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                // mengecek kolom yang kosong
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(username, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void checkLogin(final String username, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    role = jObj.getInt(TAG_ROLE);

                    // Check for error node in json
                    if (success == 1) {
                        String id_user = jObj.getString(TAG_ID_USER);
                        String nim = jObj.getString(TAG_NIM);
                        String nama = jObj.getString(TAG_NAMA);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID_USER, id_user);
                        editor.putString(TAG_NIM, nim);
                        editor.putString(TAG_NAMA, nama);
                        editor.commit();

                        // Memanggil main activity
                        if (role == 0){
                            Intent intent = new Intent(LoginActivity.this, DashboardAdmin.class);
                            finish();
                            startActivity(intent);
                        }else if (role == 1){
                            Intent intent = new Intent(LoginActivity.this, DashboardPanitia.class);
                            intent.putExtra(TAG_ID_USER, id_user);
                            intent.putExtra(TAG_NIM, nim);
                            intent.putExtra(TAG_NAMA, nama);
                            finish();
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            intent.putExtra(TAG_ID_USER, id_user);
                            intent.putExtra(TAG_NIM, nim);
                            intent.putExtra(TAG_NAMA, nama);
                            finish();
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(),
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}


