package BDD;

/**
 * Created by Christophe on 26/06/2015. For PlastProd Project on purpose
 * Représentation de l'entite AffectationMatiere en BDD.
 *
 * @author Christophe Gerard
 * @version 1.0
 */
public class AffectationMatiere {

    private long idMatiere;
    private long idNomenclature;
    private int quantite;
    private long id;

    /**
     * Constructeur par defaut
     */
    public AffectationMatiere() {
        this.id = 0;
        this.idMatiere = 0;
        this.idNomenclature = 0;
        this.quantite = 0;
    }

    /**
     * retourne l'id de la matiere associé à la nomenclature
     *
     * @return idMatiere
     */
    public long getIdMatiere() {
        return this.idMatiere;
    }

    /**
     * Modifie l'id de la matière associée à la nomenclature
     *
     * @param idMatiere l'id de la matière associée
     */
    public void setIdMatiere(long idMatiere) {
        this.idMatiere = idMatiere;
    }

    /**
     * Retourne l'id de la nomenclature associé à la matiere
     *
     * @return idNomenclature
     */
    public long getIdNomenclature() {
        return this.idNomenclature;
    }

    /**
     * Modifie l'id de la nomenclature
     *
     * @param idNomenclature id de la nomenclature
     */
    public void setIdNomenclature(long idNomenclature) {
        this.idNomenclature = idNomenclature;
    }

    /**
     * Retourne la quantite de matiere pour une nomenclature
     *
     * @return quantite
     */
    public int getQuantite() {
        return this.quantite;
    }

    /**
     * Modifie la quantite de matiere pour une nomenclature
     *
     * @param quantite quantitée de la matiere
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Change l'id de l'affectationMatiere
     *
     * @param id id de l'affectation matiere
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retourne l'id de l'affectation de la matiere
     *
     * @return id
     */
    public long getId() {
        return this.id;
    }
}
