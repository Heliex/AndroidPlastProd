package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import BDD.Prospect;
import barbeasts.plastprod.R;

/**
 * Created by Kirill on 12/06/2015.
 */
public class ListeProspectAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    private List<Prospect> prospects;

    public ListeProspectAdapter(Context context, List<Prospect> prospects)
    {
        this.prospects = prospects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return prospects.size();
    }

    @Override
    public Prospect getItem(int i) {
        return prospects.get(i);
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
            view = layoutInflater.inflate(R.layout.row_liste,null);
            holder = new ViewHolder();
            holder.Nom = (TextView)view.findViewById(R.id.Nom);
            holder.Prenom = (TextView) view.findViewById(R.id.Prenom);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        // Mettre les donnees dans les composants.
        holder.Nom.setText(prospects.get(i).getNom());
        holder.Prenom.setText(prospects.get(i).getPrenom());

        return view;
    }

    static class ViewHolder
    {
        TextView Nom;
        TextView Prenom;
    }

}
