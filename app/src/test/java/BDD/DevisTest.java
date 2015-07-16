package BDD;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class DevisTest extends TestCase {

    public void testGetId() throws Exception {
        Devis d = new Devis();
        assertEquals(0,d.getId());

    }

    public void testSetId() throws Exception {
        Devis d = new Devis();
        d.setId(234);
        assertEquals(234,d.getId());

    }

    public void testGetId_prospect() throws Exception {
        Devis d = new Devis();
        assertEquals(0,d.getId_prospect());

    }

    public void testSetId_prospect() throws Exception {
        Devis d = new Devis();
        d.setId_prospect(234234);
        assertEquals(234234,d.getId_prospect());
    }

    public void testSetTotal() throws Exception {
        Devis d = new Devis();
        d.setTotal(55.55);
        assertEquals(55.55,d.getTotal());

    }

    public void testGetTotal() throws Exception {
        Devis d = new Devis();
        assertEquals(0.0,d.getTotal());

    }

    public void testGetNumDevis() throws Exception {
        Devis d = new Devis();
        assertEquals(0,d.getNumDevis());

    }

    public void testSetNumDevis() throws Exception {
        Devis d = new Devis();
        d.setNumDevis(1234567);
        assertEquals(1234567,d.getNumDevis());

    }

    public void testGetDateDevis() throws Exception {
        Devis d = new Devis();
        assertEquals(null,d.getDateDevis());

    }

    public void testSetDateDevis() throws Exception {
        Devis d = new Devis();
        d.setDateDevis("12/05/2015");
        assertEquals("12/05/2015",d.getDateDevis());

    }

    public void testGetListeNomenclatures() throws Exception {
        Devis devis = new Devis();
        assertEquals(0,devis.getListeNomenclatures().size());

    }

    public void testSetListeNomenclatures() throws Exception {
        Devis devis = new Devis();
        Nomenclature nomenclature = new Nomenclature();
        ArrayList<Nomenclature> listeNomenclature= new ArrayList<>();
        listeNomenclature.add(nomenclature);
        devis.setListeNomenclatures(listeNomenclature);
        assertEquals(1,devis.getListeNomenclatures().size());

    }

    public void testGetDevisDemandeClient() throws Exception {
        assertEquals(35,Devis.getDevisDemandeClient());

    }

    public void testGetDevisInteret() throws Exception {
        assertEquals(70,Devis.getDevisInteret());
    }

    public void testGetDevisEmis() throws Exception {
        assertEquals(80,Devis.getDevisEmis());
    }
}