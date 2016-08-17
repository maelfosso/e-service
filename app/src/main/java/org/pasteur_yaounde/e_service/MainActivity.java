package org.pasteur_yaounde.e_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.pasteur_yaounde.e_service.capture.TakePhotoMainActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Variable qui contiendra notre context
    private Context contextEService = null;
    private NavigationView navigationViewEService = null;

    private Toolbar toolbarEService = null;
    private DrawerLayout drawerEService = null;
    private ActionBarDrawerToggle toggleEService = null;
    private FloatingActionButton boutonAddPhotoEService = null;
    private RecyclerView recyclerViewEService = null;

    /*private boolean pendingIntroAnimation;
    private static final int ANIM_DURATION_FAB = 400;
    private static final int ANIM_DURATION_TOOLBAR = 300;

    private File mFichierPhoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private ImageView imageViewPhoto;

    private static final int SELECT_PICTURE = 1;*/

    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    // private static final int PHOTO_RESULT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de la variable qui contient notre context
        contextEService = this;

        navigationViewEService = (NavigationView) findViewById(R.id.nav_view);
        navigationViewEService.setNavigationItemSelectedListener(this);

        toolbarEService = (Toolbar) findViewById(R.id.toolbar);
        // Définition du titre de l'interface
        toolbarEService.setTitle("Accueil");
        // Affichage des options dans le Toolbar
        setSupportActionBar(toolbarEService);

        drawerEService = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        toggleEService = new ActionBarDrawerToggle(
                this, 						                             /* host Activity */
                drawerEService,  				                         /* DrawerLayout object */
                toolbarEService,                                         /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,                         /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close)                        /* "close drawer" description for accessibility */
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                /*if(gdsFilsActuel >= 0)
                    // Retourne le titre en fonction du Spinner majoritaire (celui qui inssoufle la recherche)
                    getActionBar().setTitle(gdsListTitreArray[gdsFilsActuel]);
                invalidateOptionsMenu();
                // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);*/
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // if((gdsPosition < 0) || (gdsPosition > 4))	  gdsPosition = 0;
                // Retourne le titre en fonction du Spinner majoritaire (celui qui inssoufle la recherche)
                /*getActionBar().setTitle(gdsListTitreArray[2]);
                // getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu();
                // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);*/
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        drawerEService.setDrawerListener(toggleEService);
        // Action permettant d'afficher l'icone de navigation
        toggleEService.syncState();

        /*if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }*/

        boutonAddPhotoEService = (FloatingActionButton) findViewById(R.id.charge_new_photo);
        boutonAddPhotoEService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Action permettant d'aller prendre une photo", Snackbar.LENGTH_LONG).
                                                                        setAction("Action", null).show();
                //
                allerPrendrePhoto();
            }
        });
    }

    public void allerPrendrePhoto() {
        int[] startingLocation = new int[2];
        boutonAddPhotoEService.getLocationOnScreen(startingLocation);
        startingLocation[0] += boutonAddPhotoEService.getWidth() / 2;
        TakePhotoMainActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
        afficheTost(contextEService, "Disposition des éléments. Vais-je faire une photo?");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        if (drawer.isDrawerOpen(GravityCompat.START))    drawer.closeDrawer(GravityCompat.START);
        else    super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // no inspection SimplifiableIfStatement
        if (id == R.id.consulter_panier)
            // Action permettant de consulter l'état d'un panier d'examen
            return true;
        else{
            if (id == R.id.filtrer_examens) {
                // Action permettant de filtrer des examens par catégories
                return true;
            } else {
                if (id == R.id.rechercher_examen) {
                    // Action permettant de rechercher un examen dans une liste d'examens
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.affichage_panier_cart)    startActivity(new Intent(contextEService, CartMainActivity.class));
        else if (id == R.id.affichage_promotion_examen) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     *
     * @param message
     */
    public static void afficheTost(Context context, String message){
        Toast monToast= Toast.makeText(context, message, Toast.LENGTH_LONG);
        monToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        monToast.show();
    }
}