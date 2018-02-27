package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Contato extends AppCompatActivity {

    //TODO: Personalizar o Spinner com a seta e um "escolha o assunto"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Contato GTE");     //Titulo para ser exibido na sua Action Bar em frente à seta


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

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Login.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }


    public void irProximoContato(View view) {
        EditText campoEmail = (EditText)findViewById(R.id.input_email);
        EditText campoNome = (EditText)findViewById(R.id.input_nome);
        if(campoEmail.getText().length() == 0 || campoNome.getText().length() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
        }else{
            LinearLayout layout2 = (LinearLayout) findViewById(R.id.Contato_Layout2);
            LinearLayout layout1 = (LinearLayout) findViewById(R.id.Contato_Layout1);
            TextView txtT2 = (TextView)findViewById(R.id.textViewContatoT2);

            String email;
            String nome;

            email = campoEmail.getText().toString();
            nome = campoNome.getText().toString();
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);

            String str1 = getResources().getString(R.string.ola);
            String str2 = getResources().getString(R.string.continuarContato);

            txtT2.setText(str1 + " " + nome.substring(0,1).toUpperCase().concat(nome.substring(1)) + str2);
        }

    }

    public void EnviarMsg (View v){

    }
}
