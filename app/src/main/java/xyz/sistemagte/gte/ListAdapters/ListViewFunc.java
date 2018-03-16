package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.FuncConst;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 14/03/2018.
 */

public class ListViewFunc extends ArrayAdapter<FuncConst>{

    private List<FuncConst> funcList;

    private Context mCtx;

    public ListViewFunc(List<FuncConst> funcList, Context mCtx){
        super(mCtx, R.layout.list_view_func, funcList);
        this.funcList = funcList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_func, null, true);

        TextView txtid = listViewItem.findViewById(R.id.txtNome);
        TextView txtTipo = listViewItem.findViewById(R.id.txtTipo);

        FuncConst funcConst = funcList.get(position);

        txtid.setText(funcConst.getNome());
        txtTipo.setText(funcConst.getTipo());

        return listViewItem;
    }

}
