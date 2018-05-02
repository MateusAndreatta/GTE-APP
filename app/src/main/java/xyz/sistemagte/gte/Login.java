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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



import xyz.sistemagte.gte.Auxiliares.GlobalUser;


public class Login extends AppCompatActivity {

    boolean LabelLogin = false;
    float offsetY;
    TextView txtBottomSheet;
    Button btnLogin;
    EditText inputSenha;
    EditText inputEmail;
    TextView labelRecuperarSenha;

    RequestQueue requestQueue;

    String EmailHolder, SenhaHolder;

    ProgressDialog progressDialog;
    ProgressDialog DialogLogin;

    String HttpUrl = "https://sistemagte.xyz/android/login.php";
    String HttpFacebookLogin = "https://sistemagte.xyz/android/loginFB.php";
    LoginButton loginButton;
    CallbackManager callbackManager;

    private static final String TAG = "ERRO";
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient googleApiClient;

    //Booleans do login
    public boolean LoginNormal,LoginFB,LoginGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(Login.this);
        DialogLogin = new ProgressDialog(Login.this);
        DialogLogin.setMessage("Realizando o Login");

        try {
            CoordinatorLayout llBottomSheet = findViewById(R.id.bottom_sheet);
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(100);// altura que vem como padrao
            bottomSheetBehavior.setHideable(false);// true: ele vem em modo escondido
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        labelRecuperarSenha = findViewById(R.id.labelRecuperarSenha);
        inputEmail = findViewById(R.id.input_email);
        inputSenha = findViewById(R.id.input_senha);
        txtBottomSheet = findViewById(R.id.txtBottomSheet1);
        btnLogin = findViewById(R.id.btnLogin);

        iniciarComponentes();
        loginFacebook();

        //Selecionando oque queremos do nosso login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //Cria o requerimento com as especificações que definimos no GoogleSignInOptions
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

    }

