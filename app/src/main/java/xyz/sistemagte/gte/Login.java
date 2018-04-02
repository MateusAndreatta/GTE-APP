package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    float offsetY;
    TextView txtBottomSheet;
    Button btnLogin;
    EditText inputSenha;
    EditText inputEmail;
    TextView labelRecuperarSenha;

    RequestQueue requestQueue;

    String EmailHolder, SenhaHolder;

    ProgressDialog progressDialog;

    String HttpUrl = "https://sistemagte.xyz/android/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(Login.this);

        try{
            CoordinatorLayout llBottomSheet = findViewById(R.id.bottom_sheet);
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(100);// altura que vem como padrao
            bottomSheetBehavior.setHideable(false);// true: ele vem em modo escondido
       }catch (Exception ex){
           Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
       }
        labelRecuperarSenha = findViewById(R.id.labelRecuperarSenha);
        inputEmail = findViewById(R.id.input_email);
        inputSenha = findViewById(R.id.input_senha);
        txtBottomSheet = findViewById(R.id.txtBottomSheet1);
        btnLogin = findViewById(R.id.btnLogin);
    }


    public void irSenha (View v){
        Intent Tela = new Intent(this, RecuperarSenha.class);
        startActivity(Tela);
    }

    public void Logar(View view) {
        EditText campoEmail = findViewById(R.id.input_email);
        EditText campoSenha = findViewById(R.id.input_senha);
        if(campoEmail.getText().length() == 0 || campoSenha.getText().length() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
        }else {
            //TODO: fazer a comparação do login com o banco
            enviarEmailSenhaBD();
          //  Intent Tela = new Intent(this, Painel_adm.class);
          //  startActivity(Tela);
        }
    }

    public void irContato(View view) {
        Intent Tela = new Intent(this, Contato.class);
        startActivity(Tela);
    }

    public void irCadastro(View view){
        Intent Tela = new Intent(this, Cadastro.class);
        startActivity(Tela);
    }

    private void enviarEmailSenhaBD(){
        // Showing progress dialog at user registration time.
        progressDialog.setMessage(getResources().getString(R.string.loadingLogin));
        progressDialog.show();

        // Calling method to get value from EditText.
        GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        if(ServerResponse.equals("erroEmailSenha")){
                            Toast.makeText(Login.this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
                        }else if(ServerResponse.equals("UsuarioSemPermicao")){
                            Toast.makeText(Login.this, "Usuario sem permissão", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Login.this, ServerResponse, Toast.LENGTH_SHORT).show();
                            /*try{
                                JSONObject jsonObject = new JSONObject(ServerResponse);
                                Toast.makeText(Login.this, jsonObject.getString("nome"), Toast.LENGTH_LONG).show();
                            }catch (Exception ex){
                                Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }*/
                        }

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("emailAPP", EmailHolder);
                params.put("senhaAPP", SenhaHolder);

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    public void GetValueFromEditText(){
        SenhaHolder = inputSenha.getText().toString();
        EmailHolder = inputEmail.getText().toString().trim();
    }

    //Bottom sheet
    @Override
    protected void onResume() {
        super.onResume();
        this.init();
    }

    private void init() {

        offsetY = 0;
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //Trocar o icone
                if (offsetY < slideOffset) {
                    //Fazendo o slide pra cima
                    Drawable imagem = getResources().getDrawable(R.drawable.ic_expand_more_white_24dp);
                    imagem.setBounds( 0, 0, 60, 60 );
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        txtBottomSheet.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, imagem, null);
                    }
                    btnLogin.setEnabled(false);
                    inputSenha.setEnabled(false);
                    inputEmail.setEnabled(false);
                    labelRecuperarSenha.setEnabled(false);
                } else if (offsetY > slideOffset) {
                    //Fazendo o slide pra baixo
                    Drawable imagem = getResources().getDrawable(R.drawable.ic_expand_less_white_24dp);
                    imagem.setBounds( 0, 0, 60, 60 );
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        txtBottomSheet.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, imagem, null);
                    }
                    btnLogin.setEnabled(true);
                    inputSenha.setEnabled(true);
                    inputEmail.setEnabled(true);
                    labelRecuperarSenha.setEnabled(true);
                }
                offsetY = slideOffset;
            }
        });



    }

}

