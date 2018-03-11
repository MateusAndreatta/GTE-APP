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

    String email,nome,msg,assunto;

   // ProgressDialog progressDialog;

    //TODO: Personalizar o Spinner com a seta e um "escolha o assunto"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Contato GTE");     //Titulo para ser exibido na sua Action Bar em frente à seta
        Button btn = findViewById(R.id.btnEnviarMsg);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Toast.makeText(Contato.this, "Opa escutei isso!", Toast.LENGTH_SHORT).show();

                                       EditText campoMsg = findViewById(R.id.input_mensagem);
                                       msg = campoMsg.getText().toString();

                                       Spinner spinner = findViewById(R.id.spinnerAssuntosContato);
                                       assunto = spinner.getSelectedItem().toString();

//        progressDialog = new ProgressDialog(Contato.this);

                                       // Feedback de enviando msg
                                       // progressDialog.setMessage("Enviando sua mensagem.");
                                       // progressDialog.show();


                                       StringRequest strreq = new StringRequest(Request.Method.POST,
                                               "https://sistemagte.xyz/PagProcessamento/RecebeContato.php",
                                               new Response.Listener<String>() {
                                                   @Override
                                                   public void onResponse(String Response) {

                                                       //    progressDialog.dismiss();

                                                       // Showing response message coming from server.
                                                       Toast.makeText(Contato.this, Response, Toast.LENGTH_LONG).show();
                                                   }
                                               }, new Response.ErrorListener() {
                                           @Override
                                           public void onErrorResponse(VolleyError e) {
                                               e.printStackTrace();
                                               // Hiding the progress dialog after all task complete.
                                               //  progressDialog.dismiss();

                                               // Showing error message if something goes wrong.
                                               Toast.makeText(Contato.this, e.toString(), Toast.LENGTH_LONG).show();
                                           }
                                       }){@Override
                                       public Map<String, String> getParams(){
                                           Map<String, String> params = new HashMap<>();
                                           params.put("nomeAPP", nome);
                                           params.put("emailAPP", email);
                                           params.put("msgAPP", msg);
                                           params.put("assuntoAPP",assunto);
                                           return params;
                                       }
                                       };

                                       RequestQueue rq = Volley.newRequestQueue(Contato.this);
                                       rq.add(strreq);
                                   }
                               }
        );
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



            email = campoEmail.getText().toString();
            nome = campoNome.getText().toString();
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);

            String str1 = getResources().getString(R.string.ola);
            String str2 = getResources().getString(R.string.continuarContato);

            txtT2.setText(str1 + " " + nome.substring(0,1).toUpperCase().concat(nome.substring(1)) + str2);
        }

    }

  /*  public void EnviarMsg (View view){

        EditText campoMsg = findViewById(R.id.input_mensagem);
        msg = campoMsg.getText().toString();

        Spinner spinner = findViewById(R.id.spinnerAssuntosContato);
        assunto = spinner.getSelectedItem().toString();

//        progressDialog = new ProgressDialog(Contato.this);

        // Feedback de enviando msg
       // progressDialog.setMessage("Enviando sua mensagem.");
       // progressDialog.show();


        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://sistemagte.xyz/PagProcessamento/RecebeContato.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {

                    //    progressDialog.dismiss();

                        // Showing response message coming from server.
                        //Toast.makeText(Contato.this, Response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                // Hiding the progress dialog after all task complete.
             //  progressDialog.dismiss();

                // Showing error message if something goes wrong.
                Toast.makeText(Contato.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("nomeAPP", nome);
            params.put("emailAPP", email);
            params.put("msgAPP", msg);
            params.put("assuntoAPP",assunto);
            return params;
        }
        };

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(strreq);
    }

    public void inserir(View v){
        StringRequest request = new StringRequest(Request.Method.POST, "https://sistemagte.xyz/PagProcessamento/RecebeContato.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("sucesso")){
                    //SUCESSO;
                }else{
                    //FALHA
                }
            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error) {
                // error

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("nomeAPP", nome);
                params.put("emailAPP", email);
                params.put("msgAPP", msg);
                params.put("assuntoAPP",assunto);

                return params;
            }
        };
        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(request);
    }*/
}
