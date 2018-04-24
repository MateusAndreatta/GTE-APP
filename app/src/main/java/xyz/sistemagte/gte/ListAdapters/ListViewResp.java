package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.EscolasConstr;
import xyz.sistemagte.gte.Construtoras.FuncConst;
import xyz.sistemagte.gte.Construtoras.ResponsavelConstr;
import xyz.sistemagte.gte.R;

/**
 * Created by Aluno on 24/04/2018.
 */

public class ListViewResp extends ArrayAdapter<ResponsavelConstr> {

    private List<ResponsavelConstr> respList;
    private Context mCtx;

    public ListViewResp(List<ResponsavelConstr> respList, Context mCtx){
        super(mCtx, R.layout.list_view_func, respList);
        this.respList = respList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view_resp, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNomeResp);
        TextView txtCpf = listViewItem.findViewById(R.id.txtCpf);
        TextView txtEmail = listViewItem.findViewById(R.id.txtEmail);

        ResponsavelConstr respCostr = respList.get(position);

        txtNome.setText(respCostr.getNomeResp());
        txtCpf.setText(respCostr.getCpfResp());
        txtEmail.setText(respCostr.getEmailResp());

        return listViewItem;
    }

}
