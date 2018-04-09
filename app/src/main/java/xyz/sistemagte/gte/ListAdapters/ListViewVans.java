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

        TextView modeloMarca = listViewItem.findViewById(R.id.txtModeloMarca);
        TextView capacidade = listViewItem.findViewById(R.id.txtCapacidade);
        TextView placaAno = listViewItem.findViewById(R.id.txtPlacaAno);

        VansConstr vansConstr = vansList.get(position);

        modeloMarca.setText(vansConstr.getModeloVans() + " • " + vansConstr.getMarcaVans());
        placaAno.setText(vansConstr.getPlacaVans() + " • " + vansConstr.getAnoVans());
        capacidade.setText(mCtx.getResources().getString(R.string.capacidade) +": " +  vansConstr.getCapacidadeVans());

        return listViewItem;
    }

}
