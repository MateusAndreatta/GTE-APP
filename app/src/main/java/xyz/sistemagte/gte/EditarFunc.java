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
import android.widget.LinearLayout;
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

public class EditarFunc extends AppCompatActivity {
    //generico
    EditText Nome,Sobrenome,Telefone,Email,DataNasc,Cpf,Rg;
    //monitora
    EditText cep_monitora,cidade_monitora,rua_monitora,num_monitora,complemento_monitora,tel_residencia_monitora,data_admissao_monitora,hora_entrada_monitora,hora_saida_monitora,salario_monitora;
    Spinner estado_monitora,sexo_monitora;
    //motorista
    EditText cep_motorista,cidade_motorista,rua_motorista,num_motorista,complemento_motorista,cnh_motorista,validade_cnh_motorista,data_hablitacao_motorista,tel_residencial_motorista,salario_motorista;
    Spinner estado_motorista,sexo_motorista,categoria_motorista;
    String EstadoHolder;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String perfil,catHolder;

    private static String URL_MONITORA = "https://sistemagte.xyz/android/editar/editarMonitora";
    private static String URL_MOTORISTA = "https://sistemagte.xyz/android/editar/editarMotorista";
    private int idUsuario,idEmpresa;
    AlertDialog alerta;
    boolean vMotorista = false, vMonitora = false;

    LinearLayout telaGen,telaMotorista,telaMonitora;
    private static String JSON_URL = "https://sistemagte.xyz/json/listagem.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_func);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();

        Intent i = getIntent();
        perfil = i.getStringExtra("id");
        System.out.println("ENVIANDO: " +perfil);
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(EditarFunc.this);

        telaGen = findViewById(R.id.telaGenerico);
        telaMonitora = findViewById(R.id.telaMonitora);
        telaMotorista = findViewById(R.id.telaMotorista);

        //Campos Genericos
        Nome             = findViewById(R.id.cad_nome);
        Sobrenome        = findViewById(R.id.cad_sobrenome);
        Telefone         = findViewById(R.id.cad_tel);
        Email            = findViewById(R.id.cad_email);
        DataNasc         = findViewById(R.id.cad_datanascimento);
        Cpf              = findViewById(R.id.cad_cpf);
        Rg               = findViewById(R.id.cad_rg);

        //monitora
        cep_monitora            = findViewById(R.id.cad_cep_monitora);
        cidade_monitora         = findViewById(R.id.cad_cidade_monitora);
        rua_monitora            = findViewById(R.id.cad_rua_monitora);
        num_monitora            = findViewById(R.id.cad_num_monitora);
        complemento_monitora    = findViewById(R.id.cad_complemento_monitora);
        tel_residencia_monitora = findViewById(R.id.cad_tel_residencia_monitora);
        data_admissao_monitora  = findViewById(R.id.data_admissao_monitora);
        hora_entrada_monitora   = findViewById(R.id.hora_entrada_monitora);
        hora_saida_monitora     = findViewById(R.id.hora_saida_monitora);
        salario_monitora        = findViewById(R.id.salario_monitora);
        estado_monitora         = findViewById(R.id.cad_estado_monitora);
        sexo_monitora           = findViewById(R.id.cad_sexo_monitora);

