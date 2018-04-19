package xyz.sistemagte.gte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class cad_monitor extends AppCompatActivity {

    EditText cep,cidade,rua,numero,complemetno,data_admissao,hora_entrada,hora_saida;
    Spinner sexo,estados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_monitor);

        cep = findViewById(R.id.cad_cep);
        cidade = findViewById(R.id.cad_cidade);
        rua = findViewById(R.id.cad_rua);
        numero = findViewById(R.id.cad_num);
        complemetno = findViewById(R.id.cad_complemento);
        data_admissao = findViewById(R.id.data_admissao);
        hora_entrada = findViewById(R.id.hora_entrada);
        hora_saida = findViewById(R.id.hora_saida);

        estados = findViewById(R.id.spinnerEstado);
        sexo = findViewById(R.id.cad_sexo);

        MaskEditTextChangedListener mascaraCPE = new MaskEditTextChangedListener("#####-###",cep);
        MaskEditTextChangedListener mascaraHoraEntrada = new MaskEditTextChangedListener("##:##",hora_entrada);
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",data_admissao);
        MaskEditTextChangedListener mascaraHoraSaida  = new MaskEditTextChangedListener("##:##",hora_saida);

        cep.addTextChangedListener(mascaraCPE);
        hora_entrada.addTextChangedListener(mascaraHoraEntrada);
        data_admissao.addTextChangedListener(mascaraData);
        hora_saida.addTextChangedListener(mascaraHoraSaida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_monitora));     //Titulo para ser exibido na sua Action Bar em frente à seta
    }
}
