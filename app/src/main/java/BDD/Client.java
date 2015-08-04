package BDD;

import java.io.Serializable;

/**
 * Created by christophe on 03/04/2015.
 * Classe qui représente le Client dans la BDD.
 * @author Christophe Gerard.
 * @version 1.0
 */
public class Client implements Serializable { /* Id*/
    private long id; /* Nom*/
    private String nom; /* Prenom*/
    private String prenom; /* Adresse*/
    private String adresse; /* Telephone*/
    private String telephone; /* Email*/
    private String email; /* Date*/
    private String date;

    /**
     * Constructeur par default
     */
    public Client() {
        this.id = 0;
        this.nom = null;
        this.prenom = null;
        this.adresse = null;
        this.email = null;
        this.telephone = null;
        this.date = null;
    }

    /**
     * Construit un Client selon les parametres envoyées @param nom Nom du client @param prenom Prenom du client @param adresse Adresse du client @param telephone Telephone du client @param email Email du client @param date Date d'inscription du client
     */
    public Client(String nom, String prenom, String adresse, String telephone, String email, String date) {
        this.id = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.date = date;
    }

    /**
     * Retourne l'id du client
     * @return id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Modifie l'id du client
     *
     * @param id id du client
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retourne le nom du client
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Modifie le nom du client
     * @param nom Nom du client
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le prenom du client
     * @return prenom
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Modifie le prenom du client
     * @param prenom Prenom du client
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne l'adresse du client
     * @return adresse
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Modifie l'adresse du client
     * @param adresse Adresse du client
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Retourne le numéro de téléphone du client
     * @return telephone
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * Modifie le numéro de téléphone du client
     * @param telephone Numéro de téléphone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Retourne l'email du client
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Modifie l'email du client
     * @param email Email du client
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne la date d'inscription du client
     * @return date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Modifie la date d'inscription du client
     * @param date Date d'inscription
     */
    public void setDate(String date) {
        this.date = date;
    }
}
