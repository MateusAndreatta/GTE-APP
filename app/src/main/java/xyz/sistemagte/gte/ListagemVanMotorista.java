package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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
import xyz.sistemagte.gte.ListAdapters.ListViewVans;
import xyz.sistemagte.gte.ListAdapters.ListViewVansCard;
import xyz.sistemagte.gte.ListAdapters.RecyclerViewAdapter;

public class ListagemVanMotorista extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static String JSON_URL = "https://sistemagte.xyz/json/motorista/ListarDadosVan.php";
    ListView listView;
    private int idUsuario;
    AlertDialog alerta;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    List<VansConstr> listaQuery;
    List<VansConstr> vansList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_van_motorista);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.barra_pesquisa);
        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();

        vansList = new ArrayList<>();
        listaQuery = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.suas_vans));

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(ListagemVanMotorista.this);

        PuxarDados();
        listView.setTextFilterEnabled(true);
        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);// definir se seria usado o icone ou o campo inteiro
        searchView.setOnQueryTextListener(this);//passagem do contexto para usar o searchview
        searchView.setSubmitButtonEnabled(false);//Defini se terá ou nao um o botao de submit
        searchView.setQueryHint(getString(R.string.pesquisarSearchPlaceholder));//Placeholder da searchbar
    }

    private void PuxarDados(){
        vansList.clear();

        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject jsonObject = funcArray.getJSONObject(i);
                                VansConstr vansConstr  = new VansConstr(jsonObject.getString("modelo"), jsonObject.getString("marca"), jsonObject.getString("placa"),
                                        Integer.parseInt(jsonObject.getString("ano_fabri")),Integer.parseInt(jsonObject.getString("capacidade")),jsonObject.getString("nome"),Integer.parseInt(jsonObject.getString("id_van")));

                                vansList.add(vansConstr);
                                listaQuery.add(vansConstr);
                            }

                            ListViewVansCard adapter = new ListViewVansCard(vansList, getApplicationContext());

                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        progressDialog.dismiss();

                        Toast.makeText(ListagemVanMotorista.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

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

    @Override
    public boolean onQueryTextChange(String newText){
        listaQuery.clear();
        if (TextUtils.isEmpty(newText)) {
            listaQuery.addAll(vansList);
        } else {
            String queryText = newText.toLowerCase();
            for(VansConstr obj : vansList){
                if(obj.getPlacaVans().toLowerCase().contains(queryText) ||
                        obj.getMotoristaVans().toLowerCase().contains(queryText) ||
                        obj.getMarcaVans().toLowerCase().contains(queryText) ||
                        obj.getModeloVans().toLowerCase().contains(queryText)){
                    listaQuery.add(obj);
                }
            }
        }
        listView.setAdapter(new ListViewVansCard(listaQuery, ListagemVanMotorista.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }

}
