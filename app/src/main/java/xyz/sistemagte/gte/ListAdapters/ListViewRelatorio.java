package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import xyz.sistemagte.gte.Auxiliares.DefinicoesPresenca;
import xyz.sistemagte.gte.Construtoras.RelatorioRespConstr;
import xyz.sistemagte.gte.Construtoras.ResponsavelConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Liver on 30/06/2018.
 */

public class ListViewRelatorio extends ArrayAdapter<RelatorioRespConstr> {

    private List<RelatorioRespConstr> respList;
    private Context mCtx;
    private String dataAtual;
    boolean vEntrouVan = false,vChegouEscola = false,vSaiuEscola = false,vChegouCasa = false;

    public ListViewRelatorio(List<RelatorioRespConstr> respList, Context mCtx){
        super(mCtx, R.layout.list_view_presenca, respList);
        this.respList = respList;
        this.mCtx = mCtx;

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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_presenca, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNomeCrianca);
        TextView txtCpf = listViewItem.findViewById(R.id.Status);

        RelatorioRespConstr respCostr = respList.get(position);
        txtCpf.setText(mCtx.getResources().getString(R.string.status) +": " + mCtx.getString(R.string.StatusNaoDefinido));
        if(respCostr.getHora_entrada() != "null"){
            if(ValidaDatas(FormataData(respCostr.getHora_entrada()))){
                vEntrouVan = true;
                txtCpf.setText(mCtx.getResources().getString(R.string.status) +": " + mCtx.getString(R.string.StatusIndoEscola));
            }
        }
        if(respCostr.getHora_escola() != "null"){
            if(ValidaDatas(FormataData(respCostr.getHora_escola()))){
               vChegouEscola = true;
                txtCpf.setText(mCtx.getResources().getString(R.string.status) +": " + mCtx.getString(R.string.StatusNaEscola));
            }
        }
        if(respCostr.getHora_saida() != "null"){
            if(ValidaDatas(FormataData(respCostr.getHora_saida()))){
                vSaiuEscola = true;
                txtCpf.setText(mCtx.getResources().getString(R.string.status) +": " + mCtx.getString(R.string.StatusRetornandoCasa));
            }
        }
        if(respCostr.getHora_casa() != "null"){
            if(ValidaDatas(FormataData(respCostr.getHora_casa()))){
                vChegouCasa = true;
                txtCpf.setText(mCtx.getResources().getString(R.string.status) +": " + mCtx.getString(R.string.StatusEmCasa));
            }
        }

      // if(!vEntrouVan && !vChegouCasa && !vSaiuEscola && !vChegouEscola){
      //     txtCpf.setText(mCtx.getResources().getString(R.string.status) +": " + "Não definido");
      // }

        txtNome.setText(respCostr.getNome_crianca());



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
        System.out.println("RECEBENDO - " + data);
        System.out.println("Data hoje - " +dataAtual);
        if(data.equals(dataAtual)){
            System.out.println(data + " - " + dataAtual);
            return true;
        }else{
            return false;
        }
    }
}
