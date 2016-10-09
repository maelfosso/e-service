package org.pasteur_yaounde.e_service.capture;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.os.Build;
import android.os.Handler;
import android.app.Activity;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.animation.Animator;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.annotation.TargetApi;
import android.animation.AnimatorSet;
import android.view.ViewTreeObserver;
import android.animation.ObjectAnimator;
import android.view.animation.Interpolator;
import android.animation.AnimatorListenerAdapter;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.CameraView;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
// import hugo.weaving.DebugLog;

import org.pasteur_yaounde.e_service.R;
import org.pasteur_yaounde.e_service.SignUpActivity;
import org.pasteur_yaounde.e_service.abstrait.AlbumStorageDirFactory;
import org.pasteur_yaounde.e_service.abstrait.BaseAlbumDirFactory;
import org.pasteur_yaounde.e_service.abstrait.FroyoAlbumDirFactory;
import org.pasteur_yaounde.e_service.utils.Utils;
import org.pasteur_yaounde.e_service.MainActivity;
import org.pasteur_yaounde.e_service.capture.view.RevealBackgroundView;
import org.pasteur_yaounde.e_service.utils.floatButtom.SubActionButton;
import org.pasteur_yaounde.e_service.utils.floatButtom.FloatingActionMenu;

/**
 * Created by Franck on 10/08/2016.
 */
public class TakePhotoMainActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener, CameraHostProvider {
    private Context eContext = null;

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final int STATE_TAKE_PHOTO = 0;
    private static final int STATE_SETUP_PHOTO = 1;

    @Bind(R.id.vRevealBackground) RevealBackgroundView vRevealBackground;

    @Bind(R.id.annulerCapture) ImageButton btnAnnuler;
    @Bind(R.id.finish_action) ImageButton btnFinishAction;

    @Bind(R.id.vPhotoRoot) View vTakePhotoRoot;
    @Bind(R.id.ivTakenPhoto) ImageView ivTakenPhoto;
    @Bind(R.id.cameraView) CameraView cameraView;
    @Bind(R.id.vShutter) View vShutter;

    @Bind(R.id.flipperEltSpace) ViewFlipper flipperActionInterface;
    @Bind(R.id.voir_galeri) ImageButton btnGallery;
    @Bind(R.id.btnTakePhoto) Button btnCapturePhoto;
    @Bind(R.id.prendre_video) ImageButton btnCaptureVideo;

    @Bind(R.id.connect_customer) ImageButton btnConnectCoustomer;

    /*
    @Bind(R.id.envoyer_par_whatsapp) ImageButton btnEnvoyerWhatsApp;*/

    private boolean pendingIntro;
    private int currentState;

    private File photoPath;

    private static final String JPEG_FILE_PREFIX = "CPC_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    public static void startCameraFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, TakePhotoMainActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        eContext = this;

        updateStatusBarColor();
        setupRevealBackground(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)   mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        else    mAlbumStorageDirFactory = new BaseAlbumDirFactory();

