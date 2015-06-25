package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import BDD.Client;
import BDD.Commande;
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
        final ListView listeClients = (ListView) rootView.findViewById(R.id.ListViewSuivi);
        // Je crée la connexion a la base
        final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        // Je recupère ma liste de client.
        final List<Client> listClient;
        listClient = db.getAllClients();

        // adapter pour pouvoir faire du traitement sur le client.
        ListeClientAdapter adapter = new ListeClientAdapter(getActivity().getApplicationContext(),listClient);
        listeClients.setAdapter(adapter);

        double totalCA = 0;

        // Récupération du CA total
        for(int i = 0; i < listClient.size(); i++)
        {
            Client c = listClient.get(i);
            List<Commande> listeCommandes = db.getAllCommandeByClient(c.getId());
            for(int j = 0 ; j < listeCommandes.size(); j++)
            {
                totalCA += listeCommandes.get(j).getTotal();
            }
        }

        final TextView totalCAClients = (TextView)rootView.findViewById(R.id.CaTotal);
        final TextView totalCAClientsAffiche = (TextView) rootView.findViewById(R.id.CaTotalAffichee);


        StringBuilder totalCAEntier = new StringBuilder();
        totalCAEntier.append(String.valueOf(totalCA));
        totalCAEntier.append(" €");
        totalCAClientsAffiche.setText(totalCAEntier);

        // Listener sur la liste pour pouvoir choisir le user à traiter
        listeClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client c = (Client) listeClients.getItemAtPosition(i);
                totalCAClients.setVisibility(View.INVISIBLE);
                totalCAClientsAffiche.setVisibility(View.INVISIBLE);
                List<Commande> listeCommandes = db.getAllCommandeByClient(c.getId());
                double total = 0;
                for (int m = 0; m < listeCommandes.size(); m++) {
                    total += listeCommandes.get(m).getTotal();
                }
                String dateDerniereCommande = listeCommandes.get((listeCommandes.size() - 1)).getDateCommande();
                listeClients.setVisibility(View.INVISIBLE);
                TextView CA = (TextView) getActivity().findViewById(R.id.CAclient);
                TextView DateD = (TextView) getActivity().findViewById(R.id.dateDerniereCommande);
                TextView CAAfficher = (TextView) getActivity().findViewById(R.id.CaAfficher);
                TextView DateDAfficher = (TextView) getActivity().findViewById(R.id.dateDerniereCommandeAffichee);

                CAAfficher.setText(String.valueOf(total) + " €");
                DateDAfficher.setText(dateDerniereCommande);

                CA.setVisibility(View.VISIBLE);
                DateD.setVisibility(View.VISIBLE);
                CAAfficher.setVisibility(View.VISIBLE);
                DateDAfficher.setVisibility(View.VISIBLE);
            }
        });
        return rootView;
    }
}
