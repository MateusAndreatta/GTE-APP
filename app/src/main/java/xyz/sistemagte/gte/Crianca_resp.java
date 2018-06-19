package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.CriancaConst;
import xyz.sistemagte.gte.ListAdapters.ListViewCriancaAdm;
import xyz.sistemagte.gte.ListAdapters.ListViewCriancaResp;

public class Crianca_resp extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private static String JSON_URL = "https://sistemagte.xyz/json/responsavel/ListarCrianca.php";
    private static String URL_Excluir = "https://sistemagte.xyz/android/excluir/ExcluirCrianca.php";
    private static String URL_Falta = "https://sistemagte.xyz/android/DefinirFalta.php";
    private static String JSON_FALTAS = "https://sistemagte.xyz/json/responsavel/ListarFalta.php";
    private static String URL_REMOVER_FALTAS = "https://sistemagte.xyz/android/DeletarFalta.php";
    ListView listView;
    private int idUsuario;
    List<CriancaConst> criancaList;
    List<String> faltasList;
    List<CriancaConst> listaQuery;
    AlertDialog alerta;
    private int idCrianca;
    SearchView searchView;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crianca_adm);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.barra_pesquisa);
        criancaList = new ArrayList<>();
        faltasList = new ArrayList<>();
        listaQuery = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.listaCriancas));     //Titulo para ser exibido na sua Action Bar em frente à seta

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(Crianca_resp.this);
        PuxarFaltas();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent tela = new Intent(Crianca_resp.this, cad_crianca.class);
                    startActivity(tela);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Crianca_resp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadCriancaList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                CriancaConst crianca = listaQuery.get(position);
                idCrianca = Integer.parseInt(crianca.getIdCrianca());

                //Alert de confirmação do excluir
                AlertDialog.Builder builder = new AlertDialog.Builder(Crianca_resp.this);
                builder.setTitle(getResources().getString(R.string.opcoesDialog));
                builder.setMessage(getResources().getString(R.string.textoDialog));
                builder.setPositiveButton(getResources().getString(R.string.editarDialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                      Intent tela = new Intent(Crianca_resp.this, EditarCrianca.class);
                      tela.putExtra("idC",String.valueOf(idCrianca));
                      startActivity(tela);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.excluirDialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        ExcluirCrianca(idCrianca);
                    }
                });
                if(faltasList.contains(crianca.getIdCrianca())){
                    builder.setNeutralButton(getResources().getString(R.string.CancelaFaltaHoje), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            RemoverFaltas();
                        }
                    });
                }else{
                    //CancelaFaltaHoje -> String para cancelar falta
                    builder.setNeutralButton(getResources().getString(R.string.FaltaHoje), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            DefinirFalta();
                        }
                    });
                }



                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();

            }
        });
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
                startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_responsavel.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void loadCriancaList() {
        criancaList.clear();

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
                                CriancaConst funcConst = new CriancaConst(funcObject.getString("nome"), funcObject.getString("sobrenome"),
                                        funcObject.getString("cpf"), funcObject.getString(("id_crianca")));

                                criancaList.add(funcConst);
                                listaQuery.add(funcConst);
                            }

                            ListViewCriancaResp adapter = new ListViewCriancaResp(criancaList, getApplicationContext());

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
                        Toast.makeText(Crianca_resp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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

    private void ExcluirCrianca(int id){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage(getResources().getString(R.string.loadingExcluindo));
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Excluir,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        Toast.makeText(Crianca_resp.this, getResources().getString(R.string.excluidoComSucesso), Toast.LENGTH_SHORT).show();
                        loadCriancaList();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Crianca_resp.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idCrianca));

                return params;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void DefinirFalta(){
        progressDialog.setMessage(getResources().getString(R.string.carregando));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Falta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        progressDialog.dismiss();
                        Toast.makeText(Crianca_resp.this, getResources().getString(R.string.FaltaDefinida), Toast.LENGTH_SHORT).show();
                        PuxarFaltas();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Crianca_resp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(idCrianca));
                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void PuxarFaltas(){
        faltasList.clear();
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_FALTAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);
                                faltasList.add(funcObject.getString("id_crianca"));
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
                        Toast.makeText(Crianca_resp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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

    private void RemoverFaltas(){
        progressDialog.setMessage(getResources().getString(R.string.loadingExcluindo));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REMOVER_FALTAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        progressDialog.dismiss();
                        Toast.makeText(Crianca_resp.this, getResources().getString(R.string.FaltaRemovida), Toast.LENGTH_SHORT).show();
                        PuxarFaltas();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Crianca_resp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idCrianca));

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
            listaQuery.addAll(criancaList);
        } else {
            String queryText = newText.toLowerCase();
            for(CriancaConst obj : criancaList){
                if(obj.getNomeCrianca().toLowerCase().contains(queryText) ||
                        obj.getSobrenomeCrianca().toLowerCase().contains(queryText) ||
                        obj.getSobrenomeCrianca().toLowerCase().contains(queryText) ||
                        obj.getCpfCrianca().toLowerCase().contains(queryText)){
                    listaQuery.add(obj);
                }
            }
        }
        listView.setAdapter(new ListViewCriancaResp(listaQuery, Crianca_resp.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }
}
