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
import java.util.HashMap;
import java.util.List;

import BDD.Client;
import BDD.Commande;
import BDD.DataBaseHandler;
import BDD.Nomenclature;
import adapter.ListeClientAdapter;
import adapter.ListeNomenclatureAdapter;
import barbeasts.plastprod.R;

/**
 * Created by christophe on 01/04/2015. For PlastProd Project on purpose
 */
public class BonCommande extends Fragment {

    public BonCommande() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_bdc,container,false);

        // Je recupere ma liste (la vue)
        final ListView listeView = (ListView) rootView.findViewById(R.id.ListViewInfos);
        // Connexion a la base
        DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        List<Client> clients;
        clients = db.getAllClients();
        if(clients.size() > 0)
        {
            final ListeClientAdapter adapter = new ListeClientAdapter(getActivity().getApplicationContext(),clients);
            listeView.setAdapter(adapter);

            listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Quand je clique sur un client
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final Client c = (Client) listeView.getItemAtPosition(i); // Je récupère le client correspondant au clic
                    TextView tx = (TextView) getActivity().findViewById(R.id.validerCommande); // Récupère la textView qui valide la commande (Agit comme un bouton du coup)
                    tx.setVisibility(View.VISIBLE);
                    listeView.setVisibility(View.INVISIBLE);
                    final ListView listeNomenclature = (ListView) getActivity().findViewById(R.id.ListeNomenclature); // Je récupère la listView de nomenclature
                    listeNomenclature.setVisibility(View.VISIBLE);
                    final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                    final ArrayList<Nomenclature> nomenclatures = db.getAllNomenclature(); // Requête SQL qui va chercher toutes les nomenclatures.
                    final TextView somme = (TextView) getActivity().findViewById(R.id.sommeDevis);
                    final ListeNomenclatureAdapter adapterNomenclature = new ListeNomenclatureAdapter(getActivity().getApplicationContext(), nomenclatures, somme); // Adapter de nomenclature.
                    listeNomenclature.setAdapter(adapterNomenclature);
                    final TextView total = (TextView) getActivity().findViewById(R.id.montant);
                    total.setVisibility(View.VISIBLE);
                    tx.setOnClickListener(new View.OnClickListener() { // Sur le clic de Validation de commande
                        @Override
                        public void onClick(View view) {

                            if (!somme.getText().equals("")) { // Si la somme est != ""
                                HashMap<String,Integer> listeQuantite = adapterNomenclature.getListeStockee();
                                HashMap<String,Nomenclature> listeNomencature = adapterNomenclature.getListeNomenclatureStockee();
                                int[] quantitePicker = new int[nomenclatures.size()];

                                for(int i =0 ; i < nomenclatures.size() ; i++)
                                {
                                    int quantite = 0 ;
                                    Nomenclature n = listeNomencature.get(nomenclatures.get(i).getNom());
                                    if(n != null) // Si ma nomenclature n'est pas nulle c'est qu'elle à une quantité sinon quantite a 0
                                    {
                                        quantite = listeQuantite.get(n.getNom());
                                    }
                                    quantitePicker[i] = quantite;
                                    nomenclatures.get(i).setQuantite(quantite);
                                    System.out.println(" QUANTITE DE LA NOMENCLATURE : " + nomenclatures.get(i).getQuantite() + " DE NOM : " + nomenclatures.get(i).getNom());
                                }

                                if(verifierQuantite(quantitePicker)) // Si toutes les quantitées ne sont pas à zéro
                                {
                                    double prixTotal = Double.parseDouble(somme.getText().toString().replace("€", "")); // Je récupère le total

                                    Commande commande = new Commande(c.getId(), prixTotal, (int) System.currentTimeMillis(), nomenclatures);
                                    long id = db.createCommande(commande);
                                    commande.setId(id);
                                    Fragment fragment = new HomeFragment();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                                    ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_slidermenu);
                                    String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
                                    if (mDrawerList != null) {
                                        mDrawerList.setItemChecked(0, true);
                                        mDrawerList.setSelection(0);
                                    }
                                    if(getActivity().getActionBar() != null)
                                    {
                                        TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                                        tx.setText(navMenuTitles[0]);
                                        getActivity().setTitle(navMenuTitles[0]);
                                        Toast.makeText(getActivity().getApplicationContext(), "Commande crée", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else // Sinon message d'erreur
                                {
                                    Toast.makeText(getActivity().getApplicationContext(),"Toutes les quantités sont à 0 ou vous avez oublié de coché le(s) produit(s) qui à/ont une quantitée positive",Toast.LENGTH_LONG).show();
                                }

                            } else { // Sinon somme non initialisé donc erreur dans la commande (commande non crée).
                                Toast.makeText(getActivity().getApplicationContext(), "Erreur dans la commande", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
            Toast.makeText(getActivity().getApplicationContext(), "Aucun client existant", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

   public boolean verifierQuantite(int[] quantite)
   {
       int compteur = 0;
       for(int i =0 ; i < quantite.length ; i++)
       {
           if(quantite[i] == 0)
               compteur++;
       }

       if(compteur == quantite.length)
       {
           return false;
       }
       return true;
   }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        ListView listeView = (ListView)view.getRootView().findViewById(R.id.ListViewInfos);

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
