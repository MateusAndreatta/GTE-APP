package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.AcessosConst;
import xyz.sistemagte.gte.Construtoras.VansConstr;
import xyz.sistemagte.gte.ListAdapters.ListViewAcessos;
import xyz.sistemagte.gte.ListAdapters.ListViewVans;

public class Acessos extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private static String JSON_URL = "https://sistemagte.xyz/json/adm/ListarAcessos.php";
    ListView listView;
    private int idEmpresa;
    List<AcessosConst> acessoList;
    List<AcessosConst> listaQuery;
    SearchView searchView;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acessos);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();

        searchView = findViewById(R.id.barra_pesquisa);
        listView = findViewById(R.id.listView);
        acessoList = new ArrayList<>();
        listaQuery = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.listaAcessos));     //Titulo para ser exibido na sua Action Bar em frente à seta

        registerForContextMenu(listView);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(Acessos.this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Filtragem de Acessos não disponivel no momento", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadAcessoList();
        listView.setTextFilterEnabled(true);
        setupSearchView();

    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);// definir se seria usado o icone ou o campo inteiro
        searchView.setOnQueryTextListener(this);//passagem do contexto para usar o searchview
        searchView.setSubmitButtonEnabled(false);//Defini se terá ou nao um o botao de submit
        searchView.setQueryHint(getString(R.string.pesquisarSearchPlaceholder));//Placeholder da searchbar
    }

    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Painel_adm.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_adm.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void loadAcessoList() {


        // Showing progress dialog at user registration time.
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);

                                String dia2= funcObject.getString("data");
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                ParsePosition position2 = new ParsePosition(0);
                                Date data2 = format2.parse(dia2,position2);
                                format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                String date2 = format2.format(data2);

                                AcessosConst acessosConst = new AcessosConst(funcObject.getString("nome"), funcObject.getString("sobrenome"),
                                        funcObject.getString("tipo"), date2);



                                acessoList.add(acessosConst);
                                listaQuery.add(acessosConst);
                            }

                            ListViewAcessos adapter = new ListViewAcessos(acessoList, getApplicationContext());

                            listView.setAdapter(adapter);
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
                        Toast.makeText(Acessos.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idEmpresa));

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
            listaQuery.addAll(acessoList);
        } else {
            String queryText = newText.toLowerCase();
            for(AcessosConst obj : acessoList){
                if(obj.getNomeAcessos().toLowerCase().contains(queryText) ||
                        obj.getSobrenomeAcessos().toLowerCase().contains(queryText) ||
                        obj.getTipoAcessos().toLowerCase().contains(queryText) ||
                        obj.getDataAcessos().toLowerCase().contains(queryText)){
                    listaQuery.add(obj);
                }
            }
        }
        listView.setAdapter(new ListViewAcessos(listaQuery, Acessos.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }
}
