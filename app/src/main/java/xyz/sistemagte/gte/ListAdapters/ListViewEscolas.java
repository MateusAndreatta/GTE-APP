package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.sistemagte.gte.Construtoras.AcessosConst;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.Escolas;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 24/04/2018.
 */

public class ListViewEscolas extends ArrayAdapter<EscolasConstr> {

    private List<EscolasConstr> EscolaList;
    private List<EscolasConstr> orig;
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

        TextView txtNome = listViewItem.findViewById(R.id.txtNomeEscola);
        TextView txtEnd = listViewItem.findViewById(R.id.txtRuaAndNumero);

        EscolasConstr escolasConstr = EscolaList.get(position);

        txtNome.setText(escolasConstr.getNomeEscola());
        txtEnd.setText(escolasConstr.getRuaEscola() + " " + escolasConstr.getNumeroEscola());

        return listViewItem;
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<EscolasConstr> results = new ArrayList<EscolasConstr>();
                if (orig == null) {
                    orig = EscolaList;
                }
                if (constraint != null) {
                    constraint = constraint.toString().toLowerCase();
                    //verifica se temos algo na lista original
                    if (orig != null && orig.size() > 0) {
                        //para cada obj do UsuarioConst dentro lista
                        for (final EscolasConstr g : orig) {//Ocorerá para cada usuario que está na lista
                            if (((g.getNomeEscola().toLowerCase().contains(constraint.toString())) ||
                                    (g.getRuaEscola().toLowerCase().contains(constraint.toString()))));
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                EscolaList = (ArrayList<EscolasConstr>) results.values;
                notifyDataSetChanged();
            }
        };
    }}

