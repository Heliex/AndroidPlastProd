package model;

/**
 * Created by christophe on 01/04/2015.
 * Classe qui représente la vue associé à une menu dans le drawer
 * @author Christophe Gerard
 * @version 1.0
 */
public class NavDrawerItem {
    private String title;
    private int icon;
    private String count="0";
    private boolean isCounterVisible=false;

    /**
     * Constructeur par defaut
     */
    public NavDrawerItem(){}

    /**
     * Constructeur à 2 paramètres
     * @param title Titre
     * @param icon Icone
     */
    public NavDrawerItem(String title, int icon)
    {
        this.title=title;
        this.icon=icon;
    }

    /**
     * Constructeur à 4 paramètres
     * @param title Titre
     * @param icon Icone
     * @param isCounterVisible Estvisible
     * @param count Nombre de fois
     */
    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count)
    {
        this.title=title;
        this.icon=icon;
        this.isCounterVisible=isCounterVisible;
        this.count=count;
    }

    /**
     * @return Le titre
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * @return L'icone
     */
    public int getIcon()
    {
        return this.icon;
    }

    /**
     * @return Le nombre de fois
     */
    public String getCount()
    {
        return this.count;
    }

    /**
     * @return estVisible
     */
    public boolean getCounterVisibility()
    {
        return this.isCounterVisible;
    }

    /**
     * Modifie le titre
     * @param title Nouveau titre
     */
    public void setTitle(String title)
    {
        this.title=title;
    }

    /**
     * Modifie l'icone
     * @param icon Nouvelle icone
     */
    public void setIcon(int icon)
    {
        this.icon=icon;
    }

    /**
     * Nouveau count
     * @param count Count
     */
    public void setCount(String count)
    {
        this.count=count;
    }

    /**
     * Modifie la visibilité
     * @param isCounterVisible Nouvelle visibilitée
     */
    public void setCounterVisibility(boolean isCounterVisible)
    {
        this.isCounterVisible=isCounterVisible;
    }
}

