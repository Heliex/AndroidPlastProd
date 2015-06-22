package BDD;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 */
public class Matiere {

    private long id;
    private String nom;
    private double prix;

    public Matiere()
    {

    }

    public Matiere(long id,String nom, float prix)
    {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public long getId()
    {
        return this.id;
    }

    public String getNom()
    {
        return this.nom;
    }

    public double getPrix()
    {
        return this.prix;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setNom(String name)
    {
        this.nom = name;
    }

    public void setPrix(double price)
    {
        this.prix = price;
    }
}
