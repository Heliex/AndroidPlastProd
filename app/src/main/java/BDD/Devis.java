package BDD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Christophe on 06/07/2015. For PlastProd Project on purpose
 */
public class Devis {

    private long id;
    private long prospect_id;
    private double total;
    private int numDevis;
    private String dateDevis;
    private ArrayList<Nomenclature> listeNomenclatures;
    private final static int DEVIS_DEMANDE_CLIENT = 35;
    private final static int DEVIS_INTERET = 70;
    private final static int DEVIS_EMIS = 80;

    public Devis()
    {
        this.id = 0;
        this.prospect_id = 0;
        this.total = 0;
        this.numDevis = 0;
        this.dateDevis = null;
        this.listeNomenclatures = new ArrayList<>();
    }

    public Devis(long id_prospect,double total,int numDevis,ArrayList<Nomenclature> listeNomenclatures)
    {
        this.prospect_id = id_prospect;
        this.total = total;
        this.numDevis = numDevis;
        this.listeNomenclatures = listeNomenclatures;
        this.dateDevis = getDate(System.currentTimeMillis(),"dd/MM/yyyy hh:mm:ss");
    }

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getId_prospect()
    {
        return this.prospect_id;
    }

    public void setId_prospect(long prospect_id)
    {
        this.prospect_id = prospect_id;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getTotal()
    {
        return this.total;
    }

    public int getNumDevis()
    {
        return this.numDevis;
    }

    public void setNumDevis(int numDevis)
    {
        this.numDevis = numDevis;
    }

    public String getDateDevis()
    {
        return this.dateDevis;
    }

    public void setDateDevis(String date)
    {
        this.dateDevis = date;
    }

    public ArrayList<Nomenclature> getListeNomenclatures()
    {
        return this.listeNomenclatures;
    }

    public void setListeNomenclatures(ArrayList<Nomenclature> listeNomenclatures)
    {
        this.listeNomenclatures = listeNomenclatures;
    }

    private String getDate(long milliseconds,String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    public static int getDevisDemandeClient()
    {
        return DEVIS_DEMANDE_CLIENT;
    }

    public static int getDevisInteret()
    {
        return DEVIS_INTERET;
    }

    public static  int getDevisEmis()
    {
        return DEVIS_EMIS;
    }
}
