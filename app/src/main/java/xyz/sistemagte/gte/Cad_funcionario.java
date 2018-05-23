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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.FuncConst;
import xyz.sistemagte.gte.ListAdapters.ListViewFunc;

public class Cad_funcionario extends AppCompatActivity {

    EditText Nome,Sobrenome,Telefone,Email,DataNasc,Cpf,Rg,Cidade,senha,confSenha;
    Spinner tipoSpinner;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    int idEmpresa;

    private static String HTTP_URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_funcionario);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastroFunc));     //Titulo para ser exibido na sua Action Bar em frente à seta

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(Cad_funcionario.this);

        Nome = findViewById(R.id.cad_nome);
        Sobrenome = findViewById(R.id.cad_sobrenome);
        Telefone = findViewById(R.id.cad_tel);
        Email = findViewById(R.id.cad_email);
        DataNasc = findViewById(R.id.cad_datanascimento);
        Cpf = findViewById(R.id.cad_cpf);
        Rg = findViewById(R.id.cad_rg);
        Cidade = findViewById(R.id.cad_cidade);
        senha = findViewById(R.id.cad_senha);
        confSenha = findViewById(R.id.cad_conf_senha);
        tipoSpinner = findViewById(R.id.cad_func);

        MaskEditTextChangedListener mascaraCPF = new MaskEditTextChangedListener("###.###.###-##",Cpf);
        MaskEditTextChangedListener mascaraCelular = new MaskEditTextChangedListener("(##) #####-####",Telefone);
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",DataNasc);
        MaskEditTextChangedListener mascaraRG  = new MaskEditTextChangedListener("#.###.###-#",Rg);

        Cpf.addTextChangedListener(mascaraCPF);
        Telefone.addTextChangedListener(mascaraCelular);
        DataNasc.addTextChangedListener(mascaraData);
        Rg.addTextChangedListener(mascaraRG);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();

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


    public void Cadastrar(View view) {
        //TODO: CADASTRAR FUNCIONARIO
        /**
         * Verificar qual o select
         * Passar todos os parametros por bundle
         * e realizar lá o cadastro de monitora ou motorista
         * */


        progressDialog.setMessage(getResources().getString(R.string.loadingDados));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Cad_funcionario.this, getResources().getString(R.string.cadastradoSucesso), Toast.LENGTH_SHORT).show();
                        Intent tela = new Intent(Cad_funcionario.this,Funcionario_adm.class);
                        startActivity(tela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Cad_funcionario.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                String tipo = String.valueOf(tipoSpinner.getSelectedItemPosition());
                switch (tipo) {
                    case ("0"):
                        //escolha
                        Toast.makeText(Cad_funcionario.this, getResources().getString(R.string.tipoUserFeedback), Toast.LENGTH_SHORT).show();
                        break;
                    case ("1"):
                        //Motorista

                        break;
                    case ("2"):
                        //monitora

                        break;
                }

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(idEmpresa));
                params.put("nome", Nome.getText().toString());
                params.put("sobrenome", Sobrenome.getText().toString());
                params.put("tel", Telefone.getText().toString());
                params.put("email", Email.getText().toString());
                params.put("dt_nasc", DataNasc.getText().toString());
                params.put("cpf", Cpf.getText().toString());
                params.put("rg", Rg.getText().toString());
                params.put("cidade", Cidade.getText().toString());
                params.put("senha", senha.getText().toString());
                params.put("tipo", "");

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}