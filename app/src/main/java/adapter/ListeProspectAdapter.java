package adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import BDD.Prospect;
import barbeasts.plastprod.R;
import menu.SuiviProspect;

/**
 * Created by Kirill on 12/06/2015.
 */
public class ListeProspectAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    private List<Prospect> prospects;
    private Fragment fragment;


    public ListeProspectAdapter(Context context, List<Prospect> prospects,Fragment fragment)
    {
        this.prospects = prospects;
        this.layoutInflater = LayoutInflater.from(context);
        this.fragment = fragment;
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
            view = layoutInflater.inflate(R.layout.row_listeprospect,null);
            holder = new ViewHolder();
            holder.Nom = (TextView)view.findViewById(R.id.NomProspect);
            holder.Prenom = (TextView) view.findViewById(R.id.PrenomProspect);
            holder.progression = (ProgressBar)view.findViewById(R.id.progressBar);
            holder.pourcentage = (TextView)view.findViewById(R.id.pourcentageProspect);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        // Mettre les donnees dans les composants.
        holder.Nom.setText(prospects.get(i).getNom());
        holder.Prenom.setText(prospects.get(i).getPrenom());
        if(fragment instanceof SuiviProspect)
        {
            holder.progression.setVisibility(View.VISIBLE);
            holder.progression.setProgress(prospects.get(i).getPourcentage());
            holder.pourcentage.setText(String.valueOf(prospects.get(i).getPourcentage()) + "%");
        }
        else
        {
            holder.progression.setVisibility(View.GONE);
        }
        return view;
    }

    static class ViewHolder
    {
        TextView Nom;
        TextView Prenom;
        ProgressBar progression;
        TextView pourcentage;
    }

}
