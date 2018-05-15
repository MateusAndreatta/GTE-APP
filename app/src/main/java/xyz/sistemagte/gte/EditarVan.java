package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.MotoristaConstr;

public class EditarVan extends AppCompatActivity {

    EditText capacidade, modelo, placa,ano,marca;
    private int idVan;
    private int idVanJson;
    Spinner spinner;

    RequestQueue requestQueue,requestQueue2;
    ProgressDialog progressDialog;

    private static String JsonVan = "https://sistemagte.xyz/json/motorista/ListarDadosVan.php";
    private static String UrlSpinner = "https://sistemagte.xyz/json/adm/ListarMotoristaEmp.php";
    private static String URLUpdate = "https://sistemagte.xyz/android/editar/editarVan.php";

    Integer idEmpresa,idUsuario;

    ArrayAdapter<String> MotoristaListSpinner;
    ArrayList<MotoristaConstr> MotoristaConstrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_van);

        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        idEmpresa = global.getGlobalUserIdEmpresa();


        spinner = findViewById(R.id.morotistaSpinner);
        progressDialog = new ProgressDialog(EditarVan.this);
        requestQueue = Volley.newRequestQueue(this);

        MotoristaConstrList = new ArrayList<>();
        MotoristaListSpinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);

        capacidade = findViewById(R.id.cad_capacidade);
        modelo = findViewById(R.id.cad_modelo);
        placa = findViewById(R.id.cad_placa);
        ano = findViewById(R.id.cad_ano_fab);
        marca = findViewById(R.id.cad_marca);

        //Filtro para que todos os caracteres sejam maiusculos
        placa.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        MaskEditTextChangedListener mascaraPlaca = new MaskEditTextChangedListener("###-####",placa);
        MaskEditTextChangedListener mascaraAno = new MaskEditTextChangedListener("####",ano);
        MaskEditTextChangedListener mascaraCapacidade = new MaskEditTextChangedListener("##",capacidade);

        placa.addTextChangedListener(mascaraPlaca);
        capacidade.addTextChangedListener(mascaraCapacidade);
        ano.addTextChangedListener(mascaraAno);

        Intent i = getIntent();
        idVan = Integer.parseInt(i.getStringExtra("idV"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.EditarVan));
        LoadMotoristas();
        PuxarDadosVans();
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


    public void editarVan(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(EditarVan.this, R.string.informacoesSalvasSucesso, Toast.LENGTH_SHORT).show();

                        Intent tela = new Intent(EditarVan.this, vans.class);
                        startActivity(tela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(EditarVan.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                int spinnerPos2 = spinner.getSelectedItemPosition();
                MotoristaConstr motorista = MotoristaConstrList.get(spinnerPos2);

                params.put("id", String.valueOf(motorista.getId_motorista()));
                params.put("capacidade", capacidade.getText().toString());
                params.put("modelo", modelo.getText().toString());
                params.put("placa", placa.getText().toString());
                params.put("AnoFabri", ano.getText().toString());
                params.put("marca", marca.getText().toString());
                params.put("idVan", String.valueOf(idVanJson));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void PuxarDadosVans(){
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
                            idVanJson = Integer.parseInt(jo.getString("id_van"));
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

    private void LoadMotoristas() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlSpinner,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            JSONArray jsonArray = jsonObject.getJSONArray("nome");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String motorista = jsonObject1.getString("nome") + " " + jsonObject1.getString("sobrenome");
                                MotoristaListSpinner.add(motorista);
                                MotoristaConstr motoristaConstr = new MotoristaConstr(jsonObject1.getString("nome"),jsonObject1.getString("sobrenome"),Integer.parseInt(jsonObject1.getString("id_usuario")));
                                MotoristaConstrList.add(motoristaConstr);
                            }
                            spinner.setAdapter(MotoristaListSpinner);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(EditarVan.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(idEmpresa));

                return params;
            }


        };

        requestQueue2.getCache().clear();
        requestQueue2.add(stringRequest);
    }


}
