package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
                final TextView somme = (TextView) getActivity().findViewById(R.id.somme);
                final ListeNomenclatureAdapter adapterNomenclature = new ListeNomenclatureAdapter(getActivity().getApplicationContext(), nomenclatures, somme); // Adapter de nomenclature.
                listeNomenclature.setAdapter(adapterNomenclature);
                final TextView total = (TextView) getActivity().findViewById(R.id.montant);
                total.setVisibility(View.VISIBLE);
                tx.setOnClickListener(new View.OnClickListener() { // Sur le clic de Validation de commande
                    @Override
                    public void onClick(View view) {

                        if (!somme.getText().equals("")) { // Si la somme est != ""
                            int[] tabValuePicker = new int[nomenclatures.size()]; // Je crée un tableau d'entier
                            boolean estValide = true; // Un boolean
                            double prixTotal = Double.parseDouble(somme.getText().toString().replace("€", "")); // Je récupère le total
                            ArrayList<Nomenclature> nomenclatures = adapterNomenclature.getNomenclatures(); // Et la liste de nomenclature depuis l'adapter
                            for (int i = 0; i < nomenclatures.size(); i++) { // Pour chaque nomenclatures
                                View childView = listeNomenclature.getChildAt(i); // Je récupère sa vue correspondante
                                if (childView != null) { // Si la vue est pas null
                                    CheckBox box = (CheckBox) childView.findViewById(R.id.checkBox); // JE récupère la checkbox
                                    NumberPicker picker = (NumberPicker) childView.findViewById(R.id.numberPicker); // Et le picker
                                    if (!box.isChecked() && picker.getValue() > 0) { // Si la checbkox est pas coché et la quantité > 0 alors la commande est pas valide
                                        estValide = false;
                                    }
                                    tabValuePicker[i] = picker.getValue(); // On stocke la valeur du picker dans un tableau
                                }
                            }

                            if (allQuantiteA0(tabValuePicker)) { // Test pour savoir si toutes les quantités sont à 0
                                estValide = false;
                            }

                            if (estValide) { // Si la commande est Valide je la crée et je redirige vers l'accueil
                                Commande commande = new Commande(c.getId(), prixTotal, (int) System.currentTimeMillis(), nomenclatures);
                                db.createCommande(commande);
                                Fragment fragment = new HomeFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                                ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_slidermenu);
                                String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
                                if (mDrawerList != null) {
                                    mDrawerList.setItemChecked(0, true);
                                    mDrawerList.setSelection(0);
                                }
                                TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                                tx.setText(navMenuTitles[0]);
                                getActivity().setTitle(navMenuTitles[0]);
                                Toast.makeText(getActivity().getApplicationContext(), "Commande crée", Toast.LENGTH_SHORT).show();


                            } else { // Sinon commande pas valide
                                if (allQuantiteA0(tabValuePicker)) { // Si toutes les quantitées a 0
                                    Toast.makeText(getActivity().getApplicationContext(), "Toutes les quantitées sont à 0", Toast.LENGTH_SHORT).show();
                                } else { // Sinon oubli de cocher un produit avec une quantité positive
                                    Toast.makeText(getActivity().getApplicationContext(), "Vous avez oubliez de coché le(s) produit(s) qui à une quantité positive", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else { // Sinon somme non initialisé donc erreur dans la commande (commande non crée).
                            Toast.makeText(getActivity().getApplicationContext(), "Erreur dans la commande", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        return rootView;
    }

    public boolean allQuantiteA0(int[] valeur)
    {
        for(int i = 0 ; i < valeur.length ; i++)
        {
            if(valeur[i] != 0)
            {
                return false;
            }
        }

        return true;
    }
}
