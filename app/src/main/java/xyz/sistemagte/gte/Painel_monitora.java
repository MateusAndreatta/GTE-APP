package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Auxiliares.Validacoes;

public class Painel_monitora extends AppCompatActivity {
    TextView Label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_monitora);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.AppBarPainel));     //Titulo para ser exibido na sua Action Bar em frente à seta

        Label = findViewById(R.id.LabelOlaPainel);
        GlobalUser global =(GlobalUser)getApplication();
        Label.setText(Label.getText() + ", " + global.getGlobalUserNome());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_painel, menu);
        return true;
    }
    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case R.id.menu_Sair:
                Validacoes validacoes = new Validacoes();
                validacoes.Deslogar(this);
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

    public void irCriancas(View view) {
        Intent Tela = new Intent(this, ListagemCriancaMonitor.class);
        startActivity(Tela);
    }

    public void irEditar_perfil(View view) {
        Intent Tela = new Intent(this, EditarPerfilMonitora.class);
        startActivity(Tela);
    }

    public void irEditar_endereco(View view) {
        Intent Tela = new Intent(this, EditarEndereco.class);
        Tela.putExtra("tipo","monitora");
        startActivity(Tela);
    }

    public void irEmpresa(View view) {
        Intent Tela = new Intent(this, Empresa.class);
        Tela.putExtra("tipo","monitora");
        startActivity(Tela);
    }

    public void irEnquete(View view) {
        Intent Tela = new Intent(this, Enquete.class);
        Tela.putExtra("tipo","monitora");
        startActivity(Tela);
    }

    public void irFaltas(View view) {
        Intent Tela = new Intent(this, Faltas.class);
        Tela.putExtra("tipo","monitora");
        startActivity(Tela);
    }

    public void irCheck(View view){
        Intent tela = new Intent(this, SelecionarVanMonitoraParaCheckList.class);
        startActivity(tela);
    }
}
