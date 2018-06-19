package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RadioButton;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.FaltasConstr;
import xyz.sistemagte.gte.Construtoras.VansConstr;
import xyz.sistemagte.gte.ListAdapters.ListViewFaltas;
import xyz.sistemagte.gte.ListAdapters.ListViewVansCard;

public class Faltas extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private int idUsuario;
    private String perfil;

    private static String JSON_URL = "https://sistemagte.xyz/json/monitora/ListarFaltas.php";

    ListView listView;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    List<FaltasConstr> listaQuery;
    List<FaltasConstr> faltasList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faltas);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        GregorianCalendar calendar = new GregorianCalendar();
        String dia = String.valueOf(calendar.get(GregorianCalendar.DAY_OF_MONTH));
        String mes = String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.faltas) + " ("+dia+"/" + mes+ ")" );     //Titulo para ser exibido na sua Action Bar em frente à seta

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.barra_pesquisa);
        faltasList = new ArrayList<>();
        listaQuery = new ArrayList<>();

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        //Pegando o usuario que clica na tela
        Intent i = getIntent();
        perfil = i.getStringExtra("tipo");

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(Faltas.this);

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

    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                switch (perfil){
                    case "monitora":
                        startActivity(new Intent(this, Painel_monitora.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;

                    case  "motorista":
                        startActivity(new Intent(this, Painel_motorista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;

                    case "resp":
                        startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    default:
                        startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                }

                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        switch (perfil){
            case "monitora":
                startActivity(new Intent(this, Painel_monitora.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case  "motorista":
                startActivity(new Intent(this, Painel_motorista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case "resp":
                startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
        }
        return;
    }

    private void PuxarDados(){
        faltasList.clear();

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
                                FaltasConstr faltas  = new FaltasConstr(jsonObject.getString("nome"), jsonObject.getString("sobrenome"), jsonObject.getString("placa"));

                                faltasList.add(faltas);
                                listaQuery.add(faltas);
                            }

                            ListViewFaltas adapter = new ListViewFaltas(faltasList, getApplicationContext());

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

                        Toast.makeText(Faltas.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
            listaQuery.addAll(faltasList);
        } else {
            String queryText = newText.toLowerCase();
            for(FaltasConstr obj : faltasList){
                if(obj.getNomeFalta().toLowerCase().contains(queryText) ||
                        obj.getPlacaFalta().toLowerCase().contains(queryText)){
                    listaQuery.add(obj);
                }
            }
        }
        listView.setAdapter(new ListViewFaltas(listaQuery, Faltas.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }
}
