package BDD;

import java.util.List;

/**
 * Created by christophe on 03/04/2015.
 */
public class Client {

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
    }
