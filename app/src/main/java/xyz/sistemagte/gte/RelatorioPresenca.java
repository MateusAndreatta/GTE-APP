package xyz.sistemagte.gte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.SearchView;

import xyz.sistemagte.gte.Auxiliares.GlobalUser;
import xyz.sistemagte.gte.Construtoras.MensalidadeConst;
import xyz.sistemagte.gte.Construtoras.RelatorioRespConstr;
import xyz.sistemagte.gte.ListAdapters.ListViewMensalidadesResp;
import xyz.sistemagte.gte.ListAdapters.ListViewRelatorio;

public class RelatorioPresenca extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static String JSON_URL = "https://sistemagte.xyz/json/RelatorioResponsavel.php";

    ListView listView;
    private int idUsuario;
    List<RelatorioRespConstr> RelatoriopresencaList;
    List<RelatorioRespConstr> listaQuery;
    AlertDialog alerta;
    SearchView searchView;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    private String dataAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_presenca);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        GlobalUser global =(GlobalUser)getApplication();
        idUsuario = global.getGlobalUserID();
        searchView = findViewById(R.id.barra_pesquisa);
        listView = findViewById(R.id.listView);
        RelatoriopresencaList = new ArrayList<>();
        listaQuery = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getResources().getString(R.string.relatorio));     //Titulo para ser exibido na sua Action Bar em frente à seta

        GregorianCalendar calendar = new GregorianCalendar();
        String dia = String.valueOf(calendar.get(GregorianCalendar.DAY_OF_MONTH));
        String mes = String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1);
        String ano = String.valueOf(calendar.get(GregorianCalendar.YEAR));
        if(mes.length() == 1){
            mes = "0"+mes;
        }
        if(dia.length() == 1){
            dia = "0"+dia;
        }
        this.dataAtual = dia + "/" + mes + "/" + ano;

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(RelatorioPresenca.this);

        loadCriancas();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                RelatorioRespConstr relatorio = listaQuery.get(position);
                String HoraEntrada = getResources().getString(R.string.StatusNaoDefinido);
                String HoraCasa = getResources().getString(R.string.StatusNaoDefinido);
                String HoraEscola = getResources().getString(R.string.StatusNaoDefinido);
                String HoraSaida = getResources().getString(R.string.StatusNaoDefinido);

                if(relatorio.getHora_entrada() != "null"){
                    if(ValidaDatas(FormataData(relatorio.getHora_entrada()))){
                        HoraEntrada = getResources().getString(R.string.hj) + " " + relatorio.getHora_entrada().substring(11,16);
                    }
                }
                if(relatorio.getHora_escola() != "null"){
                    if(ValidaDatas(FormataData(relatorio.getHora_escola()))){
                        HoraEscola = getResources().getString(R.string.hj) + " " + relatorio.getHora_escola().substring(11,16);
                    }
                }
                if(relatorio.getHora_saida() != "null"){
                    if(ValidaDatas(FormataData(relatorio.getHora_saida()))){
                        HoraSaida = getResources().getString(R.string.hj) + " " + relatorio.getHora_escola().substring(11,16);
                    }
                }
                if(relatorio.getHora_casa() != "null"){
                    if(ValidaDatas(FormataData(relatorio.getHora_casa()))){
                        HoraCasa = getResources().getString(R.string.hj) + " " + relatorio.getHora_casa().substring(11,16);
                    }
                }
                String finalMsg = getResources().getString(R.string.horaEntrada) + " " + HoraEntrada
                        + "\n" +  getResources().getString(R.string.horaEscola) + " " + HoraEscola
                        + "\n" +  getResources().getString(R.string.horaSaida) + " " + HoraSaida
                        + "\n" +  getResources().getString(R.string.horaCasa) + " " + HoraCasa;

                AlertDialog.Builder builder = new AlertDialog.Builder(RelatorioPresenca.this);
                builder.setTitle(getResources().getString(R.string.horarios));
                builder.setMessage(finalMsg);
                builder.setPositiveButton(getResources().getString(R.string.fechar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        alerta.hide();
                    }
                });

                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();

            }
        });

        setupSearchView();
    }

    private String FormataData(String dt){
        String dia2= dt;
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition position2 = new ParsePosition(0);
        Date data2 = format2.parse(dia2,position2);
        format2 = new SimpleDateFormat("dd/MM/yyyy");
        String date2 = format2.format(data2);
        return date2;
    }

    private boolean ValidaDatas(String data){
        if(data.equals(dataAtual)){
            System.out.println(data + " - " + dataAtual);
            return true;
        }else{
            return false;
        }
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);// definir se seria usado o icone ou o campo inteiro
        searchView.setOnQueryTextListener(this);//passagem do contexto para usar o searchview
        searchView.setSubmitButtonEnabled(false);//Defini se terá ou nao um o botao de submit
        searchView.setQueryHint(getString(R.string.pesquisarSearchPlaceholder));//Placeholder da searchbar
    }

    private void loadCriancas() {
        RelatoriopresencaList.clear();
        // Showing progress dialog at user registration time.
        progressDialog.setMessage(getResources().getString(R.string.loadingRegistros));
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("nome");

                            for (int i = 0; i < funcArray.length(); i++) {
                                JSONObject funcObject = funcArray.getJSONObject(i);

                                RelatorioRespConstr funcConst = new RelatorioRespConstr (funcObject.getString("crianca"),funcObject.getString("hora_entrada"),funcObject.getString("hora_escola"),funcObject.getString("hora_saida"),funcObject.getString("hora_casa"));
                                RelatoriopresencaList.add(funcConst);
                                listaQuery.add(funcConst);
                            }

                            ListViewRelatorio adapter = new ListViewRelatorio(RelatoriopresencaList, getApplicationContext());

                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(RelatorioPresenca.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(idUsuario));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onQueryTextChange(String newText){
        listaQuery.clear();
        if (TextUtils.isEmpty(newText)) {
            listaQuery.addAll(RelatoriopresencaList);
        } else {
            String queryText = newText.toLowerCase();
            for(RelatorioRespConstr obj : RelatoriopresencaList){
                if(obj.getNome_crianca().toLowerCase().contains(queryText)) {
                    listaQuery.add(obj);
                }
            }
        }
        listView.setAdapter(new ListViewRelatorio(listaQuery, RelatorioPresenca.this));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Painel_responsavel.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    //O botao padrao do android
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Painel_responsavel.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
