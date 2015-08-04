package BDD;

/**
 * Created by Christophe on 06/07/2015. For PlastProd Project on purpose
 * Classe AffectationDevis qui représente l'entité en BDD.
 *
 * @author Gerard Christophe
 * @version 1.0
 */

public class AffectationDevis {

    private long idDevis;
    private long idNomenclature;
    private int quantite;
    private long id;

    /**
     * Constructeur par defaut
     */
    public AffectationDevis() {
        this.idDevis = 0;
        this.idNomenclature = 0;
        this.quantite = 0;
        this.id = 0;
    }

    /**
     * Retourne l'id du Devis
     *
     * @return idDevis
     */
    public long getIdDevis() {
        return this.idDevis;
    }

    /**
     * Modifie l'id du devis
     *
     * @param idDevis id du devis
     */
    public void setIdDevis(long idDevis) {
        this.idDevis = idDevis;
    }

    /**
     * Recupére l'id de nomenclature lié au devis
     *
     * @return idNomenclature
     */
    public long getIdNomenclature() {
        return this.idNomenclature;
    }

    /**
     * Modifie l'id de nomenclature lié au devis
     *
     * @param idNomenclature id de la nomenclature
     */
    public void setIdNomenclature(long idNomenclature) {
        this.idNomenclature = idNomenclature;
    }

    /**
     * quantite de nomenclature lié au devis
     *
     * @return quantite
     */
    public int getQuantite() {
        return this.quantite;
    }

    /**
     * Modifier la quantité liée
     *
     * @param quantite quantite associée
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Modifie l'id de l'affectation
     *
     * @param id id de l'affectation
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * retourn l'id de l'affectation
     *
     * @return id
     */
    public long getId() {
        return this.id;
    }
}
