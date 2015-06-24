package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
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
 * Created by christophe on 01/04/2015.
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

        listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Client c = (Client) listeView.getItemAtPosition(i);
                TextView tx = (TextView) getActivity().findViewById(R.id.validerCommande);
                tx.setVisibility(View.VISIBLE);
                listeView.setVisibility(View.INVISIBLE);
                final ListView listeNomenclature = (ListView) getActivity().findViewById(R.id.ListeNomenclature);
                listeNomenclature.setVisibility(View.VISIBLE);
                final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                final ArrayList<Nomenclature> nomenclatures = db.getAllNomenclature();
                final TextView somme = (TextView) getActivity().findViewById(R.id.somme);
                final ListeNomenclatureAdapter adapterNomenclature = new ListeNomenclatureAdapter(getActivity().getApplicationContext(), nomenclatures, somme);
                listeNomenclature.setAdapter(adapterNomenclature);
                final TextView total = (TextView) getActivity().findViewById(R.id.montant);
                total.setVisibility(View.VISIBLE);
                tx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!somme.getText().equals("")) {
                            boolean estValide = true;
                            double prixTotal = Double.parseDouble(somme.getText().toString().replace("€", ""));
                            ArrayList<Nomenclature> nomenclatures = adapterNomenclature.getNomenclatures();
                            for (int i = 0; i < nomenclatures.size(); i++) {
                                View childView = listeNomenclature.getChildAt(i);
                                if (childView != null) {
                                    CheckBox box = (CheckBox) childView.findViewById(R.id.checkBox);
                                    NumberPicker picker = (NumberPicker) childView.findViewById(R.id.numberPicker);
                                    if (!box.isChecked() && picker.getValue() > 0) {
                                        estValide = false;
                                    }
                                }
                            }
                            if (estValide) {
                                Commande commande = new Commande(c.getId(), prixTotal, (int) System.currentTimeMillis(), nomenclatures);
                                db.createCommande(commande);
                                Toast.makeText(getActivity().getApplicationContext(), "Commande crée", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),"Vous avez oubliez de coché le produit qui à une quantité positive",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Erreur dans la commande", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        return rootView;
    }
}
