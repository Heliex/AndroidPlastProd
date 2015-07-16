package BDD;

import junit.framework.TestCase;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class MatiereTest extends TestCase {

    public void testGetId() throws Exception {

        Matiere m = new Matiere();
        assertEquals(0,m.getId());

    }

    public void testGetNom() throws Exception {
        Matiere m = new Matiere();
        assertEquals(null,m.getNom());
    }

    public void testGetPrix() throws Exception {
        Matiere m = new Matiere();
        assertEquals(0.0,m.getPrix());
    }

    public void testSetId() throws Exception {
        Matiere m = new Matiere();
        m.setId(666);
        assertEquals(666,m.getId());
    }

    public void testSetNom() throws Exception {
        Matiere m = new Matiere();
        m.setNom("TUYAUX");
        assertEquals("TUYAUX",m.getNom());

    }

    public void testSetPrix() throws Exception {
        Matiere m = new Matiere();
        m.setPrix(55.55);
        assertEquals(55.55,m.getPrix());
    }
}