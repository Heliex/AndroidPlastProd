package BDD;

/**
 * Created by Christophe on 06/07/2015. For PlastProd Project on purpose
 */
public class AffectationDevis {

    private long idDevis;
    private long idNomenclature;
    private int quantite;

    public AffectationDevis()
    {
        this.idDevis = 0;
        this.idNomenclature = 0;
        this.quantite = 0;
    }

    public long getIdCommande()
    {
        return  this.idDevis;
    }

    public void setIdCommande(long idDevis)
    {
        this.idDevis = idDevis;
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
