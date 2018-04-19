package xyz.sistemagte.gte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class cad_van extends AppCompatActivity {

    EditText capacidade, modelo, placa,ano,marca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_van);

        capacidade = findViewById(R.id.cad_capacidade);
        modelo = findViewById(R.id.cad_modelo);
        placa = findViewById(R.id.cad_placa);
        ano = findViewById(R.id.cad_ano_fab);
        marca = findViewById(R.id.cad_marca);

        MaskEditTextChangedListener mascaraPlaca = new MaskEditTextChangedListener("###-###",placa);
        MaskEditTextChangedListener mascaraAno = new MaskEditTextChangedListener("####",ano);

        placa.addTextChangedListener(mascaraPlaca);
        ano.addTextChangedListener(mascaraAno);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_van));
    }


    public void cadastrarVan(View view) {
        Toast.makeText(this, "Não disponivel no momento!", Toast.LENGTH_SHORT).show();
    }
}
