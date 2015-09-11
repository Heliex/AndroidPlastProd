package other;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import BDD.DataBaseHandler;
import BDD.Devis;
import adapter.ListeDevisAdapter;
import barbeasts.plastprod.R;
import model.MainActivity;

/**
 * An asynchronous task that handles the file on drive call.
 * @author Christophe Gerard
 * @version 1.5
 */
public class ApiAsyncTask extends AsyncTask<List<Devis>, Integer, ArrayList<String>> {
    private MainActivity mActivity;
    private List<Devis> listeDevis;
    private ProgressDialog dialog ;
    /**
     * Constructor.
     * @param activity MainActivity that spawned this task.
     */
    public ApiAsyncTask(MainActivity activity) {
        this.mActivity = activity;
        this.dialog = new ProgressDialog(activity.getInstance());
    }

    @Override
    public void onPreExecute()
    {
        this.dialog.setMessage("Récupération des données depuis le serveur");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    /**
     * Background task to call Drive API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected ArrayList<String> doInBackground(List<Devis>... params) {
        ArrayList<String> listeLigne = new ArrayList<>();

        listeDevis = params[0];
        try {
            String fileID = "1euyHlY_MKSznGcPh7itNCayl_-Z_8ouhWFtInxWqoXc";
            String apiKey = "AIzaSyCDY-8ewjiMR5Xv3YW0lXGi8-XatcHkmJc";
            URL url = new URL("https://www.googleapis.com/drive/v2/files/" + fileID + "?key="+apiKey);
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);
            InputStream is = connection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if(is != null)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                try
                {
                    while((line = reader.readLine()) != null)
                    {
                        builder.append(line).append("\n");
                    }
                    JSONObject json = new JSONObject(builder.toString());
                    json = json.getJSONObject("exportLinks");
                    String downloadUrl = json.getString("text/csv");
                    listeLigne = getContent(downloadUrl,listeLigne);
                    // Seulement quand j'ai mon url de Download je charge le contenu du document
                }
                catch(JSONException json)
                {
                    json.printStackTrace();
                }
            }
        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {

            System.out.println(availabilityException.getConnectionStatusCode());

        }
        catch(MalformedURLException malformed)
        {
            malformed.printStackTrace();
        }
        catch(IOException io)
        {
            System.out.println(io.getMessage());
        }

        return listeLigne;
    }

    // Récupère le contenu d'un document d'une url donné stocker dans une liste de ligne
    public ArrayList<String> getContent(String url,ArrayList<String> listeLigne)
    {
        try {
            URL contentOfDocument = new URL(url);
            URLConnection connection = contentOfDocument.openConnection();
            connection.setDoInput(true);
            InputStream is = connection.getInputStream();
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                        listeLigne.add(line);
                }
            }
        }
         catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listeLigne;
    }

    /**
     * After task has been executed
     * @param listeLigne liste de ligne
     */
    @Override
    protected void onPostExecute(ArrayList<String> listeLigne)
    {
        String estAccepte = "";
        final DataBaseHandler db = new DataBaseHandler(mActivity.getInstance());
        if(listeLigne.size() > 0)
        {
            // D'abord je retire la premiere ligne de ma liste de ligne car elle ne sert a rien
            listeLigne.remove(0);
        }

        if(listeLigne.size() > 0)
        {
            ArrayList<Devis> maListDeDevis = new ArrayList<>();
            // J'initialiser un tableau de String
            int indiceDevis;
            // Ensuite je découpe chaque ligne en 3 chaines.

            for(int i = 0 ; i < listeLigne.size() ; i++)
            {
                String[] split = listeLigne.get(i).split(",");
                String numDevis = split[3];
                estAccepte = split[1];
                indiceDevis = indiceDansListeDevis(numDevis);
                if(indiceDevis != -1 && estAccepte.equals("Oui")) // Si l'indice vaux -1
                {
                    maListDeDevis.add(listeDevis.get(indiceDevis));
                }
                else if (indiceDevis != -1 && estAccepte.equals("Non"))
                {
                    // Faire le traitement de la suppression de devis et la notifications.
                    Devis d = listeDevis.get(indiceDevis);
                    db.removeDevis(d.getId(),d.getId_prospect());
                }
            }

            if(maListDeDevis.size() > 0) // Si mon indice est pas négatif.
            {
                if(this.dialog.isShowing())
                {
                    this.dialog.dismiss();
                }

                final ListeDevisAdapter adapter = new ListeDevisAdapter(mActivity.getInstance(),maListDeDevis);
                if(mActivity != null)
                {
                    if(mActivity.getInstance().getFragmentManager().findFragmentByTag("Fragment") != null)
                    {
                        Fragment f = mActivity.getInstance().getFragmentManager().findFragmentByTag("Fragment");
                        if(f.getView() != null)
                        {
                            final ListView lv = (ListView) f.getView().findViewById(R.id.ListeDevis);
                            lv.post(new Runnable() {
                                @Override
                                public void run() {
                                    lv.setAdapter(adapter);
                                }
                            });
                        }
                    }
                }
            }else {
                if(this.dialog.isShowing())
                {
                    this.dialog.dismiss();
                }
                mActivity.displayView(0);
                String[] navMenuTitles = mActivity.getResources().getStringArray(R.array.nav_drawer_items);
                if(mActivity.getActionBar() != null)
                {
                    TextView tx = (TextView)mActivity.getActionBar().getCustomView().findViewById(R.id.action_bar_title);
                    tx.setText(navMenuTitles[0]);
                }
                if(estAccepte.equals("Non"))
                {
                    Toast.makeText(mActivity.getApplicationContext(),"Un devis à été réfusé, il à été supprimé de la liste des devis",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(mActivity.getApplicationContext(),"Aucun devis à valider",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Renvoie l'indice du Numéro de devis dans la liste
     * @param num Numéro de devis
     * @return indice du numéro de devis s'il existe sinon -1
     */
    int indiceDansListeDevis(String num)
    {
        int numDevis = Integer.parseInt(num);
        for(int i = 0 ; i < listeDevis.size(); i++)
        {
            if(listeDevis.get(i).getNumDevis() == numDevis)
            {
                return i;
            }
        }

        return -1;
    }
}