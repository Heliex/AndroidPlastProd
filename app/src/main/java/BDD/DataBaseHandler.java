package BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import junit.framework.Test;

import java.lang.annotation.AnnotationFormatError;
import java.util.ArrayList;
import java.util.List;

import barbeasts.plastprod.R;
import other.GmailSender;

/**
 * Created by christophe on 03/04/2015. For PlastProd Project
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    //Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database version
    private static final int DATABASE_VERSION = 16;

    // Database Name
    private static final String DATABASE_NAME ="PlastProd";

    // Tables names
    private static final String TABLE_CLIENT = "CLIENT";
    private static final String TABLE_COMMANDE = "COMMANDE";
    private static final String TABLE_PROSPECT = "PROSPECT";
    private static final String TABLE_USER = "USER";
    private static final String TABLE_MATIERE = "MATIERE";
    private static final String TABLE_NOMENCLATURE = "NOMENCLATURE";
    private static final String TABLE_AFFECTATION_MATIERE = "AFFECTATION_MATIERE";
    private static final String TABLE_AFFECTATION_COMMANDE = "AFFECTATION_COMMANDE";
    private static final String TABLE_DEVIS = "DEVIS";
    private static final String TABLE_AFFECTATION_DEVIS = "AFFECTATION_DEVIS";

    // Common Columns names
    private static final String KEY_ID = "id";

    // Client Tables - Column Names
    private static final String CLIENT_KEY_NOM = "nom_client";
    private static final String CLIENT_KEY_PRENOM = "prenom_client";
    private static final String CLIENT_KEY_ADRESSE = "adresse_client";
    private static final String CLIENT_KEY_TELEPHONE = "telephone_client" ;
    private static final String CLIENT_KEY_EMAIL = "email_client";
    private static final String CLIENT_KAY_DATE_INSCRIPTION = "date_client";

    // Creation de la table Client
    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENT + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLIENT_KEY_NOM + " TEXT, " +
            CLIENT_KEY_PRENOM + " TEXT, " + CLIENT_KEY_ADRESSE + " TEXT, " +
            CLIENT_KEY_TELEPHONE + " TEXT, " + CLIENT_KEY_EMAIL + " TEXT, " + CLIENT_KAY_DATE_INSCRIPTION + " TEXT);";

    // Commande Tables - Column Names
    private static final String COMMANDE_KEY_NUMCOMMANDE = "numCommande";
    private static final String COMMANDE_KEY_DATECOMMANDE= "dateCommande";
    private static final String COMMANDE_KEY_TOTAL = "total";
    private static final String COMMANDE_KEY_CLIENT_ID = "client_id";

    // Creation de la table Commande
    private static final String CREATE_TABLE_COMMANDE=" CREATE TABLE " + TABLE_COMMANDE + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + COMMANDE_KEY_NUMCOMMANDE + " INTEGER, " + " client_id INTEGER, " + COMMANDE_KEY_DATECOMMANDE + " TEXT, " + COMMANDE_KEY_TOTAL + " FLOAT, FOREIGN KEY (" + COMMANDE_KEY_CLIENT_ID +  ")  REFERENCES "+ TABLE_CLIENT + "(" + KEY_ID + "));";

    // Prospect Tables - Column Names
    private static final String PROSPECT_KEY_NOM = "nom_prospect";
    private static final String PROSPECT_KEY_PRENOM = "prenom_prospect";
    private static final String PROSPECT_KEY_ADRESSE = "adresse_prospect";
    private static final String PROSPECT_KEY_TELEPHONE = "telephone_prospect";
    private static final String PROSPECT_KEY_EMAIL = "email_prospect";
    private static final String PROSPECT_KEY_DATE = "date_prospect";
    private static final String PROSPECT_KEY_POURCENTAGE = "pourcentage_prospect";

    // Creation de la table Prospect
    private static final String CREATE_TABLE_PROSPECT = "CREATE TABLE " + TABLE_PROSPECT + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROSPECT_KEY_NOM + " TEXT, " +
            PROSPECT_KEY_PRENOM + " TEXT, " + PROSPECT_KEY_ADRESSE + " TEXT, " +
            PROSPECT_KEY_TELEPHONE + " TEXT, " + PROSPECT_KEY_EMAIL + " TEXT, " + PROSPECT_KEY_DATE + " TEXT, " +  PROSPECT_KEY_POURCENTAGE + " INTEGER);";

    // User Tables - Column Names
    private static final String USER_KEY_EMAIL ="email_user";
    private static final String USER_KEY_MDP = "mdp_user";

   // Creation de la table User
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
           KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_KEY_EMAIL + " TEXT, " +
           USER_KEY_MDP + " TEXT);";

    // Matiere Tables - Column Names
    private static final String MATIERE_KEY_NOM = "nom_matiere";
    private static final String MATIERE_KEY_PRIX = "prix_matiere";

    // Creation de la table Matiere
    private static final String CREATE_TABLE_MATIERE = "CREATE TABLE " + TABLE_MATIERE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MATIERE_KEY_NOM + " TEXT, " +
            MATIERE_KEY_PRIX + " REAL);";

    // Nomenclature Tables - Column Names
    private static final String NOMENCLATURE_KEY_NOM = "nom_nomenclature";

    // Creation de la table Nomenclature
    private static final String CREATE_TABLE_NOMENCLATURE = "CREATE TABLE " + TABLE_NOMENCLATURE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOMENCLATURE_KEY_NOM + " TEXT);";

    // Affectation Matiere Tables - Column Names
    private static final String AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE="id_nomenclature";
    private static final String AFFECTATION_MATIERE_KEY_ID_MATIERE = "id_matiere";
    private static final String AFFECTATION_MATIERE_KEY_QUANTITE = "quantite";

    // Creation de la table AffectationMatiere

    private static final String CREATE_TABLE_AFFECTATION_MATIERE = "CREATE TABLE " + TABLE_AFFECTATION_MATIERE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AFFECTATION_MATIERE_KEY_QUANTITE + " INTEGER, " +
            AFFECTATION_MATIERE_KEY_ID_MATIERE + " INTEGER, " + AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE + " INTEGER ,FOREIGN KEY (" + AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE +  ")  REFERENCES " + TABLE_NOMENCLATURE + "(" + KEY_ID + "), FOREIGN KEY (" + AFFECTATION_MATIERE_KEY_ID_MATIERE + ") REFERENCES " + TABLE_MATIERE + "(" + KEY_ID + "));";

    // AffectationCommande Tables - Column Names
    private static final String AFFECTATION_COMMANDE_KEY_ID_NOMENCLATURE="id_nomenclature";
    private static final String AFFECTATION_COMMANDE_KEY_ID_COMMANDE = "id_commande";
    private static final String AFFECTATION_COMMANDE_KEY_QUANTITE ="quantite";

    // Creation de la table AffectationCommande

    private static final String CREATE_TABLE_AFFECTATION_COMMANDE = "CREATE TABLE " + TABLE_AFFECTATION_COMMANDE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AFFECTATION_COMMANDE_KEY_QUANTITE + " INTEGER, " +
            AFFECTATION_COMMANDE_KEY_ID_COMMANDE + " INTEGER, " + AFFECTATION_COMMANDE_KEY_ID_NOMENCLATURE + " INTEGER, " +
            "FOREIGN KEY (" + AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE + ") REFERENCES " + TABLE_NOMENCLATURE +
            "(" + KEY_ID + "), FOREIGN KEY ( " + AFFECTATION_COMMANDE_KEY_ID_COMMANDE + ") REFERENCES " + TABLE_COMMANDE + "(" + KEY_ID + "));";

    private static final String DEVIS_KEY_ID_PROSPECT = "id_prospect";
    private static final String DEVIS_KEY_TOTAL = "total";
    private static final String DEVIS_KEY_NUMDEVIS = "numDevis";
    private static final String DEVIS_KEY_DATE = "dateDevis";

    private static final String CREATE_TABLE_DEVIS = "CREATE TABLE " + TABLE_DEVIS + " ( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEVIS_KEY_ID_PROSPECT + " INTEGER, "+
            DEVIS_KEY_NUMDEVIS + " INTEGER, " + DEVIS_KEY_DATE + " TEXT, " + DEVIS_KEY_TOTAL + " REAL, FOREIGN KEY ( " + DEVIS_KEY_ID_PROSPECT +
            " ) REFERENCES " + TABLE_PROSPECT + " (" + KEY_ID + "));";

    private static final String AFFECTATION_DEVIS_KEY_ID_NOMENCLATURE = "id_nomenclature";
    private static final String AFFECTATION_DEVIS_KEY_ID_DEVIS = "id_devis";
    private static final String AFFECTATION_DEVIS_QUANTITE = "quantite";

    private static final String CREATE_TABLE_AFFECTATION_DEVIS = " CREATE TABLE " + TABLE_AFFECTATION_DEVIS + " ( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + AFFECTATION_DEVIS_KEY_ID_DEVIS + " INTEGER, " + AFFECTATION_DEVIS_KEY_ID_NOMENCLATURE +
            " INTEGER, " + AFFECTATION_DEVIS_QUANTITE + " INTEGER, FOREIGN KEY ( " + AFFECTATION_DEVIS_KEY_ID_NOMENCLATURE + " ) REFERENCES " + TABLE_NOMENCLATURE + " (" + KEY_ID + " ), FOREIGN KEY (" +
            AFFECTATION_DEVIS_KEY_ID_DEVIS + " ) REFERENCES " + TABLE_DEVIS + " ( " + KEY_ID + " )); "  ;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(LOG, CREATE_TABLE_CLIENT);
        Log.i(LOG,CREATE_TABLE_COMMANDE);
        Log.i(LOG, CREATE_TABLE_PROSPECT);
        Log.i(LOG,CREATE_TABLE_USER);
        Log.i(LOG,CREATE_TABLE_NOMENCLATURE);
        Log.i(LOG, CREATE_TABLE_MATIERE);
        Log.i(LOG,CREATE_TABLE_AFFECTATION_MATIERE);
        Log.i(LOG,CREATE_TABLE_AFFECTATION_COMMANDE);
        Log.i(LOG,CREATE_TABLE_DEVIS);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_COMMANDE);
        db.execSQL(CREATE_TABLE_PROSPECT);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MATIERE);
        db.execSQL(CREATE_TABLE_NOMENCLATURE);
        db.execSQL(CREATE_TABLE_AFFECTATION_MATIERE);
        db.execSQL(CREATE_TABLE_AFFECTATION_COMMANDE);
        db.execSQL(CREATE_TABLE_DEVIS);
        db.execSQL(CREATE_TABLE_AFFECTATION_DEVIS);
        this.createUser(db);
        this.createFakeAffectationMatiere(db);
        this.createFakeNomenclature(db);
        this.createFakeMatiere(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROSPECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATIERE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOMENCLATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AFFECTATION_MATIERE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AFFECTATION_COMMANDE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AFFECTATION_DEVIS);
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
            values.put(CLIENT_KAY_DATE_INSCRIPTION,client.getDate());

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

        Log.i(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        // Looping through all rows and adding to list
        if(c.moveToFirst())
        {
            do{
                Client client = new Client();
                client.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                client.setNom(c.getString(c.getColumnIndex(CLIENT_KEY_NOM)));
                client.setPrenom(c.getString(c.getColumnIndex(CLIENT_KEY_PRENOM)));
                client.setAdresse(c.getString(c.getColumnIndex(CLIENT_KEY_ADRESSE)));
                client.setTelephone(c.getString(c.getColumnIndex(CLIENT_KEY_TELEPHONE)));
                client.setEmail(c.getString(c.getColumnIndex(CLIENT_KEY_EMAIL)));
                client.setDate(c.getString(c.getColumnIndex(CLIENT_KAY_DATE_INSCRIPTION)));

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

        Log.i(LOG, selectQuery);
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

    public ArrayList<Nomenclature> getAllNomenclatureFromIdClient(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Nomenclature> listeNomenclatures = new ArrayList<>();
        String selectQuery = "SELECT " + TABLE_NOMENCLATURE + "." + KEY_ID + " as idNomenclature, " + TABLE_NOMENCLATURE + "." + NOMENCLATURE_KEY_NOM + " as nomNomenclature, " +
                TABLE_AFFECTATION_COMMANDE + "." + AFFECTATION_COMMANDE_KEY_QUANTITE + " as quantiteNomenclature FROM " + TABLE_NOMENCLATURE + ", " + TABLE_COMMANDE +
                ", " + TABLE_AFFECTATION_COMMANDE + ", " + TABLE_CLIENT + " WHERE " + TABLE_CLIENT + "." + KEY_ID + " = " + id + " AND " + TABLE_NOMENCLATURE + "." + KEY_ID + " = " +
                    TABLE_AFFECTATION_COMMANDE + "." + AFFECTATION_COMMANDE_KEY_ID_NOMENCLATURE + " AND " + TABLE_COMMANDE + "." + KEY_ID + " = " + TABLE_AFFECTATION_COMMANDE + "." + AFFECTATION_COMMANDE_KEY_ID_COMMANDE + " AND " + TABLE_CLIENT + "." + KEY_ID + " = " + TABLE_COMMANDE + "." + COMMANDE_KEY_CLIENT_ID + ";" ;

        Log.i(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!= null)
        {
            if(c.moveToFirst())
            {
                do {

                    Nomenclature nomenclature = new Nomenclature();
                    nomenclature.setId(c.getLong(c.getColumnIndex("idNomenclature")));
                    nomenclature.setNom(c.getString(c.getColumnIndex("nomNomenclature")));
                    nomenclature.setQuantite(c.getInt(c.getColumnIndex("quantiteNomenclature")));
                    nomenclature.setListeMatiere(this.getAllMatiereForOneNomenclatureById(nomenclature.getId()));
                    listeNomenclatures.add(nomenclature);

                }while(c.moveToNext());
            }
            c.close();
        }

        return listeNomenclatures;
    }

    public ArrayList<Nomenclature> getAllNomenclatureFromIdProspect(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Nomenclature> listeNomenclatures = new ArrayList<>();
        String selectQuery = "SELECT " + TABLE_NOMENCLATURE + "." + KEY_ID + " as idNomenclature, " + TABLE_NOMENCLATURE + "." + NOMENCLATURE_KEY_NOM + " as nomNomenclature, " +
                TABLE_AFFECTATION_DEVIS + "." + AFFECTATION_DEVIS_QUANTITE + " as quantiteNomenclature FROM " + TABLE_NOMENCLATURE + ", " + TABLE_DEVIS +
                ", " + TABLE_AFFECTATION_DEVIS + ", " + TABLE_PROSPECT + " WHERE " + TABLE_PROSPECT + "." + KEY_ID + " = " + id + " AND " + TABLE_NOMENCLATURE + "." + KEY_ID + " = " +
                TABLE_AFFECTATION_DEVIS + "." + AFFECTATION_DEVIS_KEY_ID_NOMENCLATURE + " AND " + TABLE_DEVIS + "." + KEY_ID + " = " + TABLE_AFFECTATION_DEVIS + "." + AFFECTATION_DEVIS_KEY_ID_DEVIS + " AND " + TABLE_PROSPECT + "." + KEY_ID + " = " + TABLE_DEVIS + "." + DEVIS_KEY_ID_PROSPECT + ";" ;

        Log.i(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!= null)
        {
            if(c.moveToFirst())
            {
                do {

                    Nomenclature nomenclature = new Nomenclature();
                    nomenclature.setId(c.getLong(c.getColumnIndex("idNomenclature")));
                    nomenclature.setNom(c.getString(c.getColumnIndex("nomNomenclature")));
                    nomenclature.setQuantite(c.getInt(c.getColumnIndex("quantiteNomenclature")));
                    nomenclature.setListeMatiere(this.getAllMatiereForOneNomenclatureById(nomenclature.getId()));
                    listeNomenclatures.add(nomenclature);

                }while(c.moveToNext());
            }
            c.close();
        }

        return listeNomenclatures;
    }

    /* Updating Client */
    public int updateClient(Client c)
    {
        long id = c.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLIENT_KEY_NOM,c.getNom());
        values.put(CLIENT_KEY_PRENOM, c.getPrenom());
        values.put(CLIENT_KEY_ADRESSE, c.getAdresse());
        values.put(CLIENT_KEY_EMAIL, c.getEmail());
        values.put(CLIENT_KEY_TELEPHONE, c.getTelephone());
        values.put(CLIENT_KAY_DATE_INSCRIPTION, c.getDate());
        return db.update(TABLE_CLIENT,values,KEY_ID + " = " + id,null);
    }
    // ----------------------------------- Commande table methods ------------------------------- //

    // GetCommande from Id
    public Commande getCommande(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE + " WHERE " + KEY_ID + " = " + id;
        Log.i(LOG,selectQuery);
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
        String selectQuery = "SELECT * FROM " + TABLE_COMMANDE + " WHERE " + COMMANDE_KEY_CLIENT_ID + " = " + id + " ORDER BY " + COMMANDE_KEY_DATECOMMANDE + " ASC ;"  ;
        Log.i(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        // Looping through all rows and adding to list
        if(c.moveToFirst())
        {
            do{
                Commande commande = new Commande();
                ArrayList<Nomenclature> listeNomenclature = this.getAllNomenclatureFromIdClient(id);
                commande.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                commande.setClientId(id);
                commande.setNumCommande(c.getInt(c.getColumnIndex(COMMANDE_KEY_NUMCOMMANDE)));
                commande.setDateCommande(c.getString(c.getColumnIndex(COMMANDE_KEY_DATECOMMANDE)));
                commande.setTotal(c.getFloat(c.getColumnIndex(COMMANDE_KEY_TOTAL)));
                commande.setListeNomenclature(listeNomenclature);

                // Adding to the client list
                commandes.add(commande);

            }while(c.moveToNext());
        }
        c.close();
        return commandes;
    }

    public List<Devis> getAllDevisByProspect(long id)
    {
        List<Devis> listeDevis = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABLE_DEVIS + " WHERE " + DEVIS_KEY_ID_PROSPECT + " = " + id  + " ;";
        Log.i(LOG,selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);
        if(c != null)
        {
            if(c.moveToFirst())
            {
                do {
                    Devis devis = new Devis();
                    ArrayList<Nomenclature> listeNomenclature = this.getAllNomenclatureFromIdProspect(id);
                    devis.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                    devis.setId_prospect(id);
                    devis.setNumDevis(c.getInt(c.getColumnIndex(DEVIS_KEY_NUMDEVIS)));
                    devis.setListeNomenclatures(listeNomenclature);
                    devis.setTotal(c.getDouble(c.getColumnIndex(DEVIS_KEY_TOTAL)));
                    devis.setDateDevis(c.getString(c.getColumnIndex(DEVIS_KEY_DATE)));
                    listeDevis.add(devis);
                }while(c.moveToNext());
            }
            c.close();
        }
        return listeDevis;
    }

    /* Create Commande   */
    public long createCommande(Commande commande)
    {
        try(SQLiteDatabase db = this.getWritableDatabase())
        {
            ContentValues values = new ContentValues();
            values.put(COMMANDE_KEY_CLIENT_ID,commande.getClientId());
            values.put(COMMANDE_KEY_DATECOMMANDE,commande.getDateCommande());
            values.put(COMMANDE_KEY_NUMCOMMANDE,commande.getNumCommande());
            values.put(COMMANDE_KEY_TOTAL,commande.getTotal());

            long id_commande = db.insert(TABLE_COMMANDE,null,values);

            // Gestion de la liste de nomenclature
            for(int i = 0 ; i < commande.getListeNomenclature().size(); i++) // Pour chaque nomenclature il faut que j'ajoute l'id de commande et la quantite correspondante.
            {
                int quantite = commande.getListeNomenclature().get(i).getQuantite();
                long id_nomenclature = commande.getListeNomenclature().get(i).getId();
                createAffectionCommande(id_nomenclature, id_commande, quantite, db);
            }

            return id_commande ;
        }
    }

    /* Create AffectationCommande -> Appelée implicitement par la méthode createCommande */
    public long createAffectionCommande(long id_nomenclature,long id_commande,int quantite,SQLiteDatabase db)
    {
            ContentValues values = new ContentValues();
            values.put(AFFECTATION_COMMANDE_KEY_QUANTITE,quantite);
            values.put(AFFECTATION_COMMANDE_KEY_ID_COMMANDE, id_commande);
            values.put(AFFECTATION_COMMANDE_KEY_ID_NOMENCLATURE,id_nomenclature);

            return db.insert(TABLE_AFFECTATION_COMMANDE, null, values);
    }

    /* Create Devis */
    public long createDevis(Devis devis)
    {
        try(SQLiteDatabase db = this.getWritableDatabase())
        {
            ContentValues values = new ContentValues();
            values.put(DEVIS_KEY_ID_PROSPECT,devis.getId_prospect());
            values.put(DEVIS_KEY_NUMDEVIS, devis.getNumDevis());
            values.put(DEVIS_KEY_TOTAL, devis.getTotal());
            values.put(DEVIS_KEY_DATE, devis.getDateDevis());

            long id_devis = db.insert(TABLE_DEVIS,null,values);

            // Gestion de la liste de nomenclature
            for(int i = 0 ; i < devis.getListeNomenclatures().size(); i++) // Pour chaque nomenclature il faut que j'ajoute l'id de devis et la quantite correspondante.
            {
                int quantite = devis.getListeNomenclatures().get(i).getQuantite();
                long id_nomenclature = devis.getListeNomenclatures().get(i).getId();
                createAffectationDevis(id_nomenclature, id_devis, quantite, db);
            }

            return id_devis ;
        }
    }
    /* Create AffectationDevis -> Appelée implicitement par la méthode createDevis */
    public long createAffectationDevis(long id_nomenclature, long id_devis,int quantite, SQLiteDatabase db)
    {
        ContentValues values  = new ContentValues();
        values.put(AFFECTATION_DEVIS_QUANTITE, quantite);
        values.put(AFFECTATION_DEVIS_KEY_ID_DEVIS,id_devis);
        values.put(AFFECTATION_DEVIS_KEY_ID_NOMENCLATURE,id_nomenclature);

        return db.insert(TABLE_AFFECTATION_DEVIS,null,values);
    }

    // Get All Devis From id
    public List<Devis> getAllDevisFromIdProspect(long id)
    {
        List<Devis> listeDevis = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_DEVIS + " WHERE " + DEVIS_KEY_ID_PROSPECT + " = " + id + " ;" ;
        Cursor c = db.rawQuery(selectQuery,null);
        Log.i(LOG, selectQuery);
        if(c != null)
        {
            if(c.moveToFirst())
            {
                do {
                    Devis devis = new Devis();
                    devis.setId(c.getLong(c.getColumnIndex(KEY_ID)));
                    devis.setId_prospect(c.getLong(c.getColumnIndex(DEVIS_KEY_ID_PROSPECT)));
                    devis.setDateDevis(c.getString(c.getColumnIndex(DEVIS_KEY_DATE)));
                    devis.setTotal(c.getDouble(c.getColumnIndex(DEVIS_KEY_TOTAL)));
                    devis.setNumDevis(c.getInt(c.getColumnIndex(DEVIS_KEY_NUMDEVIS)));

                    listeDevis.add(devis);

                }while(c.moveToNext());
            }
            c.close();
        }

        return listeDevis;

    }
    public String getDetailsDevisFromIdDevis(long id,double prixTotal,long idProspect,int numDevis,boolean sendEmail) // Méthode qui envoi le mail avec le details du devis.
    {
        final StringBuilder details = new StringBuilder();
        String selectQuery = " SELECT " + TABLE_NOMENCLATURE + "." + NOMENCLATURE_KEY_NOM + " as NomNomenclature, " + TABLE_AFFECTATION_DEVIS + "." + AFFECTATION_DEVIS_QUANTITE + " as QuantiteNomenclature FROM " +
        TABLE_AFFECTATION_DEVIS + ", " + TABLE_NOMENCLATURE + " WHERE " + TABLE_AFFECTATION_DEVIS + "." + AFFECTATION_DEVIS_KEY_ID_NOMENCLATURE + " = " + TABLE_NOMENCLATURE + "." + KEY_ID +
        " AND " + TABLE_AFFECTATION_DEVIS + "." + AFFECTATION_DEVIS_KEY_ID_DEVIS + " = " + id;
        Log.i(LOG,selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c!= null)
        {
            if(c.moveToFirst()) // La je parcours chaque nomenclature
            {
                do {
                    String nomNomenclature = c.getString(c.getColumnIndex("NomNomenclature"));
                    int quantite = c.getInt(c.getColumnIndex("QuantiteNomenclature"));

                    if(quantite > 0 )
                    {
                       details.append("==  Nomenclature de nom : " + nomNomenclature + " En quantitée : " + quantite + "  == \n\n");
                        selectQuery = " SELECT " + TABLE_MATIERE + "." + MATIERE_KEY_NOM + " as NomMatiere, " + TABLE_MATIERE + "." + MATIERE_KEY_PRIX + " as PrixMatiere, " + TABLE_AFFECTATION_MATIERE + "." +
                                AFFECTATION_MATIERE_KEY_QUANTITE + " as QuantiteMatiere FROM " + TABLE_AFFECTATION_MATIERE + ", " + TABLE_MATIERE + ", " + TABLE_NOMENCLATURE +  " WHERE " + TABLE_MATIERE + "." + KEY_ID + " = " +
                                TABLE_AFFECTATION_MATIERE + "." + AFFECTATION_MATIERE_KEY_ID_MATIERE + " AND " + TABLE_NOMENCLATURE + "." + NOMENCLATURE_KEY_NOM + " = " + "\"" + nomNomenclature + "\"" + ";";
                        Log.i(LOG,selectQuery);
                        Cursor deuxieme = db.rawQuery(selectQuery,null);
                        if(deuxieme != null)
                        {
                            if(deuxieme.moveToFirst())
                            {
                                do {
                                    String nomMatiere = deuxieme.getString(deuxieme.getColumnIndex("NomMatiere"));
                                    double prixMatiere = deuxieme.getDouble(deuxieme.getColumnIndex("PrixMatiere"));
                                    int quantiteMatiere = deuxieme.getInt(deuxieme.getColumnIndex("QuantiteMatiere"));

                                    details.append("\t**  MATIERE ASSOCIEE : " + nomMatiere + " DE PRIX : " + prixMatiere + " €, EN QUANTITE : " + quantiteMatiere + "  **\n");
                                }while(deuxieme.moveToNext());
                                details.append("\n\n");
                            }
                        }
                    }
                }while(c.moveToNext());
            }
            details.append("Prix total du devis : " + prixTotal + " € , Votre numéro de devis est le : " + numDevis);
            Prospect p = getProspect(idProspect);
            final String email = p.getEmail();
            if(sendEmail)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            String mail = details.toString();
                            GmailSender sender = new GmailSender("commercialPlastprod@gmail.com","Commercialplastprod88");
                            sender.sendMail("Devis","ci joint votre devis :\n\n" + mail + " \n\n n'oubliez pas de nous répondre pour faire suite ou non a votre devis : https://docs.google.com/forms/d/1HWmH8AmI_8yKuxtNAq3YKsuOtg_Ba37uK80Em15TyN8/viewform?usp=send_form","commercialplastprod@gmail.com",email);
                        }
                        catch(Exception e)
                        {
                            Log.e("MailSender",e.getMessage());
                        }
                    }
                }).start();
                p.setPourcentage(Devis.getDevisEmis());
            }
        }
        return details.toString();
    }
    // ----------------------------------- Prospect table methods ------------------------------- //

    /* Create Prospect */

    public long createProspect(Prospect prospect)
    {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(PROSPECT_KEY_NOM, prospect.getNom());
            values.put(PROSPECT_KEY_PRENOM, prospect.getPrenom());
            values.put(PROSPECT_KEY_ADRESSE, prospect.getAdresse());
            values.put(PROSPECT_KEY_TELEPHONE, prospect.getTelephone());
            values.put(PROSPECT_KEY_EMAIL, prospect.getEmail());
            values.put(PROSPECT_KEY_DATE, prospect.getDate());
            values.put(PROSPECT_KEY_POURCENTAGE,prospect.getPourcentage());
            // Insert row
            return db.insert(TABLE_PROSPECT, null, values);
        }
    }

    /* Get Prospect */
    public Prospect getProspect(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PROSPECT + " WHERE " + KEY_ID + " = " + id;
        Log.i(LOG,selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if(c!=null)
            c.moveToFirst();

        // Create The client object and set all parameters then return object.
        Prospect prospect = new Prospect();
        assert c != null;
        prospect.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        prospect.setNom(c.getString(c.getColumnIndex(PROSPECT_KEY_NOM)));
        prospect.setPrenom((c.getString(c.getColumnIndex(PROSPECT_KEY_PRENOM))));
        prospect.setAdresse((c.getString(c.getColumnIndex(PROSPECT_KEY_ADRESSE))));
        prospect.setTelephone((c.getString(c.getColumnIndex(PROSPECT_KEY_TELEPHONE))));
        prospect.setEmail((c.getString(c.getColumnIndex(PROSPECT_KEY_EMAIL))));

        c.close();
        return prospect;
    }

    /*
     * Getting all prospects
     */

    public List<Prospect> getAllProspects()
    {
        List<Prospect> prospects = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PROSPECT ;

        Log.i(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        // Looping through all rows and adding to list
        if(c.moveToFirst())
        {
            do{
                Prospect prospect = new Prospect();
                prospect.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                prospect.setNom(c.getString(c.getColumnIndex(PROSPECT_KEY_NOM)));
                prospect.setPrenom((c.getString(c.getColumnIndex(PROSPECT_KEY_PRENOM))));
                prospect.setAdresse((c.getString(c.getColumnIndex(PROSPECT_KEY_ADRESSE))));
                prospect.setTelephone((c.getString(c.getColumnIndex(PROSPECT_KEY_TELEPHONE))));
                prospect.setEmail((c.getString(c.getColumnIndex(PROSPECT_KEY_EMAIL))));
                prospect.setDate((c.getString(c.getColumnIndex(PROSPECT_KEY_DATE))));
                prospect.setPourcentage((c.getInt(c.getColumnIndex(PROSPECT_KEY_POURCENTAGE))));

                // Adding to the prospect list
                prospects.add(prospect);

            }while(c.moveToNext());
        }
        c.close();
        return prospects;
    }

    /* Getting prospect from email */
    public Prospect getProspectsFromEmail(String email)
    {

        String selectQuery = "SELECT * FROM " + TABLE_PROSPECT + " WHERE " + PROSPECT_KEY_EMAIL + " = " + "\"" + email + "\"";

        Log.i(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery,null);
        if(c!= null)
            c.moveToFirst();

        Prospect prospect = new Prospect();
        assert c != null;
        prospect.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        prospect.setNom(c.getString(c.getColumnIndex(PROSPECT_KEY_NOM)));
        prospect.setPrenom((c.getString(c.getColumnIndex(PROSPECT_KEY_PRENOM))));
        prospect.setAdresse((c.getString(c.getColumnIndex(PROSPECT_KEY_ADRESSE))));
        prospect.setTelephone((c.getString(c.getColumnIndex(PROSPECT_KEY_TELEPHONE))));
        prospect.setEmail((c.getString(c.getColumnIndex(PROSPECT_KEY_EMAIL))));
        c.close();
        return prospect;
    }


    /* Updating Prospect */
    public int updateProspect(Prospect p)
    {
        long id = p.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROSPECT_KEY_NOM, p.getNom());
        values.put(PROSPECT_KEY_PRENOM, p.getPrenom());
        values.put(PROSPECT_KEY_ADRESSE, p.getAdresse());
        values.put(PROSPECT_KEY_EMAIL, p.getEmail());
        values.put(PROSPECT_KEY_TELEPHONE, p.getTelephone());
        values.put(PROSPECT_KEY_DATE, p.getDate());
        values.put(PROSPECT_KEY_POURCENTAGE,p.getPourcentage());
        return db.update(TABLE_PROSPECT,values,KEY_ID + " = " + id,null);
    }

    /* Removing Prospect */
    public boolean removeProspect(long id) // Retirer le prospect
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROSPECT,KEY_ID + " = " + id,null) > 0;
    }

    /* Removing Devis */
    public boolean removeDevis(long idDevis,long idProspect) // Retirer le devis et toutes les affectation qui vont avec.
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(TABLE_DEVIS,KEY_ID + " = " + idDevis + " AND " + DEVIS_KEY_ID_PROSPECT + " = " + idProspect,null) > 0 && db.delete(TABLE_AFFECTATION_DEVIS,AFFECTATION_DEVIS_KEY_ID_DEVIS + " = " + idDevis,null) > 0)
        {
            return true;
        }
        return false;
    }

    // ----------------------------------- User table methods ------------------------------- //
    /* Create user */
    public void  createUser(SQLiteDatabase db)
    {
            ContentValues values = new ContentValues();
            values.put(USER_KEY_EMAIL, "commercialplastprod@gmail.com");
            values.put(USER_KEY_MDP, "Commercialplastprod88");

            db.insert(TABLE_USER, null, values);
            Log.i(LOG, "USER CREE");
            System.out.println("UTILISATEUR BIEN CREE DANS LA BDD LOCALE DU TELEPHONE MDR");
    }

    /* Get User */
    public User getUser()
    {
        String selectQuery = "SELECT * FROM " + TABLE_USER ;
        Log.i(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!= null)
            c.moveToFirst();

        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        user.setEmail_user(c.getString(c.getColumnIndex(USER_KEY_EMAIL)));
        user.setMdp_user(c.getString(c.getColumnIndex(USER_KEY_MDP)));

        return user;
    }

    // ----------------------------------- Matiere table methods ------------------------------- //
    // Get Quantite Matiere
    public int getQuantiteMatiere(long id_nomenclature,long id_matiere)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_AFFECTATION_MATIERE + " WHERE " + AFFECTATION_MATIERE_KEY_ID_MATIERE + " = " +  id_matiere + " AND " + AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE + " = " + id_nomenclature + " ;";
        Log.i(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null)
            c.moveToFirst();

        int quantite = c.getInt(c.getColumnIndex(AFFECTATION_MATIERE_KEY_QUANTITE));
        return quantite;
    }

    public Matiere getMatiere(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MATIERE + " WHERE " + KEY_ID + " = " + id  + ";";
        Log.i(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null)
            c.moveToFirst();
        Matiere matiere = new Matiere();
        matiere.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        matiere.setNom(c.getString(c.getColumnIndex(MATIERE_KEY_NOM)));
        matiere.setPrix(c.getFloat(c.getColumnIndex(MATIERE_KEY_PRIX)));

        return matiere;
    }

    public ArrayList<Matiere> getAllMatieres()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Matiere> listeMatieres = new ArrayList<Matiere>();
        String selectQuery = "SELECT * FROM " + TABLE_MATIERE;
        Log.i(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst())
        {
            do {

                Matiere matiere = new Matiere();
                matiere.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                matiere.setNom(c.getString(c.getColumnIndex(MATIERE_KEY_NOM)));
                matiere.setPrix(c.getFloat(c.getColumnIndex(MATIERE_KEY_PRIX)));

                listeMatieres.add(matiere);

            }while(c.moveToNext());
        }

        return listeMatieres;
    }

    public ArrayList<Matiere> getAllMatiereForOneNomenclatureById(long id_nomenclature)
    {
        ArrayList<Matiere> listeMatiere = new ArrayList<Matiere>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + TABLE_MATIERE + "." + KEY_ID + " as mat, " + TABLE_MATIERE + "." + MATIERE_KEY_PRIX + " as matprix, " + TABLE_MATIERE + "." + MATIERE_KEY_NOM  + " as matnom FROM " + TABLE_NOMENCLATURE + "," + TABLE_MATIERE + "," + TABLE_AFFECTATION_MATIERE + " WHERE " +TABLE_NOMENCLATURE + "." + KEY_ID + " = " + TABLE_AFFECTATION_MATIERE + "." + AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE + " AND " + TABLE_MATIERE + "." + KEY_ID + " = " + TABLE_AFFECTATION_MATIERE + "." + AFFECTATION_MATIERE_KEY_ID_MATIERE + " AND " + TABLE_NOMENCLATURE  + "." + KEY_ID + " = " + id_nomenclature + ";";
        Log.i(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null)
            if(c.moveToFirst())
            {
                do {
                    Matiere matiere = new Matiere();
                    matiere.setId(c.getInt(c.getColumnIndex("mat")));
                    matiere.setPrix(c.getDouble(c.getColumnIndex("matprix")));
                    matiere.setNom(c.getString(c.getColumnIndex("matnom")));
                    listeMatiere.add(matiere);

                }while(c.moveToNext());
            }
        return listeMatiere;
    }


    // ----------------------------------- Nomenclature table methods ------------------------------- //
    public ArrayList<Nomenclature> getAllNomenclature()
    {
        ArrayList<Nomenclature> nomenclatures = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NOMENCLATURE + ";";
        Log.i(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if(c.moveToFirst())
        {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                ArrayList<Matiere> matiere = getAllMatiereForOneNomenclatureById(id);
                Nomenclature nomenclature = new Nomenclature();
                nomenclature.setId(id);
                nomenclature.setNom(c.getString(c.getColumnIndex(NOMENCLATURE_KEY_NOM)));
                nomenclature.setListeMatiere(matiere);
                nomenclatures.add(nomenclature);

            }while(c.moveToNext());
        }

        return nomenclatures;
    }
    public Nomenclature getNomenclatureById(long id)
    {
        String selectQuery = "SELECT * FROM " + TABLE_NOMENCLATURE + " WHERE " + KEY_ID + " = " + id + ";";
        Log.i(LOG,selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Nomenclature nomenclature = new Nomenclature();
        if(c.moveToFirst())
        {

            nomenclature.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            nomenclature.setNom(c.getString(c.getColumnIndex(NOMENCLATURE_KEY_NOM)));
            ArrayList<Matiere> matieres = this.getAllMatiereForOneNomenclatureById(id);
            nomenclature.setListeMatiere(matieres);
        }

        return nomenclature;
    }

    /* Getting AllAffectationMAtiere */
    public List<AffectationMatiere> getAllAffectationMatiere()
    {
        List<AffectationMatiere> affectationMatieres = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_AFFECTATION_MATIERE + " ;";
        Log.i(LOG,selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);
        if(c != null)
        {
            if(c.moveToFirst())
            {
                do {
                    AffectationMatiere affectationMatiere = new AffectationMatiere();
                    affectationMatiere.setIdNomenclature(c.getLong(c.getColumnIndex(AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE)));
                    affectationMatiere.setIdMatiere(c.getLong(c.getColumnIndex(AFFECTATION_MATIERE_KEY_ID_MATIERE)));
                    affectationMatiere.setQuantite(c.getInt(c.getColumnIndex(AFFECTATION_MATIERE_KEY_QUANTITE)));
                    affectationMatieres.add(affectationMatiere);

                }while(c.moveToNext());
            }
        }
        return affectationMatieres;
    }

    // ----------------------------------- Test table methods ------------------------------- //
    // Création de nomenclature.
    public void createFakeNomenclature(SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        values.put(NOMENCLATURE_KEY_NOM,"TABLE PLASTIQUE");
        db.insert(TABLE_NOMENCLATURE, null, values);
        values = new ContentValues();
        values.put(NOMENCLATURE_KEY_NOM,"TUYAUX PLASTIQUE 1M");
        db.insert(TABLE_NOMENCLATURE, null, values);
        values = new ContentValues();
        values.put(NOMENCLATURE_KEY_NOM,"VOITURE PLASTIQUE");
        db.insert(TABLE_NOMENCLATURE, null, values);
        values = new ContentValues();
        values.put(NOMENCLATURE_KEY_NOM,"BOUTON PLASTIQUE");
        db.insert(TABLE_NOMENCLATURE, null, values);
    }

    // Creation de matiere
    public void createFakeMatiere(SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        values.put(MATIERE_KEY_NOM, "TUYAUX 1M PLASTIQUE");
        values.put(MATIERE_KEY_PRIX,7.50);
        db.insert(TABLE_MATIERE, null, values);
        values = new ContentValues();
        values.put(MATIERE_KEY_NOM, "PIECE PLASTIQUE 5CM");
        values.put(MATIERE_KEY_PRIX,0.50);
        db.insert(TABLE_MATIERE, null, values);
        values = new ContentValues();
        values.put(MATIERE_KEY_NOM, "PIECE PLASTIQUE 1CM");
        values.put(MATIERE_KEY_PRIX, 5.50);
        db.insert(TABLE_MATIERE, null, values);
        values = new ContentValues();
        values.put(MATIERE_KEY_NOM, "PIECE PLASTIQUE 2CM");
        values.put(MATIERE_KEY_PRIX,4.50);
        db.insert(TABLE_MATIERE, null, values);
        values = new ContentValues();
        values.put(MATIERE_KEY_NOM,"PIECE PLASTQIUE 6CM");
        values.put(MATIERE_KEY_PRIX,7.50);
        db.insert(TABLE_MATIERE,null,values);
    }

    // CReate FakeAffectationMatiere
    public void createFakeAffectationMatiere(SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        values.put(AFFECTATION_MATIERE_KEY_ID_MATIERE,1);
        values.put(AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE,1);
        values.put(AFFECTATION_MATIERE_KEY_QUANTITE,50);
        db.insert(TABLE_AFFECTATION_MATIERE,null,values);

        values = new ContentValues();
        values.put(AFFECTATION_MATIERE_KEY_ID_MATIERE,2);
        values.put(AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE,1);
        values.put(AFFECTATION_MATIERE_KEY_QUANTITE,600);
        db.insert(TABLE_AFFECTATION_MATIERE, null, values);

        values = new ContentValues();
        values.put(AFFECTATION_MATIERE_KEY_ID_MATIERE,3);
        values.put(AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE,1);
        values.put(AFFECTATION_MATIERE_KEY_QUANTITE,350);
        db.insert(TABLE_AFFECTATION_MATIERE, null, values);

        values = new ContentValues();
        values.put(AFFECTATION_MATIERE_KEY_ID_MATIERE,4);
        values.put(AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE,2);
        values.put(AFFECTATION_MATIERE_KEY_QUANTITE,10);
        db.insert(TABLE_AFFECTATION_MATIERE, null, values);

        values = new ContentValues();
        values.put(AFFECTATION_MATIERE_KEY_ID_MATIERE,5);
        values.put(AFFECTATION_MATIERE_KEY_ID_NOMENCLATURE,2);
        values.put(AFFECTATION_MATIERE_KEY_QUANTITE,150);
        db.insert(TABLE_AFFECTATION_MATIERE,null,values);
    }


}
