package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import xyz.sistemagte.gte.Auxiliares.DefinicoesPresenca;
import xyz.sistemagte.gte.Construtoras.CheckStatusConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 14/06/2018.
 */

public class ListViewCheck extends ArrayAdapter<CheckStatusConstr> {

    int idCrianca;
    private List<CheckStatusConstr> CheckList;
    private List<CheckStatusConstr> orig;
    private Context mCtx;
    private String dataAtual;
    int botao;
    //String idWebService;

    DefinicoesPresenca volley;

    public ListViewCheck(List<CheckStatusConstr> checkList, Context mCtx) {
        super(mCtx, R.layout.list_view_escolas, checkList);
        this.CheckList = checkList;
        this.mCtx = mCtx;

        GregorianCalendar calendar = new GregorianCalendar();
        String dia = String.valueOf(calendar.get(GregorianCalendar.DAY_OF_MONTH));
        String mes = String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1);
        String ano = String.valueOf(calendar.get(GregorianCalendar.YEAR));
        if(mes.length() == 1){
            mes = "0"+mes;
        }
        this.dataAtual = dia + "/" + mes + "/" + ano;
        volley = new DefinicoesPresenca();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_checklist, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        final TextView id = listViewItem.findViewById(R.id.txtId);
        final Button btn = listViewItem.findViewById(R.id.btnCheck);

        final CheckStatusConstr check = CheckList.get(position);
        idCrianca = check.getIdCriancaCheck();
        txtNome.setText(check.getNomeCheck() + " " + check.getSobrenomeCheck());
        id.setText(String.valueOf(check.getIdCriancaCheck()));
       // idWebService = String.valueOf(id.getText());
        btn.setText(R.string.monitoraCheckEntrouVan);
        if(check.getHoraEscolaCheck() != "null"){
            if(ValidaDatas(FormataData(check.getHoraEscolaCheck()))){
                btn.setText(R.string.monitoraCheckChegouEscola);
            }
        }
        if(check.getHoraSaidaCheck() != "null"){
            if(ValidaDatas(FormataData(check.getHoraSaidaCheck()))){
                btn.setText(R.string.monitoraCheckSaiuEscola);
            }
        }
        if(check.getHoraCasaCheck() != "null"){
            if(ValidaDatas(FormataData(check.getHoraCasaCheck()))){
                btn.setText(R.string.monitoraCheckChegouCasa);
            }
        }


        System.out.println(check.getHoraEntradaCheck());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = String.valueOf(btn.getText());

                if(btnText.equals(mCtx.getString(R.string.monitoraCheckChegouCasa))){
                    //TODO: Desativar btn
                    check.setHoraCasa(GerarTimeStamp());
                    volley.ChegouCasa(String.valueOf(id.getText()),mCtx);
                }else{
                    if(btnText.equals(mCtx.getString(R.string.monitoraCheckSaiuEscola))){
                        check.setHoraSaida(GerarTimeStamp());
                        volley.RetornouVan(String.valueOf(id.getText()),mCtx);
                        btn.setText(mCtx.getString(R.string.monitoraCheckChegouCasa));
                    }else{
                        if(btnText.equals(mCtx.getString(R.string.monitoraCheckChegouEscola))){
                            check.setHoraEscola(GerarTimeStamp());
                            volley.ChegouEscola(String.valueOf(id.getText()),mCtx);
                            btn.setText(mCtx.getString(R.string.monitoraCheckSaiuEscola));
                        }else{
                            //Nem entrou na vans
                            check.setHoraEntrada(GerarTimeStamp());
                            volley.EntrouNaVan(String.valueOf(id.getText()),mCtx);
                            btn.setText(mCtx.getString(R.string.monitoraCheckChegouEscola));
                        }
                    }
                }
            }
        });

        return listViewItem;
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

    //2018-06-18 15:36:57
    private String GerarTimeStamp(){
        GregorianCalendar calendar = new GregorianCalendar();
        String dia = String.valueOf(calendar.get(GregorianCalendar.DAY_OF_MONTH));
        String mes = String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1);
        String ano = String.valueOf(calendar.get(GregorianCalendar.YEAR));

        String hora = String.valueOf(calendar.get(GregorianCalendar.HOUR_OF_DAY));
        String minuto = String.valueOf(calendar.get(GregorianCalendar.MINUTE));
        String seg = String.valueOf(calendar.get(GregorianCalendar.SECOND));
        if(mes.length() == 1){
            mes = "0"+mes;
        }
        String timestamp = "TIMESTAMP: " + ano + "-" + mes + "-" + dia + " " +hora + ":" + minuto + ":" + seg;
        return timestamp;
    }

    @Override
    public int getCount() {
        return CheckList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<CheckStatusConstr> results = new ArrayList<CheckStatusConstr>();
                if (orig == null) {
                    orig = CheckList;
                }
                if (constraint != null) {
                    constraint = constraint.toString().toLowerCase();
                    //verifica se temos algo na lista original
                    if (orig != null && orig.size() > 0) {
                        //para cada obj do UsuarioConst dentro lista
                        for (final CheckStatusConstr g : orig) {//Ocorerá para cada usuario que está na lista
                            if ((g.getNomeCheck().toLowerCase().contains(constraint.toString())) ||
                                    g.getSobrenomeCheck().toLowerCase().contains(constraint.toString())) {
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                CheckList = (ArrayList<CheckStatusConstr>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}