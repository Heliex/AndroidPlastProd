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
               EditText nom = (EditText)rootView.findViewById(R.id.TextNom);
               EditText prenom = (EditText) rootView.findViewById(R.id.TextPrenom);
               EditText adresse = (EditText) rootView.findViewById(R.id.TextAdresse);
               EditText telephone = (EditText) rootView.findViewById(R.id.TextTel);
               EditText email = (EditText) rootView.findViewById(R.id.TextEmail);

               client.setNom(nom.getText().toString());
               client.setPrenom(prenom.getText().toString());
               client.setAdresse(adresse.getText().toString());
               client.setTelephone(telephone.getText().toString());
               client.setEmail(email.getText().toString());
                Log.i("AjoutClient",client.getNom());
               if(nom != null && nom.length() > 0) {
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
                       getActivity().setTitle(navMenuTitles[0]);
                       Toast.makeText(getActivity().getApplicationContext(), "Client crée", Toast.LENGTH_SHORT).show();
                    }
               }
            }
        });
        return rootView;
    }
}
