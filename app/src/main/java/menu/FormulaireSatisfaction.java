package menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            EditText editText = (EditText) getActivity().findViewById(R.id.editText2);
                            String mail = editText.getText().toString();
                            GmailSender sender = new GmailSender("commercialPlastprod@gmail.com","Commercialplastprod88");
                            sender.sendMail("Enquête de satisfaction","Venez découvrir notre enquête, elle ne prend que quelques minutes : https://docs.google.com/forms/d/1DGX6i1U-1kPehjOEf3uDdkanB6eu07p8gLKvT-YevXY/viewform?usp=send_form","christophe.gerard8@gmail.com",mail);

                        }
                        catch(Exception e)
                        {
                            Log.e("MailSender",e.getMessage());
                        }
                    }
                }).start();

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
                Toast.makeText(getActivity().getApplicationContext(),"Mail envoyé",Toast.LENGTH_SHORT).show();

            }
        });
        return rootView;
    }
}
