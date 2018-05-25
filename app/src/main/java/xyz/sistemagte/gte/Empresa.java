package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.client.result.EmailAddressParsedResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.AcessosConst;
import xyz.sistemagte.gte.ListAdapters.ListViewAcessos;

public class Empresa extends AppCompatActivity {

    TextView nomeEmpresa,cnpj,telefone;
    EditText cad_cod;

    private static String UPDATE_URL = "https://sistemagte.xyz/android/UpdateEmpresa.php";
    private static String JSON_URL = "https://sistemagte.xyz/json/empresa.php";

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    private int idEmpresa,idUsuario;
    private String perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.empresa));     //Titulo para ser exibido na sua Action Bar em frente à seta

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();
        idUsuario = global.getGlobalUserID();
        //Pegando o usuario que clica na tela
        Intent i = getIntent();
        perfil = i.getStringExtra("tipo");

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(Empresa.this);


        LinearLayout TelaComEmpresa =  findViewById(R.id.empresa);
        LinearLayout TelaSemEmpresa =  findViewById(R.id.cad_empresa);

        if(idEmpresa == 0){
            //Não tem empresa
            TelaSemEmpresa.setVisibility(View.VISIBLE);
            cad_cod = findViewById(R.id.cad_cod);
        }else{
            //Tem empresa
            TelaComEmpresa.setVisibility(View.VISIBLE);
            nomeEmpresa = findViewById(R.id.NomeEmpresa);
            cnpj = findViewById(R.id.cnpj);
            telefone = findViewById(R.id.telefone);
            PuxarDadosEmpresa();
        }
    }

    public void Enviar(View view) {
        GlobalUser global =(GlobalUser)getApplication();
        global.setGlobalUserIdEmpresa(Integer.parseInt(String.valueOf(cad_cod.getText())));
        idEmpresa = global.getGlobalUserIdEmpresa();
        EnviarEmpresa();
    }
    //BACK BUTTONS

    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                switch (perfil){
                    case "monitora":
                        startActivity(new Intent(this, Painel_monitora.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;

                    case  "motorista":
                        startActivity(new Intent(this, Painel_motorista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;

                    case "resp":
                        startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    default:
                        startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                }

                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        switch (perfil){
            case "monitora":
                startActivity(new Intent(this, Painel_monitora.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case  "motorista":
                startActivity(new Intent(this, Painel_motorista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case "resp":
                startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
        }
        return;
    }


    //Volley


    private void PuxarDadosEmpresa(){
        progressDialog.setMessage(getResources().getString(R.string.loadingDados));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);
                                nomeEmpresa.setText(funcObject.getString("nome_empresa"));
                                cnpj.setText(funcObject.getString("cnpj"));
                                telefone.setText(funcObject.getString("tel_comercial"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Empresa.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(idEmpresa));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void EnviarEmpresa(){
        progressDialog.setMessage(getResources().getString(R.string.carregando));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //Toast.makeText(Empresa.this, response, Toast.LENGTH_SHORT).show();
                        Intent tela = new Intent(Empresa.this, Empresa.class);
                        tela.putExtra("tipo",perfil);
                        startActivity(tela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Empresa.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("idUsuario", String.valueOf(idUsuario));
                params.put("idEmpresa", String.valueOf(idEmpresa));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}
