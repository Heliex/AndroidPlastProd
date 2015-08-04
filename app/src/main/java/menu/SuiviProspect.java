package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import BDD.DataBaseHandler;
import BDD.Devis;
import BDD.Prospect;
import adapter.ListeProspectAdapter;
import barbeasts.plastprod.R;
import model.MainActivity;

/**
 * Created by Kirill on 12/06/2015.
 */
public class SuiviProspect extends Fragment
{
    public SuiviProspect() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_suiviprospect,container,false);
        // Je recupere la vue de la liste depuis son id.
        final ListView listeProspectSuivi = (ListView) rootView.findViewById(R.id.ListViewSuiviProspect);
        // Je cree la connexion a la base
        final DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
        final Button precendent = (Button)rootView.findViewById(R.id.precedentSuiviProspect);

        // Je recupere ma liste de prospects.
        List<Prospect> listProspects;
        listProspects = db.getAllProspects();

        if(listProspects.size() > 0)
        {
            // adapter pour pouvoir faire du traitement sur le prospect.
            ListeProspectAdapter adapter = new ListeProspectAdapter(getActivity().getApplicationContext(),listProspects,getActivity().getFragmentManager().findFragmentByTag("Fragment"));
            listeProspectSuivi.setAdapter(adapter);

            double totalCAProspect = 0;
            for(int i = 0 ; i < listProspects.size() ; i++)
            {
                Prospect p = listProspects.get(i);
                List<Devis> listeDevis = db.getAllDevisByProspect(p.getId());
                for(int j = 0 ; j < listeDevis.size(); j++)
                {
                    totalCAProspect += listeDevis.get(j).getTotal();
                }
            }

            // Récupération des TextView
            final TextView totalCAProspects = (TextView)rootView.findViewById(R.id.CaTotalProspect);
            final TextView totalCAProspectsAffiche = (TextView) rootView.findViewById(R.id.CaTotalAfficheeProspect);

            final StringBuilder totalCAEntier = new StringBuilder();
            totalCAEntier.append(String.valueOf(totalCAProspect));
            totalCAEntier.append(" €");
            totalCAProspectsAffiche.setText(totalCAEntier);
            // Listener sur la liste pour pouvoir choisir le user a traiter
            listeProspectSuivi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Prospect p = (Prospect)listeProspectSuivi.getItemAtPosition(i);
                    totalCAProspects.setVisibility(View.INVISIBLE);
                    totalCAProspectsAffiche.setVisibility(View.INVISIBLE);
                    List<Devis> listeDevis = db.getAllDevisByProspect(p.getId());
                    precendent.setVisibility(View.VISIBLE);
                    if(listeDevis.size() > 0)
                    {
                        double total = 0;
                        for(int k = 0 ; k < listeDevis.size(); k++)
                        {
                            total+= listeDevis.get(k).getTotal();
                        }

                        String dateDernierDevis = listeDevis.get(listeDevis.size() - 1).getDateDevis();
                        listeProspectSuivi.setVisibility(View.INVISIBLE);
                        TextView CA = (TextView)rootView.findViewById(R.id.CAProspect);
                        TextView DateDernierDevis = (TextView) rootView.findViewById(R.id.dateDerniereCommandeProspect);
                        TextView CAAfficher = (TextView) rootView.findViewById(R.id.CaAfficherProspect);
                        TextView DateDernierDevisAfficher = (TextView) rootView.findViewById(R.id.dateDerniereCommandeAfficheeProspect);

                        CAAfficher.setText(String.valueOf(total) + " €");
                        DateDernierDevisAfficher.setText(dateDernierDevis);

                        CA.setVisibility(View.VISIBLE);
                        DateDernierDevis.setVisibility(View.VISIBLE);
                        CAAfficher.setVisibility(View.VISIBLE);
                        DateDernierDevisAfficher.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Ce prospect n'a fait aucun devis",Toast.LENGTH_SHORT).show();
                        totalCAProspects.setVisibility(View.VISIBLE);
                        totalCAProspectsAffiche.setVisibility(View.VISIBLE);
                    }
                }
            });

            precendent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SuiviProspect();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container,fragment,"Fragment").commit();
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
            Toast.makeText(getActivity().getApplicationContext(),"Aucun prospect trouvé",Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }
}
