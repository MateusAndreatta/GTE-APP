package xyz.sistemagte.gte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class cad_van extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_van);
    }


    public void cadastrarVan(View view) {
        Toast.makeText(this, "Não disponivel no momento!", Toast.LENGTH_SHORT).show();
    }
}
