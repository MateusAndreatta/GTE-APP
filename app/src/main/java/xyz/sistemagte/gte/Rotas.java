package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
import xyz.sistemagte.gte.ListAdapters.ListViewVansCard;

public class Rotas extends AppCompatActivity {

    private static String JSON_URL = "https://sistemagte.xyz/json/motorista/ListarDadosVan.php";
    private static String JSON_CRIANCAS = "https://sistemagte.xyz/json/motorista/ListarCriancaVan.php";
    private static String JSON_ESCOLAS = "https://sistemagte.xyz/json/motorista/ListarEscolasVan.php";
    private static String JSON_MOTORISTA = "https://sistemagte.xyz/json/motorista/EnderecoM.php";
    private int idUsuario,idVan;

    TextView txt;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    List<VansConstr> vansList;
    List<String> WordList;
    Spinner spinner;
    String enderecos = "";
    String EnderecoMotorista = "-25.513906,-49.235105";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotas);
        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();

        txt = findViewById(R.id.txtInfoVan);
        spinner = findViewById(R.id.spinnerVans);
        vansList = new ArrayList<>();
        WordList = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.rotas));



        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(Rotas.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_MOTORISTA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("endereco");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject jsonObject = funcArray.getJSONObject(i);
                                String rua = jsonObject.getString("rua");
                                String cep = jsonObject.getString("cep");
                                String numero = jsonObject.getString("num");
                                System.out.println("RUA MOTORISTA: " + rua.replace(" ","+")+ "+" + numero+"+"+cep+"|");
                                EnderecoMotorista = rua.replace(" ","+")+ "+" + numero+"+"+cep+"|";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Rotas.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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


        PuxarDadosSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                idVan = vansList.get(position).getIdVans();
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
                            spinner.setAdapter(new ArrayAdapter<String>(Rotas.this,
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

                        Toast.makeText(Rotas.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        progressDialog.setMessage(getResources().getString(R.string.iniciandoNavegacao));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_CRIANCAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("crianca");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject jsonObject = funcArray.getJSONObject(i);
                                String rua = jsonObject.getString("rua");
                                String cep = jsonObject.getString("cep");
                                String numero = jsonObject.getString("num");
                                System.out.println("RUA: "+ rua.replace(" ","+")+ "+" + numero+"+"+cep+"|");
                                enderecos += rua.replace(" ","+")+ "+" + numero+"+"+cep+"|";
                            }
                            IniciarGPS(enderecos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        progressDialog.dismiss();

                        Toast.makeText(Rotas.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                System.out.println("Enviando idvan: " + String.valueOf(idVan));
                params.put("id", String.valueOf(idVan));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void IniciarEscola(View view) {
        progressDialog.setMessage(getResources().getString(R.string.iniciandoNavegacao));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_ESCOLAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("escola");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject jsonObject = funcArray.getJSONObject(i);
                                String rua = jsonObject.getString("rua");
                                String cep = jsonObject.getString("cep");
                                String numero = jsonObject.getString("num");
                                System.out.println("RUA: " + rua.replace(" ","+")+ "+" + numero);
                                enderecos += rua.replace(" ","+")+ "+" + numero+"+"+cep+"|";
                            }
                            IniciarGPS(enderecos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        progressDialog.dismiss();

                        Toast.makeText(Rotas.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                System.out.println("Enviando idvan: " + String.valueOf(idVan));
                params.put("id", String.valueOf(idVan));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void IniciarGPS(String endereco){

        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&dir_action=navigate&destination= "+ EnderecoMotorista + "&waypoints=" + endereco +"&travelmode=driving");
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, R.string.instalarMaps, Toast.LENGTH_LONG).show();
            }
        }
    }
}
