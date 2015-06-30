package menu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        ListView listeView = (ListView)view.getRootView().findViewById(R.id.listView);

        listeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                List<Client> clients = db.getAllClients();
                ListeClientAdapter listeClientAdapter = new ListeClientAdapter(getActivity().getApplicationContext(),clients);
                final Client c = listeClientAdapter.getItem(i);
                // Titre du dialog
                alertDialogBuilder.setTitle("Client");

                // Set message
                alertDialogBuilder.setMessage("Que voulez-vous faire de ce client ?").setCancelable(true).setNegativeButton("Modifier", new DialogInterface.OnClickListener() { // Message
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("NomClient", c.getNom());
                        bundle.putLong("IDClient", c.getId());
                        bundle.putString("PrenomClient", c.getPrenom());
                        bundle.putString("AdresseClient", c.getAdresse());
                        bundle.putString("TelephoneClient", c.getTelephone());
                        bundle.putString("EmailClient",c.getEmail());
                        bundle.putString("DateClient", c.getDate());

                        Fragment fragment = new ModifierClient();
                        fragment.setArguments(bundle);
                        if(getActivity().getActionBar() != null)
                        {
                            String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
                            TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                            tx.setText(navMenuTitles[7]);
                            getActivity().setTitle(navMenuTitles[7]);
                        }

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "Fragment").commit();
                        // Ces deux lignes permettent de remplacer un fragment par un autre.

                    }
                }).setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.cancel();
                    }
                }).show();

                return true;
            }
        });

    }
}
