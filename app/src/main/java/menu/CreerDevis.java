package menu;

import android.app.Fragment;
import android.app.FragmentManager;
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

import BDD.DataBaseHandler;
import BDD.Devis;
import BDD.Nomenclature;
import BDD.Prospect;
import adapter.ListeNomenclatureAdapter;
import adapter.ListeProspectAdapter;
import barbeasts.plastprod.R;

/**
 * Created by Christophe on 06/07/2015. For PlastProd Project on purpose
 * Classe qui représente le menu Faire un devis
 * @author Christophe Gerard
 * @version 1.0
 */
public class CreerDevis extends Fragment{

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
        final View rootView = inflater.inflate(R.layout.fragment_devis, container, false);

        // Je recupere ma liste (la vue)
        final ListView listeView = (ListView) rootView.findViewById(R.id.ListViewProspectDevis);
        // Connexion a la base
        DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        List<Prospect> prospects;
        prospects = db.getAllProspects();
        if(prospects.size() > 0)
        {
            final ListeProspectAdapter adapter = new ListeProspectAdapter(getActivity().getApplicationContext(),prospects,getActivity().getFragmentManager().findFragmentByTag("Fragment"));
            listeView.setAdapter(adapter);

            listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Quand je clique sur un client
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final Prospect p = (Prospect) listeView.getItemAtPosition(i); // Je récupère le client correspondant au clic
                    TextView tx = (TextView) getActivity().findViewById(R.id.validerDevis); // Récupère la textView qui valide la commande (Agit comme un bouton du coup)
                    tx.setVisibility(View.VISIBLE);
                    listeView.setVisibility(View.INVISIBLE);
                    final ListView listeNomenclature = (ListView) getActivity().findViewById(R.id.ListeNomenclatureDevis); // Je récupère la listView de nomenclature
                    listeNomenclature.setVisibility(View.VISIBLE);
                    final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                    final ArrayList<Nomenclature> nomenclatures = db.getAllNomenclature(); // Requête SQL qui va chercher toutes les nomenclatures.
                    final TextView somme = (TextView) getActivity().findViewById(R.id.sommeDevis);
                    final ListeNomenclatureAdapter adapterNomenclature = new ListeNomenclatureAdapter(getActivity().getApplicationContext(), nomenclatures, somme); // Adapter de nomenclature.
                    listeNomenclature.setAdapter(adapterNomenclature);
                    final TextView total = (TextView) getActivity().findViewById(R.id.montantDevis);
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
                                    int numDevis = (int)System.currentTimeMillis();
                                    if(numDevis < 0)
                                        numDevis = numDevis * (-1);
                                    Devis devis = new Devis(p.getId(), prixTotal, numDevis, nomenclatures);
                                    long id = db.createDevis(devis);
                                    devis.setId(id);
                                    p.setPourcentage(Devis.getDevisInteret());
                                    db.updateProspect(p);
                                    db.getDetailsDevisFromIdDevis(devis.getId(), prixTotal, p.getId(),devis.getNumDevis(),true);

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
                                        Toast.makeText(getActivity().getApplicationContext(), "Devis crée", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else // Sinon message d'erreur
                                {
                                    Toast.makeText(getActivity().getApplicationContext(),"Toutes les quantités sont à 0 ou vous avez oublié de coché le(s) produit(s) qui à/ont une quantitée positive",Toast.LENGTH_LONG).show();
                                }

                            } else { // Sinon somme non initialisé donc erreur dans la commande (commande non crée).
                                Toast.makeText(getActivity().getApplicationContext(), "Erreur dans le devis", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity().getApplicationContext(), "Aucun prospect existant", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    /**
     * Vérifie que les quantitées cochées ne sont pas à 0
     * @param quantite Tableau de quantitée à vérifier
     * @return un booléen
     */
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
}
