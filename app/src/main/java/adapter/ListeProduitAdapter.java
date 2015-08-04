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
 * Created by christophe on 24/06/2015.
 * Classe qui permet de formater l'affichage d'une liste de Produit.
 * @author Christophe Gerard
 * @version 1.0
 */
public class ListeProduitAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Nomenclature> nomenclatures;

    /**
     * Constructeur à 2 paramètres
     * @param context Contexte de création
     * @param nomenclatures Liste de nomenclatures
     */
    public ListeProduitAdapter(Context context,List<Nomenclature> nomenclatures)
    {
        this.nomenclatures = nomenclatures;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Renvoie le nombre de nomenclatures dans la liste
     * @return taille de la liste de nomenclatures
     */
    @Override
    public int getCount() {
        return nomenclatures.size();
    }

    /**
     * Renvoie la nomenclature à la position i
     * @param i Position
     * @return un objet Nomenclature
     */
    @Override
    public Nomenclature getItem(int i) {
        return nomenclatures.get(i);
    }

    /**
     * Retourne l'id de la nomenclature à la position i
     * @param i Position dans la liste
     * @return l'id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Retourne la vue associée pour une ligne dans la liste de produits
     * @param i Position dans la liste
     * @param view Vue associée
     * @param viewGroup Vue container
     * @return La vue associée à la nomenclature
     */
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
