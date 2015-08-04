package BDD;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 * Classe qui représente l'entité Matiere en BDD.
 * @author Christophe Gerard
 * @version 1.0
 */
public class Matiere {

    private long id;
    private String nom;
    private double prix;

    /**
     * Constructeur par défaut
     */
    public Matiere()
    {
        this.id = 0;
        this.nom = null;
        this.prix = 0;
    }

    /**
     * Construit une matière
     * @param id Id de la matière
     * @param nom Nom de la matière
     * @param prix Prix de la matière
     */
    public Matiere(long id,String nom, float prix)
    {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    /**
     * Retourne l'id de la matière
     * @return id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Retourne le nom de la matière
     * @return nom
     */
    public String getNom()
    {
        return this.nom;
    }

    /**
     * Retourne le prix de la matière
     * @return prix
     */
    public double getPrix()
    {
        return this.prix;
    }

    /**
     * Modifie l'id de la matière
     * @param id Nouvelle id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Modifie le nom de la matière
     * @param name Nouveau nom
     */
    public void setNom(String name)
    {
        this.nom = name;
    }

    /**
     * Modifie le prix de la matière
     * @param price Nouveau prix
     */
    public void setPrix(double price)
    {
        this.prix = price;
    }
}
