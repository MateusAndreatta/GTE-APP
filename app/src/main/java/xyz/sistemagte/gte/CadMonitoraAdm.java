package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
import xyz.sistemagte.gte.Auxiliares.GlobalUser;

public class CadMonitoraAdm extends AppCompatActivity {

    EditText cep, cidade, rua, numero, complemetno, data_admissao, hora_entrada, hora_saida,salario,tel_residencial;
    Spinner sexo, Estado;
    String NomeHolder, SobrenomeHolder,EmailHolder,SenhaHolder,TelefoneHolder,RgHolder,CpfHolder,DtNascHolder,EstadoHolder;
    String HttpUrl = "https://sistemagte.xyz/android/cadastros/cadAdmMonitora.php\n";
    int idEmpresa;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_monitora_adm);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        cep = findViewById(R.id.cad_cep);
        cidade = findViewById(R.id.cad_cidade);
        rua = findViewById(R.id.cad_rua);
        numero = findViewById(R.id.cad_num);
        complemetno = findViewById(R.id.cad_complemento);
        data_admissao = findViewById(R.id.data_admissao);
        hora_entrada = findViewById(R.id.hora_entrada);
        hora_saida = findViewById(R.id.hora_saida);
        salario = findViewById(R.id.salario);
        tel_residencial = findViewById(R.id.cad_tel_residencial);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();


        Estado = findViewById(R.id.cad_estado);
        sexo = findViewById(R.id.cad_sexo);

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

        MaskEditTextChangedListener mascaraCEP = new MaskEditTextChangedListener("#####-###", cep);
        MaskEditTextChangedListener mascaraHoraEntrada = new MaskEditTextChangedListener("##:##", hora_entrada);
        MaskEditTextChangedListener mascaraData = new MaskEditTextChangedListener("##/##/####", data_admissao);
        MaskEditTextChangedListener mascaraHoraSaida = new MaskEditTextChangedListener("##:##", hora_saida);
        MaskEditTextChangedListener mascaraTelResidencial = new MaskEditTextChangedListener("(##) ####-####", tel_residencial);
        MaskEditTextChangedListener mascaraSalario = new MaskEditTextChangedListener("#.###,##", salario);

        cep.addTextChangedListener(mascaraCEP);
        hora_entrada.addTextChangedListener(mascaraHoraEntrada);
        data_admissao.addTextChangedListener(mascaraData);
        hora_saida.addTextChangedListener(mascaraHoraSaida);
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
                            Toast.makeText(CadMonitoraAdm.this, (getResources().getString(R.string.cepInvalido)), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue rq = Volley.newRequestQueue(CadMonitoraAdm.this);
                    rq.add(sr);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_monitora));     //Titulo para ser exibido na sua Action Bar em frente à seta
    }

    private boolean ValidarCampos(){
        if(cep.getText().length() == 0 || hora_saida.getText().length() == 0 || cidade.getText().length() == 0 || rua.getText().length() == 0
                || numero.getText().length() == 0 || data_admissao.getText().length() == 0 || hora_entrada.getText().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
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

    public void CadastrarMonitora(View view) {
        PegarEstado();
        if(ValidarCampos()){
            progressDialog.setMessage(getResources().getString(R.string.loadingDados));
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            System.out.println(ServerResponse);
                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            if(ServerResponse.trim().equals("EmailCadastrado")){
                                Toast.makeText(CadMonitoraAdm.this, R.string.emailCadastrado, Toast.LENGTH_SHORT).show();
                            }else{
                                // Showing response message coming from server.
                                Toast.makeText(CadMonitoraAdm.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                                Intent tela = new Intent(CadMonitoraAdm.this, Login.class);
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
                            Toast.makeText(CadMonitoraAdm.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
                    params.put("horaE", hora_entrada.getText().toString());
                    params.put("horaS", hora_saida.getText().toString());
                    params.put("dataA", data_admissao.getText().toString());
                    params.put("sexo", sexo.getSelectedItem().toString().toLowerCase());
                    params.put("estado",EstadoHolder);
                    params.put("telR", tel_residencial.getText().toString());
                    params.put("salario", salario.getText().toString());
                    params.put("idE", String.valueOf(idEmpresa));

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
}
