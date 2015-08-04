package adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import barbeasts.plastprod.R;
import model.NavDrawerItem;

/**
 * Created by christophe on 01/04/2015.
 * Classe qui permet de formater l'affichage des Drawer (Menu gauche et droite)
 * @author Christophe Gerard.
 * @version 1.0
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    /**
     * Constructeur à 2 paramètres
     * @param context Contexte de création
     * @param navDrawerItems Liste de NavDrawerItem
     */
    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems)
    {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    /**
     * Retourne la taille de la liste de navDrawerItem
     * @return taille de la liste
     */
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    /**
     * Retourne un objet à la position i
     * @param i Position dans la liste
     * @return un Objet
     */
    @Override
    public Object getItem(int i) {
        return navDrawerItems.get(i);
    }

    /**
     * Retourne l'id de l'objet à la position i
     * @param i Position dans la liste
     * @return l'id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Construit la vue associée à un élément dans la liste
     * @param i Position dans la liste
     * @param convertView Vue associée
     * @param parent Vue parent
     * @return un objet View
     */
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView= mInflater.inflate(R.layout.drawer_list_item,null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        imgIcon.setImageResource(navDrawerItems.get(i).getIcon());
        txtTitle.setText(navDrawerItems.get(i).getTitle());

        // Displaying count
        // Check wether it set visible or not

        if(navDrawerItems.get(i).getCounterVisibility())
        {
            txtCount.setText(navDrawerItems.get(i).getCount());
        }
        else
        {
            // Hide the counter view
            txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }
}
