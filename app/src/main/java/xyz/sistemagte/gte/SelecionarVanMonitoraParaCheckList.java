package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.VansConstr;

public class SelecionarVanMonitoraParaCheckList extends AppCompatActivity {

    private static String JSON_URL = "https://sistemagte.xyz/json/monitora/ListarDadosvan.php";
    private int idUsuario,idVan;

    TextView txt;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    List<VansConstr> vansList;
    List<String> WordList;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_van_monitora_para_check_list);

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();

        txt = findViewById(R.id.txtInfoVan);
        spinner = findViewById(R.id.spinnerVans);
        vansList = new ArrayList<>();
        WordList = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.Checklist));



        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(SelecionarVanMonitoraParaCheckList.this);

        PuxarDadosSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                idVan = vansList.get(position).getIdVans();
               // Toast.makeText(SelecionarVanMonitoraParaCheckList.this, String.valueOf(idVan), Toast.LENGTH_SHORT).show();
                txt.setText(
                        getResources().getString(R.string.modeloVan )+ " " + vansList.get(position).getModeloVans() + "\n" +
                                getResources().getString(R.string.marcaVan )+ " " + vansList.get(position).getMarcaVans() + "\n" +
                                getResources().getString(R.string.anoVan )+ " " + vansList.get(position).getAnoVans() + "\n" +
                                getResources().getString(R.string.capacidadeVan )+" " + vansList.get(position).getCapacidadeVans()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Painel_monitora.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_monitora.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void PuxarDadosSpinner(){

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
                                WordList.add(jsonObject.getString("placa"));
                            }
                            spinner.setAdapter(new ArrayAdapter<String>(SelecionarVanMonitoraParaCheckList.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    WordList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(SelecionarVanMonitoraParaCheckList.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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

    public void Iniciar(View view) {
        Intent tela = new Intent(this,CheckListMonitora.class);
        tela.putExtra("id",String.valueOf(idVan));
        startActivity(tela);
    }
}
