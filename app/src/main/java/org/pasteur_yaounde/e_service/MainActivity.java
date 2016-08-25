package org.pasteur_yaounde.e_service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.pasteur_yaounde.e_service.abstrait.AlbumStorageDirFactory;
import org.pasteur_yaounde.e_service.abstrait.BaseAlbumDirFactory;
import org.pasteur_yaounde.e_service.abstrait.FroyoAlbumDirFactory;
import org.pasteur_yaounde.e_service.capture.TakePhotoMainActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Variable qui contiendra notre context
    private Context contextEService = null;
    private NavigationView navigationViewEService = null;

    private Toolbar toolbarEService = null;
    private DrawerLayout drawerEService = null;
    private ActionBarDrawerToggle toggleEService = null;
    private FloatingActionButton boutonAddPhotoEService = null;
    private FloatingActionButton boutonAddPhotoEService_1 = null;
    private RecyclerView recyclerViewEService = null;

    /*************************************************************************************/
    private File mFichier;
    private ImageView image;
    private static final int PHOTO_RESULT = 0;

    private ImageView mImageView;
    // static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private Bitmap imageBitmap;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
     /*************************************************************************************/

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

    /***
     *
     * @param savedInstanceState
     */
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)    mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        else     mAlbumStorageDirFactory = new BaseAlbumDirFactory();

        boutonAddPhotoEService = (FloatingActionButton) findViewById(R.id.charge_new_photo);
        boutonAddPhotoEService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allerPrendrePhoto();
            }
        });

        boutonAddPhotoEService_1 = (FloatingActionButton) findViewById(R.id.charge_new_photo_1);
        boutonAddPhotoEService_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void allerPrendrePhoto() {
        int[] startingLocation = new int[2];
        boutonAddPhotoEService.getLocationOnScreen(startingLocation);
        startingLocation[0] += boutonAddPhotoEService.getWidth() / 2;
        // afficheTost(contextEService, "La position 11::== " + startingLocation[0] + "\n La position 22:: " + startingLocation[1]);
        TakePhotoMainActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Création du nom de l'image qui sera prise
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // prefixe
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        // Chemin de stockage ou dossier
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    /**
     * Photo album for this application
     */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    /**
     *
     * @return
     */
    private File getAlbumDir() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());
            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        afficheTost(contextEService, "Echec de la création du dossier");
                        return null;
                    }
                }
            }
        } else    afficheTost(contextEService, "L'espace de stockage externe non monté.");
        return storageDir;
    }

    /**
     *
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Créer le fichier où ira la photo
            File photoFile = null;
            try {
                photoFile = createImageFile();
                // Enregistrer un fichier : chemin d'accès pour une utilisation avec l'intents ACTION_VIEW
                mCurrentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
                mCurrentPhotoPath = null;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                setResult(RESULT_OK, takePictureIntent);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                // mImageView.setImageBitmap(imageBitmap);
                afficheTost(contextEService, "L'image :: " + imageBitmap + " :: bien reçu pour son affichage");
            } else    afficheTost(contextEService, "Aucun intent récupéré...");
        }
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
            /*startActivity(new Intent(contextEService, PrendrePhotoMainActivity.class));
            int[] startingLocation = new int[]{322, 561};
            PrendrePhotoMainActivity.startCameraFromLocation(startingLocation, this);
            overridePendingTransition(0, 0);*/
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
