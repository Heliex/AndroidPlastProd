package menu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import barbeasts.plastprod.R;
import other.GmailSender;

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

                EditText editText = (EditText) getActivity().findViewById(R.id.editText2);
                String mail = editText.getText().toString();
                try {
                    GmailSender gmail = new GmailSender("christophe.gerard8@gmail.com", "chris88110");
                    gmail.sendMail("Enquête de satisfaction","Nous vous demandons 5 minutes de votre temps pour consulter ce formulaire : https://docs.google.com/forms/d/1DGX6i1U-1kPehjOEf3uDdkanB6eu07p8gLKvT-YevXY/viewform?usp=send_form","christophe.gerard8@gmail.com",mail);
                }
                catch(Exception e)
                {
                    Log.e("SendMail", e.getMessage(),e) ;
                }
            }
        });
        return rootView;
    }
}
