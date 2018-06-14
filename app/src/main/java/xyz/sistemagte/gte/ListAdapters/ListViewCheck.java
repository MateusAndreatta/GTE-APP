package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.sistemagte.gte.Construtoras.CheckStatusConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 14/06/2018.
 */

public class ListViewCheck extends ArrayAdapter<CheckStatusConstr> {

    int idCrianca;
    private List<CheckStatusConstr> CheckList;
    private List<CheckStatusConstr> orig;
    private Context mCtx;

    public ListViewCheck(List<CheckStatusConstr> checkList, Context mCtx) {
        super(mCtx, R.layout.list_view_escolas, checkList);
        this.CheckList = checkList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_checklist, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        final TextView id = listViewItem.findViewById(R.id.txtId);
        Button btn = listViewItem.findViewById(R.id.btnCheck);

        CheckStatusConstr check = CheckList.get(position);
        idCrianca = check.getIdCriancaCheck();
        txtNome.setText(check.getNomeCheck() + " " + check.getSobrenomeCheck());
        id.setText(String.valueOf(check.getIdCriancaCheck()));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, id.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return listViewItem;
    }

    @Override
    public int getCount() {
        return CheckList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<CheckStatusConstr> results = new ArrayList<CheckStatusConstr>();
                if (orig == null) {
                    orig = CheckList;
                }
                if (constraint != null) {
                    constraint = constraint.toString().toLowerCase();
                    //verifica se temos algo na lista original
                    if (orig != null && orig.size() > 0) {
                        //para cada obj do UsuarioConst dentro lista
                        for (final CheckStatusConstr g : orig) {//Ocorerá para cada usuario que está na lista
                            if ((g.getNomeCheck().toLowerCase().contains(constraint.toString())) ||
                                    g.getSobrenomeCheck().toLowerCase().contains(constraint.toString())) {
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
                CheckList = (ArrayList<CheckStatusConstr>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}