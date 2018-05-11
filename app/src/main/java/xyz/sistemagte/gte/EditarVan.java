package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;

public class EditarVan extends AppCompatActivity {

    EditText capacidade, modelo, placa,ano,marca;
    private int idVan;

    RequestQueue requestQueue;

    private static String JsonVan = "https://sistemagte.xyz/json/motorista/ListarDadosVan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_van);


        requestQueue = Volley.newRequestQueue(this);

        capacidade = findViewById(R.id.cad_capacidade);
        modelo = findViewById(R.id.cad_modelo);
        placa = findViewById(R.id.cad_placa);
        ano = findViewById(R.id.cad_ano_fab);
        marca = findViewById(R.id.cad_marca);

        MaskEditTextChangedListener mascaraPlaca = new MaskEditTextChangedListener("###-###",placa);
        MaskEditTextChangedListener mascaraAno = new MaskEditTextChangedListener("####",ano);

        placa.addTextChangedListener(mascaraPlaca);
        ano.addTextChangedListener(mascaraAno);

        Intent i = getIntent();
        idVan = Integer.parseInt(i.getStringExtra("idV"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.EditarVan));
        PuxarDadosVans();
    }
    public void editarVan(View view) {
        Toast.makeText(this, "Não disponivel no momento!", Toast.LENGTH_SHORT).show();
    }


    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, vans.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, vans.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void PuxarDadosVans(){

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonVan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try{
                            JSONObject obj = new JSONObject(ServerResponse);

                            JSONArray funcArray = obj.getJSONArray("nome");
                            JSONObject jo = funcArray.getJSONObject(0);

                            capacidade.setText(jo.getString("capacidade"));
                            modelo.setText(jo.getString("modelo"));
                            placa.setText(jo.getString("placa"));
                            ano.setText(jo.getString("ano_fabri"));
                            marca.setText(jo.getString("marca"));

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EditarVan.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            System.out.println(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(EditarVan.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(idVan));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
