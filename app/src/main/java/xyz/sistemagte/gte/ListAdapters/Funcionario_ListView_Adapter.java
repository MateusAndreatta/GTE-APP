package xyz.sistemagte.gte.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.sistemagte.gte.Construtoras.Funcionario;
import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 14/03/2018.
 */

public class Funcionario_ListView_Adapter extends ArrayAdapter<Funcionario> {

    private List<Funcionario> funcList;


    private Context mCtx;


    public Funcionario_ListView_Adapter(List<Funcionario> heroList, Context mCtx) {
        super(mCtx, R.layout.list_items, heroList);
        this.funcList = heroList;
        this.mCtx = mCtx;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);


        View listViewItem = inflater.inflate(R.layout.list_items, null, true);


        TextView txtid = listViewItem.findViewById(R.id.txtID);
        TextView txtName = listViewItem.findViewById(R.id.txtName);


        Funcionario func = funcList.get(position);


        txtid.setText(func.getNome());
        txtName.setText(func.getSobrenome());


        return listViewItem;
    }
}
