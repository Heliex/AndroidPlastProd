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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<String,Integer> listeQuantiteStockee;
    private HashMap<String,Nomenclature> listeNomenclatureStockee;
    private Context context;
    private TextView prixTotal;
    ViewHolder holder;
    private double valeurAjoutee;
    private double valeurSoustraite;

    public ListeNomenclatureAdapter(Context context,ArrayList<Nomenclature> nomenclatures,TextView somme)
    {
        this.context = context;
        this.nomenclatures = nomenclatures;
        this.layoutInflater = LayoutInflater.from(context);
        this.somme = 0;
        this.prixTotal = somme;
        this.valeurAjoutee = 0;
        this.valeurSoustraite = 0;
        this.listeQuantiteStockee = new HashMap<>();
        this.listeNomenclatureStockee = new HashMap<>();
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
                public void onClick(View view) { // Lister sur la checkbox
                    CheckBox box = (CheckBox) view.findViewById(R.id.checkBox); // Je récupère la checkbox
                    int quantite = picker.getValue(); // Recupere la quantite du picker
                    final boolean isChecked = box.isChecked();
                    if(isChecked && quantite > 0) // Si on check, calculer le cout total
                    {
                        picker.setEnabled(false); // On desactive la modif sur le picker
                        double prix = nomenclature.getPrixTotal(new DataBaseHandler(getContext()),nomenclature.getId());
                        nomenclature.setQuantite(quantite);
                        setValeurAjoutee(ajouterSomme(prix, quantite)); // Je garde en mémoire la valeur ajoutée
                        getPrixTotal().setText(String.valueOf(getSomme() + "€"));
                        listeQuantiteStockee.put(nomenclature.getNom(), quantite);
                        listeNomenclatureStockee.put(nomenclature.getNom(),nomenclature);
                    }
                    else if(!isChecked && quantite > 0) // Sinon  supprimer du cout total
                    {
                        picker.setEnabled(true); // On réactive la modif sur le picker
                        nomenclature.setQuantite(quantite);
                        double prix = nomenclature.getPrixTotal(new DataBaseHandler(getContext()),nomenclature.getId());
                        setValeurSoustraite(soustraireSomme(prix, quantite)); // Je garde en mémoire la valeur soustraite
                        if(verifierOperation(getValeurAjoutee(),getValeurSoustraite())) // Je vérifie que ma soustraction n'est pas faussée (a cause du numberPicker)
                        {
                            getPrixTotal().setText(String.valueOf(getSomme() + "€"));
                            picker.setValue(0);
                        }
                        else // Si la valeur soustraite est supérieure à celle ajoutée alors on affiche une erreur.
                        {
                            Toast.makeText(getContext(),"La valeur soustraite est supèrieur a celle précédemment ajoutée",Toast.LENGTH_SHORT).show();
                            picker.setValue(0);
                            setSomme(0);
                            getPrixTotal().setText("0" + "€");
                        }
                        listeQuantiteStockee.remove(nomenclature.getNom());
                        listeNomenclatureStockee.remove(nomenclature.getNom());

                    }
                    else if(isChecked && quantite ==0) // Si le produit est coché avec un quantité à 0
                    {
                        Toast.makeText(getContext(),"vous devez mettre une quantité positive ou ne pas cocher le produit",Toast.LENGTH_SHORT).show();
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

    public boolean verifierOperation(double ajout,double soustraite)
    {
        if(ajout != 0 && soustraite != 0)
        {
            if(ajout - soustraite < 0)
                return false;
        }
        return true;
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

    public double ajouterSomme(double prix,int quantite)
    {
        this.somme = somme + ( prix * quantite);

        return (prix * quantite);
    }

    public double soustraireSomme(double prix,int quantite)
    {

        this.somme = somme - ( prix * quantite);
        return (prix * quantite);
    }

    public TextView getPrixTotal()
    {
        return this.prixTotal;
    }

    public void setPrixTotal(TextView tx)
    {
        this.prixTotal = tx;
    }

    public double getValeurAjoutee()
    {
        return this.valeurAjoutee;
    }

    public void setValeurAjoutee(double valeurAjoutee)
    {
        this.valeurAjoutee = valeurAjoutee;
    }

    public double getValeurSoustraite()
    {
        return this.valeurSoustraite;
    }

    public void setValeurSoustraite(double valeurSoustraite)
    {
        this.valeurSoustraite = valeurSoustraite;
    }

    public HashMap<String,Integer> getListeStockee()
    {
        return this.listeQuantiteStockee;
    }

    public HashMap<String,Nomenclature> getListeNomenclatureStockee()
    {
        return this.listeNomenclatureStockee;
    }
}
