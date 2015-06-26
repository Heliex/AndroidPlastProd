package BDD;

/**
 * Created by Christophe on 26/06/2015. For PlastProd Project on purpose
 */
public class AffectationMatiere {

    private long idMatiere;
    private long idNomenclature;
    private int quantite;

    public long getIdMatiere()
    {
        return this.idMatiere;
    }

    public void setIdMatiere(long idMatiere)
    {
        this.idMatiere = idMatiere;
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
