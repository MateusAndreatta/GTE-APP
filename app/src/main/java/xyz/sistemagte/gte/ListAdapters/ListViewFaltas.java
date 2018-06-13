package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.AcessosConst;
import xyz.sistemagte.gte.Construtoras.FaltasConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Aluno on 13/06/2018.
 */

public class ListViewFaltas extends ArrayAdapter<FaltasConstr> {

    private List<FaltasConstr> FaltasList;

    private Context mCtx;

    public ListViewFaltas(List<FaltasConstr> FaltasList, Context mCtx){
        super(mCtx, R.layout.list_view_faltas, FaltasList);
        this.FaltasList = FaltasList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_faltas, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        TextView txtPlaca = listViewItem.findViewById(R.id.txtPlaca);

        FaltasConstr faltasConstr = FaltasList.get(position);

        txtNome.setText(faltasConstr.getNomeFalta() + " " + faltasConstr.getPlacaFalta());
        txtPlaca.setText(" • " + faltasConstr.getSobrenomePlaca());

        return listViewItem;
    }

}
