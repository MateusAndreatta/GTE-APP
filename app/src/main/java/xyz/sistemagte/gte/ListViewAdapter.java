package xyz.sistemagte.gte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andreatta on 06/03/2018.
 */

public class ListViewAdapter extends ArrayAdapter<Hero> {


    private List<Hero> heroList;


    private Context mCtx;


    public ListViewAdapter(List<Hero> heroList, Context mCtx) {
        super(mCtx, R.layout.list_items, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);


        View listViewItem = inflater.inflate(R.layout.list_items, null, true);


        TextView txtid = listViewItem.findViewById(R.id.txtID);
        TextView txtName = listViewItem.findViewById(R.id.txtName);


        Hero hero = heroList.get(position);


        txtid.setText(hero.getNome());
        txtName.setText(hero.getSobrenome());


        return listViewItem;
    }
}