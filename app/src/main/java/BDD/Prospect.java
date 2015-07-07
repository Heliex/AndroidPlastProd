package BDD;

/**
 * Created by Kirill on 11/06/2015.
 */
public class Prospect {
    // Id
    private long id;
    // Nom
    private String nom;
    // Prenom
    private String prenom;
    // Adresse
    private String adresse;
    // Telephone
    private String telephone;
    // Email
    private String email;
    // Date
    private String date;

    private int pourcentage ;

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getNom()
    {
        return this.nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return this.prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getAdresse()
    {
        return this.adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public String getTelephone()
    {
        return this.telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public  int getPourcentage()
    {
        return this.pourcentage;
    }

    public void setPourcentage(int pourcent)
    {
        this.pourcentage = pourcent;
    }

}
