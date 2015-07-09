package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import BDD.Client;
import BDD.Devis;
import barbeasts.plastprod.R;

/**
 * Created by christophe on 05/05/2015.
 */
public class ListeDevisAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Devis> devis;

    public ListeDevisAdapter(Context context,List<Devis> devis)
    {
        this.devis = devis;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return devis.size();
    }

    @Override
    public Devis getItem(int i) {
        return devis.get(i);
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
            view = layoutInflater.inflate(R.layout.row_liste_devis,null);
            holder = new ViewHolder();
            holder.NumeroDevis = (TextView)view.findViewById(R.id.NumeroDevis);
            holder.Total = (TextView) view.findViewById(R.id.TotalDevis);
            holder.Date = (TextView) view.findViewById(R.id.dateDevis);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        // Mettre les données dans les composants.
        holder.NumeroDevis.setText("Numéro de devis : " + String.valueOf(devis.get(i).getNumDevis()));
        holder.Date.setText("Date du devis : " + devis.get(i).getDateDevis());
        holder.Total.setText("Total du devis : " + String.valueOf(devis.get(i).getTotal()) + " €");

        return view;
    }

    static class ViewHolder
    {
        TextView Total;
        TextView NumeroDevis;
        TextView Date;
    }
}
