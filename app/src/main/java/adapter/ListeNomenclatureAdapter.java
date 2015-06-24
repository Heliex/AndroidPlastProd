package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
            holder.numberPicker = (NumberPicker)view.findViewById(R.id.numberPicker);
            holder.numberPicker.setMinValue(0);
            holder.numberPicker.setMaxValue(20);

            final CheckBox box =(CheckBox) view.findViewById(R.id.checkBox);
            final NumberPicker picker = (NumberPicker)view.findViewById(R.id.numberPicker);
            picker.setWrapSelectorWheel(false);

            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox box = (CheckBox) view.findViewById(R.id.checkBox);
                    int quantite = picker.getValue(); // Recupere la quantite du picker
                    final boolean isChecked = box.isChecked();
                    if(isChecked && quantite > 0) // Si on check, calculer le cout total
                    {
                        picker.setEnabled(false); // On desactive la modif sur le picker
                        double prix = nomenclature.getPrixTotal(new DataBaseHandler(getContext()),nomenclature.getId());
                        nomenclature.setQuantite(quantite);
                        ajouterSomme(prix, quantite);
                        getPrixTotal().setText(String.valueOf(getSomme() + "€"));
                    }
                    else if(!isChecked && quantite > 0) // Sinon  supprimer du cout total
                    {
                        picker.setEnabled(true); // On réactive la modif sur le picker
                        nomenclature.setQuantite(quantite);
                        double prix = nomenclature.getPrixTotal(new DataBaseHandler(getContext()),nomenclature.getId());
                        soustraireSomme(prix,quantite);
                        getPrixTotal().setText(String.valueOf(getSomme() + "€"));
                    }
                    else if(isChecked && quantite ==0)
                    {
                        Toast.makeText(getContext(),"vous devez mettre une quantité positive ou ne pas cocher la nomenclature",Toast.LENGTH_SHORT).show();
                        box.setChecked(false);
                    }
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

    public ArrayList<Nomenclature> getNomenclatures()
    {
        return this.nomenclatures;
    }

    public Context getContext()
    {
        return this.context;
    }

    static class ViewHolder
    {
        TextView Nom;
        CheckBox checkbox;
        NumberPicker numberPicker;
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

    public void ajouterSomme(double prix,int quantite)
    {
        this.somme = somme + ( prix * quantite);
    }

    public void soustraireSomme(double prix,int quantite)
    {
        this.somme = somme - ( prix * quantite);
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
