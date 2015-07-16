package BDD;

import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Crated by Christophe on 11/05/2015.
 */
public class Commande {

    private long id;
    private long client_id;
    private  int numCommande;
    private double total;
    private String dateCommande;
    private ArrayList<Nomenclature> listeNomenclature;

    public Commande()
    {
        listeNomenclature = new ArrayList<>();
        this.id = 0;
    }

    public Commande(long client_id, int numCommande,double total,String dateCommande,ArrayList<Nomenclature> listeNomenclature)
    {
        this.client_id = client_id;
        this.listeNomenclature = listeNomenclature;
        this.numCommande = numCommande;
        this.total = total;
        this.dateCommande = dateCommande;
    }

    public Commande(long client_id,double total,int numCommande,ArrayList<Nomenclature> listeNomenclature)
    {
        this.client_id = client_id;
        this.total = total;
        this.numCommande = numCommande;
        this.dateCommande = getDate(System.currentTimeMillis(),"dd/MM/yyyy hh:mm:ss");
        this.listeNomenclature = listeNomenclature;
    }

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

    public double getTotal()
    {
        return this.total;
    }

    public void setTotal(double total)
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

    private String getDate(long milliseconds,String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

   public ArrayList<Nomenclature> getListeNomenclature()
   {
       return this.listeNomenclature;
   }

   public void setListeNomenclature(ArrayList<Nomenclature> listeNomenclature)
   {
       this.listeNomenclature = listeNomenclature;
   }
}
