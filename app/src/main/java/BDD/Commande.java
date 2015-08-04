package BDD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Christophe on 11/05/2015.
 * Classe qui représente l'entité Commande en BDD.
 * @author Christophe Gerard
 * @version 1.0
 */
public class Commande {

    private long id;
    private long client_id;
    private  int numCommande;
    private double total;
    private String dateCommande;
    private ArrayList<Nomenclature> listeNomenclature;

    /**
     * Constructeur par defaut
     */
    public Commande()
    {
        listeNomenclature = new ArrayList<>();
        this.id = 0;
        this.client_id = 0;
        this.numCommande = 0;
        this.total = 0;
        this.dateCommande = null;
    }

    /**
     * Construit une Commande selon les paramètres donné
     * @param client_id Id du client
     * @param numCommande Numéro de commande
     * @param total Total de la commande
     * @param dateCommande Date de la commande
     * @param listeNomenclature Liste de nomenclature associé
     */
    public Commande(long client_id, int numCommande,double total,String dateCommande,ArrayList<Nomenclature> listeNomenclature)
    {
        this.client_id = client_id;
        this.listeNomenclature = listeNomenclature;
        this.numCommande = numCommande;
        this.total = total;
        this.dateCommande = dateCommande;
    }

    /**
     * Construit une commande selon le paramètres donné
     * @param client_id Id du client
     * @param total Total de la commande
     * @param numCommande Numéro de commande
     * @param listeNomenclature Liste de nomenclature associé
     */
    public Commande(long client_id,double total,int numCommande,ArrayList<Nomenclature> listeNomenclature)
    {
        this.client_id = client_id;
        this.total = total;
        this.numCommande = numCommande;
        this.dateCommande = getDate(System.currentTimeMillis(),"dd/MM/yyyy hh:mm:ss");
        this.listeNomenclature = listeNomenclature;
    }

    /**
     * Retourne l'id de la commande
     * @return id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Modifie l'id de la commande
     * @param id Id de la commande
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Retourne l'id de client associé à la commande
     * @return client_id
     */
    public long getClientId()
    {
        return this.client_id;
    }

    /**
     * Modifie l'id de client
     * @param client_id Id client
     */
    public void setClientId(long client_id)
    {
        this.client_id = client_id;
    }

    /**
     * Retourne le numéro de commande
     * @return numCommande
     */
    public int getNumCommande()
    {
        return this.numCommande;
    }

    /**
     * Modifie le numéro de commande
     * @param num Numéro de commande
     */
    public void setNumCommande(int num)
    {
        this.numCommande = num;
    }

    /**
     * Retourne le total de la commande
     * @return total
     */
    public double getTotal()
    {
        return this.total;
    }

    /**
     * Modifie le total de la commande
     * @param total Total de la commande
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * Retourne la date de la commande
     * @return dateCommande
     */
    public String getDateCommande()
    {
        return this.dateCommande;
    }

    /**
     * Modifie la date de la commande
     * @param date Date de la commande
     */
    public void setDateCommande(String date)
    {
        this.dateCommande = date;
    }

    /**
     * Récupère la date courante sous forme de chaine de caractères selon le timestamp et le format passé en paramètre
     * @param milliseconds timestamp
     * @param dateFormat Format de la date (Ex : "dd/MM/yyyy")
     * @return Une date formatée en chaine de caractères
     */
    private String getDate(long milliseconds,String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * Retourne la liste de nomenclature associé à la commande
     * @return listeNomenclature
     */
   public ArrayList<Nomenclature> getListeNomenclature()
   {
       return this.listeNomenclature;
   }

    /**
     * Modifie la liste de nomenclature associé à la commande
     * @param listeNomenclature Liste de nomenclature
     */
   public void setListeNomenclature(ArrayList<Nomenclature> listeNomenclature)
   {
       this.listeNomenclature = listeNomenclature;
   }
}
