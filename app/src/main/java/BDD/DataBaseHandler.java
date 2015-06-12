package BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christophe on 03/04/2015. For PlastProd Project
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    //Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME ="PlastProd";

    // Tables names
    private static final String TABLE_CLIENT = "CLIENT";
    private static final String TABLE_COMMANDE = "COMMANDE";

    // Common Columns names
    private static final String KEY_ID = "id";

    // Client Tables - Column Names
    private static final String CLIENT_KEY_NOM = "nom_client";
    private static final String CLIENT_KEY_PRENOM = "prenom_client";
    private static final String CLIENT_KEY_ADRESSE = "adresse_client";
    private static final String CLIENT_KEY_TELEPHONE = "telephone_client" ;
    private static final String CLIENT_KEY_EMAIL = "email_client";

    // Creation de la table Client
    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENT + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + CLIENT_KEY_NOM + " TEXT, " +
            CLIENT_KEY_PRENOM + " TEXT, " + CLIENT_KEY_ADRESSE + " TEXT, " +
            CLIENT_KEY_TELEPHONE + " TEXT, " + CLIENT_KEY_EMAIL + " TEXT);";


    // Commande Tables - Column Names
    private static final String COMMANDE_KEY_NUMCOMMANDE = "numCommande";
    private static final String COMMANDE_KEY_DATECOMMANDE= "dateCommande";
    private static final String COMMANDE_KEY_TOTAL = "total";
    private static final String COMMANDE_KEY_CLIENT_ID = "client_id";

    // Creation de la table Commande
    private static final String CREATE_TABLE_COMMANDE=" CREATE TABLE " + TABLE_COMMANDE + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + COMMANDE_KEY_NUMCOMMANDE + " INTEGER, " + " client_id INTEGER, " + COMMANDE_KEY_DATECOMMANDE + " TEXT, " + COMMANDE_KEY_TOTAL + " FLOAT, FOREIGN KEY (client_id)  REFERENCES CLIENT(id));";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(LOG, CREATE_TABLE_CLIENT);
        Log.i(LOG,CREATE_TABLE_COMMANDE);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_COMMANDE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE);
        // Create news
        onCreate(db);
    }


    // ----------------------------------- Client table methods ------------------------------- //
    /* Create Client */

    public long createClient(Client client)
    {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(CLIENT_KEY_NOM, client.getNom());
            values.put(CLIENT_KEY_PRENOM, client.getPrenom());
            values.put(CLIENT_KEY_ADRESSE, client.getAdresse());
            values.put(CLIENT_KEY_TELEPHONE, client.getTelephone());
            values.put(CLIENT_KEY_EMAIL, client.getEmail());

            // Insert row
            return db.insert(TABLE_CLIENT, null, values);
        }

    }

    /* Get Client */

    public Client getClient(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null)
            c.moveToFirst();

        // Create The client object and set all parameters then return object.
        Client client = new Client();
        assert c != null;
        client.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        client.setNom(c.getString(c.getColumnIndex(CLIENT_KEY_NOM)));
        client.setPrenom((c.getString(c.getColumnIndex(CLIENT_KEY_PRENOM))));
        client.setAdresse((c.getString(c.getColumnIndex(CLIENT_KEY_ADRESSE))));
        client.setTelephone((c.getString(c.getColumnIndex(CLIENT_KEY_TELEPHONE))));
        client.setEmail((c.getString(c.getColumnIndex(CLIENT_KEY_EMAIL))));

        c.close();
        return client;
    }

    /*
     * Getting all clients
     */

    public List<Client> getAllClients()
    {
        List<Client> clients = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CLIENT ;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        // Looping through all rows and adding to list
        if(c.moveToFirst())
        {
            do{
                Client client = new Client();
                client.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                client.setNom(c.getString(c.getColumnIndex(CLIENT_KEY_NOM)));
                client.setPrenom((c.getString(c.getColumnIndex(CLIENT_KEY_PRENOM))));
                client.setAdresse((c.getString(c.getColumnIndex(CLIENT_KEY_ADRESSE))));
                client.setTelephone((c.getString(c.getColumnIndex(CLIENT_KEY_TELEPHONE))));
                client.setEmail((c.getString(c.getColumnIndex(CLIENT_KEY_EMAIL))));

                // Adding to the client list
                clients.add(client);

            }while(c.moveToNext());
        }
        c.close();
        return clients;
    }

    /* Getting client from email */
    public Client getClientsFromEmail(String email)
    {

        String selectQuery = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_KEY_EMAIL + " = " + "\"" + email + "\"";

        Log.e(LOG,selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery,null);
        if(c!= null)
            c.moveToFirst();

        Client client = new Client();
        assert c != null;
        client.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        client.setNom(c.getString(c.getColumnIndex(CLIENT_KEY_NOM)));
        client.setPrenom((c.getString(c.getColumnIndex(CLIENT_KEY_PRENOM))));
        client.setAdresse((c.getString(c.getColumnIndex(CLIENT_KEY_ADRESSE))));
        client.setTelephone((c.getString(c.getColumnIndex(CLIENT_KEY_TELEPHONE))));
        client.setEmail((c.getString(c.getColumnIndex(CLIENT_KEY_EMAIL))));
        c.close();
        return client;
    }

    /* Commande Table Methods */

    // GetCommande from Id
    public Commande getCommande(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE + " WHERE " + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null)
            c.moveToFirst();

        Commande commande = new Commande();
        assert commande!= null;
        commande.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        commande.setClientId(c.getInt(c.getColumnIndex(COMMANDE_KEY_CLIENT_ID)));
        commande.setTotal(c.getFloat(c.getColumnIndex(COMMANDE_KEY_TOTAL)));
        commande.setDateCommande(c.getString(c.getColumnIndex(COMMANDE_KEY_DATECOMMANDE)));
        commande.setNumCommande(c.getInt(c.getColumnIndex(COMMANDE_KEY_NUMCOMMANDE)));

        return commande;
    }

    public List<Commande> getAllCommandeByClient(long id)
    {
        List<Commande> commandes = new ArrayList<Commande>();
        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE + " WHERE " + COMMANDE_KEY_CLIENT_ID + " = " + id  ;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        // Looping through all rows and adding to list
        if(c.moveToFirst())
        {
            do{
                Commande commande = new Commande();
                commande.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                commande.setClientId(id);
                commande.setNumCommande(c.getInt(c.getColumnIndex(COMMANDE_KEY_NUMCOMMANDE)));
                commande.setDateCommande(c.getString(c.getColumnIndex(COMMANDE_KEY_DATECOMMANDE)));
                commande.setTotal(c.getFloat(c.getColumnIndex(COMMANDE_KEY_TOTAL)));

                // Adding to the client list
                commandes.add(commande);

            }while(c.moveToNext());
        }
        c.close();
        return commandes;
    }

    /* Create Commande   */
    public long createCommande(Commande commande)
    {
        try(SQLiteDatabase db = this.getWritableDatabase())
        {
            ContentValues values = new ContentValues();
            values.put(COMMANDE_KEY_CLIENT_ID,commande.getClientId());
            values.put(COMMANDE_KEY_DATECOMMANDE,commande.getDateCommande().toString());
            values.put(COMMANDE_KEY_NUMCOMMANDE,commande.getNumCommande());
            values.put(COMMANDE_KEY_TOTAL,commande.getTotal());

            return db.insert(TABLE_COMMANDE,null,values);
        }
    }
}
