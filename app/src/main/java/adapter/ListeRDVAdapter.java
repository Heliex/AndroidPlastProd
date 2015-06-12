package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import BDD.Client;
import barbeasts.plastprod.R;
import model.RDV;

/**
 * Created by Christophe on 10/06/2015. For PlastProd Project
 */
public class ListeRDVAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<RDV> RDV;

    public ListeRDVAdapter(Context context,List<RDV> rdv)
    {
        this.RDV = rdv;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return RDV.size();
    }

    @Override
    public RDV getItem(int i) {
        return RDV.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.row_liste_rdv,null);
            holder = new ViewHolder();
            holder.Nom = (TextView)view.findViewById(R.id.Nom);
            holder.hDebut = (TextView) view.findViewById(R.id.hDebut);
            holder.hFin = (TextView) view.findViewById(R.id.hFin);
            holder.date = (TextView) view.findViewById(R.id.Date);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        // Mettre les donnees dans les composants.
        holder.Nom.setText(RDV.get(i).getName());
        holder.Nom.setTextColor(Color.BLUE);
        holder.Nom.setTextSize(22);
        holder.hDebut.setText(RDV.get(i).gethDebut());
        holder.hDebut.setTextColor(Color.BLACK);
        holder.hFin.setText(RDV.get(i).gethFin());
        holder.hFin.setTextColor(Color.BLACK);
        holder.date.setText(RDV.get(i).getDate());
        holder.date.setTextColor(Color.CYAN);

        return view;
    }

    static class ViewHolder
    {
        TextView Nom;
        TextView hDebut;
        TextView hFin;
        TextView date;
    }
}
