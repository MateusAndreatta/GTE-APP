package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadResponsavel extends AppCompatActivity {

    EditText campo_nome,campo_sobrenome,campo_email,campo_senha,campo_confSenha,campo_telefone,campo_rg,campo_cpf,campo_dataNasc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_responsavel);

        setContentView(R.layout.activity_cadastro);
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

        MaskEditTextChangedListener mascaraCPF = new MaskEditTextChangedListener("###.###.###-##",campo_cpf);
        MaskEditTextChangedListener mascaraCelular = new MaskEditTextChangedListener("(##) #####-####",campo_telefone);
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",campo_dataNasc);
        MaskEditTextChangedListener mascaraRG  = new MaskEditTextChangedListener("##.###.###-#",campo_rg);

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
}
