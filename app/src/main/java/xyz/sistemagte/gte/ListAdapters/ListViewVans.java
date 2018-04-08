package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.VansConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 08/04/2018.
 */

public class ListViewVans extends ArrayAdapter<VansConstr>{

    private List<VansConstr> vansList;

    private Context mCtx;

    public ListViewVans(List<VansConstr> vansList, Context mCtx){
        super(mCtx, R.layout.list_view_func, vansList);
        this.vansList = vansList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_vans, null, true);

        TextView txtid = listViewItem.findViewById(R.id.txtNome);
        TextView txtTipo = listViewItem.findViewById(R.id.txtTipo);

        VansConstr funcConst = vansList.get(position);

        txtid.setText(funcConst.getNome());
        txtTipo.setText(funcConst.getTipo());

        return listViewItem;
    }

}
