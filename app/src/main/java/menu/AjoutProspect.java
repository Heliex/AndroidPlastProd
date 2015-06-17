package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import BDD.DataBaseHandler;
import BDD.Prospect;
import barbeasts.plastprod.R;

/**
 * Created by Kirill on 11/06/2015.
 */
public class AjoutProspect extends Fragment
{
    public AjoutProspect() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ajout, container, false);
        final Button validerAjout = (Button)rootView.findViewById(R.id.validerAjout);
        validerAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prospect prospect = new Prospect();
                EditText nom = (EditText)rootView.findViewById(R.id.TextNom);
                EditText prenom = (EditText) rootView.findViewById(R.id.TextPrenom);
                EditText adresse = (EditText) rootView.findViewById(R.id.TextAdresse);
                EditText telephone = (EditText) rootView.findViewById(R.id.TextTel);
                EditText email = (EditText) rootView.findViewById(R.id.TextEmail);

                prospect.setNom(nom.getText().toString());
                prospect.setPrenom(prenom.getText().toString());
                prospect.setAdresse(adresse.getText().toString());
                prospect.setTelephone(telephone.getText().toString());
                prospect.setEmail(email.getText().toString());
                Log.e("AjoutProspect", prospect.getNom());
                if(nom != null && nom.length() > 0) {
                    DataBaseHandler db = new DataBaseHandler(getActivity().getApplicationContext());
                    long id = db.createProspect(prospect);
                    if(id != -1) {

                        // Afficher la notif
                        TextView tx = (TextView) rootView.findViewById(R.id.Notif);
                        tx.setText("Prospect cree.");
                    }
                }
            }
        });
        return rootView;
    }
}
