package xyz.sistemagte.gte;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {


    int ProximaTela;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastro GTE");     //Titulo para ser exibido na sua Action Bar em frente à seta

        final Button cadastrar = (Button) findViewById(R.id.cadastro);
        Spinner sp1      = (Spinner) findViewById(R.id.cad_tipousuario);


        /**
         * 1 - Motorista
         * 2 - Monitora
         * 3 - Responsavel
         * */
        //Array do spinner tipo Usuario
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipousuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View arg1, int pos, long id) {
                switch(pos){
                    case (1):
                        cadastrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view){

                                ProximaTela = 1;
                            }
                        });
                        break;
                    case (2):
                        cadastrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view){

                                ProximaTela = 2;
                            }
                        });
                        break;
                    case (3):
                        cadastrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view){

                                ProximaTela = 3;
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> spinner) {
            }
        });

    }
    //este é para o da navbar (seta)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    public void Cadastrar(View view) {
        if(ProximaTela == 1){
          Intent intent = new Intent(Cadastro.this, cad_motorista.class);
          startActivity(intent);
        }
        if(ProximaTela == 2){
            Intent intent = new Intent(Cadastro.this, cad_monitora.class);
            startActivity(intent);
        }
        if(ProximaTela == 3){
     //     SQLiteDatabase db;
     //      db = openOrCreateDatabase( "siste370_bd", SQLiteDatabase.OPEN_READWRITE, null);
     //      try {
     //          String sql =
     //"INSERT or replace INTO tbl_Contain (DESCRIPTION, expirydate, AMOUNT, TRNS,isdefault) VALUES('this is','03/04/2005','5000','tran','y')" ;
     //          db.execSQL(sql);
     //      }
     //      catch (Exception e) {
     //          Toast.makeText(this, "ERROR "+e.toString(), Toast.LENGTH_LONG).show();
     //      }
        }
    }

}
