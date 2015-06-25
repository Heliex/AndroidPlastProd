package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import BDD.Nomenclature;
import adapter.ListeClientAdapter;
import adapter.ListeNomenclatureAdapter;
import adapter.ListeProduitAdapter;
import barbeasts.plastprod.R;

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
        final List<Client> listClient;
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
                TextView listeClient = (TextView)getActivity().findViewById(R.id.listeClient);
                listeClient.setVisibility(View.INVISIBLE);
                listeClients.setVisibility(View.INVISIBLE);
                TextView listeProduitsCommandes = (TextView) getActivity().findViewById(R.id.listeProduitsCommandes);
                listeProduitsCommandes.setVisibility(View.VISIBLE);
                List<Nomenclature> nomenclatures = db.getAllNomenclatureFromIdClient(c.getId());
                final ListView listeNomenclatures = (ListView)rootView.findViewById(R.id.listeProduits);
                for(int m = 0; m < nomenclatures.size(); m++)
                {
                    if(nomenclatures.get(m).getQuantite() == 0) // On retire les produits qui ne sont pas "effectivement" commandé uniquement pour l'affichage
                    {
                        nomenclatures.remove(nomenclatures.get(m));
                        m=0;
                    }
                }
                trierListe(nomenclatures); // Trie de liste
                ListeProduitAdapter listeProduitAdapter = new ListeProduitAdapter(getActivity().getApplicationContext(),nomenclatures);
                listeNomenclatures.setAdapter(listeProduitAdapter);
                listeNomenclatures.setVisibility(View.VISIBLE);

            }
        });
        return rootView;
    }

    private void trierListe(List<Nomenclature> nomenclatures) // Methode qui permet de me retirer les matieres qui apparraissent plusieurs fois dans la liste de produits en gérant la quantité associée.
    {
        for(int i =0; i < nomenclatures.size(); i++)
        {
            Nomenclature n = nomenclatures.get(i);
            for(int j=i+1; j < nomenclatures.size(); j++)
            {
                Nomenclature n1 = nomenclatures.get(j);
                if(n.getNom().equals(n1.getNom()))
                {
                    n.setQuantite(n.getQuantite() + n1.getQuantite());
                    nomenclatures.remove(n1);
                }

            }
            int count = getCount(nomenclatures,n.getNom());
            if(count > 1)
            {
                trierListe(nomenclatures);
            }
        }
    }

    public int getCount(List<Nomenclature> nomenclatures, String nom) // Methode qui me renvoie le nombre d'occurences dans ma liste avec le nom passé en paramètre
    {
        int compteur = 0;
        for(int i =0; i < nomenclatures.size(); i++)
        {
            if(nomenclatures.get(i).getNom().equals(nom))
            {
                compteur++;
            }
        }

        return compteur;
    }
}
