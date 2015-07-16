package BDD;

import junit.framework.TestCase;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class AffectationDevisTest extends TestCase {

    public void testGetIdCommande() throws Exception {
        AffectationDevis af = new AffectationDevis();
        assertEquals(0,af.getIdCommande());

    }

    public void testSetIdCommande() throws Exception {
        AffectationDevis af = new AffectationDevis();
        af.setIdCommande(666);
        assertEquals(666,af.getIdCommande());
    }

    public void testGetIdNomenclature() throws Exception {
        AffectationDevis af = new AffectationDevis();
        assertEquals(0,af.getIdNomenclature());
    }

    public void testSetIdNomenclature() throws Exception {
        AffectationDevis af = new AffectationDevis();
        af.setIdNomenclature(666);
        assertEquals(666,af.getIdNomenclature());

    }

    public void testGetQuantite() throws Exception {
        AffectationDevis af = new AffectationDevis();
        assertEquals(0,af.getQuantite());

    }

    public void testSetQuantite() throws Exception {
        AffectationDevis af = new AffectationDevis();
        af.setQuantite(150);
        assertEquals(150,af.getQuantite());
    }
}