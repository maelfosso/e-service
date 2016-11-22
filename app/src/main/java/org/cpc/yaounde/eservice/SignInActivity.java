package org.cpc.yaounde.eservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.cpc.yaounde.eservice.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.i_forgot_password) TextView forgotPassword;
    @Bind(R.id.sign_in) Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        signIn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sign_in:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.i_forgot_password:
                MainActivity.afficheTost(this, "Bientot disponible...");
                break;
        }
    }
}
