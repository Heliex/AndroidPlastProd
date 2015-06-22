package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import BDD.Client;
import BDD.Nomenclature;
import barbeasts.plastprod.R;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 */
public class ListeNomenclatureAdapter extends BaseAdapter{


    private LayoutInflater layoutInflater;
    private ArrayList<Nomenclature> nomenclatures;
    private Context context;
    ViewHolder holder;

    public ListeNomenclatureAdapter(Context context,ArrayList<Nomenclature> nomenclatures)
    {
        this.context = context;
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

        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.row_liste_nomenclature,null);
            holder = new ViewHolder();
            holder.Nom = (TextView)view.findViewById(R.id.Nom_nomenclature);
            holder.checkbox = (CheckBox) view.findViewById(R.id.checkBox);
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!getHolder().checkbox.isChecked()) // Calcul du total avec la nomenclature  en -.
                    {
                        Toast.makeText(getContext(), "UnChecked", Toast.LENGTH_SHORT).show();

                    }
                    if(getHolder().checkbox.isChecked()) // Calcul du total avec la nomenclature en +.
                    {
                        Toast.makeText(getContext(), "Checked", Toast.LENGTH_SHORT).show();
                    }

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
}
