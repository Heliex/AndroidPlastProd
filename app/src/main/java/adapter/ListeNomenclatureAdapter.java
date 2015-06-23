package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import BDD.Nomenclature;
import barbeasts.plastprod.R;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 */
public class ListeNomenclatureAdapter extends BaseAdapter{

    private double somme;
    private LayoutInflater layoutInflater;
    private ArrayList<Nomenclature> nomenclatures;
    private Context context;
    private TextView prixTotal;
    ViewHolder holder;

    public ListeNomenclatureAdapter(Context context,ArrayList<Nomenclature> nomenclatures,TextView somme)
    {
        this.context = context;
        this.nomenclatures = nomenclatures;
        this.layoutInflater = LayoutInflater.from(context);
        this.somme = 0;
        this.prixTotal = somme;
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
    public View getView(int i, View view, final ViewGroup viewGroup) {

        if(view == null)
        {
            // Récupere la nomenclature au bon id.
            final Nomenclature nomenclature = nomenclatures.get(i);
            view = layoutInflater.inflate(R.layout.row_liste_nomenclature,null);
            holder = new ViewHolder();
            holder.Nom = (TextView)view.findViewById(R.id.Nom_nomenclature);
            holder.checkbox = (CheckBox) view.findViewById(R.id.checkBox);
            CheckBox box =(CheckBox) view.findViewById(R.id.checkBox);
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox box = (CheckBox) view.findViewById(R.id.checkBox);
                    final boolean isChecked = box.isChecked();
                    if(isChecked) // Si on check, calculer le cout total
                    {
                        double prix = nomenclature.getPrixTotal(new DataBaseHandler(getContext()),nomenclature.getId());
                       ajouterSomme(prix);
                    }
                    else // Sinon  supprimer du cout total
                    {
                        double prix = nomenclature.getPrixTotal(new DataBaseHandler(getContext()),nomenclature.getId());
                        soustraireSomme(prix);
                    }

                    getPrixTotal().setText(String.valueOf(getSomme()+"€"));
                    getPrixTotal().setVisibility(View.VISIBLE);
                }
            });
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        // Mettre les données dans les composants.
        holder.Nom.setText(nomenclatures.get(i).getNom());
        return view;
    }

    public Context getContext()
    {
        return this.context;
    }

    static class ViewHolder
    {
        TextView Nom;
        CheckBox checkbox;
    }
    public ViewHolder getHolder()
    {
        return this.holder;
    }

    public double getSomme()
    {
        return this.somme;
    }

    public void setSomme(double somme)
    {
        this.somme = somme;
    }

    public void ajouterSomme(double prix)
    {
        this.somme = somme + prix;
    }

    public void soustraireSomme(double prix)
    {
        this.somme = somme - prix;
    }

    public TextView getPrixTotal()
    {
        return this.prixTotal;
    }

    public void setPrixTotal(TextView tx)
    {
        this.prixTotal = tx;
    }
}
