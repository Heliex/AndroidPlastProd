package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import BDD.DataBaseHandler;
import BDD.Prospect;
import adapter.ListeProspectAdapter;
import barbeasts.plastprod.R;

/**
 * Created by Kirill on 12/06/2015.
 */
public class SuiviProspect extends Fragment
{
    public SuiviProspect() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_suivi,container,false);
        // Je recupere la vue de la liste depuis son id.
        final ListView listeProduits = (ListView) rootView.findViewById(R.id.ListViewSuivi);
        // Je cree la connexion a la base
        DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        // Je recupere ma liste de client.
        List<Prospect> listProspects;
        listProspects = db.getAllProspects();

        // adapter pour pouvoir faire du traitement sur le client.
        ListeProspectAdapter adapter = new ListeProspectAdapter(getActivity().getApplicationContext(),listProspects);
        listeProduits.setAdapter(adapter);

        // Listener sur la liste pour pouvoir choisir le user a traiter
        listeProduits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prospect c = (Prospect)listeProduits.getItemAtPosition(i);
            }
        });
        return rootView;
    }
}
