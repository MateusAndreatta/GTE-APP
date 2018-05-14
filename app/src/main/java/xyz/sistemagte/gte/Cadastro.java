package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.Validacoes;

public class Cadastro extends AppCompatActivity {

    EditText campo_nome,campo_sobrenome,campo_email,campo_senha,campo_confSenha,campo_telefone,campo_rg,campo_cpf,campo_dataNasc;

    Spinner tipoUser;

    RequestQueue requestQueue;

    String NomeHolder, SobrenomeHolder,EmailHolder,SenhaHolder,TelefoneHolder,RgHolder,CpfHolder,DtNascHolder;

    ProgressDialog progressDialog;

    String HttpUrl = "https://sistemagte.xyz/android/cadastros/cadGenResp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro));    //Titulo para ser exibido na sua Action Bar em frente à seta

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

        //Array do spinner tipo Usuario
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipousuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoUser.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);

        //aplica mascara
        MaskEditTextChangedListener mascaraCPF = new MaskEditTextChangedListener("###.###.###-##",campo_cpf);
        MaskEditTextChangedListener mascaraCelular = new MaskEditTextChangedListener("(##) #####-####",campo_telefone);
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",campo_dataNasc);
        MaskEditTextChangedListener mascaraRG  = new MaskEditTextChangedListener("#.###.###-#",campo_rg);

        campo_cpf.addTextChangedListener(mascaraCPF);
        campo_telefone.addTextChangedListener(mascaraCelular);
        campo_dataNasc.addTextChangedListener(mascaraData);
        campo_rg.addTextChangedListener(mascaraRG);
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

        if(campo_nome.getText().length() == 0 || campo_sobrenome.getText().length() == 0 || campo_email.getText().length() == 0
                || campo_senha.getText().length() == 0 || campo_confSenha.getText().length() == 0 || campo_telefone.getText().length() == 0
                || campo_rg.getText().length() == 0 || campo_cpf.getText().length() == 0 || campo_dataNasc.getText().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Validacoes validacoes = new Validacoes();
            if(validacoes.ValidarSenhas(this, campo_senha.getText().toString(),campo_confSenha.getText().toString()))
            {
                return true;
            }else{
                return false;
            }

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
            if(validacoes.ValidarSenhas(this,campo_senha.getText().toString(),campo_confSenha.getText().toString())){
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
                        Intent telaMonitora = new Intent(Cadastro.this, cad_monitor.class);
                        startActivity(telaMonitora);
                        break;
                    case ("3"):
                        //responsavel
                        try{
                          CadastrarResp();
                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                            Toast.makeText(this, "Cadastro indisponivel no momento", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
            }

        }
    }


    private void CadastrarResp(){
        if(ValidarCampos()) {
            // Showing progress dialog at user registration time.
            progressDialog.setMessage(getResources().getString(R.string.loadingDados));
            progressDialog.show();
            GetValueFromEditText();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            System.out.println(ServerResponse);
                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Showing response message coming from server.
                            Toast.makeText(Cadastro.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                            Intent tela = new Intent(Cadastro.this, Login.class);
                            startActivity(tela);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Showing error message if something goes wrong.
                            Toast.makeText(Cadastro.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("nome", campo_nome.getText().toString());
                    params.put("sobrenome", campo_sobrenome.getText().toString());
                    params.put("email", campo_email.getText().toString());
                    params.put("senha", campo_senha.getText().toString());
                    params.put("telefone", campo_telefone.getText().toString());
                    params.put("rg", campo_rg.getText().toString());
                    params.put("cpf", campo_cpf.getText().toString());
                    params.put("nascimento", campo_dataNasc.getText().toString());

                    return params;
                }

            };

            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }
    }

}
