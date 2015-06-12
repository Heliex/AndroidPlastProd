package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import BDD.Client;
import BDD.Commande;
import BDD.DataBaseHandler;
import adapter.ListeClientAdapter;
import barbeasts.plastprod.R;

/**
 * Created by christophe on 01/04/2015.
 */
public class BonCommande extends Fragment {

    public BonCommande() {}

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

                Client c = (Client)listeView.getItemAtPosition(i);
                listeView.setVisibility(View.INVISIBLE);

                EditText montant = (EditText) rootView.findViewById(R.id.montantCommande);
                Button b = (Button) rootView.findViewById(R.id.validerCommande);

                b.setVisibility(View.VISIBLE);
                montant.setVisibility(View.VISIBLE);
                Button button = new Button(getActivity().getApplicationContext());
                button.setText("Precedent");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new BonCommande();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                });
                RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.layoutbdc);
                rl.removeAllViewsInLayout();
                rl.addView(montant);
                rl.addView(b);
                rl.addView(button);

            }
        });
        return rootView;
    }
}
