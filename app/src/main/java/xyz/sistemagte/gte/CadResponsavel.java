package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import xyz.sistemagte.gte.Auxiliares.Validacoes;

public class CadResponsavel extends AppCompatActivity {

    EditText campo_nome,campo_sobrenome,campo_email,campo_senha,campo_confSenha,campo_telefone,campo_rg,campo_cpf,campo_dataNasc;
    private static String HTTPURL = "https://sistemagte.xyz/android/cadastros/cadResp.php";
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    int idEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_responsavel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastroResp));    //Titulo para ser exibido na sua Action Bar em frente à seta

        campo_nome = findViewById(R.id.cad_nome);
        campo_sobrenome = findViewById(R.id.cad_sobrenome);
        campo_email = findViewById(R.id.cad_email);
        campo_senha = findViewById(R.id.cad_senha);
        campo_confSenha = findViewById(R.id.cad_conf_senha);
        campo_telefone = findViewById(R.id.cad_tel);
        campo_rg = findViewById(R.id.cad_rg);
        campo_cpf = findViewById(R.id.cad_cpf);
        campo_dataNasc = findViewById(R.id.cad_datanascimento);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);

        MaskEditTextChangedListener mascaraCPF = new MaskEditTextChangedListener("###.###.###-##",campo_cpf);
        MaskEditTextChangedListener mascaraCelular = new MaskEditTextChangedListener("(##) #####-####",campo_telefone);
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",campo_dataNasc);
        MaskEditTextChangedListener mascaraRG  = new MaskEditTextChangedListener("#.###.###-#",campo_rg);

        campo_cpf.addTextChangedListener(mascaraCPF);
        campo_telefone.addTextChangedListener(mascaraCelular);
        campo_dataNasc.addTextChangedListener(mascaraData);
        campo_rg.addTextChangedListener(mascaraRG);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();
    }

    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Responsavel.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    public void Cadastrar(View view) {
        if (ValidarCampos()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTPURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            Toast.makeText(CadResponsavel.this, getResources().getString(R.string.cadastradoSucesso), Toast.LENGTH_SHORT).show();
                            Intent tela = new Intent(CadResponsavel.this, Responsavel.class);
                            startActivity(tela);
                            System.out.println(ServerResponse);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(CadResponsavel.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nome", campo_nome.getText().toString());
                    params.put("sobrenome", campo_sobrenome.getText().toString());
                    params.put("senha", campo_senha.getText().toString());
                    params.put("email", campo_email.getText().toString());
                    params.put("telefone", campo_telefone.getText().toString());
                    params.put("cpf", campo_cpf.getText().toString());
                    params.put("rg", campo_rg.getText().toString());
                    params.put("nascimento", campo_dataNasc.getText().toString());
                    params.put("id", String.valueOf(idEmpresa));

                    return params;
                }

            };

            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }

    }

    private boolean ValidarCampos(){
        if(
        campo_nome.getText().length() == 0 ||
        campo_sobrenome.getText().length() == 0 ||
        campo_email.getText().length() == 0 ||
        campo_senha.getText().length() == 0 ||
        campo_confSenha.getText().length() == 0 ||
        campo_telefone.getText().length() == 0 ||
        campo_rg.getText().length() == 0 ||
        campo_cpf.getText().length() == 0 ||
        campo_dataNasc.getText().length() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Validacoes validacoes = new Validacoes();
            if(validacoes.ValidarSenhas(this,campo_senha.getText().toString(), campo_confSenha.getText().toString())){
                return true;
            }else{
                return false;
            }
        }
    }
}
