package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;

public class Graficos extends AppCompatActivity {

    private int idEmpresa,idUsuario;
    Spinner EscolaSpinner;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    int idEscolaHolder;

    String HttpUrlSpinner = "https://sistemagte.xyz/json/ListarEscolasIdEmpresa.php";
    ArrayAdapter<String> EscolasListSpinner;
    ArrayList<EscolasConstr> EscolasListConst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);
        EscolaSpinner = findViewById(R.id.escolas);

        requestQueue = Volley.newRequestQueue(this);
        EscolasListSpinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        EscolasListConst = new ArrayList<>();
        progressDialog = new ProgressDialog(Graficos.this);
        //botão
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.grafico));

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        idEmpresa = global.getGlobalUserIdEmpresa();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlSpinner,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //Toast.makeText(cad_crianca.this, ServerResponse, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            JSONArray jsonArray = jsonObject.getJSONArray("nome");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String escola = jsonObject1.getString("nome");
                                EscolasListSpinner.add(escola);
                                EscolasConstr escolasConstr = new EscolasConstr(jsonObject1.getString("nome"), jsonObject1.getString("cep"), jsonObject1.getString("rua"), jsonObject1.getString("numero"), jsonObject1.getString("complemento"), jsonObject1.getString("estado"), jsonObject1.getString("cidade"), jsonObject1.getInt("idEscola"), jsonObject1.getInt("idEnderecoEscola"));
                                EscolasListConst.add(escolasConstr);
                            }
                            EscolaSpinner.setAdapter(EscolasListSpinner);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(Graficos.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idEmpresa));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

}
