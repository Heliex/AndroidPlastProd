package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import BDD.Client;
import BDD.Nomenclature;
import barbeasts.plastprod.R;

/**
 * Created by Christophe on 24/06/2015. For PlastProd Project
 */
public class ListeProduitAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Nomenclature> nomenclatures;

    public ListeProduitAdapter(Context context,List<Nomenclature> nomenclatures)
    {
        this.nomenclatures = nomenclatures;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nomenclatures.size();
    }

    @Override
    public Nomenclature getItem(int i) {
        return nomenclatures.get(i);
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
            view = layoutInflater.inflate(R.layout.row_listeproduits,null);
            holder = new ViewHolder();
            holder.Nom = (TextView)view.findViewById(R.id.nomProduits);
            holder.Quantite = (TextView) view.findViewById(R.id.quantiteProduits);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        // Mettre les données dans les composants.
        holder.Nom.setText(nomenclatures.get(i).getNom());
        holder.Quantite.setText(String.valueOf(nomenclatures.get(i).getQuantite()));

        return view;
    }

    static class ViewHolder
    {
        TextView Nom;
        TextView Quantite;
    }
}
