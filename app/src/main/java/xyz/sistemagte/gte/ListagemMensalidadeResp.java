package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import xyz.sistemagte.gte.ListAdapters.ListViewMensalidadesResp;

public class ListagemMensalidadeResp extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private static String JSON_URL = "https://sistemagte.xyz/json/responsavel/ListarMensalidades.php";

    ListView listView;
    private int idUsuario;
    private int idmensalidade;
    List<MensalidadeConst> mensalidadeConstList;
    List<MensalidadeConst> listaQuery;
    AlertDialog alerta;
    SearchView searchView;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Boolean VerificModal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_mensalidade_resp);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        searchView = findViewById(R.id.barra_pesquisa);
        listView = findViewById(R.id.listView);
        mensalidadeConstList = new ArrayList<>();
        listaQuery = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.ListarMensalidades));     //Titulo para ser exibido na sua Action Bar em frente à seta

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(ListagemMensalidadeResp.this);

        loadMensalidadesList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                MensalidadeConst mensalidadeConst = listaQuery.get(position);
                idmensalidade = mensalidadeConst.getMensalidadeID();

                //Alert de confirmação do excluir
                AlertDialog.Builder builder = new AlertDialog.Builder(ListagemMensalidadeResp.this);
                builder.setTitle(getResources().getString(R.string.opcoesDialog));
                builder.setMessage(getResources().getString(R.string.textoDialog));
                builder.setPositiveButton(getResources().getString(R.string.GerarBoleto), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        WebView webView = findViewById(R.id.wb);
                        webView.loadUrl("https://sistemagte.xyz/boleto/boleto_itau.php?id="+idmensalidade);
                        MostrarModal();
                    }
                });

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
                if(VerificModal){
                    FecharModal();
                }else {
                    startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                    finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                }
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        if(VerificModal){
            FecharModal();
        }else {
            startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
        }
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
                        System.out.println(response);

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);


                            
                            JSONArray funcArray = obj.getJSONArray("nome");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);

                                MensalidadeConst funcConst = new MensalidadeConst (funcObject.getString("nome"),funcObject.getString("nome_crianca"),funcObject.getString("status"),funcObject.getString("sobrenome"),funcObject.getString("sobre_crianca"),Integer.parseInt(funcObject.getString("id_usuario")), Integer.parseInt(funcObject.getString("id_crianca")), Double.parseDouble(funcObject.getString("valor_emitido")), Integer.parseInt(funcObject.getString("id_mensalidade")));
                                mensalidadeConstList.add(funcConst);
                                listaQuery.add(funcConst);
                            }

                            ListViewMensalidadesResp adapter = new ListViewMensalidadesResp(mensalidadeConstList, getApplicationContext());

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
                        Toast.makeText(ListagemMensalidadeResp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        listView.setAdapter(new ListViewMensalidadesResp(listaQuery, ListagemMensalidadeResp.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }

    private void MostrarModal(){
        LinearLayout telaNormal =  findViewById(R.id.layoutNormalMensalidades);
        telaNormal.setVisibility(View.GONE);
        LinearLayout modal =  findViewById(R.id.layoutWeb);
        modal.setVisibility(View.VISIBLE);
        VerificModal = true;
    }

    private void FecharModal(){
        LinearLayout telaNormal =  findViewById(R.id.layoutNormalMensalidades);
        telaNormal.setVisibility(View.VISIBLE);
        LinearLayout modal =  findViewById(R.id.layoutWeb);
        modal.setVisibility(View.GONE);
        VerificModal = false;
    }


}
