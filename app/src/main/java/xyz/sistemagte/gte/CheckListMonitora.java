package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Auxiliares.Validacoes;
import xyz.sistemagte.gte.Construtoras.CheckStatusConstr;
import xyz.sistemagte.gte.Construtoras.CriancaConst;
import xyz.sistemagte.gte.ListAdapters.ListViewCheck;
import xyz.sistemagte.gte.ListAdapters.ListViewCriancaAdm;

public class CheckListMonitora extends AppCompatActivity{


    private static String JSON_URL = "https://sistemagte.xyz/json/monitora/checklist.php";
    ListView listView;
    private int idEmpresa,idUsuario;
    private String idVan;
    List<CheckStatusConstr> checkList;
    List<CheckStatusConstr> listaQuery;
    AlertDialog alerta;
    SearchView searchView;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    WebView webViewChecklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_monitora);
        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();
        idUsuario = global.getGlobalUserIdEmpresa();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent i = getIntent();
        idVan = i.getStringExtra("id");

        webViewChecklist = findViewById(R.id.webViewChecklist);
        webViewChecklist.getSettings().setJavaScriptEnabled(false);
        webViewChecklist.loadUrl("https://sistemagte.xyz/android/checklist2.php?id=" + idUsuario +"&van=" + idVan);



        webViewChecklist.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                webViewChecklist.clearCache(true);
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.listaCriancas));     //Titulo para ser exibido na sua Action Bar em frente à seta


    }

   // @Override
   // public boolean dispatchTouchEvent(MotionEvent ev) {
   //     // Your code here
   //     webViewChecklist.reload();
   //     return super.dispatchTouchEvent(ev);
   // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_reload, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, SelecionarVanMonitoraParaCheckList.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case R.id.recarregar:
                Toast.makeText(this, R.string.att, Toast.LENGTH_SHORT).show();
                webViewChecklist.reload();
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, SelecionarVanMonitoraParaCheckList.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}