package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Contato extends AppCompatActivity {

    // Creating EditText.
    EditText Nome, Email,Mensagem ;
    Spinner Assunto;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String NomeHolder, EmailHolder, MensagemHolder , AssuntoHolder;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "https://sistemagte.xyz/PagProcessamento/RecebeContato.php";

    //TODO: Personalizar o Spinner com a seta e um "escolha o assunto"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Contato GTE");     //Titulo para ser exibido na sua Action Bar em frente à seta

        Nome     = findViewById(R.id.input_nome);
        Email    = findViewById(R.id.input_email);
        Mensagem = findViewById(R.id.input_mensagem);
        Assunto  = findViewById(R.id.spinnerAssuntosContato);


        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(Contato.this);


        Button btn = findViewById(R.id.btnEnviarMsg);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       // Showing progress dialog at user registration time.
                                       progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                                       progressDialog.show();

                                       // Calling method to get value from EditText.
                                       GetValueFromEditText();

                                       // Creating string request with post method.
                                       StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                                               new Response.Listener<String>() {
                                                   @Override
                                                   public void onResponse(String ServerResponse) {

                                                       // Hiding the progress dialog after all task complete.
                                                       progressDialog.dismiss();

                                                       // Showing response message coming from server.
                                                       Toast.makeText(Contato.this, ServerResponse, Toast.LENGTH_LONG).show();
                                                   }
                                               },
                                               new Response.ErrorListener() {
                                                   @Override
                                                   public void onErrorResponse(VolleyError volleyError) {

                                                       // Hiding the progress dialog after all task complete.
                                                       progressDialog.dismiss();

                                                       // Showing error message if something goes wrong.
                                                       Toast.makeText(Contato.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                                   }
                                               }) {
                                           @Override
                                           protected Map<String, String> getParams() {

                                               // Creating Map String Params.
                                               Map<String, String> params = new HashMap<String, String>();

                                               // Adding All values to Params.
                                               params.put("nomeAPP", NomeHolder);
                                               params.put("emailAPP", EmailHolder);
                                               params.put("msgAPP", MensagemHolder);
                                               params.put("assuntoAPP", AssuntoHolder);

                                               return params;
                                           }

                                       };

                                       requestQueue.getCache().clear();
                                       requestQueue.add(stringRequest);

                                   }
                               }
        );
    }


    // Creating method to get value from EditText.
    public void GetValueFromEditText(){

        NomeHolder = Nome.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        MensagemHolder = Mensagem.getText().toString();
        AssuntoHolder = Assunto.getSelectedItem().toString();

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

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Login.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }


    public void irProximoContato(View view) {
        EditText campoEmail = (EditText)findViewById(R.id.input_email);
        EditText campoNome = (EditText)findViewById(R.id.input_nome);
        if(campoEmail.getText().length() == 0 || campoNome.getText().length() == 0){
            Toast.makeText(this, getResources().getString(R.string.verificarCampos), Toast.LENGTH_SHORT).show();
        }else{
            LinearLayout layout2 = (LinearLayout) findViewById(R.id.Contato_Layout2);
            LinearLayout layout1 = (LinearLayout) findViewById(R.id.Contato_Layout1);
            TextView txtT2 = (TextView)findViewById(R.id.textViewContatoT2);


            String nomeP = campoNome.getText().toString();
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);

            String str1 = getResources().getString(R.string.ola);
            String str2 = getResources().getString(R.string.continuarContato);

            txtT2.setText(str1 + " " + nomeP.substring(0,1).toUpperCase().concat(nomeP.substring(1)) + str2);
        }

    }

}
