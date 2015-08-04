package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import BDD.Client;
import BDD.DataBaseHandler;
import barbeasts.plastprod.R;

/**
 * Created by christophe on 01/04/2015.
 */
public class ModifierClient extends Fragment {

    public ModifierClient() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ajout, container, false);
        final Button modifierClient = (Button)rootView.findViewById(R.id.validerAjout);
        modifierClient.setText("Modifier");

        final EditText editTextNom = (EditText) rootView.findViewById(R.id.TextNom);
        final EditText editTextPrenom = (EditText) rootView.findViewById(R.id.TextPrenom);
        final EditText editTextAdresse = (EditText) rootView.findViewById(R.id.TextAdresse);
        final EditText editTextTelephone = (EditText) rootView.findViewById(R.id.TextTel);
        final EditText editTextEmail = (EditText) rootView.findViewById(R.id.TextEmail);
        final EditText editTextDate = (EditText) rootView.findViewById(R.id.TextDate);
        final Button annuler = (Button) rootView.findViewById(R.id.annulerAjout);
        final long id = getArguments().getLong("IDClient");
        String nom = getArguments().getString("NomClient");
        String prenom = getArguments().getString("PrenomClient");
        String adresse = getArguments().getString("AdresseClient");
        String telephone = getArguments().getString("TelephoneClient");
        String email = getArguments().getString("EmailClient");
        String date = getArguments().getString("DateClient");

        editTextNom.setText(nom);
        editTextPrenom.setText(prenom);
        editTextAdresse.setText(adresse);
        editTextTelephone.setText(telephone);
        editTextEmail.setText(email);
        editTextDate.setText(date);

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Toast.makeText(getActivity().getApplicationContext(), "Modification annulée", Toast.LENGTH_SHORT).show();
            }
        });

        modifierClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                String nom = editTextNom.getText().toString();
                String prenom = editTextPrenom.getText().toString();
                String adresse = editTextAdresse.getText().toString();
                String telephone = editTextTelephone.getText().toString();
                String email = editTextEmail.getText().toString();
                String date = editTextDate.getText().toString();

                Client c = new Client();
                c.setId(id);
                c.setNom(nom);
                c.setPrenom(prenom);
                c.setAdresse(adresse);
                c.setTelephone(telephone);
                c.setEmail(email);
                c.setDate(date);

                if (db.updateClient(c) > 0) {
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
                    Toast.makeText(getActivity().getApplicationContext(), "Modification effectuée avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur lors de la modification", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }
}
