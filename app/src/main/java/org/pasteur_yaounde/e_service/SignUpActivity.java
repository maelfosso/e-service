package org.pasteur_yaounde.e_service;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private Context eContext = null;

    @Bind(R.id.phone_number) EditText takePhoneNumber;
    @Bind(R.id.first_name) EditText takenFirstName;
    @Bind(R.id.last_name) EditText takeLastName;
    @Bind(R.id.password) EditText takePassword;
    @Bind(R.id.sign_up) Button btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        eContext = this;

        btnSingUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.sign_up){
                    if(testFormulaire() == true)
                        MainActivity.afficheTost(eContext, "Les données sont en attente de transmission sur serveur");
                    else    MainActivity.afficheTost(eContext, "Veuiller remplir tous les champs encore vide");
                }
            }
        });
    }

    public boolean testFormulaire(){
        if(takePhoneNumber.getText().toString().equals(""))    return false;
        else{
            if(takenFirstName.getText().toString().equals(""))    return false;
            else{
                if(takeLastName.getText().toString().equals(""))    return false;
                else   if(takePassword.getText().toString().equals(""))    return false;
            }
        }
        return true;
    }
}