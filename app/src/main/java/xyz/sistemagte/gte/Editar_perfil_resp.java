package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Map;
import java.util.concurrent.ExecutionException;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.CriancaConst;
import xyz.sistemagte.gte.ListAdapters.ListViewCriancaAdm;

public class Editar_perfil_resp extends AppCompatActivity {

    private int idEmpresa,idUsuario;

    EditText Nome,Sobrenome, Email,DataNasc,Cpf,Rg ;

    RequestQueue requestQueue;
    RequestQueue requestQueue2;

    String NomeHolder,SobrenomeHolder, EmailHolder,DataNascHolder,CpfHolder,RgHolder;

    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;

    String HttpUrl = "https://sistemagte.xyz/android/editar/editarResponsavel.php";

    private static String JSON_URL = "https://sistemagte.xyz/json/responsavel/ListarResponsavel.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_resp);

        Nome      = findViewById(R.id.editar_nome);
        Sobrenome = findViewById(R.id.editar_sobrenome);
        Email     = findViewById(R.id.editar_email);
        DataNasc  = findViewById(R.id.editar_datanascimento);
        Cpf   = findViewById(R.id.editar_cpf);
        Rg   = findViewById(R.id.editar_rg);


        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(Editar_perfil_resp.this);
        progressDialog2 = new ProgressDialog(Editar_perfil_resp.this);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();
        idUsuario = global.getGlobalUserID();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.editar_perfil));     //Titulo para ser exibido na sua Action Bar em frente à seta

        loadDados();

    }

    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_responsavel.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }



    public void salvarEdicao(View view) {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage(getResources().getString(R.string.loadingMsg));
        progressDialog.show();

        // Calling method to get value from EditText.
        GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(Editar_perfil_resp.this, getResources().getString(R.string.MsgSucesso), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Editar_perfil_resp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idUsuario));
                params.put("nome", NomeHolder);
                params.put("sobrenome", SobrenomeHolder);
                params.put("email", EmailHolder);
                params.put("data", DataNascHolder);
                params.put("cpf", CpfHolder);
                params.put("rg", RgHolder);

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
        Toast.makeText(this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
    }


    // Creating method to get value from EditText.
    public void GetValueFromEditText(){
        NomeHolder = Nome.getText().toString().trim();
        SobrenomeHolder = Sobrenome.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        DataNascHolder = DataNasc.getText().toString().trim();
        CpfHolder = Cpf.getText().toString().trim();
        RgHolder = Rg.getText().toString().trim();
    }

    private void loadDados() {
        // Showing progress dialog at user registration time.
        progressDialog2.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog2.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog2.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            JSONObject funcObject = funcArray.getJSONObject(0);

                            Nome.setText(funcObject.getString("nome"));
                            Sobrenome.setText(funcObject.getString("sobrenome"));
                            Email.setText(funcObject.getString("email"));
                            DataNasc.setText(funcObject.getString("dt_nasc"));
                            Cpf.setText(funcObject.getString("cpf"));
                            Rg.setText(funcObject.getString("rg"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog2.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Editar_perfil_resp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idUsuario));

                return params;
            }

        };

        requestQueue2.getCache().clear();
        requestQueue2.add(stringRequest);
    }

}
