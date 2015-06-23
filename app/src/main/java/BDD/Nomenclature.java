package BDD;

import java.util.ArrayList;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 */
public class Nomenclature {

    private String nom;
    private long id;
    private ArrayList<Matiere> listeMatiere;

    public Nomenclature()
    {
        this.listeMatiere = new ArrayList<Matiere>();
    }

    public long getId()
    {
        return this.id;
    }

    public String getNom()
    {
        return this.nom;
    }

    public ArrayList<Matiere> getListeMatiere()
    {
        return this.listeMatiere;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setListeMatiere(ArrayList<Matiere> listeMatiere)
    {
        this.listeMatiere = listeMatiere;
    }

    public double getPrixTotal(DataBaseHandler db,long id_nomenclature)
    {
        double somme = 0;
        for(int i = 0 ; i < listeMatiere.size(); i++)
        {
            long id = listeMatiere.get(i).getId();
            int qte = db.getQuantiteMatiere(id_nomenclature,id);
            double prix = listeMatiere.get(i).getPrix();
            somme += (prix * qte);
        }

        return somme;
    }
}