        //motorista
        cep_motorista                 = findViewById(R.id.cad_cep_motorista);
        cidade_motorista              = findViewById(R.id.cad_cidade_motorista);
        rua_motorista                 = findViewById(R.id.cad_rua_motorista);
        num_motorista                 = findViewById(R.id.cad_num_motorista);
        complemento_motorista         = findViewById(R.id.cad_complemento_motorista);
        cnh_motorista                 = findViewById(R.id.cad_cnh_motorista);
        validade_cnh_motorista        = findViewById(R.id.cad_ValidCnh_motorista);
        data_hablitacao_motorista     = findViewById(R.id.cad_data_hablitacao_motorista);
        tel_residencial_motorista     = findViewById(R.id.cad_tel_residencial_motorista);
        salario_motorista             = findViewById(R.id.salario_motorista);
        estado_motorista              = findViewById(R.id.cad_estado_motorista);
        sexo_motorista                = findViewById(R.id.cad_sexo_motorista);
        categoria_motorista           = findViewById(R.id.cad_categoria_motorista);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.editarFunc));     //Titulo para ser exibido na sua Action Bar em frente à seta


        MaskEditTextChangedListener mascaraCPF = new MaskEditTextChangedListener("###.###.###-##",Cpf);
        MaskEditTextChangedListener mascaraCelular = new MaskEditTextChangedListener("(##) #####-####",Telefone);
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",DataNasc);
        MaskEditTextChangedListener mascaraRG  = new MaskEditTextChangedListener("#.###.###-#",Rg);
        Cpf.addTextChangedListener(mascaraCPF);
        Telefone.addTextChangedListener(mascaraCelular);
        DataNasc.addTextChangedListener(mascaraData);
        Rg.addTextChangedListener(mascaraRG);

        //motorista
        MaskEditTextChangedListener mascaraCPE = new MaskEditTextChangedListener("#####-###",cep_motorista);
        MaskEditTextChangedListener mascaraCNH = new MaskEditTextChangedListener("###########",cnh_motorista);
        MaskEditTextChangedListener mascaraValida  = new MaskEditTextChangedListener("##/##/####",validade_cnh_motorista);
        MaskEditTextChangedListener mascaraDtHabi  = new MaskEditTextChangedListener("##/##/####",data_hablitacao_motorista);
        MaskEditTextChangedListener mascaraTelResidencial = new MaskEditTextChangedListener("(##) ####-####", tel_residencial_motorista);
        MaskEditTextChangedListener mascaraSalario = new MaskEditTextChangedListener("#.###,##", salario_motorista);

        cep_motorista.addTextChangedListener(mascaraCPE);
        cnh_motorista.addTextChangedListener(mascaraCNH);
        validade_cnh_motorista.addTextChangedListener(mascaraValida);
        data_hablitacao_motorista.addTextChangedListener(mascaraDtHabi);
        tel_residencial_motorista.addTextChangedListener(mascaraTelResidencial);
        salario_motorista.addTextChangedListener(mascaraSalario);

        //monitora

        MaskEditTextChangedListener mascaraCEP = new MaskEditTextChangedListener("#####-###", cep_monitora);
        MaskEditTextChangedListener mascaraHoraEntrada = new MaskEditTextChangedListener("##:##", hora_entrada_monitora);
        MaskEditTextChangedListener mascaraData2 = new MaskEditTextChangedListener("##/##/####", data_admissao_monitora);
        MaskEditTextChangedListener mascaraHoraSaida = new MaskEditTextChangedListener("##:##", hora_saida_monitora);
        MaskEditTextChangedListener mascaraTelResidencial2 = new MaskEditTextChangedListener("(##) ####-####", tel_residencia_monitora);
        MaskEditTextChangedListener mascaraSalario2 = new MaskEditTextChangedListener("#.###,##", salario_monitora);

        cep_monitora.addTextChangedListener(mascaraCEP);
        hora_entrada_monitora.addTextChangedListener(mascaraHoraEntrada);
        data_admissao_monitora.addTextChangedListener(mascaraData2);
        hora_saida_monitora.addTextChangedListener(mascaraHoraSaida);
        tel_residencia_monitora.addTextChangedListener(mascaraTelResidencial2);
        salario_monitora.addTextChangedListener(mascaraSalario2);


        //monitora
        cep_monitora.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String sendCep = cep_monitora.getText().toString();
                    sendCep = sendCep.replace(".", "");
                    sendCep = sendCep.replace("-", "");
                    String url = "https://viacep.com.br/ws/" + sendCep + "/json/unicode/";
                    StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject objeto = new JSONObject(response);
                                String enderecoO = objeto.getString("logradouro"), cidadeO = objeto.getString("localidade");
                                rua_monitora.setText(enderecoO);
                                cidade_monitora.setText(cidadeO);
                                switch(objeto.getString("uf")){
                                    case "AC"://acre
                                        estado_monitora.setSelection(1); // este numero é o numero do item,
                                        break;
                                    case "AL"://alagoas
                                        estado_monitora.setSelection(2);
                                        break;
                                    case "AP"://amapa
                                        estado_monitora.setSelection(3);
                                        break;
                                    case"AM"://amazonas
                                        estado_monitora.setSelection(4);
                                        break;
                                    case"BA"://Bahia
                                        estado_monitora.setSelection(5);
                                        break;
                                    case "CE"://ceara
                                        estado_monitora.setSelection(6); // este numero é o numero do item,
                                        break;
                                    case "DF"://distrito federal
                                        estado_monitora.setSelection(7); // este numero é o numero do item,
                                        break;
                                    case "ES"://espirito santo
                                        estado_monitora.setSelection(8); // este numero é o numero do item,
                                        break;
                                    case "GO"://goias
                                        estado_monitora.setSelection(9); // este numero é o numero do item,
                                        break;
                                    case "MA"://maranhão
                                        estado_monitora.setSelection(10); // este numero é o numero do item,
                                        break;
                                    case "MT"://mato grosso
                                        estado_monitora.setSelection(11); // este numero é o numero do item,
                                        break;
                                    case "MS"://mato grosso do sul
                                        estado_monitora.setSelection(12); // este numero é o numero do item,
                                        break;
                                    case "MG"://Minas gerais
                                        estado_monitora.setSelection(13); // este numero é o numero do item,
                                        break;
                                    case "PA"://pará
                                        estado_monitora.setSelection(14); // este numero é o numero do item,
                                        break;
                                    case "PB"://paraiba
                                        estado_monitora.setSelection(15); // este numero é o numero do item,
                                        break;
                                    case "PR"://paraná
                                        estado_monitora.setSelection(16); // este numero é o numero do item,
                                        break;
                                    case "PE"://pernambuco
                                        estado_monitora.setSelection(17); // este numero é o numero do item,
                                        break;
                                    case "PI"://piaui
                                        estado_monitora.setSelection(18); // este numero é o numero do item,
                                        break;
                                    case "RJ"://rio de janeiro
                                        estado_monitora.setSelection(19); // este numero é o numero do item,
                                        break;
                                    case "RN"://rio grande do norte
                                        estado_monitora.setSelection(20); // este numero é o numero do item,
                                        break;
                                    case "RS"://rio grande do sul
                                        estado_monitora.setSelection(21); // este numero é o numero do item,
                                        break;
                                    case "RO"://rondonia
                                        estado_monitora.setSelection(22); // este numero é o numero do item,
                                        break;
                                    case "RR"://roraima
                                        estado_monitora.setSelection(23); // este numero é o numero do item,
                                        break;
                                    case "SC"://santa catarina
                                        estado_monitora.setSelection(24); // este numero é o numero do item,
                                        break;
                                    case "SP"://são paulo
                                        estado_monitora.setSelection(25); // este numero é o numero do item,
                                        break;
                                    case "SE"://sergipe
                                        estado_monitora.setSelection(26); // este numero é o numero do item,
                                        break;
                                    case "TO"://tocantins
                                        estado_monitora.setSelection(1); // este numero é o numero do item,
                                        break;

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditarFunc.this, (getResources().getString(R.string.cepInvalido)), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue rq = Volley.newRequestQueue(EditarFunc.this);
                    rq.add(sr);
                }
            }
        });
        //motorista
        cep_motorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String sendCep = cep_motorista.getText().toString();
                    sendCep = sendCep.replace(".", "");
                    sendCep = sendCep.replace("-", "");
                    String url = "https://viacep.com.br/ws/" + sendCep + "/json/unicode/";
                    StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject objeto = new JSONObject(response);
                                String enderecoO = objeto.getString("logradouro"), cidadeO = objeto.getString("localidade");
                                rua_motorista.setText(enderecoO);
                                cidade_motorista.setText(cidadeO);
                                switch(objeto.getString("uf")){
                                    case "AC"://acre
                                        estado_motorista.setSelection(1); // este numero é o numero do item,
                                        break;
                                    case "AL"://alagoas
                                        estado_motorista.setSelection(2);
                                        break;
                                    case "AP"://amapa
                                        estado_motorista.setSelection(3);
                                        break;
                                    case"AM"://amazonas
                                        estado_motorista.setSelection(4);
                                        break;
                                    case"BA"://Bahia
                                        estado_motorista.setSelection(5);
                                        break;
                                    case "CE"://ceara
                                        estado_motorista.setSelection(6); // este numero é o numero do item,
                                        break;
                                    case "DF"://distrito federal
                                        estado_motorista.setSelection(7); // este numero é o numero do item,
                                        break;
                                    case "ES"://espirito santo
                                        estado_motorista.setSelection(8); // este numero é o numero do item,
                                        break;
                                    case "GO"://goias
                                        estado_motorista.setSelection(9); // este numero é o numero do item,
                                        break;
                                    case "MA"://maranhão
                                        estado_motorista.setSelection(10); // este numero é o numero do item,
                                        break;
                                    case "MT"://mato grosso
                                        estado_motorista.setSelection(11); // este numero é o numero do item,
                                        break;
                                    case "MS"://mato grosso do sul
                                        estado_motorista.setSelection(12); // este numero é o numero do item,
                                        break;
                                    case "MG"://Minas gerais
                                        estado_motorista.setSelection(13); // este numero é o numero do item,
                                        break;
                                    case "PA"://pará
                                        estado_motorista.setSelection(14); // este numero é o numero do item,
                                        break;
                                    case "PB"://paraiba
                                        estado_motorista.setSelection(15); // este numero é o numero do item,
                                        break;
                                    case "PR"://paraná
                                        estado_motorista.setSelection(16); // este numero é o numero do item,
                                        break;
                                    case "PE"://pernambuco
                                        estado_motorista.setSelection(17); // este numero é o numero do item,
                                        break;
                                    case "PI"://piaui
                                        estado_motorista.setSelection(18); // este numero é o numero do item,
                                        break;
                                    case "RJ"://rio de janeiro
                                        estado_motorista.setSelection(19); // este numero é o numero do item,
                                        break;
                                    case "RN"://rio grande do norte
                                        estado_motorista.setSelection(20); // este numero é o numero do item,
                                        break;
                                    case "RS"://rio grande do sul
                                        estado_motorista.setSelection(21); // este numero é o numero do item,
                                        break;
                                    case "RO"://rondonia
                                        estado_motorista.setSelection(22); // este numero é o numero do item,
                                        break;
                                    case "RR"://roraima
                                        estado_motorista.setSelection(23); // este numero é o numero do item,
                                        break;
                                    case "SC"://santa catarina
                                        estado_motorista.setSelection(24); // este numero é o numero do item,
                                        break;
                                    case "SP"://são paulo
                                        estado_motorista.setSelection(25); // este numero é o numero do item,
                                        break;
                                    case "SE"://sergipe
                                        estado_motorista.setSelection(26); // este numero é o numero do item,
                                        break;
                                    case "TO"://tocantins
                                        estado_motorista.setSelection(1); // este numero é o numero do item,
                                        break;

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditarFunc.this, (getResources().getString(R.string.cepInvalido)), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue rq = Volley.newRequestQueue(EditarFunc.this);
                    rq.add(sr);
                }
            }
        });
        PuxarDados();
    }

    private void PuxarDados(){
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        System.out.println("RECEBIDO: " + response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("motorista");
                            JSONObject funcObject = funcArray.getJSONObject(0);
                            vMotorista = true;

                            String dia = funcObject.getString("dt_nasc");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position = new ParsePosition(0);
                            Date data = format.parse(dia,position);
                            format = new SimpleDateFormat("dd/MM/yyyy");
                            String date = format.format(data);

                            String dia2 = funcObject.getString("val_cnh");
                            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position2 = new ParsePosition(0);
                            Date data2 = format2.parse(dia2,position2);
                            format2 = new SimpleDateFormat("dd/MM/yyyy");
                            String date2 = format2.format(data2);

                            String dia3 = funcObject.getString("dt_hab");
                            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position3 = new ParsePosition(0);
                            Date data3 = format3.parse(dia3,position3);
                            format3 = new SimpleDateFormat("dd/MM/yyyy");
                            String date3 = format3.format(data3);

                           cep_motorista            .setText(funcObject.getString("cep"));
                           cidade_motorista         .setText(funcObject.getString("cidade"));
                           rua_motorista            .setText(funcObject.getString("rua"));
                           num_motorista            .setText(funcObject.getString("num"));
                           complemento_motorista    .setText(funcObject.getString("complemento"));
                           cnh_motorista            .setText(funcObject.getString("cnh"));
                           tel_residencial_motorista.setText(funcObject.getString("tel_residencial"));
                           salario_motorista        .setText(funcObject.getString("salario"));
                           Nome                     .setText(funcObject.getString("nome"));
                           Sobrenome                .setText(funcObject.getString("sobrenome"));
                           Telefone                 .setText(funcObject.getString("tel_cel"));
                           Email                    .setText(funcObject.getString("email"));
                           Cpf                      .setText(funcObject.getString("cpf"));
                           Rg                       .setText(funcObject.getString("rg"));
                           DataNasc                 .setText(date);
                           data_hablitacao_motorista.setText(date3);
                           validade_cnh_motorista   .setText(date2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("monitora");
                            JSONObject funcObject = funcArray.getJSONObject(0);
                            vMonitora = true;

                            String dia = funcObject.getString("dt_nasc");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position = new ParsePosition(0);
                            Date data = format.parse(dia,position);
                            format = new SimpleDateFormat("dd/MM/yyyy");
                            String date = format.format(data);

                            String dia2 = funcObject.getString("dt_admissao");
                            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position2 = new ParsePosition(0);
                            Date data2 = format2.parse(dia2,position2);
                            format2 = new SimpleDateFormat("dd/MM/yyyy");
                            String date2 = format2.format(data2);



                            cep_monitora           .setText(funcObject.getString("cep"));
                            cidade_monitora        .setText(funcObject.getString("cidade"));
                            rua_monitora           .setText(funcObject.getString("rua"));
                            num_monitora           .setText(funcObject.getString("num"));
                            complemento_monitora   .setText(funcObject.getString("complemento"));
                            tel_residencia_monitora.setText(funcObject.getString("tel_residencial"));
                            hora_entrada_monitora  .setText(funcObject.getString("hora_entrada"));
                            hora_saida_monitora    .setText(funcObject.getString("hora_saida"));
                            salario_monitora       .setText(funcObject.getString("salario"));
                            Nome                     .setText(funcObject.getString("nome"));
                            Sobrenome                .setText(funcObject.getString("sobrenome"));
                            Telefone                 .setText(funcObject.getString("tel_cel"));
                            Email                    .setText(funcObject.getString("email"));
                            Cpf                      .setText(funcObject.getString("cpf"));
                            Rg                       .setText(funcObject.getString("rg"));
                            DataNasc                 .setText(date);
                            data_admissao_monitora.setText(date2);


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
                        Toast.makeText(EditarFunc.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", String.valueOf(perfil));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void TrocarTela(View view) {
        if(vMotorista){
            telaGen.setVisibility(View.GONE);
            telaMotorista.setVisibility(View.VISIBLE);
        }
        if(vMonitora){
            telaGen.setVisibility(View.GONE);
            telaMonitora.setVisibility(View.VISIBLE);
        }
    }

    public void editar_motorista(View view) {
        if(PegarCategoria()){
            PegarEstadoMotorista();
            progressDialog.setMessage(getResources().getString(R.string.loadingDados));
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MOTORISTA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            System.out.println(ServerResponse);
                            progressDialog.dismiss();

                            if(ServerResponse.trim().equals("EmailCadastrado")){
                                Toast.makeText(EditarFunc.this, R.string.emailCadastrado, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(EditarFunc.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                                Intent tela = new Intent(EditarFunc.this, Funcionario_adm.class);
                                startActivity(tela);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            progressDialog.dismiss();
                            Toast.makeText(EditarFunc.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("nome", Nome.getText().toString());
                    params.put("sobrenome", Sobrenome.getText().toString());
                    params.put("email", Email.getText().toString());
                    params.put("telefone", Telefone.getText().toString());
                    params.put("rg", Rg.getText().toString());
                    params.put("cpf", Cpf.getText().toString());
                    params.put("nascimento", DataNasc.getText().toString());

                    params.put("cep", cep_motorista.getText().toString());
                    params.put("cidade", cidade_motorista.getText().toString());
                    params.put("rua", rua_motorista.getText().toString());
                    params.put("numero", num_motorista.getText().toString());
                    params.put("complemento", complemento_motorista.getText().toString());
                    params.put("cng", cnh_motorista.getText().toString());
                    params.put("validadeCNH", validade_cnh_motorista.getText().toString());
                    params.put("dtHab", data_hablitacao_motorista.getText().toString());
                    params.put("categoria", catHolder);
                    params.put("sexo", sexo_motorista.getSelectedItem().toString().toLowerCase());
                    params.put("estado",EstadoHolder);
                    params.put("telR", tel_residencial_motorista.getText().toString());
                    params.put("salario", salario_motorista.getText().toString());
                    params.put("idE", String.valueOf(idEmpresa));

                    return params;
                }

            };

            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }

    }

    public boolean PegarCategoria(){
        if(categoria_motorista.getSelectedItemPosition() == 0){
            Toast.makeText(this, R.string.verificarCNH, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(categoria_motorista.getSelectedItemPosition() == 1){
                catHolder = "cat_d";
                return true;
            }else{
                catHolder = "cat_e";
                return true;
            }
        }
    }

    public void EditarMonitora(View view) {
        PegarEstadoMonitora();
        progressDialog.setMessage(getResources().getString(R.string.loadingDados));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MONITORA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        System.out.println(ServerResponse);
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        if(ServerResponse.trim().equals("EmailCadastrado")){
                            Toast.makeText(EditarFunc.this, R.string.emailCadastrado, Toast.LENGTH_SHORT).show();
                        }else{
                            // Showing response message coming from server.
                            Toast.makeText(EditarFunc.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                            Intent tela = new Intent(EditarFunc.this, Funcionario_adm.class);
                            startActivity(tela);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(EditarFunc.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("nome", Nome.getText().toString());
                params.put("sobrenome", Sobrenome.getText().toString());
                params.put("email", Email.getText().toString());
                params.put("telefone", Telefone.getText().toString());
                params.put("rg", Rg.getText().toString());
                params.put("cpf", Cpf.getText().toString());
                params.put("nascimento", DataNasc.getText().toString());

                params.put("cep", cep_monitora.getText().toString());
                params.put("cidade", cidade_monitora.getText().toString());
                params.put("rua", rua_monitora.getText().toString());
                params.put("numero", num_monitora.getText().toString());
                params.put("complemento", complemento_monitora.getText().toString());
                params.put("horaE", hora_entrada_monitora.getText().toString());
                params.put("horaS", hora_saida_monitora.getText().toString());
                params.put("dataA", data_admissao_monitora.getText().toString());
                params.put("sexo", sexo_monitora.getSelectedItem().toString().toLowerCase());
                params.put("estado",EstadoHolder);
                params.put("telR", tel_residencia_monitora.getText().toString());
                params.put("salario", salario_monitora.getText().toString());
                params.put("idE", String.valueOf(idEmpresa));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void PegarEstadoMonitora(){
        EstadoHolder = estado_monitora.getSelectedItem().toString();

        switch (estado_monitora.getSelectedItem().toString()) {//pega o nome do item ali em cima
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

    private void PegarEstadoMotorista(){
        EstadoHolder = estado_motorista.getSelectedItem().toString();

        switch (estado_motorista.getSelectedItem().toString()) {//pega o nome do item ali em cima
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Funcionario_adm.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Funcionario_adm.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

}
