package BDD;

import junit.framework.TestCase;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class AffectationCommandeTest extends TestCase {

    public void testGetIdCommande() throws Exception {
        AffectationCommande af = new AffectationCommande();
        assertEquals(0,af.getIdCommande());

    }

    public void testSetIdCommande() throws Exception {
        AffectationCommande af = new AffectationCommande();
        af.setIdCommande(666);
        assertEquals(666,af.getIdCommande());
    }

    public void testGetIdNomenclature() throws Exception {
        AffectationCommande af = new AffectationCommande();
        assertEquals(0,af.getIdNomenclature());
    }

    public void testSetIdNomenclature() throws Exception {
        AffectationCommande af = new AffectationCommande();
        af.setIdNomenclature(666);
        assertEquals(666,af.getIdNomenclature());

    }

    public void testGetQuantite() throws Exception {
        AffectationCommande af = new AffectationCommande();
        assertEquals(0,af.getQuantite());

    }

    public void testSetQuantite() throws Exception {
        AffectationCommande af = new AffectationCommande();
        af.setQuantite(150);
        assertEquals(150,af.getQuantite());
    }
}