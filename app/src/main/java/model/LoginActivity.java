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

                DataBaseHandler db = new DataBaseHandler(getApplicationContext()); // BDD
                User user = db.getUser(); // Recupere le user stocker dans la bdd locale
                if(email.equals(user.getEmail_user())) // Si email == email dans la bdd
                {
                    if(mdp.equals(user.getMdp_user())) // Si mdp == mdp dans la bdd
                    {
                        startActivity(main); // On lance le mainActivity=> Redirige vers l'accueil
                        Toast.makeText(getApplicationContext(),"Connecté",Toast.LENGTH_SHORT).show();
                    }
                    else // Sinon erreur dans le mdp
                    {
                        Toast.makeText(getApplicationContext(),"Erreur dans la saisie du mot de passe",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(!email.equals(user.getEmail_user()) && !email.isEmpty()) // Sinon si email != email dans BDD et email pas vide
                {
                    Toast.makeText(getApplicationContext(),"Erreur dans la saisie de l'email",Toast.LENGTH_SHORT).show(); // erreur dans le mail
                }
                else // Sinon aucun champs saisie
                {
                    Toast.makeText(getApplicationContext(),"Aucun champ rempli",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}



