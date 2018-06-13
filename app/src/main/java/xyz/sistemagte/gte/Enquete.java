package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
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

    TextView txtPergunta,txtSim,txtNao,txtPouco;
    ProgressBar progresSim, progresNao, progresPouco;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    private int idUsuario, idEnquete;
    private String perfil;

    private RadioButton rSim,rNao,rPouco;
    private String resposta = "";

    private static String URL_VERIFICA = "https://sistemagte.xyz/android/enquete.php";
    private static String URL_VOTAR = "https://sistemagte.xyz/android/resposta_enquete.php";
    //resultados
    String resulSim, resulNao, resulPouco,pergunta;

    Boolean votou;

    private LinearLayout TelaVotou;
    private LinearLayout TelaNaoVotou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.enquete));     //Titulo para ser exibido na sua Action Bar em frente à seta

        txtNao = findViewById(R.id.txtNaoResul);
        txtSim = findViewById(R.id.txtSimResul);
        txtPouco = findViewById(R.id.txtPoucoResul);
        txtPergunta = findViewById(R.id.txtEnqueteTitulo);

        progresNao = findViewById(R.id.progressBarNao);
        progresPouco = findViewById(R.id.progressBarUmPouco);
        progresSim = findViewById(R.id.progressBarSim);

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

        TelaVotou =    findViewById(R.id.telaResultados);
        TelaNaoVotou =  findViewById(R.id.telaResponder);

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
                params.put("id", String.valueOf(idUsuario));
                params.put("idEnquete", String.valueOf(idEnquete));
                params.put("resposta", resposta);

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
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
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray JsonPergunta = obj.getJSONArray("resposta");

                            JSONObject jsonObject = JsonPergunta.getJSONObject(0);
                            resulSim = jsonObject.getString("ss");
                            jsonObject = JsonPergunta.getJSONObject(1);
                            resulNao =  jsonObject.getString("nn");
                            jsonObject = JsonPergunta.getJSONObject(2);
                            resulPouco =  jsonObject.getString("tt");
                            jsonObject = JsonPergunta.getJSONObject(3);
                            pergunta = jsonObject.getString("pergunta");

                            txtNao.setText(txtNao.getText() + " - " + resulNao + "%");
                            txtSim.setText(txtSim.getText() + " - " + resulSim + "%");
                            txtPouco.setText(txtPouco.getText() + " - " + resulPouco + "%");
                            txtPergunta.setText(pergunta);

                            progresNao.setProgress(Integer.parseInt(resulNao));
                            progresPouco.setProgress(Integer.parseInt(resulPouco));
                            progresSim.setProgress(Integer.parseInt(resulSim));
                            TelaVotou.setVisibility(View.VISIBLE);
                        }catch (Exception ignored){}

                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray JsonPergunta = obj.getJSONArray("pergunta");

                            JSONObject jsonObject = JsonPergunta.getJSONObject(0);
                            idEnquete = Integer.parseInt(jsonObject.getString("id_enquete"));
                            jsonObject = JsonPergunta.getJSONObject(1);
                            pergunta = jsonObject.getString("pergunta");
                            txtPergunta.setText(pergunta);
                            TelaNaoVotou.setVisibility(View.VISIBLE);
                        }catch (Exception ignored){}
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
