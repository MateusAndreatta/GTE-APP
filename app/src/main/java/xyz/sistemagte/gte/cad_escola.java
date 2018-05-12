package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;

public class cad_escola extends AppCompatActivity {

    EditText NomeEscola, CEP,Cidade, Rua, Numero, Complemento;
    Spinner Estado;

    int idEmpresa;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    private static String HttpURL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_escola);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastroEscola));

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(cad_escola.this);

        Estado = findViewById(R.id.spinnerEstado);

        NomeEscola = findViewById(R.id.cad_nome);
        CEP = findViewById(R.id.cad_cep);
        Cidade = findViewById(R.id.cad_cidade);
        Rua = findViewById(R.id.cad_rua);
        Numero = findViewById(R.id.cad_num);
        Complemento = findViewById(R.id.cad_complemento);

        MaskEditTextChangedListener mascaraCEP = new MaskEditTextChangedListener("#####-###",CEP);

        CEP.addTextChangedListener(mascaraCEP);

        /*
        CEP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String sendCep = CEP.getText().toString();
                    sendCep = sendCep.replace(".", "");
                    sendCep = sendCep.replace("-", "");
                    String url = "https://viacep.com.br/ws/" + sendCep + "/json/unicode/";
                    StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject objeto = new JSONObject(response);
                                String enderecoO = objeto.getString("logradouro"), cidadeO = objeto.getString("localidade");
                                Rua.setText(enderecoO);
                                Cidade.setText(cidadeO);
                                switch(objeto.getString("uf")){
                                    case "AC"://acre
                                        Estado.setSelection(1); // este numero é o numero do item,
                                        break;
                                    case "AL"://alagoas
                                        Estado.setSelection(2);
                                        break;
                                    case "AP"://amapa
                                        Estado.setSelection(3);
                                        break;
                                    case"AM"://amazonas
                                        Estado.setSelection(4);
                                        break;
                                    case"BA"://Bahia
                                        Estado.setSelection(5);
                                        break;
                                    case "CE"://ceara
                                        Estado.setSelection(6);
                                        break;
                                    case "DF"://distrito federal
                                        Estado.setSelection(7);
                                        break;
                                    case "ES"://espirito santo
                                        Estado.setSelection(8);
                                        break;
                                    case "GO"://goias
                                        Estado.setSelection(9);
                                        break;
                                    case "MA"://maranhão
                                        Estado.setSelection(10);
                                        break;
                                    case "MT"://mato grosso
                                        Estado.setSelection(11);
                                        break;
                                    case "MS"://mato grosso do sul
                                        Estado.setSelection(12);
                                        break;
                                    case "MG"://Minas gerais
                                        Estado.setSelection(13);
                                        break;
                                    case "PA"://pará
                                        Estado.setSelection(14);
                                        break;
                                    case "PB"://paraiba
                                        Estado.setSelection(15);
                                        break;
                                    case "PR"://paraná
                                        Estado.setSelection(16);
                                        break;
                                    case "PE"://pernambuco
                                        Estado.setSelection(17);
                                        break;
                                    case "PI"://piaui
                                        Estado.setSelection(18);
                                        break;
                                    case "RJ"://rio de janeiro
                                        Estado.setSelection(19);
                                        break;
                                    case "RN"://rio grande do norte
                                        Estado.setSelection(20);
                                        break;
                                    case "RS"://rio grande do sul
                                        Estado.setSelection(21);
                                        break;
                                    case "RO"://rondonia
                                        Estado.setSelection(22);
                                        break;
                                    case "RR"://roraima
                                        Estado.setSelection(23);
                                        break;
                                    case "SC"://santa catarina
                                        Estado.setSelection(24);
                                        break;
                                    case "SP"://são paulo
                                        Estado.setSelection(25);
                                        break;
                                    case "SE"://sergipe
                                        Estado.setSelection(26);
                                        break;
                                    case "TO"://tocantins
                                        Estado.setSelection(1);
                                        break;

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(cad_escola.this, (getResources().getString(R.string.cepInvalido)), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue rq = Volley.newRequestQueue(cad_escola.this);
                    rq.add(sr);
                }
            }
        });*/
    }


    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Escolas.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Escolas.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }


    public void cadastrarEscola(View view) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(cad_escola.this, ServerResponse, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(cad_escola.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idEmpresa));
                params.put("NomeEscola", NomeEscola.getText().toString());
                params.put("CEP", CEP.getText().toString());
                params.put("Cidade", Cidade.getText().toString());
                params.put("Rua", Rua.getText().toString());
                params.put("Numero", Numero.getText().toString());

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
