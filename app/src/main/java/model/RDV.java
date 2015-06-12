package model;

/**
 * Created by Christophe on 10/06/2015. For PlastProd Project
 */
public class RDV {

    private String name;
    private String hDebut;
    private String hFin;
    private String date;


    public RDV(String name, String debut, String fin, String date)
    {
        this.name = name;
        this.hDebut = debut;
        this.hFin = fin;
        this.date= date;
    }

    public String getName()
    {
        if(this.name != null) {
            return this.name;
        }
        return null;
    }

    public String gethDebut()
    {
        if(this.hDebut !=null)
            return this.hDebut;

        return null;
    }

    public String gethFin()
    {
        if(this.hFin != null)
            return this.hFin;

        return null;
    }

    public void setName(String name)
    {
        if(name != null)
        {
            this.name = name;
        }
    }

    public void setHDebut(String hdebut)
    {
        if(hdebut != null)
        {
            this.hDebut = hdebut;
        }
    }

    public void sethFin(String hfin)
    {
        if(hfin != null)
        {
            this.hFin = hfin;
        }
    }

    public String getDate()
    {
        if(date!=null)
            return this.date;
        return null;
    }

    public void setDate(String date)
    {
        if(date !=null)
            this.date = date;
    }
}
