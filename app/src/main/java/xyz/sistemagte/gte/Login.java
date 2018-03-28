package xyz.sistemagte.gte;

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
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    float offsetY;
    TextView txtBottomSheet;
    Button btnLogin;
    EditText inputSenha;
    EditText inputEmail;
    TextView labelRecuperarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

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
            Intent Tela = new Intent(this, Painel_adm.class);
            startActivity(Tela);
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

