package BDD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Christophe on 06/07/2015. For PlastProd Project on purpose
 * Classe qui représente l'entité Devis en BDD.
 * @author Christophe Gerard
 * @version 1.0
 */
public class Devis {

    private long id;
    private long prospect_id;
    private double total;
    private int numDevis;
    private String dateDevis;
    private ArrayList<Nomenclature> listeNomenclatures;
    private final static int DEVIS_DEMANDE_CLIENT = 35;
    private final static int DEVIS_INTERET = 50;
    private final static int DEVIS_EMIS = 80;

    /**
     * Constructeur par défaut
     */
    public Devis()
    {
        this.id = 0;
        this.prospect_id = 0;
        this.total = 0;
        this.numDevis = 0;
        this.dateDevis = null;
        this.listeNomenclatures = new ArrayList<>();
    }

    /**
     * Constructeur qui crée un Devis selon les paramètres
     * @param id_prospect Id du prospect
     * @param total Total du devis
     * @param numDevis Numéro du devis
     * @param listeNomenclatures Liste de nomenclatures associée
     */
    public Devis(long id_prospect,double total,int numDevis,ArrayList<Nomenclature> listeNomenclatures)
    {
        this.prospect_id = id_prospect;
        this.total = total;
        this.numDevis = numDevis;
        this.listeNomenclatures = listeNomenclatures;
        this.dateDevis = getDate(System.currentTimeMillis(),"dd/MM/yyyy hh:mm:ss");
    }

    /**
     * Retourne l'id du devis
     * @return id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Modifie l'id du devis
     * @param id Nouveau id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Retourne l'id du prospect associé au devis
     * @return prospect_id
     */
    public long getId_prospect()
    {
        return this.prospect_id;
    }

    /**
     * Modifie l'id du prospect associé
     * @param prospect_id Nouveau id de prospect
     */
    public void setId_prospect(long prospect_id)
    {
        this.prospect_id = prospect_id;
    }

    /**
     * Modifie le total du devis
     * @param total Nouveau total
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * Retourne le total du devis
     * @return total
     */
    public double getTotal()
    {
        return this.total;
    }

    /**
     * Retourne le numéro de devis
     * @return numDevis
     */
    public int getNumDevis()
    {
        return this.numDevis;
    }

    /**
     * Modifie le numéro de Devis
     * @param numDevis Nouveau numéro de Devis
     */
    public void setNumDevis(int numDevis)
    {
        this.numDevis = numDevis;
    }

    /**
     * Retourne la date de création du devis
     * @return dateDevis
     */
    public String getDateDevis()
    {
        return this.dateDevis;
    }

    /**
     * Modifie la date du devis
     * @param date Nouvelle date de devis
     */
    public void setDateDevis(String date)
    {
        this.dateDevis = date;
    }

    /**
     * Retourne la liste de nomenclatures associée au devis
     * @return listeNomenclatures
     */
    public ArrayList<Nomenclature> getListeNomenclatures()
    {
        return this.listeNomenclatures;
    }

    /**
     * Modifie la liste de nomenclatures associée au devis
     * @param listeNomenclatures Nouvelle liste de nomenclatures
     */
    public void setListeNomenclatures(ArrayList<Nomenclature> listeNomenclatures)
    {
        this.listeNomenclatures = listeNomenclatures;
    }

    /**
     * Formate une date en chaine de caractères
     * @param milliseconds Timestamp
     * @param dateFormat Format de la date (Ex : "dd/MM/yyyy").
     * @return une date formatée en chaine de caractères
     */
    private String getDate(long milliseconds,String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * Retourne le pourcentage au stade Demande Client
     * @return un entier
     */
    public static int getDevisDemandeClient()
    {
        return DEVIS_DEMANDE_CLIENT;
    }

    /**
     * Retourne le pourcentage au Stade Devis Interet
     * @return un entier
     */
    public static int getDevisInteret()
    {
        return DEVIS_INTERET;
    }

    /**
     * Retourne le pourcentage au stade Devis Emis
     * @return un entier
     */
    public static  int getDevisEmis()
    {
        return DEVIS_EMIS;
    }
}
