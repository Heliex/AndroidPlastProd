package other;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;


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

import BDD.AffectationMatiere;
import BDD.DataBaseHandler;
import model.MainActivity;

/**
 * Created by Christophe on 19/07/2015. For PlastProd Project on purpose
 * @author Christophe Gerard
 * @version 1.0
 */
public class SynchroAffectationMatiere extends AsyncTask<Void,Void,Void> {

    MainActivity mActivity;
    ProgressDialog dialog;

    /**
     * Constructeur à 2 paramètres
     * @param activity Instance de MainActivity
     * @param mDialog Affiche la progression
     */
    public SynchroAffectationMatiere(MainActivity activity,ProgressDialog mDialog) {
        this.mActivity = activity;
        this.dialog = mDialog;
    }


    /**
     * Tache de fonds
     * @param params type = Void donc pas de paramètres
     * @return Void(Rien du tout)
     */
    @Override
    protected Void doInBackground(Void... params) {
        MAJ();
        return null;
    }

    /**
     * Mets à jour les affectationMatiere sur la BDD Distante
     */
    public void MAJ()
    {
        try
        {
            DataBaseHandler db = new DataBaseHandler(mActivity.getApplicationContext());
            List<AffectationMatiere> listeAffectation = db.getAllAffectationMatiere();
            Gson gson = new GsonBuilder().create();
            String arrayListToJson=gson.toJson(listeAffectation);
            URL link = new URL("http://heliex.alwaysdata.net//SynchroBase/SynchroAffectationMatiere.php");
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

    /**
     * Une fois que la tâche de fond s'est exécutée on affiche une notification
     * @param aVoid Void(Rien)
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(this.dialog.isShowing())
        {
            this.dialog.dismiss();
        }
        Toast.makeText(mActivity.getApplicationContext(),"Mise à jour éffectuée avec succès",Toast.LENGTH_SHORT).show();
    }
}
