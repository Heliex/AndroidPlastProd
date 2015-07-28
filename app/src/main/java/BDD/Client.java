package BDD;

import java.io.Serializable;
import java.util.List;

/**
 * Created by christophe on 03/04/2015.
 */
public class Client implements Serializable{

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

        public Client()
        {
            this.id = 0;
            this.nom = null;
            this.prenom = null;
            this.adresse = null;
            this.email = null;
            this.telephone = null;
            this.date = null;
        }
        public Client(String nom, String prenom, String adresse, String telephone, String email, String date)
        {
            this.id = 0;
            this.nom = nom;
            this.prenom = prenom;
            this.adresse = adresse;
            this.telephone = telephone;
            this.email = email;
            this.date = date;
        }

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
    }
