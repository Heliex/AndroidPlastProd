package BDD;

import java.util.ArrayList;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 * Classe qui représente l'entité Nomenclature en BDD.
 * @author Christophe Gerard
 * @version 1.0
 */
public class Nomenclature {

    private String nom;
    private long id;
    private ArrayList<Matiere> listeMatiere;
    private int quantite;

    /**
     * Constructeur par defaut
     */
    public Nomenclature()
    {
        this.nom = null;
        this.id = 0;
        this.listeMatiere = null;
        this.quantite = 0;
    }

    /**
     * Retourne l'id de la nomenclature
     * @return id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Retourne le nom de la nomenclature
     * @return nom
     */
    public String getNom()
    {
        return this.nom;
    }

    /**
     * Retourne la liste de matière associée à cette nomenclature
     * @return listeMatiere - Une liste d'objet Matiere
     */
    public ArrayList<Matiere> getListeMatiere()
    {
        return this.listeMatiere;
    }

    /**
     * Modifie l'id de la nomenclature
     * @param id Nouvelle ID
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Modifie le nom de la nomenclature
     * @param nom Nouveau nom
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * Modifie la liste de matière associée
     * @param listeMatiere Nouvelle liste de matière
     */
    public void setListeMatiere(ArrayList<Matiere> listeMatiere)
    {
        this.listeMatiere = listeMatiere;
    }

    /**
     * Retourne le prix total de la nomenclature
     * @param db DataBaseHandler
     * @param id_nomenclature Id de la nomenclature
     * @return le prix total de la nomenclature
     */
    public double getPrixTotal(DataBaseHandler db,long id_nomenclature)
    {
        double somme = 0;
        if(listeMatiere != null)
        {
            for(int i = 0 ; i < listeMatiere.size(); i++)
            {
                long id = listeMatiere.get(i).getId();
                int qte = db.getQuantiteMatiere(id_nomenclature,id);
                double prix = listeMatiere.get(i).getPrix();
                somme += (prix * qte);
            }
        }
        return somme;
    }

    /**
     * Retourne la quantité
     * @return quantite
     */
    public int getQuantite()
    {
        return this.quantite;
    }

    /**
     * Modifie la quantite
     * @param qte Nouvelle quantitée
     */
    public void setQuantite(int qte)
    {
        this.quantite = qte;
    }
}
