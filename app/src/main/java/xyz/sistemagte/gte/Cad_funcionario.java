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
import xyz.sistemagte.gte.Auxiliares.Validacoes;
import xyz.sistemagte.gte.Construtoras.FuncConst;
import xyz.sistemagte.gte.ListAdapters.ListViewFunc;

public class Cad_funcionario extends AppCompatActivity {

    EditText Nome,Sobrenome,Telefone,Email,DataNasc,Cpf,Rg,Cidade,senha,confSenha;
    Spinner tipoSpinner;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    int idEmpresa;
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

    private boolean ValidarCampos(){

        if(Nome.getText().length() == 0 || Sobrenome.getText().length() == 0 || Email.getText().length() == 0
                || senha.getText().length() == 0 || confSenha.getText().length() == 0 || Telefone.getText().length() == 0
                || Rg.getText().length() == 0 || Cpf.getText().length() == 0 || DataNasc.getText().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Validacoes validacoes = new Validacoes();
            if(validacoes.ValidarSenhas(this, senha.getText().toString(),confSenha.getText().toString()))
            {
                return true;
            }else{
                return false;
            }
        }
    }


    public void Cadastrar(View view) {
        Validacoes validacoes = new Validacoes();
        if(ValidarCampos()){
            if(validacoes.ValidarSenhas(this,senha.getText().toString(),confSenha.getText().toString())){
                String tipo = String.valueOf(tipoSpinner.getSelectedItemPosition());
                switch (tipo){
                    case("0"):
                        //escolha
                        Toast.makeText(this, getResources().getString(R.string.tipoUserFeedback), Toast.LENGTH_SHORT).show();
                        break;
                    case("1"):
                        //Motorista
                        Intent telaMotorista = new Intent(Cad_funcionario.this, cad_motorista_adm.class);
                        telaMotorista.putExtra("nome", Nome.getText().toString());
                        telaMotorista.putExtra("sobrenome", Sobrenome.getText().toString());
                        telaMotorista.putExtra("email", Email.getText().toString());
                        telaMotorista.putExtra("senha", senha.getText().toString());
                        telaMotorista.putExtra("telefone", Telefone.getText().toString());
                        telaMotorista.putExtra("rg", Rg.getText().toString());
                        telaMotorista.putExtra("cpf", Cpf.getText().toString());
                        telaMotorista.putExtra("nascimento", DataNasc.getText().toString());
                        startActivity(telaMotorista);
                        break;
                    case("2"):
                        //Monitora
                        Intent telaMonitora = new Intent(Cad_funcionario.this, CadMonitoraAdm.class);
                        telaMonitora.putExtra("nome", Nome.getText().toString());
                        telaMonitora.putExtra("sobrenome", Sobrenome.getText().toString());
                        telaMonitora.putExtra("email", Email.getText().toString());
                        telaMonitora.putExtra("senha", senha.getText().toString());
                        telaMonitora.putExtra("telefone", Telefone.getText().toString());
                        telaMonitora.putExtra("rg", Rg.getText().toString());
                        telaMonitora.putExtra("cpf", Cpf.getText().toString());
                        telaMonitora.putExtra("nascimento", DataNasc.getText().toString());
                        startActivity(telaMonitora);
                        break;
                }
            }

        }
    }
}