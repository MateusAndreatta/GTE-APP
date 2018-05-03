package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;

public class Editar_perfil_motorista extends AppCompatActivity {

    EditText nome,sobrenome,email,cpf,rg,nasc,cep,cidade,rua,num,complemento,cnh,validaCnh;
    Spinner sexo,categoria,Estado;

    private static String JSON_URL = "https://sistemagte.xyz/json/motorista/ListarMotorista.php";
    ListView listView;
    private int idUsuario;
    AlertDialog alerta;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_motorista);

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();

        nome = findViewById(R.id.nome);
        sobrenome = findViewById(R.id.sobrenome);
        email = findViewById(R.id.email);
        cpf = findViewById(R.id.cpf);
        rg = findViewById(R.id.rg);
        nasc = findViewById(R.id.nasc);
        cep = findViewById(R.id.cep);
        cidade = findViewById(R.id.cidade);
        rua = findViewById(R.id.rua);
        num = findViewById(R.id.num);
        complemento = findViewById(R.id.complemento);
        cnh = findViewById(R.id.cnh);
        validaCnh = findViewById(R.id.ValidCnh);
        sexo = findViewById(R.id.sexo);
        categoria = findViewById(R.id.categoria);
        Estado = findViewById(R.id.estado);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.EditarPerfil));

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(Editar_perfil_motorista.this);

        PuxarDados();
    }

    private void PuxarDados(){
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            JSONObject funcObject = funcArray.getJSONObject(0);


                            String dia = funcObject.getString("dt_nasc");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position = new ParsePosition(0);
                            Date data = format.parse(dia,position);
                            format = new SimpleDateFormat("dd/MM/yyyy");
                            String date = format.format(data);

                            String dia2 = funcObject.getString("val_cnh");
                            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position2 = new ParsePosition(0);
                            Date data2 = format2.parse(dia2,position2);
                            format2 = new SimpleDateFormat("dd/MM/yyyy");
                            String date2 = format2.format(data2);


                            nome.setText(funcObject.getString ("nome"));
                            sobrenome.setText(funcObject.getString ("sobrenome"));
                            email.setText(funcObject.getString ("email"));
                            cpf.setText(funcObject.getString ("cpf"));
                            rg.setText(funcObject.getString ("rg"));
                            nasc.setText(date);
                            cep.setText(funcObject.getString ("cep"));
                            cidade.setText(funcObject.getString ("cidade"));
                            rua.setText(funcObject.getString ("rua"));
                            num.setText(funcObject.getString ("num"));
                            complemento.setText(funcObject.getString ("complemento"));
                            cnh.setText(funcObject.getString ("cnh"));
                            validaCnh.setText(date2);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Editar_perfil_motorista.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Painel_motorista.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_motorista.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}

