package BDD;

import junit.framework.TestCase;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class AffectationMatiereTest extends TestCase {

    public void testGetIdMatiere() throws Exception {
        AffectationMatiere af = new AffectationMatiere();
        assertEquals(0,af.getIdMatiere());

    }

    public void testSetIdMatiere() throws Exception {

        AffectationMatiere af = new AffectationMatiere();
        af.setIdMatiere(666);
        assertEquals(666,af.getIdMatiere());

    }

    public void testGetIdNomenclature() throws Exception {
        AffectationMatiere af = new AffectationMatiere();
        assertEquals(0,af.getIdNomenclature());
    }

    public void testSetIdNomenclature() throws Exception {
        AffectationMatiere af = new AffectationMatiere();
        af.setIdNomenclature(666);
        assertEquals(666,af.getIdNomenclature());
    }

    public void testGetQuantite() throws Exception {
        AffectationMatiere af = new AffectationMatiere();
        assertEquals(0,af.getQuantite());

    }

    public void testSetQuantite() throws Exception {
        AffectationMatiere af = new AffectationMatiere();
        af.setQuantite(150);
        assertEquals(150,af.getQuantite());
    }
}