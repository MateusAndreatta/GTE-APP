package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.sistemagte.gte.Construtoras.VansConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 18/05/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VansViewHolder> {

    @Override
    public VansViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_vans, viewGroup, false);
        VansViewHolder pvh = new VansViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(VansViewHolder ViewHolder, int i) {
        ViewHolder.Placa.setText("Placa: " + vansList.get(i).getPlacaVans());
        ViewHolder.ano.setText("Ano: " + String.valueOf(vansList.get(i).getAnoVans()));
        ViewHolder.marcaModelo.setText(vansList.get(i).getMarcaVans() + " " + vansList.get(i).getModeloVans());
        ViewHolder.capacidade.setText("Capacidade: " + String.valueOf(vansList.get(i).getCapacidadeVans()));
        ViewHolder.motorista.setText("Motorista: " + vansList.get(i).getMotoristaVans());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class VansViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView Placa,ano,marcaModelo,capacidade,motorista;

        VansViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            Placa = (TextView) itemView.findViewById(R.id.txtPlaca);
            ano = (TextView) itemView.findViewById(R.id.txtAno);
            marcaModelo = (TextView) itemView.findViewById(R.id.txtMarcaAndModelo);
            capacidade = (TextView) itemView.findViewById(R.id.txtCapacidade);
            //motorista  = (TextView) itemView.findViewById(R.id.txtMotorista);
        }

    }

    List<VansConstr> vansList;

    public RecyclerViewAdapter(List<VansConstr> vans){
        vansList = new ArrayList<>();
        this.vansList = vans;
    }

    @Override
    public int getItemCount() {
        return vansList.size();
    }

}