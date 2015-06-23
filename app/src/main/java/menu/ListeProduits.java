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
import BDD.Commande;
import BDD.DataBaseHandler;
import BDD.Nomenclature;
import adapter.ListeClientAdapter;
import adapter.ListeProduitAdapter;
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
        final ListView listeClients = (ListView) rootView.findViewById(R.id.listView);
        // Je crée la connexion a la base
        final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        // Je recupère ma liste de client.
        List<Client> listClient;
        listClient = db.getAllClients();

        // adapter pour pouvoir faire du traitement sur le client.
        final ListeClientAdapter adapter = new ListeClientAdapter(getActivity().getApplicationContext(),listClient);
        listeClients.setAdapter(adapter);

        // Listener sur la liste pour pouvoir choisir le user à traiter
        listeClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // JE recupere le clients sur lequels j'ai cliquer
                Client c = (Client)listeClients.getItemAtPosition(i);
                listeClients.setVisibility(View.INVISIBLE);
                ArrayList<Nomenclature> nomenclatures = db.getAllNomenclatureFromIdClient(c.getId());
                System.out.println(nomenclatures);
                final ListView listeNomenclatures = (ListView)rootView.findViewById(R.id.listeProduits);
                ListeProduitAdapter listeProduitAdapter = new ListeProduitAdapter(getActivity().getApplicationContext(),nomenclatures);
                listeNomenclatures.setAdapter(listeProduitAdapter);
                listeNomenclatures.setVisibility(View.VISIBLE);


            }
        });
        return rootView;
    }
}
