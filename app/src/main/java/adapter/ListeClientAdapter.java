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
import barbeasts.plastprod.R;

/**
 * Created by christophe on 05/05/2015.
 */
public class ListeClientAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Client> clients;

    public ListeClientAdapter(Context context,List<Client> clients)
    {
        this.clients = clients;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Client getItem(int i) {
        return clients.get(i);
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

        // Mettre les données dans les composants.
        holder.Nom.setText(clients.get(i).getNom());
        holder.Prenom.setText(clients.get(i).getPrenom());

        return view;
    }

    static class ViewHolder
    {
        TextView Nom;
        TextView Prenom;
    }
}
