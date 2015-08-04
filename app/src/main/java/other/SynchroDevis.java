package other;

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
import BDD.Devis;
import model.LoginActivity;
import model.MainActivity;

/**
 * Created by Christophe on 19/07/2015. For PlastProd Project on purpose
 * @author Christophe Gerard
 * @version 1.0
 */
public class SynchroDevis extends AsyncTask<Void,Void,Void> {

    MainActivity mActivity;

    /**
     * Constructeur � 1 param�tre
     * @param activity Instance de MainActivity
     */
    public SynchroDevis(MainActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Tache de fonds
     * @param params type = Void donc pas de param�tres
     * @return Void(Rien du tout)
     */
    @Override
    protected Void doInBackground(Void... params) {
        MAJ();
        return null;
    }

    /**
     * Mets � jour les Devis sur la BDD Distante
     */
    public void MAJ()
    {
        try
        {
            DataBaseHandler db = new DataBaseHandler(mActivity.getApplicationContext());
            List<Devis> listeDevis = db.getAllDevis();
            Gson gson = new GsonBuilder().create();
            String arrayListToJson=gson.toJson(listeDevis);
            long idCommercial = LoginActivity.getUser().getId();
            URL link = new URL("http://heliex.alwaysdata.net//SynchroBase/SynchroDevis.php?idCommercial="+idCommercial);
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
            connection.disconnect();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
