package other;

import android.app.ProgressDialog;
import android.os.AsyncTask;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import BDD.Commande;
import BDD.DataBaseHandler;
import model.MainActivity;

/**
 * Created by Christophe on 19/07/2015. For PlastProd Project on purpose
 */
public class SynchroCommande extends AsyncTask<Void,Void,Void> {

    MainActivity mActivity;
    ProgressDialog dialog;

    public SynchroCommande(MainActivity activity) {
        this.mActivity = activity;
        this.dialog = new ProgressDialog(activity.getInstance());
    }

    @Override
    public void onPreExecute()
    {
        this.dialog.setMessage("Mise à jour de la base de données"  );
        this.dialog.setCancelable(false);
        this.dialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {
        MAJ();
        return null;
    }

    public void MAJ()
    {
        try
        {
            DataBaseHandler db = new DataBaseHandler(mActivity.getApplicationContext());
            List<Commande> listeCommande = db.getAllCommande();
            Gson gson = new GsonBuilder().create();
            String arrayListToJson=gson.toJson(listeCommande);
            URL link = new URL("http://christophe.gerard88.free.fr/SynchroBase/SynchroCommande.php");
            assert(link != null);
            System.out.println(arrayListToJson);
            HttpURLConnection connection = (HttpURLConnection)link.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setRequestProperty("Content-Type", "application/json" );
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            assert (arrayListToJson != null);
            os.writeBytes(arrayListToJson);
            os.flush();
            os.close();
            InputStreamReader isReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isReader);
            String line="";
            StringBuilder chaine = new StringBuilder();
            while((line = reader.readLine()) != null)
            {
                chaine.append(line).append("\n");
            }
            System.out.println(chaine);
            int responseCode = connection.getResponseCode();
            System.out.println("Code réponse : " + responseCode);
            System.out.println("Header : " + connection.getHeaderFields().toString());
            System.out.println("Message de réponse : " + connection.getResponseMessage());
            if(responseCode == 200)
            {
                if(dialog.isShowing())
                    dialog.dismiss();
            }

            connection.disconnect();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
        }
    }
}
