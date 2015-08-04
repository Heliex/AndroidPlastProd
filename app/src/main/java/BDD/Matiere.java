package BDD;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 * Classe qui repr�sente l'entit� Matiere en BDD.
 * @author Christophe Gerard
 * @version 1.0
 */
public class Matiere {

    private long id;
    private String nom;
    private double prix;

    /**
     * Constructeur par d�faut
     */
    public Matiere()
    {
        this.id = 0;
        this.nom = null;
        this.prix = 0;
    }

    /**
     * Construit une mati�re
     * @param id Id de la mati�re
     * @param nom Nom de la mati�re
     * @param prix Prix de la mati�re
     */
    public Matiere(long id,String nom, float prix)
    {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    /**
     * Retourne l'id de la mati�re
     * @return id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Retourne le nom de la mati�re
     * @return nom
     */
    public String getNom()
    {
        return this.nom;
    }

    /**
     * Retourne le prix de la mati�re
     * @return prix
     */
    public double getPrix()
    {
        return this.prix;
    }

    /**
     * Modifie l'id de la mati�re
     * @param id Nouvelle id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Modifie le nom de la mati�re
     * @param name Nouveau nom
     */
    public void setNom(String name)
    {
        this.nom = name;
    }

    /**
     * Modifie le prix de la mati�re
     * @param price Nouveau prix
     */
    public void setPrix(double price)
    {
        this.prix = price;
    }
}
