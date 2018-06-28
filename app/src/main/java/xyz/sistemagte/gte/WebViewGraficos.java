package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        if(i.getStringExtra("tipo").equals("anual")){
            webViewGrafico.loadUrl("https://sistemagte.xyz/android/graficoAnual.php?escola=" +id);
        }else{
            String mes = i.getStringExtra("mes");
            System.out.println(mes);
            webViewGrafico.loadUrl("https://sistemagte.xyz/android/grafico.php?escola=" +id + "&mes" + mes);
        }

    }
}
