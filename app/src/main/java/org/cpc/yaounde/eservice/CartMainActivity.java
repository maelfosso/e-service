package org.cpc.yaounde.eservice;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.cpc.yaounde.eservice.R;

/**
 * Created by Franck on 09/08/2016.
 */
public class CartMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Variable qui contiendra notre context
    private Context context = null;
    private NavigationView navigationView = null;

    private Toolbar toolbar = null;
    private DrawerLayout drawer = null;
    private ActionBarDrawerToggle toggle = null;
    private FloatingActionButton boutonAddPhoto = null;
    private RecyclerView recyclerView = null;
    private TextView totalView = null;
    private TextView textBoutonView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_interface_main);

        // Initialisation de la variable qui contient notre context
        context = this;
        // TODO Use fields...
        // Typeface sRobotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");

        navigationView = (NavigationView) findViewById(R.id.nav_cart_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        // Définition du titre de l'interface
        toolbar.setTitle("Cart");
        // Affichage des options dans le Toolbar
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cart_main);
        toggle = new ActionBarDrawerToggle(
                this, 						                             /* host Activity */
                drawer,  				                         /* DrawerLayout object */
                toolbar,                                         /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,                         /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close)                        /* "close drawer" description for accessibility */
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                // if((gdsPosition >= 0) && (gdsPosition <= 4))
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
        drawer.setDrawerListener(toggle);
        // Action permettant d'afficher l'icone de navigation
        toggle.syncState();

        recyclerView = (RecyclerView) findViewById(R.id.liste_cart_examens);

        totalView = (TextView) findViewById(R.id.montant_total_examen);
        // Action permettant de prendre le coût total des examens

        textBoutonView = (TextView) findViewById(R.id.btn_valid_commande);
        // Action permettant de prendre le coût total des examens
        textBoutonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cart_main);
        if (drawer.isDrawerOpen(GravityCompat.START))    drawer.closeDrawer(GravityCompat.START);
        else    super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        /*if (id == R.id.consulter_panier)
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
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.affichage_panier_cart) {
            // Handle the camera action
        } else if (id == R.id.affichage_promotion_examen) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cart_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}