package com.aplicacion.pm01122restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        
        obtenerUusuario();
    }

    private void obtenerUusuario()
    {
        RequestQueue cola = Volley.newRequestQueue(this);
        String endpoint = "https://jsonplaceholder.typicode.com/posts";

        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, endpoint, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Mensaje " , response.substring(0,500));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        cola.add(stringRequest);
    }
}