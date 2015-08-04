package BDD;

/**
 * Created by Christophe on 26/06/2015. For PlastProd Project on purpose
 * Classe qui représente l'entité AffectationCommande en BDD
 *
 * @author Gerard Christophe
 * @version 1.0
 */
public class AffectationCommande {

    /**
     *
     */
    private long idCommande;
    private long id;
    private long idNomenclature;
    private int quantite;

    /**
     * Constructeur par defaut
     */
    public AffectationCommande() {
        this.id = 0;
        this.idCommande = 0;
        this.idNomenclature = 0;
        this.quantite = 0;
    }

    /**
     * Retourne l'id d'une commande
     *
     * @return idCommande
     */
    public long getIdCommande() {
        return this.idCommande;
    }

    /**
     * Modifie l'id d'une commande
     *
     * @param idCommande id de la commande
     */
    public void setIdCommande(long idCommande) {
        this.idCommande = idCommande;
    }

    /**
     * Retourne l'id de la nomenclature associé à la commande
     *
     * @return idNomenclature
     */
    public long getIdNomenclature() {
        return this.idNomenclature;
    }

    /**
     * Modifie l'id d'une nomenclature
     *
     * @param idNomenclature id de la nomenclature
     */
    public void setIdNomenclature(long idNomenclature) {
        this.idNomenclature = idNomenclature;
    }

    /**
     * Retourne la quantite de nomenclature pour une commande
     *
     * @return quantite
     */
    public int getQuantite() {
        return this.quantite;
    }

    /**
     * Modifie la quantite de nomenclatuer associé à la commande
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
     * Retourne l'id de l'affectation
     *
     * @return id
     */
    public long getId() {
        return this.id;
    }
}
