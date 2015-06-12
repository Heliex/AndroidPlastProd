package menu;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.ListeRDVAdapter;
import barbeasts.plastprod.R;
import model.RDV;
import model.Utility;

/**
 * Created by christophe on 01/04/2015.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        Utility utility = new Utility();
        List<RDV> listeRDV = utility.readCalendarEvent(getActivity().getApplicationContext());
        ListView lv = (ListView) rootView.findViewById(R.id.listView2);
        ListeRDVAdapter adapter = new ListeRDVAdapter(getActivity().getApplicationContext(),listeRDV);
        lv.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.calendar);
        if(button != null)
        {
            button.setOnClickListener(new View.OnClickListener() {
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

        return rootView;
    }
}
