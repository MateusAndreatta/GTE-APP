package xyz.sistemagte.gte;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;


public class cad_motorista extends AppCompatActivity{

    EditText cep,cidade,rua,numero,complemento,cnh,validaCnh;
    Spinner sexo,categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_motorista);

        cep = findViewById(R.id.cad_cep);
        cidade = findViewById(R.id.cad_cidade);
        rua = findViewById(R.id.cad_rua);
        numero = findViewById(R.id.cad_num);
        complemento = findViewById(R.id.cad_complemento);
        cnh = findViewById(R.id.cad_cnh);
        validaCnh = findViewById(R.id.cad_ValidCnh);

        categoria = findViewById(R.id.cad_categoria);
        sexo = findViewById(R.id.cad_sexo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_motorista));

        MaskEditTextChangedListener mascaraCPE = new MaskEditTextChangedListener("#####-###",cep);
        MaskEditTextChangedListener mascaraCNH = new MaskEditTextChangedListener("###########",cnh);
        MaskEditTextChangedListener mascaraValida  = new MaskEditTextChangedListener("##/##/####",validaCnh);

        cep.addTextChangedListener(mascaraCPE);
        cnh.addTextChangedListener(mascaraCNH);
        validaCnh.addTextChangedListener(mascaraValida);



    }
}