        btnAnnuler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.annulerCapture)    onBackPressed();
            }
        });
        btnFinishAction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.finish_action)    finish();
            }
        });

        btnConnectCoustomer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.connect_customer){
                    startActivity(new Intent(eContext, SignUpActivity.class));
                    /*onSendByEmail(new Uri[]{Uri.fromFile(photoPath)});
                    backToCapture();*/
                }
            }
        });
        /*
        btnEnvoyerWhatsApp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.envoyer_par_whatsapp){
                    MainActivity.afficheTost(eContext, "Envoyer par WhatsApp?");
                    openWhatsappContact("+23795165033");
                    backToCapture();
                }
            }
        });*/
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateStatusBarColor() {
        if (Utils.isAndroid5()) {
            getWindow().setStatusBarColor(0xff111111);
        }
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setFillPaintColor(0xFF16181a);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else    vRevealBackground.setToFinishedFrame();
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            vTakePhotoRoot.setVisibility(View.VISIBLE);
            if (pendingIntro) {
                // startIntroAnimation();
            }
        } else    vTakePhotoRoot.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.onPause();
    }

    @OnClick(R.id.btnTakePhoto)
    public void onTakePhotoClick() {
        btnCapturePhoto.setEnabled(false);
        cameraView.takePicture(true, true);
        animateShutter();
    }

    private void animateShutter() {
        vShutter.setVisibility(View.VISIBLE);
        vShutter.setAlpha(0.f);

        ObjectAnimator alphaInAnim = ObjectAnimator.ofFloat(vShutter, "alpha", 0f, 0.8f);
        alphaInAnim.setDuration(100);
        alphaInAnim.setStartDelay(100);
        alphaInAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator alphaOutAnim = ObjectAnimator.ofFloat(vShutter, "alpha", 0.8f, 0f);
        alphaOutAnim.setDuration(200);
        alphaOutAnim.setInterpolator(DECELERATE_INTERPOLATOR);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaInAnim, alphaOutAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                vShutter.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    /**
     * Méthode permettant d'envoyer un message WhatsApp à un numéro précis
     * @param number
     */
    private void openWhatsappContact(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
        sendIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(sendIntent, ""));
    }

    /**
     *
     * @param attachments
     */
    private void onSendByEmail(Uri[] attachments) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mailIntent.setType("text/plain");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"malko1278@yahoo.fr"});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Envoi de photo d'ordonnance");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "Ici le contenu du message");
        if(attachments != null) {
            ArrayList uris = new ArrayList();
            for(Uri uri: attachments)    uris.add(uri);
            mailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        }
        startActivity(Intent.createChooser(mailIntent, "Envoyer un message"));
    }

    private void backToCapture(){
        btnCapturePhoto.setEnabled(true);
        flipperActionInterface.showNext();
        updateState(STATE_TAKE_PHOTO);
    }

    @Override
    public CameraHost getCameraHost() {
        return new MyCameraHost(this);
    }

    class MyCameraHost extends SimpleCameraHost {

        private Camera.Size previewSize;

        public MyCameraHost(Context ctxt) {
            super(ctxt);
        }

        @Override
        public boolean useFullBleedPreview() {
            return true;
        }

        @Override
        public Camera.Size getPictureSize(PictureTransaction xact, Camera.Parameters parameters) {
            return previewSize;
        }

        @Override
        public Camera.Parameters adjustPreviewParameters(Camera.Parameters parameters) {
            Camera.Parameters parameters1 = super.adjustPreviewParameters(parameters);
            previewSize = parameters1.getPreviewSize();
            return parameters1;
        }

        @Override
        public void saveImage(PictureTransaction xact, final Bitmap bitmap) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    showTakenPicture(bitmap);
                }
            });
        }

        @Override
        public void saveImage(PictureTransaction xact, byte[] image) {
            super.saveImage(xact, image);
            photoPath = getPhotoPath();

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Si le chemin a bien été créé...
            if (photoFile != null){
                photoPath.renameTo(photoFile);
                photoPath = photoFile;
            }
        }
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

    private File getAlbumDir() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());
            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        // afficheTost(contextEService, "Echec de la création du dossier");
                        return null;
                    }
                }
            }
        }
        return storageDir;
    }

    private void showTakenPicture(Bitmap bitmap) {
        flipperActionInterface.showNext();
        ivTakenPhoto.setImageBitmap(bitmap);
        updateState(STATE_SETUP_PHOTO);
    }

    @Override
    public void onBackPressed() {
        if (currentState == STATE_SETUP_PHOTO) {
            btnCapturePhoto.setEnabled(true);
            flipperActionInterface.showNext();
            updateState(STATE_TAKE_PHOTO);
        } else    super.onBackPressed();
    }

    private void updateState(int state) {
        currentState = state;
        if (currentState == STATE_TAKE_PHOTO) {
            flipperActionInterface.setInAnimation(this, R.anim.slide_in_from_right);
            flipperActionInterface.setOutAnimation(this, R.anim.slide_out_to_left);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivTakenPhoto.setVisibility(View.GONE);
                }
            }, 400);
        } else if (currentState == STATE_SETUP_PHOTO) {
            flipperActionInterface.setInAnimation(this, R.anim.slide_in_from_left);
            flipperActionInterface.setOutAnimation(this, R.anim.slide_out_to_right);
            ivTakenPhoto.setVisibility(View.VISIBLE);
        }
    }
}