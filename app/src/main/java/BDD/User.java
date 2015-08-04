package BDD;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 * Classe qui représente l'entité User en BDD.
 * @author Christophe Gerard
 * @version 1.0
 */
public class User {

    private long id;
    private String email_user;
    private String mdp_user;

    /**
     * Constructeur à deux paramètres
     * @param email_user Email utilisateur
     * @param mdp_user Mdp utilisateur
     */
    public User(String email_user,String mdp_user)
    {
        this.email_user = email_user;
        this.mdp_user = mdp_user;
    }

    /**
     * Constructeur par defaut
     */
    public User()
    {
        this.id = 0;
        this.email_user = null;
        this.mdp_user = null;
    }

    /**
     * Retourne l'email de l'utilisateur
     * @return email_user
     */
    public String getEmail_user()
    {
        return this.email_user;
    }

    /**
     * Retourne le mdp utilisateur
     * @return mdp_user
     */
    public String getMdp_user()
    {
        return this.mdp_user;
    }

    /**
     * Retourne l'id de l'utilisateur
     * @return id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Modifie l'email de l'utilisateur
     * @param mail Nouveau mail de l'utilisateur
     */
    public void setEmail_user(String mail)
    {
        this.email_user = mail;
    }

    /**
     * Modifie le mdp de l'utilisateur
     * @param mdp Nouveau mdp user
     */
    public void setMdp_user(String mdp)
    {
        this.mdp_user = mdp;
    }

    /**
     * Modifie l'id du user
     *
     * @param id Id du user
     */
    public void setId(long id)
    {
        this.id = id;
    }
}
