package BDD;

import junit.framework.TestCase;

import java.util.Properties;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class ProspectTest extends TestCase {

    public void testGetId() throws Exception {
        Prospect p = new Prospect();
        assertEquals(0,p.getId());

    }

    public void testSetId() throws Exception {
        Prospect p = new Prospect();
        p.setId(123124);
        assertEquals(123124,p.getId());
    }

    public void testGetNom() throws Exception {
        Prospect p = new Prospect();
        assertEquals(null,p.getNom());
    }

    public void testSetNom() throws Exception {
        Prospect p = new Prospect();
        p.setNom("GERARD");
        assertEquals("GERARD",p.getNom());

    }

    public void testGetPrenom() throws Exception {
        Prospect p = new Prospect();
        assertEquals(null,p.getPrenom());

    }

    public void testSetPrenom() throws Exception {
        Prospect p = new Prospect();
        p.setPrenom("CHRISTOPHE");
        assertEquals("CHRISTOPHE", p.getPrenom());

    }

    public void testGetAdresse() throws Exception {
        Prospect p = new Prospect();
        assertEquals(null,p.getAdresse());
    }

    public void testSetAdresse() throws Exception {
        Prospect p = new Prospect();
        p.setAdresse("12 RUE DES JAMBONS");
        assertEquals("12 RUE DES JAMBONS",p.getAdresse());

    }

    public void testGetTelephone() throws Exception {
        Prospect p = new Prospect();
        assertEquals(null,p.getTelephone());
    }

    public void testSetTelephone() throws Exception {
        Prospect p = new Prospect();
        p.setTelephone("0623941032");
        assertEquals("0623941032",p.getTelephone());

    }

    public void testGetEmail() throws Exception {
        Prospect p = new Prospect();
        assertEquals(null,p.getEmail());
    }

    public void testSetEmail() throws Exception {
        Prospect p = new Prospect();
        p.setEmail("Christophe.gerard8@gmail.com");
        assertEquals("Christophe.gerard8@gmail.com",p.getEmail());

    }

    public void testGetDate() throws Exception {
        Prospect p = new Prospect();
        assertEquals(null,p.getDate());

    }

    public void testSetDate() throws Exception {
        Prospect p = new Prospect();
        p.setDate("12/05/2015");
        assertEquals("12/05/2015",p.getDate());

    }

    public void testGetPourcentage() throws Exception {
        Prospect p = new Prospect();
        assertEquals(0,p.getPourcentage());
    }

    public void testSetPourcentage() throws Exception {
        Prospect p = new Prospect();
        p.setPourcentage(80);
        assertEquals(80,p.getPourcentage());
    }
}