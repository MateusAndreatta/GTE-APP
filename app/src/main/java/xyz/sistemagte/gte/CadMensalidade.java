package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.CriancaConst;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.Construtoras.MensalidadeConst;
import xyz.sistemagte.gte.Construtoras.ResponsavelConstr;

public class CadMensalidade extends AppCompatActivity {

    private int idEmpresa, idResp;

    EditText valor, dtVencimento;
    Spinner respSpinner,criancaSpinner;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "https://sistemagte.xyz/android/cadastros/cadMensalidade.php";
    String JsonUrlResponsaveis = "https://sistemagte.xyz/json/adm/ListarResponsaveis.php";
    String JsonUrlCriancas = "https://sistemagte.xyz/json/responsavel/ListarCrianca.php";

    ArrayAdapter<String> RespListSpinner;
    ArrayAdapter<String> CriancaListSpinner;
    ArrayList<MensalidadeConst> MensalidadeConst;
    ArrayList<ResponsavelConstr> ResponsavelConstrList;
    ArrayList<CriancaConst> CriancaConstrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_mensalidade);
        respSpinner = findViewById(R.id.responsaveis);
        criancaSpinner = findViewById(R.id.criancas);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        valor = findViewById(R.id.cad_valor);
        dtVencimento = findViewById(R.id.cad_dt_vencimento);

        requestQueue = Volley.newRequestQueue(this);
        RespListSpinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        CriancaListSpinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        ResponsavelConstrList = new ArrayList<>();
        CriancaConstrList = new ArrayList<>();
        progressDialog = new ProgressDialog(CadMensalidade.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadMensalidade));

        //aplica mascara
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",dtVencimento);


        dtVencimento.addTextChangedListener(mascaraData);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();
        CarregarResponsaveis();

        respSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ResponsavelConstr resp = ResponsavelConstrList.get(position);
                idResp = resp.getIdResp();
                CarregarCriancasResp();
                //Toast.makeText(CadMensalidade.this, String.valueOf(idResp), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Mensalidades_adm.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Mensalidades_adm.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    public void Cadastrar(View view) {

        progressDialog.setMessage(getResources().getString(R.string.loadingDados));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        System.out.println(ServerResponse);
                        progressDialog.dismiss();

                        Toast.makeText(CadMensalidade.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                        Intent tela = new Intent(CadMensalidade.this, Mensalidades_adm.class);
                        startActivity(tela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(CadMensalidade.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                int spinnerPos = respSpinner.getSelectedItemPosition();
                ResponsavelConstr resp = ResponsavelConstrList.get(spinnerPos);
                int spinnerPos2 = criancaSpinner.getSelectedItemPosition();
                CriancaConst crianca = CriancaConstrList.get(spinnerPos2);

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();


                params.put("valor",valor.getText().toString());
                params.put("dtv", dtVencimento.getText().toString());
                params.put("responsavel", String.valueOf(resp.getIdResp()));
                params.put("crianca", String.valueOf(crianca.getIdCrianca()));


                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void CarregarResponsaveis() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonUrlResponsaveis,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            JSONArray jsonArray = jsonObject.getJSONArray("nome");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String resp = jsonObject1.getString("nome") + " " + jsonObject1.getString("sobrenome");
                                RespListSpinner.add(resp);
                                ResponsavelConstr responsavelConstr = new ResponsavelConstr(jsonObject1.getString("nome"), jsonObject1.getString("sobrenome"), jsonObject1.getString("email"), jsonObject1.getString("cpf"), jsonObject1.getString("rg"), jsonObject1.getString("dt_nasc"), Integer.parseInt(jsonObject1.getString("id_usuario")), Integer.parseInt(jsonObject1.getString("id_empresa")));
                                ResponsavelConstrList.add(responsavelConstr);
                            }
                            respSpinner.setAdapter(RespListSpinner);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(CadMensalidade.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(idEmpresa));
                return params;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void CarregarCriancasResp(){
        CriancaListSpinner.clear();
        CriancaConstrList.clear();
        progressDialog.setMessage(getResources().getString(R.string.loadingDados));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonUrlCriancas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            JSONArray jsonArray = jsonObject.getJSONArray("nome");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String crianca = jsonObject1.getString("nome") + " " + jsonObject1.getString("sobrenome");
                                CriancaListSpinner.add(crianca);
                                CriancaConst criancas = new CriancaConst(jsonObject1.getString("nome"), jsonObject1.getString("sobrenome"),
                                        jsonObject1.getString("cpf"), jsonObject1.getString(("id_crianca")));
                                CriancaConstrList.add(criancas);
                            }
                            criancaSpinner.setAdapter(CriancaListSpinner);

                            if(CriancaListSpinner.isEmpty()){
                                Toast.makeText(CadMensalidade.this, respSpinner.getSelectedItem().toString() + " " +  getString(R.string.nenhumaCriancaCadastrada), Toast.LENGTH_LONG).show();
                            }

                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(CadMensalidade.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(idResp));
                return params;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}
