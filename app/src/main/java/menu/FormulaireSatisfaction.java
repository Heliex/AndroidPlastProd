package menu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import barbeasts.plastprod.R;

/**
 * Created by christophe on 01/04/2015.
 */
public class FormulaireSatisfaction extends Fragment {

    public FormulaireSatisfaction() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_formulaire,container,false);
        final Button envoiForm = (Button) rootView.findViewById(R.id.envoiForm);
        envoiForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoiForm.setText("Clicked");
            }
        });
        return rootView;
    }
}
