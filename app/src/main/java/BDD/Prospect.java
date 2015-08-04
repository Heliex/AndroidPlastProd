package BDD;

import java.io.Serializable;

/**
 * Created by Kirill on 11/06/2015.
 * Classe qui repr�sente l'entit� prospect en base de donn�es.
 * @author Kirill Safronov
 * @version 1.0
 */
public class Prospect implements Serializable{
    // Id
    private long id;
    // Nom
    private String nom;
    // Prenom
    private String prenom;
    // Adresse
    private String adresse;
    // Telephone
    private String telephone;
    // Email
    private String email;
    // Date
    private String date;

    private int pourcentage ;

    /**
     * Constructeur par d�faut
     */
    public Prospect()
    {
        this.id = 0;
        this.nom = null;
        this.prenom = null;
        this.adresse = null;
        this.telephone = null;
        this.email = null;
        this.date = null;
        this.pourcentage = 0;
    }

    /**
     * Retourne l'id du prospect
     * @return id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Modifie l'id du prospect
     * @param id Nouvelle id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Retourne le nom du prospect
     * @return nom
     */
    public String getNom()
    {
        return this.nom;
    }

    /**
     * Modifie le nom du prospect
     * @param nom Nouveau nom
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * Retourne le pr�nom du prospect
     * @return prenom
     */
    public String getPrenom()
    {
        return this.prenom;
    }

    /**
     * Modifie le pr�nom du prospect
     * @param prenom Nouveau pr�nom
     */
    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    /**
     * Retourne l'adresse du prospect
     * @return adresse
     */
    public String getAdresse()
    {
        return this.adresse;
    }

    /**
     * Modifie l'adresse du prospect
     * @param adresse Nouvelle adresse
     */
    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    /**
     * Retourne le num�ro de t�l�phone du prospect
     * @return telephone
     */
    public String getTelephone()
    {
        return this.telephone;
    }

    /**
     * Modifie le num�ro de t�l�phone du prospect
     * @param telephone Nouveau num�ro de t�l�phone
     */
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    /**
     * Retourne l'email du prospect
     * @return email
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Modifie l'email du prospect
     * @param email Nouvelle email du prospect
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Retourne la date d'inscription du prospect
     * @return date
     */
    public String getDate()
    {
        return this.date;
    }

    /**
     * Modifie la date d'inscription du prospect
     * @param date Nouvelle date
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * Retourne le pourcentage de progression du prospect
     * @return pourcentage
     */
    public int getPourcentage()
    {
        return this.pourcentage;
    }

    /**
     * Modifie le pourcentage du prospect
     * @param pourcent Nouveau pourcentage
     */
    public void setPourcentage(int pourcent)
    {
        this.pourcentage = pourcent;
    }

}
