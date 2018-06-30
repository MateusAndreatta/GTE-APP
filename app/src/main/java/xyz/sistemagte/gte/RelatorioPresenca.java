package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView;
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
import java.util.List;
import java.util.Map;
import android.widget.SearchView;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.MensalidadeConst;
import xyz.sistemagte.gte.Construtoras.RelatorioRespConstr;
import xyz.sistemagte.gte.ListAdapters.ListViewMensalidadesResp;
import xyz.sistemagte.gte.ListAdapters.ListViewRelatorio;

public class RelatorioPresenca extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static String JSON_URL = "https://sistemagte.xyz/json/RelatorioResponsavel.php";

    ListView listView;
    private int idUsuario;
    List<RelatorioRespConstr> RelatoriopresencaList;
    List<RelatorioRespConstr> listaQuery;
    AlertDialog alerta;
    SearchView searchView;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_presenca);

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        searchView = findViewById(R.id.barra_pesquisa);
        listView = findViewById(R.id.listView);
        RelatoriopresencaList = new ArrayList<>();
        listaQuery = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.relatorio));     //Titulo para ser exibido na sua Action Bar em frente à seta


        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(RelatorioPresenca.this);

        loadCriancas();
        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);// definir se seria usado o icone ou o campo inteiro
        searchView.setOnQueryTextListener(this);//passagem do contexto para usar o searchview
        searchView.setSubmitButtonEnabled(false);//Defini se terá ou nao um o botao de submit
        searchView.setQueryHint(getString(R.string.pesquisarSearchPlaceholder));//Placeholder da searchbar
    }

    private void loadCriancas() {
        RelatoriopresencaList.clear();
        // Showing progress dialog at user registration time.
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);

                                RelatorioRespConstr funcConst = new RelatorioRespConstr (funcObject.getString("crianca"),funcObject.getString("hora_entrada"),funcObject.getString("hora_escola"),funcObject.getString("hora_saida"),funcObject.getString("hora_casa"));
                                RelatoriopresencaList.add(funcConst);
                                listaQuery.add(funcConst);
                            }

                            ListViewRelatorio adapter = new ListViewRelatorio(RelatoriopresencaList, getApplicationContext());

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
                        Toast.makeText(RelatorioPresenca.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
    public boolean onQueryTextChange(String newText){
        listaQuery.clear();
        if (TextUtils.isEmpty(newText)) {
            listaQuery.addAll(RelatoriopresencaList);
        } else {
            String queryText = newText.toLowerCase();
            for(RelatorioRespConstr obj : RelatoriopresencaList){
                if(obj.getNome_crianca().toLowerCase().contains(queryText)) {
                    listaQuery.add(obj);
                }
            }
        }
        listView.setAdapter(new ListViewRelatorio(listaQuery, RelatorioPresenca.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }
}
