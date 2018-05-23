package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.AcessosConst;
import xyz.sistemagte.gte.ListAdapters.ListViewAcessos;

public class Empresa extends AppCompatActivity {

    private static String UPDATE_URL = "https://sistemagte.xyz/android/EpdateEmpresa.php";
    private static String JSON_URL = "https://sistemagte.xyz/json/empresa.php";

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    private int idEmpresa;
    private int perfil;
    /**
     * 1 - Resp
     * 2 - Monitora
     * 3 - Motorista
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();
        //Pegando o usuario que clica na tela
        Intent i = getIntent();
        perfil = Integer.parseInt(i.getStringExtra("user"));

        LinearLayout TelaComEmpresa =  findViewById(R.id.empresa);
        LinearLayout TelaSemEmpresa =  findViewById(R.id.cad_empresa);

        if(idEmpresa == 0){
            //Não tem empresa
            TelaSemEmpresa.setVisibility(View.VISIBLE);
            EnviarEmpresa();
        }else{
            //Tem empresa
            TelaComEmpresa.setVisibility(View.VISIBLE);
            PuxarDadosEmpresa();
        }
    }


    private void PuxarDadosEmpresa(){
        progressDialog.setMessage(getResources().getString(R.string.loadingDados));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Empresa.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(idEmpresa));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void EnviarEmpresa(){
        progressDialog.setMessage(getResources().getString(R.string.carregando));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Empresa.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(idEmpresa));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
