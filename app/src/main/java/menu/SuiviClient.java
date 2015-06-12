package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import adapter.ListeClientAdapter;
import barbeasts.plastprod.R;

/**
 * Created by christophe on 01/04/2015.
 */
public class SuiviClient extends Fragment {
    public SuiviClient() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_suivi,container,false);
        // Je récupère la vue de la liste depuis son id.
        final ListView listeProduits = (ListView) rootView.findViewById(R.id.ListViewSuivi);
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
            }
        });
        return rootView;
    }
}
