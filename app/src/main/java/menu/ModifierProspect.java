package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
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
import BDD.Prospect;
import barbeasts.plastprod.R;

/**
 * Created by Kirill on 01/07/2015.
 */
public class ModifierProspect extends Fragment
{
    public ModifierProspect() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ajout, container, false);
        final Button modifierProspect = (Button)rootView.findViewById(R.id.validerAjout);
        final Button annuler = (Button) rootView.findViewById(R.id.annulerAjout);
        modifierProspect.setText("Modifier");

        final EditText editTextNom = (EditText) rootView.findViewById(R.id.TextNom);
        final EditText editTextPrenom = (EditText) rootView.findViewById(R.id.TextPrenom);
        final EditText editTextAdresse = (EditText) rootView.findViewById(R.id.TextAdresse);
        final EditText editTextTelephone = (EditText) rootView.findViewById(R.id.TextTel);
        final EditText editTextEmail = (EditText) rootView.findViewById(R.id.TextEmail);
        final EditText editTextDate = (EditText) rootView.findViewById(R.id.TextDate);

        final long id = getArguments().getLong("IDProspect");
        String nom = getArguments().getString("NomProspect");
        String prenom = getArguments().getString("PrenomProspect");
        String adresse = getArguments().getString("AdresseProspect");
        String telephone = getArguments().getString("TelephoneProspect");
        String email = getArguments().getString("EmailProspect");
        String date = getArguments().getString("DateProspect");
        final int pourcentage = getArguments().getInt("Pourcentage");

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

        modifierProspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                String nom = editTextNom.getText().toString();
                String prenom = editTextPrenom.getText().toString();
                String adresse = editTextAdresse.getText().toString();
                String telephone = editTextTelephone.getText().toString();
                String email = editTextEmail.getText().toString();
                String date = editTextDate.getText().toString();

                Prospect p = new Prospect();
                p.setId(id);
                p.setNom(nom);
                p.setPrenom(prenom);
                p.setAdresse(adresse);
                p.setTelephone(telephone);
                p.setEmail(email);
                p.setDate(date);
                p.setPourcentage(pourcentage);
                if (db.updateProspect(p) > 0) {
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
