package xyz.sistemagte.gte;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
    }
    //TODO: criar o btn de contato

    public void irSenha (View v){
        //TODO: trocar o intent para a de esqueci minha senha
        Intent Tela = new Intent(this, Contato.class);
        startActivity(Tela);
    }
}
