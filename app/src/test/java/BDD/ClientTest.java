package BDD;

import junit.framework.TestCase;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class ClientTest extends TestCase {

    public void testGetId() throws Exception {
        Client c = new Client();
        assertEquals(0,c.getId());

    }

    public void testSetId() throws Exception {
        Client c = new Client();
        c.setId(123124);
        assertEquals(123124,c.getId());
    }

    public void testGetNom() throws Exception {
        Client c = new Client();
        assertEquals(null,c.getNom());
    }

    public void testSetNom() throws Exception {
        Client c = new Client();
        c.setNom("GERARD");
        assertEquals("GERARD",c.getNom());

    }

    public void testGetPrenom() throws Exception {
        Client c = new Client();
        assertEquals(null,c.getPrenom());

    }

    public void testSetPrenom() throws Exception {
        Client c = new Client();
        c.setPrenom("CHRISTOPHE");
        assertEquals("CHRISTOPHE", c.getPrenom());

    }

    public void testGetAdresse() throws Exception {
        Client c = new Client();
        assertEquals(null,c.getAdresse());
    }

    public void testSetAdresse() throws Exception {
        Client c = new Client();
        c.setAdresse("12 RUE DES JAMBONS");
        assertEquals("12 RUE DES JAMBONS",c.getAdresse());

    }

    public void testGetTelephone() throws Exception {
        Client c = new Client();
        assertEquals(null,c.getTelephone());
    }

    public void testSetTelephone() throws Exception {
        Client c = new Client();
        c.setTelephone("0623941032");
        assertEquals("0623941032",c.getTelephone());

    }

    public void testGetEmail() throws Exception {
        Client c = new Client();
        assertEquals(null,c.getEmail());
    }

    public void testSetEmail() throws Exception {
        Client c = new Client();
        c.setEmail("Christophe.gerard8@gmail.com");
        assertEquals("Christophe.gerard8@gmail.com",c.getEmail());

    }

    public void testGetDate() throws Exception {
        Client c = new Client();
        assertEquals(null,c.getDate());

    }

    public void testSetDate() throws Exception {
        Client c = new Client();
        c.setDate("12/05/2015");
        assertEquals("12/05/2015",c.getDate());

    }
}