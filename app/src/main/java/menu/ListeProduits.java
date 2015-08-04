package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import BDD.Nomenclature;
import adapter.ListeClientAdapter;
import adapter.ListeProduitAdapter;
import barbeasts.plastprod.R;

/**
 * Created by christophe on 01/04/2015.
 * Classe qui représente le menu lister les produits
 * @author Christophe Gerard
 * @version 1.0
 */
public class ListeProduits extends Fragment{

    public ListeProduits(){}

    /**
     * Création de la vue pour ce menu
     * @param inflater Zone à crée
     * @param container Vue parent
     * @param savedInstanceState Etat du bundle à la création de la vue
     * @return un objet View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_listesproduits,container,false);
        // Je récupère la vue de la liste depuis son id.
        final ListView listeClients = (ListView) rootView.findViewById(R.id.listView);
        // Je crée la connexion a la base
        final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
        final Button annuler = (Button)rootView.findViewById(R.id.precedentListeProduit);

        // Je recupère ma liste de client.
        final List<Client> listClient;
        listClient = db.getAllClients();

        if(listClient.size() > 0)
        {
            // adapter pour pouvoir faire du traitement sur le client.
            final ListeClientAdapter adapter = new ListeClientAdapter(getActivity().getApplicationContext(),listClient);
            listeClients.setAdapter(adapter);

            // Listener sur la liste pour pouvoir choisir le user à traiter
            listeClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // JE recupere le clients sur lequels j'ai cliquer
                    Client c = (Client)listeClients.getItemAtPosition(i);
                    annuler.setVisibility(View.VISIBLE);
                    List<Nomenclature> nomenclatures = db.getAllNomenclatureFromIdClient(c.getId());
                    if(nomenclatures.size() > 0)
                    {
                        TextView listeClient = (TextView)getActivity().findViewById(R.id.listeClient);
                        listeClient.setVisibility(View.INVISIBLE);
                        listeClients.setVisibility(View.INVISIBLE);
                        TextView listeProduitsCommandes = (TextView) getActivity().findViewById(R.id.listeProduitsCommandes);
                        listeProduitsCommandes.setVisibility(View.VISIBLE);
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
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Ce client n'a passé aucune commande",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new ListeProduits();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container,fragment,"Fragment").commit();
                }
            });
        }
        else
        {
            Fragment fragment = new HomeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment,"Fragment").commit();
            ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_slidermenu);
            ListView mDrawerRightList = (ListView) getActivity().findViewById(R.id.list_Rightslidermenu);
            String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
            if(mDrawerList != null && mDrawerRightList != null) {
                mDrawerList.setItemChecked(0, true);
                mDrawerList.setSelection(0);
                mDrawerRightList.setItemChecked(0,true);
                mDrawerRightList.setSelection(0);
            }
            if(getActivity().getActionBar() != null)
            {
                TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                tx.setText(navMenuTitles[0]);
                getActivity().setTitle(navMenuTitles[0]);
            }
            Toast.makeText(getActivity().getApplicationContext(),"Aucun client existant",Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }

    /**
     * Trie les doublons en gérant les quantitées associée (Méthode récursive)
     * @param nomenclatures Liste de nomenclatures à triée
     */
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

    /**
     * Renvoie le nombre d'occurence dans la liste du nom passé en paramètres
     * @param nomenclatures Liste de nomenclatures
     * @param nom Nom à tester
     * @return Nombre d'occurence dans la liste
     */
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
