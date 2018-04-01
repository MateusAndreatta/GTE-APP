package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import xyz.sistemagte.gte.metodos.Validacoes;

public class Cadastro extends AppCompatActivity {

    EditText campo_nome,campo_sobrenome,campo_email,campo_senha,campo_confSenha,campo_telefone,campo_rg,campo_cpf,campo_dataNasc;

    Spinner tipoUser;

    RequestQueue requestQueue;

    String NomeHolder, SobrenomeHolder,EmailHolder,SenhaHolder,TelefoneHolder,RgHolder,CpfHolder,DtNascHolder;

    ProgressDialog progressDialog;

    String HttpUrl = "https://sistemagte.xyz/PagProcessamento/RecebeCadastro.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastro GTE");     //Titulo para ser exibido na sua Action Bar em frente à seta

        //Declarações
        campo_nome = findViewById(R.id.cad_nome);
        campo_sobrenome = findViewById(R.id.cad_sobrenome);
        campo_email = findViewById(R.id.cad_email);
        campo_senha = findViewById(R.id.cad_senha);
        campo_confSenha = findViewById(R.id.cad_conf_senha);
        campo_telefone = findViewById(R.id.cad_tel);
        campo_rg = findViewById(R.id.cad_rg);
        campo_cpf = findViewById(R.id.cad_cpf);
        campo_dataNasc = findViewById(R.id.cad_datanascimento);
        tipoUser = findViewById(R.id.cad_tipousuario);

        Button cadastrar = findViewById(R.id.cadastro);



        //Array do spinner tipo Usuario
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipousuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoUser.setAdapter(adapter);
    }
    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    private boolean ValidarCampos(){
        //TODO: Fazer 2 if, um para verificar se todos os campos estao preenchidos, e um pra verificar a senha.
        if(campo_nome.getText().length() == 0 || campo_sobrenome.getText().length() == 0 || campo_email.getText().length() == 0
                || campo_senha.getText().length() == 0 || campo_confSenha.getText().length() == 0 || campo_telefone.getText().length() == 0
                || campo_rg.getText().length() == 0 || campo_cpf.getText().length() == 0 || campo_dataNasc.getText().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }


    public void GetValueFromEditText(){

        NomeHolder      = campo_nome.getText().toString().trim();
        SobrenomeHolder = campo_sobrenome.getText().toString().trim();
        EmailHolder     = campo_email.getText().toString().trim();
        SenhaHolder     = campo_senha.getText().toString();
        TelefoneHolder  = campo_telefone.getText().toString().trim();
        RgHolder        = campo_rg.getText().toString().trim();
        CpfHolder       = campo_cpf.getText().toString().trim();
        DtNascHolder    = campo_dataNasc.getText().toString().trim();

    }

    public void Cadastrar(View view) {
        /**
         * 0 - escolha
         * 1 - Motorista
         * 2 - Monitora
         * 3 - Responsavel
         * */
        Validacoes validacoes = new Validacoes();
        if(ValidarCampos()){
            if(!validacoes.ValidarSenhas(campo_senha.getText().toString(),campo_confSenha.getText().toString())){
                Toast.makeText(this, getResources().getString(R.string.senhasDiferentes), Toast.LENGTH_SHORT).show();
            }else{
                String tipo = String.valueOf(tipoUser.getSelectedItemPosition());
                switch (tipo){
                    case("0"):
                        //escolha
                        Toast.makeText(this, getResources().getString(R.string.tipoUserFeedback), Toast.LENGTH_SHORT).show();
                        break;
                    case("1"):
                        //Motorista
                        Intent telaMotorista = new Intent(Cadastro.this, cad_motorista.class);
                        startActivity(telaMotorista);
                        break;
                    case("2"):
                        //Monitora
                        Intent telaMonitora = new Intent(Cadastro.this, cad_monitora.class);
                        startActivity(telaMonitora);
                        break;
                    case ("3"):
                        //responsavel
                        Toast.makeText(this, "Cadastro indisponivel no momento", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        }
    }

}
