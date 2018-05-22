package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.VansConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Aluno on 22/05/2018.
 */

public class ListViewVansCard extends ArrayAdapter<VansConstr> {

    private List<VansConstr> vansList;

    private Context mCtx;

    public ListViewVansCard(List<VansConstr> vansList, Context mCtx){
        super(mCtx, R.layout.recycler_view_vans, vansList);
        this.vansList = vansList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.recycler_view_vans, null, true);

        CardView cv;
        TextView Placa,ano,marcaModelo,capacidade,motorista;

        //cv          = (CardView) listViewItem.findViewById(R.id.cv);
        Placa       = listViewItem.findViewById(R.id.txtPlaca);
        ano         = listViewItem.findViewById(R.id.txtAno);
        marcaModelo = listViewItem.findViewById(R.id.txtMarcaAndModelo);
        capacidade  = listViewItem.findViewById(R.id.txtCapacidade);
       // motorista   = listViewItem.findViewById(R.id.txtMotorista);

        VansConstr vansConstr = vansList.get(position);

        Placa.setText("Placa: " + vansConstr.getPlacaVans());
        ano.setText("Ano: " + String.valueOf(vansConstr.getAnoVans()));
        marcaModelo.setText(vansConstr.getMarcaVans() + " " + vansConstr.getModeloVans());
        capacidade.setText("Capacidade: " + String.valueOf(vansConstr.getCapacidadeVans()));
       // motorista.setText("Motorista: " + vansConstr.getMotoristaVans());
        capacidade.setText(mCtx.getResources().getString(R.string.capacidade) +": " +  vansConstr.getCapacidadeVans());

        return listViewItem;
    }
}
