package org.pasteur_yaounde.e_service;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.pasteur_yaounde.e_service.adapter.ExamsListAdapter;
import org.pasteur_yaounde.e_service.data.GlobalVariable;
import org.pasteur_yaounde.e_service.fragment.AskCotationFragment;
import org.pasteur_yaounde.e_service.fragment.CartFragment;
import org.pasteur_yaounde.e_service.fragment.ExamsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CoordinatorLayout parentView;
    private NavigationView navigationView;

    private Toolbar toolbar, searchToolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    // private FloatingActionButton takePhoto;
    private RecyclerView recyclerView;

    private ExamsListAdapter adapter;
    private boolean isSearch = false;
    private GlobalVariable global;

    private ExamsFragment f_exams;
    private CartFragment f_cart;
    private AskCotationFragment f_supervisor;


    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    /***
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        global = (GlobalVariable)getApplication();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initComponents();
        prepareActionBar(toolbar);
        displayView(R.id.nav_home, "Accueil");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initComponents() {
        parentView = (CoordinatorLayout) findViewById(R.id.main_content);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchToolbar = (Toolbar) findViewById(R.id.toolbar_search);
        // recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        f_exams = new ExamsFragment();
        f_cart = new CartFragment();
        f_supervisor = new AskCotationFragment();
    }

    private void prepareActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        if (!isSearch) {
            settingDrawer();
        }
    }

    private void settingDrawer() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        // Set the drawer toggle as the DrawerListener
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        updateCartCounter(navigationView, R.id.nav_cart, global.getCartItem());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(isSearch ? R.menu.menu_search_toolbar : R.menu.menu_main, menu);

        if (isSearch) {
            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setIconified(false);
            search.setQueryHint("Search exams...");

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if ((ExamsFragment) getSupportFragmentManager().findFragmentByTag("Exams") != null) {
                        f_exams.adapter.getFilter().filter(s);
                    } else if ((CartFragment) getSupportFragmentManager().findFragmentByTag("Cart") != null) {
                        f_cart.adapter.getFilter().filter(s);
                    }

                    return true;
                }
            });
            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    closeSearch();
                    return true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search: {
                isSearch = true;
                searchToolbar.setVisibility(View.VISIBLE);
                prepareActionBar(searchToolbar);
                supportInvalidateOptionsMenu();
                return true;
            }
            case R.id.action_cart: {
                Snackbar.make(parentView, "Cart Clicked", Snackbar.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_filter_category: {
                Snackbar.make(parentView, "Filter by Category Clicked", Snackbar.LENGTH_SHORT).show();
            }
            case android.R.id.home:
                closeSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        updateCartCounter(navigationView, R.id.nav_cart, global.getCartItem());
        super.onResume();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    private void updateCartCounter(NavigationView navigationView, @IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView().findViewById(R.id.counter);
        view.setText(String.valueOf(count));
    }

    /**
     *
     * @param id
     * @param title
     */
    private void displayView(int id, String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        Fragment fragment = null;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.nav_home:
                // fragment = new ExamsFragment();
                fragmentTransaction.replace(R.id.frame_content, f_exams, "Exams");
                break;
            case R.id.nav_cart:
                // fragment = new CartFragment();
                fragmentTransaction.replace(R.id.frame_content, f_cart, "Cart");
                break;
            case R.id.nav_supervisor:

                fragmentTransaction.replace(R.id.frame_content, f_supervisor, "Supervisor");
                break;
            default:

                break;
        }

        // fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    private void closeSearch() {
        if (isSearch) {
            isSearch = false;
            prepareActionBar(toolbar);
            searchToolbar.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
            } else    afficheTost(context, "Aucun intent récupéré...");
        }*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayView(id, "");
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