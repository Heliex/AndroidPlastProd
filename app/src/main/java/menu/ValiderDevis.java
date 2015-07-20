package menu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.ThreadPoolExecutor;

import BDD.Client;
import BDD.Commande;
import BDD.DataBaseHandler;
import BDD.Devis;
import BDD.Nomenclature;
import BDD.Prospect;
import adapter.ListeDevisAdapter;
import adapter.ListeProspectAdapter;
import barbeasts.plastprod.R;
import model.MainActivity;
import other.ApiAsyncTask;

/**
 * Created by Christophe on 08/07/2015. For PlastProd Project on purpose
 */
public class ValiderDevis extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_validerdevis, container, false);
        final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
        final ListView listeProspects = (ListView)rootView.findViewById(R.id.ListViewProspectDevisValider);
        if(((MainActivity)getActivity()).getCredential().getSelectedAccountName() == null)
        {
            ((MainActivity)getActivity()).chooseAccount();
        }
        List<Prospect> prospects;
        prospects = db.getAllProspects();
        if(prospects.size() > 0)
        {
            final ListeProspectAdapter adapter = new ListeProspectAdapter(getActivity().getApplicationContext(),prospects,getActivity().getFragmentManager().findFragmentByTag("Fragment"));
            listeProspects.setAdapter(adapter);

            listeProspects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// Quand on clique sur un prospect
                    listeProspects.setVisibility(View.INVISIBLE);
                    final Prospect prospect = (Prospect)listeProspects.getItemAtPosition(position); // Récupère le prospect.

                    final List<Devis> listeDevis = db.getAllDevisFromIdProspect(prospect.getId()); // Récupère la liste de devis lié à ce prospect.
                    if(listeDevis.size() > 0)
                    {
                        // Trouver un moyen d'attendre la fin de l'execution de la tache asynchrone.
                        final ListeDevisAdapter adapter = new ListeDevisAdapter(getActivity().getApplicationContext(),listeDevis);
                        final ListView listeDevisAffichage = (ListView) rootView.findViewById(R.id.ListeDevis); // Récupère la listview qui affiche les devis.
                        listeDevisAffichage.setAdapter(adapter);

                        // Test ici.
                        MainActivity main = (MainActivity)getActivity();
                        ArrayList<String> result = new ArrayList<String>();
                        if(main.getCredential().getSelectedAccountName() == null)
                        {
                            main.chooseAccount();
                            if(main.isDeviceOnline())
                            {
                                main.callApiAsyncTask(listeDevis);

                            }
                            else
                            {
                                Toast.makeText(getActivity().getApplicationContext(),"PAS DE CONNECTION MD R",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            if(main.isDeviceOnline())
                            {
                                main.callApiAsyncTask(listeDevis);
                            }
                            else
                            {
                                Toast.makeText(getActivity().getApplicationContext(),"PAS DE CONNECTION MD R",Toast.LENGTH_SHORT).show();
                            }
                        }


                        listeDevisAffichage.setVisibility(View.VISIBLE);
                        listeDevisAffichage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {// Quand on clique sur un devis mdr.
                                listeDevisAffichage.setVisibility(View.INVISIBLE);

                                Devis devis = (Devis)listeDevisAffichage.getItemAtPosition(position);
                                String details = db.getDetailsDevisFromIdDevis(devis.getId(),devis.getTotal(),devis.getId_prospect(),devis.getNumDevis(),false);

                                ScrollView scrollView = (ScrollView)rootView.findViewById(R.id.scrollViewDevis);
                                scrollView.setVisibility(View.VISIBLE);
                                TextView recapitulatif = (TextView)rootView.findViewById(R.id.recapitulatifDevis);
                                recapitulatif.setVisibility(View.VISIBLE);
                                recapitulatif.setText(details);
                                Button b = (Button) rootView.findViewById(R.id.ValiderDevisFinal);
                                b.setVisibility(View.VISIBLE);
                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {// Quand on valide le devis.
                                        Devis devis = adapter.getItem(position);
                                        ArrayList<Nomenclature> listeNomenclature = db.getAllNomenclatureFromIdProspect(prospect.getId());
                                        devis.setListeNomenclatures(listeNomenclature);
                                        // C'est ici que je vais convertir mon devis en commande et mon prospect en client.
                                        convertProspectToClientAndDevisToCommande(devis, prospect, db);

                                        // Changement de fragment
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
                                            Toast.makeText(getActivity().getApplicationContext(), "Devis validé avec succès, Prospect converti en client et Devis converti en commande", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else
                    {
                        ((MainActivity)getActivity()).displayView(0);
                        String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
                        if(getActivity().getActionBar() != null)
                        {
                            TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                            tx.setText(navMenuTitles[0]);
                        }
                        Toast.makeText(getActivity().getApplicationContext(),"Aucun devis trouvé",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            ((MainActivity)getActivity()).displayView(0);
            String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
            if(getActivity().getActionBar() != null)
            {
                TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                tx.setText(navMenuTitles[0]);
            }
            Toast.makeText(getActivity().getApplicationContext(),"Aucun prospect existant",Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }


    public void convertProspectToClientAndDevisToCommande(Devis devis, Prospect prospect,DataBaseHandler db)
    {
        // Conversion du Prospect en Client
        Client client = new Client(prospect.getNom(),prospect.getPrenom(),prospect.getAdresse(),prospect.getEmail(),prospect.getTelephone(),prospect.getDate());
        long idClient = db.createClient(client);
        System.out.println("ID DU CLIENT : " + idClient);
        client.setId(idClient);
        // Conversion du Devis en Commande.

        Commande commande = new Commande(client.getId(),devis.getNumDevis(),devis.getTotal(),devis.getDateDevis(),devis.getListeNomenclatures());
        long id = db.createCommande(commande);
        commande.setId(id);
        // Maintenant que j'ai toute les données qu'il faut je peux supprimer de la base le prospect et le devis.
        db.removeProspect(prospect.getId());
        db.removeDevis(devis.getId(),prospect.getId());
    }
}
