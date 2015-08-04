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
 * Classe qui permet de formater une liste de RDV à l'affichage
 * @author Christophe Gerard
 * @version 1.0
 */
public class ListeRDVAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<RDV> RDV;

    /**
     * Constructeur à 2 paramètres
     * @param context Contexte de création
     * @param rdv Liste de Rendez-Vous
     */
    public ListeRDVAdapter(Context context,List<RDV> rdv)
    {
        this.RDV = rdv;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Retourne la taille de la liste de RDV
     * @return taille de liste de RDV
     */
    @Override
    public int getCount() {
        return RDV.size();
    }

    /**
     * Retourne le RDV à la position i dans la liste
     * @param i Position dans la liste
     * @return un objet RDV
     */
    @Override
    public RDV getItem(int i) {
        return RDV.get(i);
    }

    /**
     * Retourne l'id du rdv à la position i dans la liste
     * @param i Position dans la liste
     * @return l'id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Retourne la vue associée pour le RDV i de la liste
     * @param i Position dans la liste
     * @param view Vue associée
     * @param viewGroup Vue container
     * @return un objet View
     */
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
