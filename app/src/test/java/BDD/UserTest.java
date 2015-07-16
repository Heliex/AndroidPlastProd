package BDD;

import junit.framework.TestCase;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class UserTest extends TestCase {

    public void testGetEmail_user() throws Exception {
        User u = new User();
        assertEquals(null,u.getEmail_user());
    }

    public void testGetMdp_user() throws Exception {
        User u = new User();
        assertEquals(null,u.getMdp_user());
    }

    public void testGetId() throws Exception {
        User u = new User();
        assertEquals(0,u.getId());

    }

    public void testSetEmail_user() throws Exception {
        User u = new User();
        u.setEmail_user("commercialplastprod@gmail.com");
        assertEquals("commercialplastprod@gmail.com",u.getEmail_user());

    }

    public void testSetMdp_user() throws Exception {
        User u = new User();
        u.setMdp_user("Commercialplastprod88");
        assertEquals("Commercialplastprod88",u.getMdp_user());

    }

    public void testSetId() throws Exception {
        User u = new User();
        u.setId(666);
        assertEquals(666,u.getId());

    }
}