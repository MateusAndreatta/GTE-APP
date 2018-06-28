package xyz.sistemagte.gte;

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
        webViewGrafico.loadUrl("https://sistemagte.xyz/android/graficoAnual.php");
    }
}
