package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.RelatorioRespConstr;
import xyz.sistemagte.gte.Construtoras.ResponsavelConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Liver on 30/06/2018.
 */

public class ListViewRelatorio extends ArrayAdapter<RelatorioRespConstr> {

    private List<RelatorioRespConstr> respList;
    private Context mCtx;

    public ListViewRelatorio(List<RelatorioRespConstr> respList, Context mCtx){
        super(mCtx, R.layout.list_view_presenca, respList);
        this.respList = respList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_presenca, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNomeCrianca);
        TextView txtCpf = listViewItem.findViewById(R.id.Status);

        RelatorioRespConstr respCostr = respList.get(position);

        txtNome.setText(respCostr.getNome_crianca());
        txtCpf.setText(mCtx.getResources().getString(R.string.status) +": ");


        return listViewItem;
    }
}
