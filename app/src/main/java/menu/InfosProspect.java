package menu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import BDD.Prospect;
import adapter.ListeClientAdapter;
import adapter.ListeProspectAdapter;
import barbeasts.plastprod.R;

/**
 * Created by Kirill on 30/06/2015.
 */
public class InfosProspect extends Fragment
{
    public InfosProspect() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_infos,container,false);

        // Je récupère ma liste (la vue)
        final ListView listeView = (ListView) rootView.findViewById(R.id.ListViewInfos);
        // Connexion à la base
        DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());

        List<Prospect> prospects;
        prospects = db.getAllProspects();
        if(prospects.size() > 0)
        {
            ListeProspectAdapter adapter = new ListeProspectAdapter(getActivity().getApplicationContext(),prospects,getActivity().getFragmentManager().findFragmentByTag("Fragment"));
            listeView.setAdapter(adapter);

            listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Prospect p = (Prospect)listeView.getItemAtPosition(i);
                    listeView.setVisibility(View.INVISIBLE);

                    // Je récupère toutes les TextView
                    TextView infosNom = (TextView)rootView.findViewById(R.id.infosNom);
                    TextView infosPrenom = (TextView)rootView.findViewById(R.id.infosPrenom);
                    TextView infosEmail = (TextView)rootView.findViewById(R.id.infosEmail);
                    TextView infosAdresse = (TextView)rootView.findViewById(R.id.infosAdresse);
                    TextView infosTelephone = (TextView)rootView.findViewById(R.id.infosTelephone);
                    TextView infosDate = (TextView) rootView.findViewById(R.id.infosDate);

                    // Ensuite je leur attribue les données.
                    infosNom.setText(p.getNom());
                    infosPrenom.setText(p.getPrenom());
                    infosEmail.setText(p.getEmail());
                    infosAdresse.setText(p.getAdresse());
                    infosTelephone.setText(p.getTelephone());
                    infosDate.setText(p.getDate());

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
                            Fragment fragment = new InfosProspect();
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
            Toast.makeText(getActivity().getApplicationContext(), "Aucun prospect existant", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        ListView listeView = (ListView)view.getRootView().findViewById(R.id.ListViewInfos);

        listeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                List<Prospect> prospects = db.getAllProspects();
                ListeProspectAdapter listeProspectAdapter = new ListeProspectAdapter(getActivity().getApplicationContext(),prospects,getActivity().getFragmentManager().findFragmentByTag("Fragment"));
                final Prospect p = listeProspectAdapter.getItem(i);
                // Titre du dialog
                alertDialogBuilder.setTitle("Prospect");

                // Set message
                alertDialogBuilder.setMessage("Que voulez-vous faire de ce prospect ?").setCancelable(true).setNegativeButton("Modifier", new DialogInterface.OnClickListener() { // Message
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("NomProspect", p.getNom());
                        bundle.putLong("IDProspect", p.getId());
                        bundle.putString("PrenomProspect", p.getPrenom());
                        bundle.putString("AdresseProspect", p.getAdresse());
                        bundle.putString("TelephoneProspect", p.getTelephone());
                        bundle.putString("EmailProspect",p.getEmail());
                        bundle.putString("DateProspect", p.getDate());

                        Fragment fragment = new ModifierProspect();
                        fragment.setArguments(bundle);
                        if(getActivity().getActionBar() != null)
                        {
                            String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_Right_items);
                            TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                            tx.setText(navMenuTitles[6]);
                            getActivity().setTitle(navMenuTitles[6]);
                        }

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "Fragment").commit();
                        // Ces deux lignes permettent de remplacer un fragment par un autre.

                    }
                }).setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.cancel();
                    }
                }).show();

                return true;
            }
        });

    }
}
