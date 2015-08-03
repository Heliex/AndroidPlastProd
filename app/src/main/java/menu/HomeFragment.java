package menu;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import adapter.ListeRDVAdapter;
import barbeasts.plastprod.R;
import model.MainActivity;
import model.RDV;
import model.Utility;
import other.SynchroAffectationCommande;
import other.SynchroAffectationDevis;
import other.SynchroAffectationMatiere;
import other.SynchroBase;
import other.SynchroCommande;
import other.SynchroDevis;
import other.SynchroMatiere;
import other.SynchroNomenclature;
import other.SynchroProspect;


/**
 * Created by christophe on 01/04/2015.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        List<RDV> listeRDV = Utility.readCalendarEvent(getActivity().getApplicationContext());
        ListView lv = (ListView) rootView.findViewById(R.id.listView2);
        ListeRDVAdapter adapter = new ListeRDVAdapter(getActivity().getApplicationContext(),listeRDV);
        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.imageButton);
        lv.setAdapter(adapter);
        Button button = (Button) rootView.findViewById(R.id.calendar);
        if(button != null)
        {
            button.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                @Override
                public void onClick(View view) {
                    long startMillis = System.currentTimeMillis();
                    Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                    builder.appendPath("time");
                    ContentUris.appendId(builder, startMillis);
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                    startActivity(intent);
                }
            });
        }
        if(imageButton != null)
        {
            MainActivity activity = (MainActivity) getActivity();
            final ProgressDialog mDialog = new ProgressDialog(activity.getInstance());

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Mise à jour de la base de données");
                    mDialog.show();
                    new SynchroBase((MainActivity)getActivity()).execute();
                    new SynchroProspect((MainActivity)getActivity()).execute();
                    new SynchroCommande((MainActivity)getActivity()).execute();
                    new SynchroAffectationCommande((MainActivity)getActivity()).execute();
                    new SynchroAffectationDevis((MainActivity)getActivity()).execute();
                    new SynchroMatiere((MainActivity)getActivity()).execute();
                    new SynchroNomenclature((MainActivity)getActivity()).execute();
                    new SynchroDevis((MainActivity)getActivity()).execute();
                    new SynchroAffectationMatiere((MainActivity)getActivity(),mDialog).execute();
                }
            });
        }
        return rootView;
    }
}
