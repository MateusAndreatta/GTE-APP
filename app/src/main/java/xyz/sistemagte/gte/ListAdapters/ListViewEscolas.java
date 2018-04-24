package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.AcessosConst;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Aluno on 24/04/2018.
 */

public class ListViewEscolas extends ArrayAdapter<EscolasConstr> {

    private List<EscolasConstr> EscolaList;

    private Context mCtx;

    public ListViewEscolas(List<EscolasConstr> EscolaList, Context mCtx){
        super(mCtx, R.layout.list_view_escolas, EscolaList);
        this.EscolaList = EscolaList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_escolas, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        TextView txtEnd = listViewItem.findViewById(R.id.txtTipoAndData);

        EscolasConstr escolasConstr = EscolaList.get(position);

        txtNome.setText(escolasConstr.getNomeAcessos() + " " + escolasConstr.getSobrenomeAcessos());
        txtEnd.setText(escolasConstr.getTipoAcessos() + " • " + escolasConstr.getDataAcessos());

        return listViewItem;
    }
}
