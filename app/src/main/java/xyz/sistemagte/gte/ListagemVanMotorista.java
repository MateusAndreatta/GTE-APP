package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.CriancaConst;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.Construtoras.VansConstr;
import xyz.sistemagte.gte.ListAdapters.RecyclerViewAdapter;

public class ListagemVanMotorista extends AppCompatActivity {

    private static String JSON_URL = "https://sistemagte.xyz/json/motorista/ListarDadosVan.php";
    ListView listView;
    private int idUsuario;
    AlertDialog alerta;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    List<VansConstr> vansList;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_van_motorista);

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.ListaVanMotorista));


        vansList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(ListagemVanMotorista.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(vansList);
        mRecyclerView.setAdapter(mAdapter);
        PuxarDados();
    }

    private void PuxarDados(){
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
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
                                JSONObject funcObject = funcArray.getJSONObject(0);
                                //TODO: Colocar na webservice para trazer o nome do motorista, provisioriamente estamos puxando a id_usuario
                                VansConstr vansConstr = new VansConstr(funcObject.getString("modelo"),funcObject.getString("marca"),
                                        funcObject.getString("placa"),Integer.parseInt(funcObject.getString("ano_fabri")),
                                        Integer.parseInt(funcObject.getString("capacidade")),funcObject.getString("id_usuario"),Integer.parseInt(funcObject.getString("id_van")));
                                vansList.add(i,vansConstr);
                                mAdapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(ListagemVanMotorista.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idUsuario));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Painel_motorista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_motorista.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
