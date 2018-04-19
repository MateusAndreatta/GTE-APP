package xyz.sistemagte.gte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class cad_escola extends AppCompatActivity {

    EditText Nome,Sobrenome, CEP,Cidade, Rua, Numero, Complemento;
    Spinner Estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_escola);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastroEscola));


        Estado = findViewById(R.id.spinnerEstado);

        Nome = findViewById(R.id.cad_nome);
        Sobrenome = findViewById(R.id.cad_sobrenome);
        CEP = findViewById(R.id.cad_cep);
        Cidade = findViewById(R.id.cad_cidade);
        Rua = findViewById(R.id.cad_rua);
        Numero = findViewById(R.id.cad_num);
        Complemento = findViewById(R.id.cad_complemento);

        MaskEditTextChangedListener mascaraCEP = new MaskEditTextChangedListener("#####-###",CEP);

        CEP.addTextChangedListener(mascaraCEP);
    }
}
