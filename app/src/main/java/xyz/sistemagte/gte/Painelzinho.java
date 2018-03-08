package xyz.sistemagte.gte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Painelzinho extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painelzinho);
    }

    public void irLista(View view){
        Intent tela = new Intent(this, Listagem.class);
        startActivity(tela);
    }

    public void irCad(View view){
        Intent tela = new Intent(this, CRUD.class);
        startActivity(tela);
    }
    public void irEdit(View view){
        Intent tela = new Intent(this, CRUD_editar.class);
        startActivity(tela);
    }

    public void irDel(View view) {
        Intent tela = new Intent(this, CRUD_deletar.class);
        startActivity(tela);
    }
}
