package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ServiceWorkerClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;

public class EditarCrianca extends AppCompatActivity {

    private int idEmpresa,idUsuario,idCrianca;

    EditText Nome,Sobrenome, CEP,DataNasc,Cpf,Cidade, Rua, Numero, Complemento;
    Spinner Estado, EscolaSpinner;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String NomeHolder,SobrenomeHolder,DataNascHolder,CpfHolder, CidadeHolder,CEPHolder,NumeroHolder,RuaHolder, ComplementoHolder, EstadoHolder;
    int idEscolaHolder;
    String HttpUrl = "https://sistemagte.xyz/android/editar/editarCriancaResp.php";
    String HttpUrlSpinner = "https://sistemagte.xyz/json/ListarEscolasIdEmpresa.php";
    String JsonURLCrianca = "https://sistemagte.xyz/json/ListarDadosCriancaId.php";

    ArrayAdapter<String> EscolasListSpinner;
    ArrayList<EscolasConstr> EscolasListConst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_crianca);
        EscolaSpinner = findViewById(R.id.escolas);
        Estado = findViewById(R.id.cad_estado);

        Nome = findViewById(R.id.cad_nome);
        Sobrenome = findViewById(R.id.cad_sobrenome);
        CEP = findViewById(R.id.cad_cep);
        DataNasc = findViewById(R.id.cad_datanascimento);
        Cpf = findViewById(R.id.cad_cpf);
        Cidade = findViewById(R.id.cad_cidade);
        Rua = findViewById(R.id.cad_rua);
        Numero = findViewById(R.id.cad_num);
        Complemento = findViewById(R.id.cad_complemento);

        requestQueue = Volley.newRequestQueue(this);
        EscolasListSpinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        EscolasListConst = new ArrayList<>();
        progressDialog = new ProgressDialog(EditarCrianca.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.editarCrianca));

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        idEmpresa = global.getGlobalUserIdEmpresa();
        Intent i = getIntent();
        idCrianca = Integer.parseInt(i.getStringExtra("idC"));
        PuxarDadosCrianca();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlSpinner,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //Toast.makeText(cad_crianca.this, ServerResponse, Toast.LENGTH_SHORT).show();
                        try{
                            JSONObject jsonObject=new JSONObject(ServerResponse);
                            JSONArray jsonArray=jsonObject.getJSONArray("nome");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String escola = jsonObject1.getString("nome");
                                EscolasListSpinner.add(escola);
                                EscolasConstr escolasConstr = new EscolasConstr(jsonObject1.getString("nome"),jsonObject1.getString("cep"),jsonObject1.getString("rua"),jsonObject1.getString("numero"),jsonObject1.getString("complemento"),jsonObject1.getString("estado"),jsonObject1.getString("cidade"),jsonObject1.getInt("idEscola"),jsonObject1.getInt("idEnderecoEscola"));
                                EscolasListConst.add(escolasConstr);
                            }
                            EscolaSpinner.setAdapter(EscolasListSpinner);
                        }catch (JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(EditarCrianca.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idEmpresa));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


    }

    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_responsavel.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){

        try {
            NomeHolder = Nome.getText().toString().trim();
            SobrenomeHolder = Sobrenome.getText().toString().trim();
            CEPHolder = CEP.getText().toString().trim();
            DataNascHolder = DataNasc.getText().toString().trim();
            CpfHolder = Cpf.getText().toString().trim();
            CidadeHolder = Cidade.getText().toString();
            RuaHolder = Rua.getText().toString();
            NumeroHolder = Numero.getText().toString();
            ComplementoHolder = Complemento.getText().toString();
            EstadoHolder = Estado.getSelectedItem().toString();

            switch (Estado.getSelectedItem().toString()) {//pega o nome do item ali em cima
                case "Acre": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "AC";
                    break;
                case "Alagoas": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "AL";
                    break;
                case "Amapá": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "AP";
                    break;
                case "Amazonas": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "AM";
                    break;
                case "Bahia": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "BA";
                    break;
                case "Ceará": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "CE";
                    break;
                case "Distrito Federal": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "DF";
                    break;
                case "Espírito Santo": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "ES";
                    break;
                case "Goiás": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "GO";
                    break;
                case "Maranhão": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "MA";
                    break;
                case "Mato Grosso": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "MT";
                    break;
                case "Mato Grosso do Sul": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "MS";
                    break;
                case "Minas Gerais": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "MG";
                    break;
                case "Pará": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "PA";
                    break;
                case "Paraiba": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "PB";
                    break;
                case "Paraná": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "PR";
                    break;
                case "Pernambuco": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "PE";
                    break;
                case "Piauí": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "PI";
                    break;
                case "Rio de Janeiro": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "RJ";
                    break;
                case "Rio Grande do Norte": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "RN";
                    break;
                case "Rio Grande do Sul": //este nome deve ser igual ao item ali de cima
                    EstadoHolder = "RS";
                    break;
                case "Rondônia": //este nome deve ser igual ao item ali de cima
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


            int spinnerPos = EscolaSpinner.getSelectedItemPosition();
            EscolasConstr escolasConstr = EscolasListConst.get(spinnerPos);
            idEscolaHolder = escolasConstr.getIdEscola();//pegar a id da escola
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println(e.getMessage());
        }
    }

    public void Cadastrar(View view) {
     //   Toast.makeText(this, "Desativado temporariamente", Toast.LENGTH_SHORT).show();

        if(VerificarCampos()) {
            // Showing progress dialog at user registration time.
            progressDialog.setMessage(getResources().getString(R.string.loadingDados));
            progressDialog.show();

            // Calling method to get value from EditText.
            GetValueFromEditText();

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            Toast.makeText(EditarCrianca.this, ServerResponse, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Toast.makeText(EditarCrianca.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            progressDialog.dismiss();
                            Toast.makeText(EditarCrianca.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<>();

                    // Adding All values to Params.
                    params.put("id", String.valueOf(idCrianca).trim());
                    params.put("nome", NomeHolder);
                    params.put("sobrenome", SobrenomeHolder);
                    params.put("data", DataNascHolder);
                    params.put("cpf", CpfHolder);
                    params.put("cep", CEPHolder);
                    params.put("cidade", CidadeHolder);
                    params.put("rua", RuaHolder);
                    params.put("numero", NumeroHolder);
                    params.put("complemento", ComplementoHolder);
                    params.put("estado", EstadoHolder);
                    params.put("idEscola", String.valueOf(idEscolaHolder));

                    return params;
                }

            };

            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }

    }

    private boolean VerificarCampos(){
      /*  if(Nome.getText().length() == 0 || Sobrenome.getText().length() == 0 || Telefone.getText().length() == 0 || CEP.getText().length() == 0
                || DataNasc.getText().length() == 0 || Cpf.getText().length() == 0 || Rg.getText().length() == 0 || Cidade.getText().length() == 0
                || Rua.getText().length() == 0 || Numero.getText().length() == 0 || Estado.getSelectedItemPosition() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else
        {
            return true;
        }
        */
        return true;
    }

    private void PuxarDadosCrianca(){

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURLCrianca,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try{
                            JSONObject obj = new JSONObject(ServerResponse);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            JSONObject jo = funcArray.getJSONObject(0);

                            Nome.setText(jo.getString("nome"));
                            String EstadoBD = jo.getString("estado");
                            Sobrenome.setText(jo.getString("sobrenome"));
                            DataNasc.setText(jo.getString("dt_nasc"));
                            Cpf.setText(jo.getString("cpf"));
                            CEP.setText(jo.getString("cep"));
                            Cidade.setText(jo.getString("cidade"));
                            Rua.setText(jo.getString("rua"));
                            Numero.setText(jo.getString("num"));
                            Complemento.setText(jo.getString("complemento"));

                            switch(EstadoBD){
                                case "AC"://acre
                                    Estado.setSelection(1);
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
                                case "PR"://paraná 16
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
                                default: Estado.setSelection(0);
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EditarCrianca.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            System.out.println(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(EditarCrianca.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(idCrianca));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
