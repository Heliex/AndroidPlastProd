package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
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
        ListeClientAdapter adapter = new ListeClientAdapter(getActivity().getApplicationContext(),clients);
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
                final  DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                ArrayList<Nomenclature> nomenclatures = db.getAllNomenclature();
                final TextView somme = (TextView) getActivity().findViewById(R.id.somme);
                final ListeNomenclatureAdapter adapterNomenclature = new ListeNomenclatureAdapter(getActivity().getApplicationContext(), nomenclatures,somme);
                listeNomenclature.setAdapter(adapterNomenclature);
                final TextView total = (TextView) getActivity().findViewById(R.id.montant);
                total.setVisibility(View.VISIBLE);
                tx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       double prixTotal = Double.parseDouble(somme.getText().toString().replace("€", ""));
                       ArrayList<Nomenclature> listeNomenclatures = adapterNomenclature.getNomenclatures();
                       trierListeNomenclature(listeNomenclatures);
                       Commande commande = new Commande(c.getId(),prixTotal,(int)System.currentTimeMillis(),listeNomenclatures);
                       db.createCommande(commande);
                       Toast.makeText(getActivity().getApplicationContext(),"Commande crée",Toast.LENGTH_SHORT).show();
                    }
                });
                
            }
        });
        return rootView;
    }


    public static void trierListeNomenclature(ArrayList<Nomenclature> listeNomenclatures)
    {
        for(int i = 0; i < listeNomenclatures.size() ; i++)
        {
            if(listeNomenclatures.get(i).getQuantite() == 0)
            {
                listeNomenclatures.remove(listeNomenclatures.get(i));
                i= 0;
            }
        }
    }
}
