package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

public class WebViewGraficos extends AppCompatActivity {

    WebView webViewGrafico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_graficos);
        webViewGrafico = findViewById(R.id.webViewGrafico);
        webViewGrafico.getSettings().setJavaScriptEnabled(true);
        Intent i = getIntent();
        String id = i.getStringExtra("idEscola");
        String escola = i.getStringExtra("nomeEscola");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.grafico) + " " + escola);

        if(i.getStringExtra("tipo").equals("anual")){
            webViewGrafico.loadUrl("https://sistemagte.xyz/android/graficoAnual.php?escola=" +id);
        }else{
            String mes = i.getStringExtra("mes");
            System.out.println(mes);
            webViewGrafico.loadUrl("https://sistemagte.xyz/android/grafico.php?escola=" +id + "&mes=" + mes);
        }

    }

    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Graficos.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Graficos.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
    public void voltar(View view) {
        Intent tela = new Intent(this,Graficos.class);
        startActivity(tela);
    }
}
