package xyz.sistemagte.gte.Auxiliares;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aluno on 18/06/2018.
 */

public class DefinicoesPresenca {

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String URL_ENTROU = "https://sistemagte.xyz/android/presenca/entrada.php";
    String URL_ESCOLA = "https://sistemagte.xyz/android/presenca/escola.php";
    String URL_RETORNOVAN = "https://sistemagte.xyz/android/presenca/saida.php";
    String URL_CASA = "https://sistemagte.xyz/android/presenca/casa.php";

    public void EntrouNaVan(final String id, final Context ctx){
        requestQueue = Volley.newRequestQueue(ctx);
        progressDialog = new ProgressDialog(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ENTROU,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                       // Toast.makeText(ctx, ServerResponse, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ctx, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void ChegouEscola(final String id, final Context ctx){
        requestQueue = Volley.newRequestQueue(ctx);
        progressDialog = new ProgressDialog(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ESCOLA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                       // Toast.makeText(ctx, ServerResponse, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ctx, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void RetornouVan(final String id, final Context ctx){
        requestQueue = Volley.newRequestQueue(ctx);
        progressDialog = new ProgressDialog(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RETORNOVAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                      //  Toast.makeText(ctx, ServerResponse, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ctx, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void ChegouCasa(final String id, final Context ctx){
        requestQueue = Volley.newRequestQueue(ctx);
        progressDialog = new ProgressDialog(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CASA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                       // Toast.makeText(ctx, ServerResponse, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ctx, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
