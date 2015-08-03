package model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import BDD.Client;
import BDD.DataBaseHandler;
import BDD.User;
import barbeasts.plastprod.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        // Sur le clique du bouton, chercher le user dans la bdd et verifier la valeur des champs.
        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent main = new Intent(getApplicationContext(),MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Récuperer la valeur des champs.
                String email = mEmailView.getText().toString();
                String mdp = mPasswordView.getText().toString();
                if(!email.isEmpty()  && !mdp.isEmpty())
                {
                    DataBaseHandler db = new DataBaseHandler(getApplicationContext()); // BDD
                    long idUser = db.UserExists(email,mdp);
                    if(idUser != -1 )
                    {

                        user = db.getUserById(idUser);
                        if(user != null)
                        {
                            if(email.equals(user.getEmail_user())) // Si email == email dans la bdd
                            {
                                if(mdp.equals(user.getMdp_user())) // Si mdp == mdp dans la bdd
                                {
                                    startActivity(main); // On lance le mainActivity=> Redirige vers l'accueil
                                    Toast.makeText(getApplicationContext(),"Connecté",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    else
                    {
                        if(email.length() > 0 && mdp.length() > 0)
                        {
                            if(db.isEmailProblem(email))
                            {
                                Toast.makeText(getApplicationContext(),"Erreur de saisie dans l'email",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Erreur de saisie dans le mdp",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                else if(email.isEmpty() && mdp.length() > 0)
                {
                    Toast.makeText(getApplicationContext(),"Aucun email saisi",Toast.LENGTH_SHORT).show();
                }
                else if(email.length() > 0 && mdp.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Aucun mdp saisi",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Aucun champ rempli",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static User getUser()
    {
        return user;
    }
}



