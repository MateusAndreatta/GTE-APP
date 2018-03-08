package xyz.sistemagte.gte;

import android.app.ProgressDialog;
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


import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class CRUD extends AppCompatActivity {

    String ServerURL = "https://sistemagte.xyz/android/get_data.php";
    EditText nome, email;
    Button button;
    String TempNome, TempEmail;


    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        nome = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText3);
        button = (Button) findViewById(R.id.button);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(CRUD.this);

        progressDialog = new ProgressDialog(CRUD.this);

    }

    public void GetData() {

        TempNome = nome.getText().toString();

        TempEmail = email.getText().toString();

    }

    public void enviar(View view) {
        GetData();

        InsertByVolley(TempNome, TempEmail);
    }


    public void InsertByVolley(String nome, String email) {


        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Calling method to get value from EditText.
        GetData();

        final String NameHolder = nome;
        final String EmailHolder = email;
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(CRUD.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(CRUD.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nome", NameHolder);
                params.put("email", EmailHolder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(CRUD.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

}