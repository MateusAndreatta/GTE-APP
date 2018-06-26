package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;

public class EditarEndereco extends AppCompatActivity {
    EditText cep,cidade,rua,num,complemento;
    Spinner Estado;

    private static String JSON_URL = "https://sistemagte.xyz/json/motorista/ListarMotorista.php";
    ListView listView;
    private int idUsuario;
    AlertDialog alerta;
    private String perfil;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_endereco);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        //Pegando o usuario que clica na tela
        Intent i = getIntent();
        perfil = i.getStringExtra("tipo");

        cep = findViewById(R.id.editar_cep);
        rua = findViewById(R.id.editar_rua);
        cidade = findViewById(R.id.editar_cidade);
        num = findViewById(R.id.editar_num);
        complemento = findViewById(R.id.editar_complemento);
        Estado = findViewById(R.id.estado);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.editar_endereco));

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(EditarEndereco.this);

        PuxarDados();

        MaskEditTextChangedListener mascaraCEP = new MaskEditTextChangedListener("#####-###",cep);

        cep.addTextChangedListener(mascaraCEP);

        cep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String sendCep = cep.getText().toString();
                    sendCep = sendCep.replace(".", "");
                    sendCep = sendCep.replace("-", "");
                    String url = "https://viacep.com.br/ws/" + sendCep + "/json/unicode/";
                    StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject objeto = new JSONObject(response);
                                String enderecoO = objeto.getString("logradouro"), cidadeO = objeto.getString("localidade");
                                rua.setText(enderecoO);
                                cidade.setText(cidadeO);
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
                                        Estado.setSelection(27);
                                        break;

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditarEndereco.this, (getResources().getString(R.string.cepInvalido)), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue rq = Volley.newRequestQueue(EditarEndereco.this);
                    rq.add(sr);
                }
            }
        });
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

            default:
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
        }
        return;
    }
    private void PuxarDados(){
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            JSONObject funcObject = funcArray.getJSONObject(0);



                            cep.setText(funcObject.getString ("cep"));
                            cidade.setText(funcObject.getString ("cidade"));
                            rua.setText(funcObject.getString ("rua"));
                            num.setText(funcObject.getString ("num"));
                            complemento.setText(funcObject.getString ("complemento"));


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
                        Toast.makeText(EditarEndereco.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
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

}




