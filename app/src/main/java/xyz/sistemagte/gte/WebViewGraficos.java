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

        Intent i = getIntent();
        String id = i.getStringExtra("idEscola");

        webViewGrafico = findViewById(R.id.webViewGrafico);
        webViewGrafico.getSettings().setJavaScriptEnabled(true);
        webViewGrafico.loadUrl("https://sistemagte.xyz/android/graficoAnual.php?escola=" +id);
    }
}
