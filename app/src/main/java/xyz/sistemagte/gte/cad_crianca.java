package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sistemagte.gte.Construtoras.CriancaConst;

public class Cad_crianca extends AppCompatActivity {

    private int idEmpresa,idUsuario;

    EditText Nome,Sobrenome,Telefone, CEP,DataNasc,Cpf,Rg,Cidade, Rua,Numero, Complemento;
    Spinner Estado;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String NomeHolder,SobrenomeHolder,DataNascHolder,CpfHolder,RgHolder, CidadeHolder,CEPHolder,TelefoneHolder,RGHolder,Numero;

    String HttpUrl = "https://sistemagte.xyz/android/cadastro/CadCrianca.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_crianca);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_crianca));

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

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){
        NomeHolder = Nome.getText().toString().trim();
        SobrenomeHolder = Sobrenome.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        DataNascHolder = DataNasc.getText().toString().trim();
        CpfHolder = Cpf.getText().toString().trim();
        RgHolder = Rg.getText().toString().trim();
    }

    public void Cadastrar(View view) {

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
                        Toast.makeText(Cad_crianca.this, getResources().getString(R.string.MsgSucesso), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Cad_crianca.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
}
