package BDD;

/**
 * Created by Christophe on 26/06/2015. For PlastProd Project on purpose
 */
public class AffectationCommande {

    private long idCommande;
    private long idNomenclature;
    private int quantite;

    public long getIdCommande()
    {
        return  this.idCommande;
    }

    public void setIdCommande(long idCommande)
    {
        this.idCommande = idCommande;
    }

    public long getIdNomenclature()
    {
        return this.idNomenclature;
    }

    public void setIdNomenclature(long idNomenclature)
    {
        this.idNomenclature = idNomenclature;
    }

    public int getQuantite()
    {
        return this.quantite;
    }

    public void setQuantite(int quantite)
    {
        this.quantite = quantite;
    }
}