    private void loginFacebook(){
        //progressDialog.setMessage("Carregando seus dados!");
        //progressDialog.show();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess");
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response.getError() != null) {
                            // handle error
                            Toast.makeText(Login.this, "Erro", Toast.LENGTH_SHORT).show();
                        } else {

                            String user_lastname = object.optString("last_name");
                            String user_firstname = object.optString("first_name");
                            String user_email = response.getJSONObject().optString("email");


                            Log.i("Last name: ", user_lastname);
                            Log.i("name: ",user_firstname);
                            Log.i("email: ",user_email);
                            enviarEmailFB(user_email);
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void iniciarComponentes(){
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        callbackManager = CallbackManager.Factory.create();
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
            enviarEmailSenhaBD();
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
                        switch (ServerResponse.trim()){
                            case "UsuarioNaoCadastrado":
                                Toast.makeText(Login.this, getResources().getString(R.string.LoginEmailNaoCadastrado), Toast.LENGTH_SHORT).show();
                                break;

                            case "SenhaIncoreta":
                                Toast.makeText(Login.this, getResources().getString(R.string.LoginSenhaIncorreta), Toast.LENGTH_SHORT).show();
                                break;

                            case "UsuarioSemPermicao":
                                Toast.makeText(Login.this, getResources().getString(R.string.LoginPermissao), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                            //nenhum erro

                                if(Objects.equals(ServerResponse, "UsuarioNaoCadastrado")){
                                    Toast.makeText(Login.this, "Caiu no if", Toast.LENGTH_SHORT).show();
                                }

                                try{
                                    JSONObject jsonObject = new JSONObject(ServerResponse);
                                    JSONObject jsonArray = jsonObject.getJSONObject("nome");
                                    String id = jsonArray.getString("id");
                                    String tipo = jsonArray.getString("tipo");
                                    String nome = jsonArray.getString("nome");
                                    String sobrenome = jsonArray.getString("Sobrenome");
                                    String empresa = jsonArray.getString("id empresa");
                                    String email = jsonArray.getString("email");

                                    GlobalUser global =(GlobalUser)getApplication();
                                    global.setGlobalUserID(Integer.parseInt(id));
                                    global.setGlobalUserTipoUser(Integer.parseInt(tipo));
                                    global.setGlobalUserIdEmpresa(Integer.parseInt(empresa));
                                    global.setGlobalUserNome(nome);
                                    global.setGlobalUserSobrenome(sobrenome);
                                    global.setGlobalUserEmail(email);

                                     switch (tipo){
                                        case("1"):
                                            Intent telaMotorista = new Intent(Login.this, Painel_motorista.class);
                                            startActivity(telaMotorista);
                                            break;
                                        case ("2"):
                                            Intent telaResponsavel = new Intent(Login.this, Painel_responsavel.class);
                                            startActivity(telaResponsavel);
                                            break;
                                        case ("3"):
                                            Intent telaMonitora = new Intent(Login.this, Painel_monitora.class);
                                            startActivity(telaMonitora);
                                            break;
                                        case ("4"):
                                            Intent telaAdm = new Intent(Login.this, Painel_adm.class);
                                            startActivity(telaAdm);
                                            break;
                                        default:
                                            Toast.makeText(Login.this, getResources().getString(R.string.LoginPermissao), Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }catch (Exception ex){
                                    Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }//fechamento do switch
                        progressDialog.dismiss();
                    }//onresponse
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

    private void enviarEmailFB(final String emailFB){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpFacebookLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();
                        switch (ServerResponse.trim()){
                            case "UsuarioNaoCadastrado":
                                Toast.makeText(Login.this, getResources().getString(R.string.LoginEmailNaoCadastrado), Toast.LENGTH_SHORT).show();
                                break;

                            case "UsuarioSemPermicao":
                                Toast.makeText(Login.this, getResources().getString(R.string.LoginPermissao), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                //nenhum erro

                                if(Objects.equals(ServerResponse, "UsuarioNaoCadastrado")){
                                    Toast.makeText(Login.this, "Caiu no if", Toast.LENGTH_SHORT).show();
                                }

                                try{
                                    JSONObject jsonObject = new JSONObject(ServerResponse);
                                    JSONObject jsonArray = jsonObject.getJSONObject("nome");
                                    String id = jsonArray.getString("id");
                                    String tipo = jsonArray.getString("tipo");
                                    String nome = jsonArray.getString("nome");
                                    String sobrenome = jsonArray.getString("Sobrenome");
                                    String empresa = jsonArray.getString("id empresa");
                                    String email = jsonArray.getString("email");

                                    GlobalUser global =(GlobalUser)getApplication();
                                    global.setGlobalUserID(Integer.parseInt(id));
                                    global.setGlobalUserTipoUser(Integer.parseInt(tipo));
                                    global.setGlobalUserIdEmpresa(Integer.parseInt(empresa));
                                    global.setGlobalUserNome(nome);
                                    global.setGlobalUserSobrenome(sobrenome);
                                    global.setGlobalUserEmail(email);

                                    switch (tipo){
                                        case("1"):
                                            Intent telaMotorista = new Intent(Login.this, Painel_motorista.class);
                                            startActivity(telaMotorista);
                                            break;
                                        case ("2"):
                                            Intent telaResponsavel = new Intent(Login.this, Painel_responsavel.class);
                                            startActivity(telaResponsavel);
                                            break;
                                        case ("3"):
                                            Intent telaMonitora = new Intent(Login.this, Painel_monitora.class);
                                            startActivity(telaMonitora);
                                            break;
                                        case ("4"):
                                            Intent telaAdm = new Intent(Login.this, Painel_adm.class);
                                            startActivity(telaAdm);
                                            break;
                                        default:
                                            Toast.makeText(Login.this, getResources().getString(R.string.LoginPermissao), Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }catch (Exception ex){
                                    Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }//fechamento do switch
                    }//onresponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("emailAPP", emailFB);

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

    public void ColocarOutrosLogins(View view) {
        View btns = findViewById(R.id.layoutBtnsLogin);
        TextView texto = findViewById(R.id.labelOptsLogin);

        if(!LabelLogin){
            btns.setVisibility(View.VISIBLE);
            texto.setText(R.string.mostrarMenos);


            Drawable imagem = getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
            imagem.setBounds( 0, 0, 60, 60 );
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                texto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, imagem, null);
            }
            LabelLogin = true;
        }else{
            Drawable imagem = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
            imagem.setBounds( 0, 0, 60, 60 );
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                texto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, imagem, null);
            }
            btns.setVisibility(View.GONE);
            texto.setText(R.string.login_opts);
            LabelLogin = false;
        }


    }


    //Metodos para login com o google

    @Override
    protected void onStart() {
        super.onStart();
        //Verificar se o usuario não está logado
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        account = null;//forcando o usuario a deslogar
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        //se receber NULL o usuario nao está logado
        if(!(account == null)){         //Usuario está logado
            enviarEmailFB(account.getEmail());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            enviarEmailFB(account.getEmail());
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}

