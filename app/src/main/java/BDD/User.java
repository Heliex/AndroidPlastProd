package BDD;

/**
 * Created by Christophe on 22/06/2015. For PlastProd Project
 */
public class User {

    private long id;
    private String email_user;
    private String mdp_user;

    public User(String email_user,String mdp_user)
    {
        this.email_user = email_user;
        this.mdp_user = mdp_user;
    }

    public User()
    {
        this.id = 0;
        this.email_user = null;
        this.mdp_user = null;
    }

    public String getEmail_user()
    {
        return this.email_user;
    }

    public String getMdp_user()
    {
        return this.mdp_user;
    }

    public long getId()
    {
        return this.id;
    }

    public void setEmail_user(String mail)
    {
        this.email_user = mail;
    }

    public void setMdp_user(String mdp)
    {
        this.mdp_user = mdp;
    }

    public void setId(long id)
    {
        this.id = id;
    }
}
