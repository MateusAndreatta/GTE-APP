package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Auxiliares.Validacoes;
import xyz.sistemagte.gte.Construtoras.ResponsavelConstr;
import xyz.sistemagte.gte.ListAdapters.ListViewResp;

public class EditarResponsavelAdm extends AppCompatActivity {

    private static String JsonDados = "https://sistemagte.xyz/json/adm/ListarDadosResp.php";
    private static String URL_Cadastro = "https://sistemagte.xyz/android/editar/editarResponsavel.php";
    private int idResp;
    private int idEmpresa;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    EditText campo_nome,campo_sobrenome,campo_email,campo_telefone,campo_rg,campo_cpf,campo_dataNasc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_responsavel_adm);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        campo_nome = findViewById(R.id.edit_nome);
        campo_sobrenome = findViewById(R.id.edit_sobrenome);
        campo_email = findViewById(R.id.edit_email);
        campo_telefone = findViewById(R.id.edit_tel);
        campo_rg = findViewById(R.id.edit_rg);
        campo_cpf = findViewById(R.id.edit_cpf);
        campo_dataNasc = findViewById(R.id.edit_datanascimento);

        MaskEditTextChangedListener mascaraCPF = new MaskEditTextChangedListener("###.###.###-##",campo_cpf);
        MaskEditTextChangedListener mascaraCelular = new MaskEditTextChangedListener("(##) #####-####",campo_telefone);
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",campo_dataNasc);
        MaskEditTextChangedListener mascaraRG  = new MaskEditTextChangedListener("#.###.###-#",campo_rg);

        campo_cpf.addTextChangedListener(mascaraCPF);
        campo_telefone.addTextChangedListener(mascaraCelular);
        campo_dataNasc.addTextChangedListener(mascaraData);
        campo_rg.addTextChangedListener(mascaraRG);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.editarResponsavel));     //Titulo para ser exibido na sua Action Bar em frente à seta

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(EditarResponsavelAdm.this);

        Intent i = getIntent();
        idResp = Integer.parseInt(i.getStringExtra("idR"));
        PuxarDados();
    }

    private void PuxarDados(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonDados,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = obj.getJSONArray("nome");
                            JSONObject jo = jsonArray.getJSONObject(0);

                            String dia = jo.getString("dt_nasc");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            ParsePosition position = new ParsePosition(0);
                            Date data = format.parse(dia,position);
                            format = new SimpleDateFormat("dd/MM/yyyy");
                            String date = format.format(data);

                            campo_nome.setText(jo.getString("nome"));
                            campo_sobrenome.setText(jo.getString("sobrenome"));
                            campo_email.setText(jo.getString("email"));
                            campo_telefone.setText(jo.getString("tel_cel"));
                            campo_rg.setText(jo.getString("rg"));
                            campo_cpf.setText(jo.getString("cpf"));
                            campo_dataNasc.setText(date);

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EditarResponsavelAdm.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            System.out.println(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                        Toast.makeText(EditarResponsavelAdm.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("idUsuario", String.valueOf(idResp));
                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Responsavel.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private boolean ValidarCampos(){
        if(
            campo_nome.getText().length() == 0 ||
            campo_sobrenome.getText().length() == 0 ||
            campo_email.getText().length() == 0 ||
            campo_telefone.getText().length() == 0 ||
            campo_rg.getText().length() == 0 ||
            campo_cpf.getText().length() == 0 ||
            campo_dataNasc.getText().length() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }


    public void Editar(View view) {
        if (ValidarCampos()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Cadastro,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            Toast.makeText(EditarResponsavelAdm.this, getResources().getString(R.string.informacoesSalvasSucesso), Toast.LENGTH_SHORT).show();
                            Intent tela = new Intent(EditarResponsavelAdm.this, Responsavel.class);
                            startActivity(tela);
                            System.out.println(ServerResponse);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(EditarResponsavelAdm.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nome", campo_nome.getText().toString());
                    params.put("sobrenome", campo_sobrenome.getText().toString());
                    params.put("email", campo_email.getText().toString());
                    params.put("telefone", campo_telefone.getText().toString());
                    params.put("cpf", campo_cpf.getText().toString());
                    params.put("rg", campo_rg.getText().toString());
                    params.put("nascimento", campo_dataNasc.getText().toString());
                    params.put("id", String.valueOf(idResp));

                    return params;
                }

            };

            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }

    }
}
