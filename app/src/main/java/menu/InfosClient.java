package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import adapter.ListeClientAdapter;
import barbeasts.plastprod.R;

/**
 * Created by christophe on 01/04/2015.
 */
public class InfosClient extends Fragment {

    public InfosClient() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_infos,container,false);

        // Je récupère ma liste (la vue)
        final ListView listeView = (ListView) rootView.findViewById(R.id.ListViewInfos);
        // Connexion à la base
        DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        List<Client> clients;
        clients = db.getAllClients();
        if(clients.size() > 0)
        {
            ListeClientAdapter adapter = new ListeClientAdapter(getActivity().getApplicationContext(),clients);
            listeView.setAdapter(adapter);

            listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Client c = (Client)listeView.getItemAtPosition(i);
                    listeView.setVisibility(View.INVISIBLE);

                    // Je récupère toutes les TextView
                    TextView infosNom = (TextView)rootView.findViewById(R.id.infosNom);
                    TextView infosPrenom = (TextView)rootView.findViewById(R.id.infosPrenom);
                    TextView infosEmail = (TextView)rootView.findViewById(R.id.infosEmail);
                    TextView infosAdresse = (TextView)rootView.findViewById(R.id.infosAdresse);
                    TextView infosTelephone = (TextView)rootView.findViewById(R.id.infosTelephone);
                    TextView infosDate = (TextView) rootView.findViewById(R.id.infosDate);

                    // Ensuite je leur attribue les données.
                    infosNom.setText(c.getNom());
                    infosPrenom.setText(c.getPrenom());
                    infosEmail.setText(c.getEmail());
                    infosAdresse.setText(c.getAdresse());
                    infosTelephone.setText(c.getTelephone());
                    infosDate.setText(c.getDate());

                    // ET enfin j'affiche le tout.
                    infosNom.setVisibility(View.VISIBLE);
                    infosPrenom.setVisibility(View.VISIBLE);
                    infosEmail.setVisibility(View.VISIBLE);
                    infosAdresse.setVisibility(View.VISIBLE);
                    infosTelephone.setVisibility(View.VISIBLE);
                    infosDate.setVisibility(View.VISIBLE);
                    RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.listeinfos);
                    rl.removeAllViewsInLayout();
                    Button b = new Button(getActivity().getApplicationContext());
                    rl.addView(infosNom);
                    rl.addView(infosPrenom);
                    rl.addView(infosEmail);
                    rl.addView(infosAdresse);
                    rl.addView(infosTelephone);
                    rl.addView(infosDate);
                    b.setText("Precedent");
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Fragment fragment = new InfosClient();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();

                        }
                    });
                    rl.addView(b);


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
            Toast.makeText(getActivity().getApplicationContext(), "Aucun client existant", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }
}
