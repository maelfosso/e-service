package org.pasteur_yaounde.e_service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import org.pasteur_yaounde.e_service.capture.TakePhotoMainActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout lyt_connexion;
    LinearLayout lyt_take_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();
    }

    private void initComponents() {
        lyt_connexion = (LinearLayout) findViewById(R.id.lyt_connexion);
        lyt_take_photo = (LinearLayout) findViewById(R.id.lyt_take_photo);

        lyt_connexion.setOnClickListener(this);
        lyt_take_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_connexion:

                break;
            case R.id.lyt_take_photo:
                int[] startingLocation = new int[2];
                view.getLocationOnScreen(startingLocation);
                startingLocation[0] += view.getWidth() / 2;

                TakePhotoMainActivity.startCameraFromLocation(startingLocation, this);
                overridePendingTransition(0, 0);
                break;
            default:

                break;
        }
    }
}
