package xyz.sistemagte.gte;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Login extends AppCompatActivity {

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
            bottomSheetBehavior.setPeekHeight(110);// altura que vem como padrao
            bottomSheetBehavior.setHideable(false);// true: ele vem em modo escondido

       }catch (Exception ex){
           Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
       }
    }
    //TODO: criar o btn de contato no bottom sheet

    public void irSenha (View v){
        //TODO: trocar o intent para a de esqueci minha senha
        Intent Tela = new Intent(this, Contato.class);
        startActivity(Tela);
    }

    public void Logar(View view) {
        EditText campoEmail = (EditText)findViewById(R.id.input_email);
        EditText campoSenha = (EditText)findViewById(R.id.input_senha);
        if(campoEmail.getText().length() == 0 || campoSenha.getText().length() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
        }else {
            //TODO: fazer a comparação do login com o banco
            Intent Tela = new Intent(this, Painel_adm.class);
            startActivity(Tela);
        }
    }

    public void Contato(View view) {
        Intent Tela = new Intent(this, Contato.class);
        startActivity(Tela);
    }

    public void irCadastro(View view){
        Intent Tela = new Intent(this, Cadastro.class);
        startActivity(Tela);
    }

}

