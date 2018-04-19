package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;

public class Painel_adm extends AppCompatActivity {

    TextView Label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_adm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.AppBarPainel));     //Titulo para ser exibido na sua Action Bar em frente à seta

        Label = findViewById(R.id.LabelOlaPainel);
        GlobalUser global =(GlobalUser)getApplication();
        Label.setText(Label.getText() + ", " + global.getGlobalUserNome());
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

    public void irFuncionarios(View view) {
        Intent Tela = new Intent(this, Funcionario_adm.class);
        startActivity(Tela);
    }

    public void irCriancas(View view) {
        Intent Tela = new Intent(this, CriancaListagemAdm.class);
        startActivity(Tela);
    }

    public void irVan(View view) {
        Intent Tela = new Intent(this, vans.class);
        startActivity(Tela);
    }

    public void irMensalidade(View view) {
        try {
            Intent Tela = new Intent(this, vans.class);
            startActivity(Tela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void irAcessos(View view) {
        Intent Tela = new Intent(this, Acessos.class);
        startActivity(Tela);
    }

    public void irGraficos(View view) {
        Toast.makeText(this, "Gráficos indisponível no momento", Toast.LENGTH_SHORT).show();
    }
}
