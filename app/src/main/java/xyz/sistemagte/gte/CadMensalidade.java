package xyz.sistemagte.gte;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.Construtoras.MensalidadeConst;

public class CadMensalidade extends AppCompatActivity {

    private int idEmpresa, idResp;

    EditText valor, dtVencimento;
    Spinner resp;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "MUDAR";

    ArrayAdapter<String> RespListSpinner;
    ArrayList<MensalidadeConst> MensalidadeConst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_mensalidade);

        setContentView(R.layout.activity_cad_mensalidade);
        resp = findViewById(R.id.responsaveis);

        valor = findViewById(R.id.cad_valor);
        dtVencimento = findViewById(R.id.cad_dt_vencimento);

        requestQueue = Volley.newRequestQueue(this);
        /*resp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        resp = new ArrayList<>();*/
        progressDialog = new ProgressDialog(CadMensalidade.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.cadMensalidade));

        //aplica mascara
        MaskEditTextChangedListener mascaraData  = new MaskEditTextChangedListener("##/##/####",dtVencimento);


        dtVencimento.addTextChangedListener(mascaraData);

        GlobalUser global =(GlobalUser)getApplication();
        idEmpresa = global.getGlobalUserIdEmpresa();

       

    }

    public void Cadastrar(View view) {
    }
}
