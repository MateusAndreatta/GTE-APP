package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.Construtoras.ResponsavelConstr;
import xyz.sistemagte.gte.Construtoras.VansConstr;

public class cad_van extends AppCompatActivity {

    EditText capacidade, modelo, placa,ano,marca;
    Spinner spinner;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String HTTP_Cad = "";
    String UrlSpinner = "";

    ArrayAdapter<String> MotoristaListSpinner;
    ArrayAdapter<String> RespListSpinner;
    ArrayList<vans> vansList;
    ArrayList<ResponsavelConstr> ResponsavelConstrList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_van);

        capacidade = findViewById(R.id.cad_capacidade);
        modelo = findViewById(R.id.cad_modelo);
        placa = findViewById(R.id.cad_placa);
        ano = findViewById(R.id.cad_ano_fab);
        marca = findViewById(R.id.cad_marca);

        spinner = findViewById(R.id.morotistaSpinner);

        MaskEditTextChangedListener mascaraPlaca = new MaskEditTextChangedListener("###-###",placa);
        MaskEditTextChangedListener mascaraAno = new MaskEditTextChangedListener("####",ano);

        placa.addTextChangedListener(mascaraPlaca);
        ano.addTextChangedListener(mascaraAno);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadastro_van));
    }


    public void cadastrarVan(View view) {
        Toast.makeText(this, "Não disponivel no momento!", Toast.LENGTH_SHORT).show();
/*  
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_Cad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(cad_van.this, R.string.cadastradoSucesso, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(cad_van.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("capacidade", capacidade.getText().toString());
                params.put("modelo", modelo.getText().toString());
                params.put("placa", placa.getText().toString());
                params.put("ano", ano.getText().toString());
                params.put("marca", marca.getText().toString());

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    */
    }

    private void LoadMotoristas(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlSpinner,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //Toast.makeText(cad_crianca.this, ServerResponse, Toast.LENGTH_SHORT).show();
                        try{
                            JSONObject jsonObject=new JSONObject(ServerResponse);
                            JSONArray jsonArray=jsonObject.getJSONArray("nome");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String motorista = jsonObject1.getString("nome");
                                MotoristaListSpinner.add(motorista);
                                VansConstr vansConstr = new VansConstr(jsonObject1.getString("nome"),jsonObject1.getString("cep"),jsonObject1.getString("rua"),jsonObject1.getString("numero"),jsonObject1.getString("complemento"),jsonObject1.getString("estado"),jsonObject1.getString("cidade"),jsonObject1.getInt("idEscola"),jsonObject1.getInt("idEnderecoEscola"));
                                EscolasListConst.add(vansConstr);
                            }
                            spinner.setAdapter(MotoristaListSpinner);
                        }catch (JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(vans.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("id", String.valueOf(idEmpresa));

                return params;
            }
        }

}
