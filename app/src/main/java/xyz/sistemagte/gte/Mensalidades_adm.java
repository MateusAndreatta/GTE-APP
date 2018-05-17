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
import xyz.sistemagte.gte.Construtoras.MensalidadeConst;
import xyz.sistemagte.gte.ListAdapters.ListViewCriancaAdm;
import xyz.sistemagte.gte.ListAdapters.ListViewMensalidades;

public class Mensalidades_adm extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private static String JSON_URL = "https://sistemagte.xyz/json/adm/ListarMensalidades.php";
    private static String URLExcluir = "https://sistemagte.xyz/android/excluir/ExcluirMensalidade.php";
    private static String URLPagar = "https://sistemagte.xyz/android/DefinirMensalidadePaga.php";
    private static String URLNaoPago = "https://sistemagte.xyz/android/DefinirMensalidadePendente.php";
    ListView listView;
    private int idEmpresa;
    private int idmensalidade;
    List<MensalidadeConst> mensalidadeConstList;
    List<MensalidadeConst> listaQuery;
    AlertDialog alerta;
    SearchView searchView;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensalidades__adm);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();
        searchView = findViewById(R.id.barra_pesquisa);
        listView = findViewById(R.id.listView);
        mensalidadeConstList = new ArrayList<>();
        listaQuery = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.ListarMensalidades));     //Titulo para ser exibido na sua Action Bar em frente à seta


        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(Mensalidades_adm.this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
           Intent tela = new Intent(Mensalidades_adm.this,CadMensalidade.class);
           startActivity(tela);
           }
        });

        loadMensalidadesList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                MensalidadeConst mensalidadeConst = listaQuery.get(position);
                idmensalidade = mensalidadeConst.getMensalidadeID();

                //Alert de confirmação do excluir
                AlertDialog.Builder builder = new AlertDialog.Builder(Mensalidades_adm.this);
                builder.setTitle(getResources().getString(R.string.opcoesDialog));
                builder.setMessage(getResources().getString(R.string.textoDialog));
                builder.setPositiveButton(getResources().getString(R.string.editarDialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent tela = new Intent(Mensalidades_adm.this,EditarMensalidade.class);
                        tela.putExtra("idM",String.valueOf(idmensalidade));
                        startActivity(tela);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.excluirDialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        ExcluirMensalidade();
                    }
                });
                if(mensalidadeConst.getStatus().equals("1")){
                    //Pago
                    builder.setNeutralButton(getResources().getString(R.string.PendenteMensalidadeDialog), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            NaoPagarMensalidade();
                        }
                    });
                }else{
                    //nao pago
                    builder.setNeutralButton(getResources().getString(R.string.PagoMensalidadeDialog), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            PagarMensalidade();
                        }
                    });

                }

                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();
            }
        });


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

    private void loadMensalidadesList() {
        mensalidadeConstList.clear();
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
                        System.out.println(response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);

                                MensalidadeConst funcConst = new MensalidadeConst (funcObject.getString("nome"),funcObject.getString("nome_crianca"),funcObject.getString("status"),funcObject.getString("sobrenome"),funcObject.getString("sobre_crianca"),Integer.parseInt(funcObject.getString("id_usuario")), Integer.parseInt(funcObject.getString("id_crianca")), Double.parseDouble(funcObject.getString("valor_emitido")), Integer.parseInt(funcObject.getString("id_mensalidade")));
                                mensalidadeConstList.add(funcConst);
                                listaQuery.add(funcConst);
                            }

                            ListViewMensalidades adapter = new ListViewMensalidades(mensalidadeConstList, getApplicationContext());

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
                        Toast.makeText(Mensalidades_adm.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
            listaQuery.addAll(mensalidadeConstList);
        } else {
            String queryText = newText.toLowerCase();
            for(MensalidadeConst obj : mensalidadeConstList){
                if(obj.getNomeCrianca().toLowerCase().contains(queryText) ||
                        obj.getSobreCrianca().toLowerCase().contains(queryText) ||
                        obj.getNomeResp().toLowerCase().contains(queryText) ||
                        obj.getSobreResp().toLowerCase().contains(queryText) ||
                        obj.getStatus().toLowerCase().contains(queryText) ||
                        obj.getValor().toString().toLowerCase().contains(queryText)){
                    listaQuery.add(obj);
                }
            }
        }
        listView.setAdapter(new ListViewMensalidades(listaQuery, Mensalidades_adm.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }


    private void ExcluirMensalidade(){

        progressDialog.setMessage(getResources().getString(R.string.loadingExcluindo));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLExcluir,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("ENVIANDO ID: " + response);
                        progressDialog.dismiss();
                        Toast.makeText(Mensalidades_adm.this, R.string.excluidoComSucesso, Toast.LENGTH_SHORT).show();
                        loadMensalidadesList();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Mensalidades_adm.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(idmensalidade));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void PagarMensalidade(){
       progressDialog.setMessage(getResources().getString(R.string.loadingExcluindo));
       progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLPagar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Mensalidades_adm.this, R.string.pagoSucesso, Toast.LENGTH_SHORT).show();
                        //loadMensalidadesList();
                        Intent tela = new Intent(Mensalidades_adm.this, Mensalidades_adm.class);
                        startActivity(tela);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Mensalidades_adm.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(idmensalidade));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void NaoPagarMensalidade(){
        progressDialog.setMessage(getResources().getString(R.string.loadingExcluindo));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLNaoPago,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Mensalidades_adm.this, R.string.pagoSucesso, Toast.LENGTH_SHORT).show();
                        //loadMensalidadesList();
                        Intent tela = new Intent(Mensalidades_adm.this, Mensalidades_adm.class);
                        startActivity(tela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Mensalidades_adm.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(idmensalidade));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}
