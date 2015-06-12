package BDD;

import java.sql.Date;

/**
 * Created by Christophe on 11/05/2015.
 */
public class Commande {

    private long id;
    private long client_id;
    private int numCommande;
    private float total;
    private String dateCommande;

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getClientId()
    {
        return this.client_id;
    }

    public void setClientId(long client_id)
    {
        this.client_id = client_id;
    }

    public int getNumCommande()
    {
        return this.numCommande;
    }

    public void setNumCommande(int num)
    {
        this.numCommande = num;
    }

    public float getTotal()
    {
        return this.total;
    }

    public void setTotal(float total)
    {
        this.total = total;
    }

    public String getDateCommande()
    {
        return this.dateCommande;
    }

    public void setDateCommande(String date)
    {
        this.dateCommande = date;
    }
}
