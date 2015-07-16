package BDD;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class CommandeTest extends TestCase {

    public void testGetId() {
        Commande commande = new Commande();
        long id_expected = 0;
        long id = commande.getId();
        assertEquals(id_expected,id);
    }

    public void testSetId() {
        Commande commande = new Commande();
        long id_expected = 88;
        commande.setId(88);
        long id = commande.getId();
        assertEquals(id_expected,id);
    }

    public void testGetClientId()  {
        Commande commande = new Commande();
        long idClient_expected = 0;
        long idClient = commande.getClientId();
        assertEquals(idClient_expected,idClient);

    }

    public void testSetClientId(){
        Commande commande = new Commande();
        long idClient_expected = 888;
        commande.setClientId(888);
        long idClient = commande.getClientId();
        assertEquals(idClient_expected,idClient);
    }

    public void testGetNumCommande()  {

        Commande commande = new Commande();
        int numExpected = 0;
        int num = commande.getNumCommande();
        assertEquals(numExpected,num);

    }

    public void testSetNumCommande() {

        Commande commande = new Commande();
        int numExpected = 888;
        commande.setNumCommande(888);
        int num = commande.getNumCommande();
        assertEquals(numExpected,num);

    }

    public void testGetTotal()  {

        Commande commande = new Commande();
        double total = commande.getTotal();
        double totalExpected= 0;
        assertEquals(totalExpected,total);
    }

    public void testSetTotal(){

        Commande commande = new Commande();
        commande.setTotal(55.55);
        double expected = 55.55;
        assertEquals(expected,commande.getTotal());

    }

    public void testGetDateCommande()  {
        Commande commande = new Commande();
        assertEquals(null,commande.getDateCommande());

    }

    public void testSetDateCommande(){
        Commande commande = new Commande();
        commande.setDateCommande("12/05/2015");
        assertEquals("12/05/2015",commande.getDateCommande());

    }

    public void testGetListeNomenclature(){
        Commande commande = new Commande();
        assertEquals(0,commande.getListeNomenclature().size());

    }

    public void testSetListeNomenclature(){
        Commande commande = new Commande();
        ArrayList<Nomenclature> listeNomenclature = new ArrayList<Nomenclature>();
        listeNomenclature.add(new Nomenclature());
        commande.setListeNomenclature(listeNomenclature);
        assertEquals(1,commande.getListeNomenclature().size());
    }
}