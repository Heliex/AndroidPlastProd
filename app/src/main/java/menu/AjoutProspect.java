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

import BDD.DataBaseHandler;
import BDD.Devis;
import BDD.Prospect;
import barbeasts.plastprod.R;

/**
 * Created by Kirill on 11/06/2015.
 * Classe qui représente le Menu AjoutProspect.
 * @author Kirill Safronov
 * @version 1.0
 */
public class AjoutProspect extends Fragment
{
    public AjoutProspect() {}

    /**
     * Création de la vue pour ce menu
     * @param inflater Zone à crée
     * @param container Vue parent
     * @param savedInstanceState Etat du bundle à la création de la vue
     * @return un objet View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ajout, container, false);
        final Button validerAjout = (Button)rootView.findViewById(R.id.validerAjout);
        final Button annulerAjout = (Button) rootView.findViewById(R.id.annulerAjout);
        annulerAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
                ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_slidermenu);
                String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
                if(mDrawerList != null) {
                    mDrawerList.setItemChecked(0, true);
                    mDrawerList.setSelection(0);
                }
                if(getActivity().getActionBar() != null)
                {
                    TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                    tx.setText(navMenuTitles[0]);
                    getActivity().setTitle(navMenuTitles[0]);
                }
                Toast.makeText(getActivity().getApplicationContext(), "Création de Prospect annulée", Toast.LENGTH_SHORT).show();
            }
        });
        validerAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prospect prospect = new Prospect();
                boolean estValide = true;
                EditText nom = (EditText)rootView.findViewById(R.id.TextNom);
                EditText prenom = (EditText) rootView.findViewById(R.id.TextPrenom);
                EditText adresse = (EditText) rootView.findViewById(R.id.TextAdresse);
                EditText telephone = (EditText) rootView.findViewById(R.id.TextTel);
                EditText email = (EditText) rootView.findViewById(R.id.TextEmail);
                EditText date = (EditText) rootView.findViewById(R.id.TextDate);

                String name = nom.getText().toString();
                String prenomVerifier = prenom.getText().toString();
                String adresseVerifier = adresse.getText().toString();
                String telephoneVerifier = telephone.getText().toString();
                String emailVerifier = email.getText().toString();
                String dateVerifier = date.getText().toString();

                String patternNom = "[a-zA-ZéèêëàâäîïôöûüùÉÈËÊÁÂÀÄÏÎÖÔÛÜÙÚ _-]{1,255}";
                String patternAdresse = "[a-zA-ZéèêëàâäîïôöûüùÉÈËÊÁÂÀÄÏÎÖÔÛÜÙÚ0-9 _-]{1,255}";
                String patternTelephone = "[\\d]{10}";
                String patternEmail = "^[-+.\\w]{1,64}@[-.\\w]{1,64}\\.[-.\\w]{2,6}$";
                String patternDate = "[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}";

                 /* Vérification des champs */
                if(name.matches(patternNom) && prenomVerifier.matches(patternNom) && adresseVerifier.matches(patternAdresse) && telephoneVerifier.matches(patternTelephone) && emailVerifier.matches(patternEmail) && dateVerifier.matches(patternDate) ) // Si le nom est le prénom ne contiennent que des caractères.
                {
                    prospect.setNom(name);
                    prospect.setPrenom(prenomVerifier);
                    prospect.setAdresse(adresseVerifier);
                    prospect.setTelephone(telephoneVerifier);
                    prospect.setEmail(emailVerifier);
                    prospect.setDate(dateVerifier);
                    prospect.setPourcentage(Devis.getDevisDemandeClient());
                }
                else
                {
                    estValide = false;
                }


                if(estValide) {
                    Log.i("AjoutProspect", prospect.getNom());
                    DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                    long id = db.createProspect(prospect);
                    if(id != -1) {
                        prospect.setId(id);
                        Fragment fragment = new HomeFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
                        ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_slidermenu);
                        String[] navMenuTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
                        if(mDrawerList != null) {
                            mDrawerList.setItemChecked(0, true);
                            mDrawerList.setSelection(0);
                        }
                        TextView tx = (TextView)getActivity().getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                        tx.setText(navMenuTitles[0]);
                        getActivity().setTitle(navMenuTitles[0]);
                        Toast.makeText(getActivity().getApplicationContext(), "Prospect crée", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Erreur lors de la validation ??",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Erreur de saisie",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}
