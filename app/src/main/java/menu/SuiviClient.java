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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        if(listClient.size() > 0) // SI j'ai des clients alors je peux passer a la suite
        {
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


            final StringBuilder totalCAEntier = new StringBuilder();
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
                    if(listeCommandes.size() > 0)
                    {
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
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Ce client n'as passé aucune commande",Toast.LENGTH_SHORT).show();
                        totalCAClients.setVisibility(View.VISIBLE);
                        totalCAClientsAffiche.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else // Sinon pas de client je redirige vers l'accueil et notifie à l'écran.
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

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        ListView listeView = (ListView)view.getRootView().findViewById(R.id.ListViewSuivi);

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
