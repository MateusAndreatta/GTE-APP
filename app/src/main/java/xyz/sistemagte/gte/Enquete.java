package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.VansConstr;

public class Enquete extends AppCompatActivity {

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    private int idUsuario;
    private String perfil;

    private RadioButton rSim,rNao,rPouco;
    private String resposta = "";

    private static String URL_VERIFICA = "https://sistemagte.xyz/android/enquete.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.empresa));     //Titulo para ser exibido na sua Action Bar em frente à seta

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        //Pegando o usuario que clica na tela
        Intent i = getIntent();
        perfil = i.getStringExtra("tipo");

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(Enquete.this);

        rSim = findViewById(R.id.r1);
        rNao = findViewById(R.id.r2);
        rPouco = findViewById(R.id.r3);

        VerificaVoto();
    }

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

    public void votar(View view) {
        //TODO: Request da reposta
        if(rSim.isChecked()){
            resposta = "S";
        }else if (rNao.isChecked()){
            resposta = "N";
        }else if(rPouco.isChecked()){
            resposta = "P";
        }
        if(resposta.equals("")){
            Toast.makeText(this, R.string.radioVazioEnquete, Toast.LENGTH_SHORT).show();
        }else {
             /* BASE VOLLEY
        progressDialog.setMessage(getResources().getString(R.string.carregando));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_VOTAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //Toast.makeText(Empresa.this, response, Toast.LENGTH_SHORT).show();
                        Intent tela = new Intent(Enquete.this, Enquete.class);
                        tela.putExtra("tipo",perfil);
                        startActivity(tela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Enquete.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("idUsuario", String.valueOf(idUsuario));
                params.put("voto","");

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

        */
        }


        /**
         * mandar
         * id -> idusuario
         * idEnquete -> id da enquete
         * resp -> resposta
         * */

    }

    public void voltar(View view) {
        switch (perfil){
            case "monitora":
                startActivity(new Intent(this, Painel_monitora.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                break;
            case "motorista":
                startActivity(new Intent(this, Painel_motorista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                break;
            case "resp":
                startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                break;
            default:
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
        }
    }

    private void VerificaVoto(){

        progressDialog.setMessage(getResources().getString(R.string.carregando));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_VERIFICA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                      // //Toast.makeText(Empresa.this, response, Toast.LENGTH_SHORT).show();
                      // Intent tela = new Intent(Enquete.this, Enquete.class);
                      // tela.putExtra("tipo",perfil);
                      // startActivity(tela);
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray JsonPergunta = obj.getJSONArray("resposta");

                            JSONObject jsonObject = JsonPergunta.getJSONObject(0);
                            System.out.println(jsonObject.getString("ss"));
                            System.out.println(jsonObject.getString("nn"));
                            System.out.println(jsonObject.getString("tt"));
                            System.out.println(jsonObject.getString("pergunta"));
                        }catch (Exception ex){

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Enquete.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(idUsuario));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

}
