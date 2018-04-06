package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.CriancaConst;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 06/04/2018.
 */

public class ListViewCrianca extends ArrayAdapter<CriancaConst>{

    private List<CriancaConst> criancaList;

    private Context mCtx;

    public ListViewCrianca(List<CriancaConst> criancaList, Context mCtx){
        super(mCtx, R.layout.list_view_crianca, criancaList);
        this.criancaList = criancaList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_crianca, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        TextView txtResp = listViewItem.findViewById(R.id.txtResp);

        CriancaConst criancaConst = criancaList.get(position);

        txtNome.setText(criancaConst.getNomeCrianca() + " " + criancaConst.getSobrenomeCrianca());
        txtResp.setText(criancaConst.getResponsavelCrianca());

        return listViewItem;
    }

}
