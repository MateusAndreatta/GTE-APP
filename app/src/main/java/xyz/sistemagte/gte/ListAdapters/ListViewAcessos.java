package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.AcessosConst;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 07/04/2018.
 */

public class ListViewAcessos extends ArrayAdapter<AcessosConst>{


    private List<AcessosConst> AcessoList;

    private Context mCtx;

    public ListViewAcessos(List<AcessosConst> AcessoList, Context mCtx){
        super(mCtx, R.layout.list_view_acesso, AcessoList);
        this.AcessoList = AcessoList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_acesso, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        TextView txtTipoData = listViewItem.findViewById(R.id.txtTipoAndData);

        AcessosConst acessosConst = AcessoList.get(position);

        txtNome.setText(acessosConst.getNomeAcessos() + " " + acessosConst.getSobrenomeAcessos());
        txtTipoData.setText(acessosConst.getTipoAcessos() + " • " + acessosConst.getDataAcessos());

        return listViewItem;
    }

}
