package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.CriancaConst;

public class cad_crianca extends AppCompatActivity {

    private int idEmpresa,idUsuario;

    EditText Nome,Sobrenome,Telefone, CEP,DataNasc,Cpf,Rg,Cidade, Rua, Numero, Complemento;
    Spinner Estado;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String NomeHolder,SobrenomeHolder,DataNascHolder,CpfHolder,RgHolder, CidadeHolder,CEPHolder,TelefoneHolder,NumeroHolder,RuaHolder, ComplementoHolder, EstadoHolder;

    String HttpUrl = "https://sistemagte.xyz/android/cadastros/cadCriancaResp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_crianca);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(cad_crianca.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_crianca));

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        idEmpresa = global.getGlobalUserIdEmpresa();

        //TODO: preencher o spinner escolas com as escolas cadastradas no banco referente a uma devida empresa do usuario
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
        NomeHolder = Nome.getText().toString().trim();
        SobrenomeHolder = Sobrenome.getText().toString().trim();
        TelefoneHolder = Telefone.getText().toString().trim();
        CEPHolder = CEP.getText().toString().trim();
        DataNascHolder = DataNasc.getText().toString().trim();
        CpfHolder = Cpf.getText().toString().trim();
        RgHolder = Rg.getText().toString().trim();
        CidadeHolder = Cidade.getText().toString();
        RuaHolder = Rua.getText().toString();
        NumeroHolder = Numero.getText().toString();
        ComplementoHolder = Complemento.getText().toString();
        EstadoHolder = Estado.getSelectedItem().toString();
    }

    public void Cadastrar(View view) {

        if(VerificarCampos()) {


            // Showing progress dialog at user registration time.
            progressDialog.setMessage(getResources().getString(R.string.loadingMsg));
            progressDialog.show();

            // Calling method to get value from EditText.
            GetValueFromEditText();

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Showing response message coming from server.
                            Toast.makeText(cad_crianca.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Showing error message if something goes wrong.
                            Toast.makeText(cad_crianca.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<>();

                    // Adding All values to Params.
                    params.put("id", String.valueOf(idUsuario));
                    params.put("nome", NomeHolder);
                    params.put("sobrenome", SobrenomeHolder);
                    params.put("data", DataNascHolder);
                    params.put("cpf", CpfHolder);
                    params.put("rg", RgHolder);
                    params.put("tel", TelefoneHolder);
                    params.put("cep", CEPHolder);
                    params.put("cidade", CidadeHolder);
                    params.put("rua", RuaHolder);
                    params.put("numero", NumeroHolder);
                    params.put("complemento", ComplementoHolder);
                    params.put("estado", EstadoHolder);

                    return params;
                }

            };

            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }
    }

    private boolean VerificarCampos(){
    //EditText Nome,Sobrenome,Telefone, CEP,DataNasc,Cpf,Rg,Cidade, Rua, Numero, Complemento;
        //        Spinner Estado;
        //TODO: Verificar o spiner tbm
        //lembrando q tem 2 o das escolas e dos estados
        if(Nome.getText().length() == 0 || Sobrenome.getText().length() == 0 || Telefone.getText().length() == 0 || CEP.getText().length() == 0
                || DataNasc.getText().length() == 0 || Cpf.getText().length() == 0 || Rg.getText().length() == 0 || Cidade.getText().length() == 0
                || Rua.getText().length() == 0 || Numero.getText().length() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else
        {
            return true;
        }
    }
}
