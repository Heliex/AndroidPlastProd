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
public class AjoutClient extends Fragment {

    public AjoutClient() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ajout, container, false);
        final Button validerAjout = (Button)rootView.findViewById(R.id.validerAjout);
        validerAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Client client = new Client();
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
                   client.setNom(name);
                   client.setPrenom(prenomVerifier);
                   client.setAdresse(adresseVerifier);
                   client.setTelephone(telephoneVerifier);
                   client.setEmail(emailVerifier);
                   client.setDate(dateVerifier);
               }
               else
               {
                   estValide = false;
               }

               if(estValide) {
                   Log.i("AjoutClient", client.getNom());
                   DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                   long id = db.createClient(client);
                   if(id != -1) {
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
                       Toast.makeText(getActivity().getApplicationContext(), "Client crée", Toast.LENGTH_SHORT).show();
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
