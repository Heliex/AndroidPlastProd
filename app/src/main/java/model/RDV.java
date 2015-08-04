package model;

/**
 * Created by Christophe on 10/06/2015. For PlastProd Project
 * Classe qui repr�sente un RDV pour google Calendar
 * @author Christophe Gerard
 * @version 1.0
 */
public class RDV {

    private String name;
    private String hDebut;
    private String hFin;
    private String date;

    /**
     * Construit un RDV
     * @param name Nom de RDV
     * @param debut Heure de debut
     * @param fin Heure de fin
     * @param date Date de RDV
     */
    public RDV(String name, String debut, String fin, String date)
    {
        this.name = name;
        this.hDebut = debut;
        this.hFin = fin;
        this.date= date;
    }

    /**
     * @return Le nom du RDV
     */
    public String getName()
    {
        if(this.name != null) {
            return this.name;
        }
        return null;
    }

    /**
     * @return L'heure de d�but de RDV
     */
    public String gethDebut()
    {
        if(this.hDebut !=null)
            return this.hDebut;

        return null;
    }

    /**
     * @return L'heure de fin du rdv
     */
    public String gethFin()
    {
        if(this.hFin != null)
            return this.hFin;

        return null;
    }

    /**
     * Modifie le nom de RDV
     * @param name Nouveau Nom
     */
    public void setName(String name)
    {
        if(name != null)
        {
            this.name = name;
        }
    }

    /**
     * Modifie l'heure de d�but du RDV
     * @param hdebut Nouvelle heure de d�but
     */
    public void setHDebut(String hdebut)
    {
        if(hdebut != null)
        {
            this.hDebut = hdebut;
        }
    }

    /**
     * Modifie l'heure de fin du RDV
     * @param hfin Nouvelle heure de fin
     */
    public void sethFin(String hfin)
    {
        if(hfin != null)
        {
            this.hFin = hfin;
        }
    }

    /**
     * @return La date en chaine de carat�res
     */
    public String getDate()
    {
        if(date!=null)
            return this.date;
        return null;
    }

    /**
     * Modifie la date
     * @param date Nouvelle date
     */
    public void setDate(String date)
    {
        if(date !=null)
            this.date = date;
    }
}
