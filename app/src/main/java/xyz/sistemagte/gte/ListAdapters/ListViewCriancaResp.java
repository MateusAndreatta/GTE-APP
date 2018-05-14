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

import xyz.sistemagte.gte.Construtoras.CriancaConst;
import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 09/04/2018.
 */

public class ListViewCriancaResp extends ArrayAdapter<CriancaConst> {

    private List<CriancaConst> criancaList;
    private List<CriancaConst> orig;

    private Context mCtx;

    public ListViewCriancaResp(List<CriancaConst> criancaList, Context mCtx){
        super(mCtx, R.layout.list_view_crianca, criancaList);
        this.criancaList = criancaList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_crianca_resp, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        TextView txtResp = listViewItem.findViewById(R.id.txtCpf);

        CriancaConst criancaConst = criancaList.get(position);

        txtNome.setText(criancaConst.getNomeCrianca().substring(0,1).toUpperCase().concat(criancaConst.getNomeCrianca().substring(1)) + " " + criancaConst.getSobrenomeCrianca().substring(0,1).toUpperCase().concat(criancaConst.getSobrenomeCrianca().substring(1)));
        txtResp.setText(mCtx.getResources().getString(R.string.cpf)+ ": "+ criancaConst.getCpfCrianca());

        return listViewItem;
    }

    @Override
    public int getCount() {
        return criancaList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<CriancaConst> results = new ArrayList<CriancaConst>();
                if (orig == null) {
                    orig = criancaList;
                }
                if (constraint != null) {
                    constraint = constraint.toString().toLowerCase();
                    //verifica se temos algo na lista original
                    if (orig != null && orig.size() > 0) {
                        //para cada obj do UsuarioConst dentro lista
                        for (final CriancaConst g : orig) {//Ocorerá para cada usuario que está na lista
                            if ((g.getNomeCrianca().toLowerCase().contains(constraint.toString())) ||
                                    (g.getCpfCrianca().toLowerCase().contains(constraint.toString())))
                                     {
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                criancaList = (ArrayList<CriancaConst>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
