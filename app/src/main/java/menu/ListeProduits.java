package menu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import adapter.ListeClientAdapter;
import barbeasts.plastprod.R;
import model.MainActivity;

/**
 * Created by christophe on 01/04/2015.
 */
public class ListeProduits extends Fragment{

    public ListeProduits(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_listesproduits,container,false);
        // Je récupère la vue de la liste depuis son id.
        final ListView listeProduits = (ListView) rootView.findViewById(R.id.listView);
        // Je crée la connexion a la base
        DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        // Je recupère ma liste de client.
        List<Client> listClient;
        listClient = db.getAllClients();

        // adapter pour pouvoir faire du traitement sur le client.
        ListeClientAdapter adapter = new ListeClientAdapter(getActivity().getApplicationContext(),listClient);
        listeProduits.setAdapter(adapter);

        // Listener sur la liste pour pouvoir choisir le user à traiter
        listeProduits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client c = (Client)listeProduits.getItemAtPosition(i);
                listeProduits.setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.Titre).setVisibility(View.VISIBLE);

                TextView tx = (TextView) rootView.findViewById(R.id.Titre);
                RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.listeproduits);
                rl.removeAllViewsInLayout();
                rl.addView(tx);
                Button b = new Button(getActivity().getApplicationContext());
                b.setText("Precedent");
                b.setGravity(View.TEXT_ALIGNMENT_CENTER);
                // J'ai mis en place un système qui permet de recharger le fragment actuelle sur le clique du bouton précédent.
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new ListeProduits();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();

                    }
                });
                rl.addView(b);

            }
        });
        return rootView;
    }
}
