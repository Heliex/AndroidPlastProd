package menu;

import android.app.Activity;
import android.app.Fragment;
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

import BDD.DataBaseHandler;
import BDD.Devis;
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
        List<Prospect> prospects;
        prospects = db.getAllProspects();

        if(prospects.size() > 0)
        {
            final ListeProspectAdapter adapter = new ListeProspectAdapter(getActivity().getApplicationContext(),prospects);
            listeProspects.setAdapter(adapter);

            listeProspects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// Quand on clique sur un prospect
                    listeProspects.setVisibility(View.INVISIBLE);
                    final Prospect prospect = (Prospect)listeProspects.getItemAtPosition(position); // Récupère le prospect.
                    final List<Devis> listeDevis = db.getAllDevisFromIdProspect(prospect.getId()); // Récupère la liste de devis lié à ce prospect.

                    // Test ici.
                    MainActivity main = (MainActivity)getActivity();
                    ArrayList<String> result = new ArrayList<String>();
                    if(main.getCredential().getSelectedAccountName() == null)
                    {
                        main.chooseAccount();
                        if(main.isDeviceOnline())
                        {
                            main.callApiAsyncTask();
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
                             main.callApiAsyncTask();
                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"PAS DE CONNECTION MD R",Toast.LENGTH_SHORT).show();
                        }
                    }


                    ListeDevisAdapter adapter = new ListeDevisAdapter(getActivity().getApplicationContext(),listeDevis);
                    final ListView listeDevisAffichage = (ListView) rootView.findViewById(R.id.ListeDevis); // Récupère la listview qui affiche les devis.
                    listeDevisAffichage.setAdapter(adapter);
                    listeDevisAffichage.setVisibility(View.VISIBLE);

                    listeDevisAffichage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// Quand on clique sur un devis mdr.
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
                                public void onClick(View v) {
                                    Toast.makeText(getActivity().getApplicationContext(),"PLUS QUA CODER LA VALIDATION LOL",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            });
        }

        return rootView;
    }



}
