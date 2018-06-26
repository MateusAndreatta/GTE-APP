package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.Validacoes;


public class cad_motorista extends AppCompatActivity{

    EditText cep,cidade,rua,numero,complemento,cnh,validaCnh,cad_data_hablitacao,salario,tel_residencial;
    Spinner sexo,categoria,Estado;
    String NomeHolder, SobrenomeHolder,EmailHolder,SenhaHolder,TelefoneHolder,RgHolder,CpfHolder,DtNascHolder,EstadoHolder,catHolder;
    String HttpUrl = "https://sistemagte.xyz/android/cadastros/cadGenMotorista.php";

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_motorista);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        cep = findViewById(R.id.cad_cep);
        cidade = findViewById(R.id.cad_cidade);
        rua = findViewById(R.id.cad_rua);
        numero = findViewById(R.id.cad_num);
        complemento = findViewById(R.id.cad_complemento);
        cnh = findViewById(R.id.cad_cnh);
        validaCnh = findViewById(R.id.cad_ValidCnh);
        cad_data_hablitacao = findViewById(R.id.cad_data_hablitacao);
        salario = findViewById(R.id.salario);
        tel_residencial = findViewById(R.id.cad_tel_residencial);

        categoria = findViewById(R.id.cad_categoria);
        sexo = findViewById(R.id.cad_sexo);
        Estado = findViewById(R.id.cad_estado);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);

        Intent i = getIntent();
        NomeHolder = i.getStringExtra("nome");
        SobrenomeHolder = i.getStringExtra("sobrenome");
        EmailHolder = i.getStringExtra("email");
        SenhaHolder = i.getStringExtra("senha");
        TelefoneHolder = i.getStringExtra("telefone");
        RgHolder = i.getStringExtra("rg");
        CpfHolder = i.getStringExtra("cpf");
        DtNascHolder = i.getStringExtra("nascimento");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_motorista));

        MaskEditTextChangedListener mascaraCPE = new MaskEditTextChangedListener("#####-###",cep);
        MaskEditTextChangedListener mascaraCNH = new MaskEditTextChangedListener("###########",cnh);
        MaskEditTextChangedListener mascaraValida  = new MaskEditTextChangedListener("##/##/####",validaCnh);
        MaskEditTextChangedListener mascaraDtHabi  = new MaskEditTextChangedListener("##/##/####",cad_data_hablitacao);
        MaskEditTextChangedListener mascaraTelResidencial = new MaskEditTextChangedListener("(##) ####-####", tel_residencial);
        MaskEditTextChangedListener mascaraSalario = new MaskEditTextChangedListener("#.###,##", salario);

        cep.addTextChangedListener(mascaraCPE);
        cnh.addTextChangedListener(mascaraCNH);
        validaCnh.addTextChangedListener(mascaraValida);
        cad_data_hablitacao.addTextChangedListener(mascaraDtHabi);
        tel_residencial.addTextChangedListener(mascaraTelResidencial);
        salario.addTextChangedListener(mascaraSalario);


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
                                        Estado.setSelection(6); // este numero é o numero do item,
                                        break;
                                    case "DF"://distrito federal
                                        Estado.setSelection(7); // este numero é o numero do item,
                                        break;
                                    case "ES"://espirito santo
                                        Estado.setSelection(8); // este numero é o numero do item,
                                        break;
                                    case "GO"://goias
                                        Estado.setSelection(9); // este numero é o numero do item,
                                        break;
                                    case "MA"://maranhão
                                        Estado.setSelection(10); // este numero é o numero do item,
                                        break;
                                    case "MT"://mato grosso
                                        Estado.setSelection(11); // este numero é o numero do item,
                                        break;
                                    case "MS"://mato grosso do sul
                                        Estado.setSelection(12); // este numero é o numero do item,
                                        break;
                                    case "MG"://Minas gerais
                                        Estado.setSelection(13); // este numero é o numero do item,
                                        break;
                                    case "PA"://pará
                                        Estado.setSelection(14); // este numero é o numero do item,
                                        break;
                                    case "PB"://paraiba
                                        Estado.setSelection(15); // este numero é o numero do item,
                                        break;
                                    case "PR"://paraná
                                        Estado.setSelection(16); // este numero é o numero do item,
                                        break;
                                    case "PE"://pernambuco
                                        Estado.setSelection(17); // este numero é o numero do item,
                                        break;
                                    case "PI"://piaui
                                        Estado.setSelection(18); // este numero é o numero do item,
                                        break;
                                    case "RJ"://rio de janeiro
                                        Estado.setSelection(19); // este numero é o numero do item,
                                        break;
                                    case "RN"://rio grande do norte
                                        Estado.setSelection(20); // este numero é o numero do item,
                                        break;
                                    case "RS"://rio grande do sul
                                        Estado.setSelection(21); // este numero é o numero do item,
                                        break;
                                    case "RO"://rondonia
                                        Estado.setSelection(22); // este numero é o numero do item,
                                        break;
                                    case "RR"://roraima
                                        Estado.setSelection(23); // este numero é o numero do item,
                                        break;
                                    case "SC"://santa catarina
                                        Estado.setSelection(24); // este numero é o numero do item,
                                        break;
                                    case "SP"://são paulo
                                        Estado.setSelection(25); // este numero é o numero do item,
                                        break;
                                    case "SE"://sergipe
                                        Estado.setSelection(26); // este numero é o numero do item,
                                        break;
                                    case "TO"://tocantins
                                        Estado.setSelection(1); // este numero é o numero do item,
                                        break;

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(cad_motorista.this, (getResources().getString(R.string.cepInvalido)), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue rq = Volley.newRequestQueue(cad_motorista.this);
                    rq.add(sr);
                }
            }
        });


    }

    private boolean ValidarCampos(){
        if(cep.getText().length() == 0 || cidade.getText().length() == 0 || rua.getText().length() == 0
                || numero.getText().length() == 0 || cnh.getText().length() == 0 || validaCnh.getText().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public void Cadastrar_motorista(View view) {
        if(ValidarCampos() && PegarCategoria()){
            progressDialog.setMessage(getResources().getString(R.string.loadingDados));
            progressDialog.show();
            PegarEstado();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            System.out.println(ServerResponse);
                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            if(ServerResponse.trim().equals("EmailCadastrado")){
                                Toast.makeText(cad_motorista.this, R.string.emailCadastrado, Toast.LENGTH_SHORT).show();
                            }else{
                                // Showing response message coming from server.
                                Toast.makeText(cad_motorista.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                                Intent tela = new Intent(cad_motorista.this, Login.class);
                                startActivity(tela);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Showing error message if something goes wrong.
                            Toast.makeText(cad_motorista.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("nome", NomeHolder);
                    params.put("sobrenome", SobrenomeHolder);
                    params.put("email", EmailHolder);
                    params.put("senha", SenhaHolder);
                    params.put("telefone", TelefoneHolder);
                    params.put("rg", RgHolder);
                    params.put("cpf", CpfHolder);
                    params.put("nascimento", DtNascHolder);

                    params.put("cep", cep.getText().toString());
                    params.put("cidade", cidade.getText().toString());
                    params.put("rua", rua.getText().toString());
                    params.put("numero", numero.getText().toString());
                    params.put("complemento", complemento.getText().toString());
                    params.put("cnh", cnh.getText().toString());
                    params.put("validadeCnh", validaCnh.getText().toString());
                    params.put("dtHabilitacao", cad_data_hablitacao.getText().toString());
                    params.put("telR", tel_residencial.getText().toString());
                    params.put("salario", salario.getText().toString());
                    params.put("estado", EstadoHolder);
                    params.put("categoria", catHolder);
                    params.put("sexo", sexo.getSelectedItem().toString().toLowerCase());

                    return params;
                }

            };

            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }

    }

    private void PegarEstado(){
        EstadoHolder = Estado.getSelectedItem().toString();

        switch (Estado.getSelectedItem().toString()) {//pega o nome do item ali em cima
            case "Acre":
                EstadoHolder = "AC";
                break;
            case "Alagoas":
                EstadoHolder = "AL";
                break;
            case "Amapá":
                EstadoHolder = "AP";
                break;
            case "Amazonas":
                EstadoHolder = "AM";
                break;
            case "Bahia":
                EstadoHolder = "BA";
                break;
            case "Ceará":
                EstadoHolder = "CE";
                break;
            case "Distrito Federal":
                EstadoHolder = "DF";
                break;
            case "Espírito Santo":
                EstadoHolder = "ES";
                break;
            case "Goiás":
                EstadoHolder = "GO";
                break;
            case "Maranhão":
                EstadoHolder = "MA";
                break;
            case "Mato Grosso":
                EstadoHolder = "MT";
                break;
            case "Mato Grosso do Sul":
                EstadoHolder = "MS";
                break;
            case "Minas Gerais":
                EstadoHolder = "MG";
                break;
            case "Pará":
                EstadoHolder = "PA";
                break;
            case "Paraiba":
                EstadoHolder = "PB";
                break;
            case "Paraná":
                EstadoHolder = "PR";
                break;
            case "Pernambuco":
                EstadoHolder = "PE";
                break;
            case "Piauí":
                EstadoHolder = "PI";
                break;
            case "Rio de Janeiro":
                EstadoHolder = "RJ";
                break;
            case "Rio Grande do Norte":
                EstadoHolder = "RN";
                break;
            case "Rio Grande do Sul":
                EstadoHolder = "RS";
                break;
            case "Rondônia":
                EstadoHolder = "RO";
                break;
            case "Roraima":
                EstadoHolder = "RR";
                break;
            case "Santa Catarina":
                EstadoHolder = "SC";
                break;
            case "São Paulo":
                EstadoHolder = "SP";
                break;
            case "Sergipe":
                EstadoHolder = "SE";
                break;
            case "Tocantinss":
                EstadoHolder = "TO";
                break;
        }
    }

    public boolean PegarCategoria(){
        if(categoria.getSelectedItemPosition() == 0){
            Toast.makeText(this, R.string.verificarCNH, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(categoria.getSelectedItemPosition() == 1){
                catHolder = "cat_d";
                return true;
            }else{
                catHolder = "cat_e";
                return true;
            }
        }
    }

}
