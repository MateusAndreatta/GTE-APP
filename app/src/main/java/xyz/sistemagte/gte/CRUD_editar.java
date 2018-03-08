package xyz.sistemagte.gte;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CRUD_editar extends AppCompatActivity {

    String ServerURL = "https://sistemagte.xyz/android/get_data_update.php" ;
    EditText nome, email, id ;
    Button button;
    String TempNome, TempEmail,TempID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_editar);

        id = (EditText)findViewById(R.id.editText1);
        nome = (EditText)findViewById(R.id.editText2);
        email = (EditText)findViewById(R.id.editText3);
        button = (Button)findViewById(R.id.button);
    }

    public void enviar(View view) {
        GetData();

        InsertData(TempID,TempNome, TempEmail);

    }

    public void GetData(){

        TempNome = nome.getText().toString();

        TempEmail = email.getText().toString();

        TempID = id.getText().toString();

    }

    public void InsertData(final String id, final String nome, final String email){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String idHolder = id;
                String NameHolder = nome ;
                String EmailHolder = email ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("id", idHolder));
                nameValuePairs.add(new BasicNameValuePair("nome", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("email", EmailHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(CRUD_editar.this, "Editado com sucesso", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(id ,nome, email);
    }
}
