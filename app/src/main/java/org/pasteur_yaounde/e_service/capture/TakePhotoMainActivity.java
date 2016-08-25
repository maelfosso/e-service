package org.pasteur_yaounde.e_service.capture;

import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.OnClick;
// import hugo.weaving.DebugLog;

import org.pasteur_yaounde.e_service.R;
import org.pasteur_yaounde.e_service.utils.Utils;
import org.pasteur_yaounde.e_service.MainActivity;
import org.pasteur_yaounde.e_service.capture.view.RevealBackgroundView;
import org.pasteur_yaounde.e_service.utils.floatButtom.SubActionButton;
import org.pasteur_yaounde.e_service.utils.floatButtom.FloatingActionMenu;

/**
 * Created by Franck on 10/08/2016.
 */
public class TakePhotoMainActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener, CameraHostProvider {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final int STATE_TAKE_PHOTO = 0;
    private static final int STATE_SETUP_PHOTO = 1;

    @Bind(R.id.vRevealBackground) RevealBackgroundView vRevealBackground;

    @Bind(R.id.annulerCapture) ImageButton btnAnnuler;
    @Bind(R.id.grid_on) ImageButton btnGridOn;
    @Bind(R.id.camera_rear) ImageButton btnCamera;

    @Bind(R.id.vPhotoRoot) View vTakePhotoRoot;
    @Bind(R.id.ivTakenPhoto) ImageView ivTakenPhoto;
    @Bind(R.id.cameraView) CameraView cameraView;
    @Bind(R.id.vShutter) View vShutter;

    @Bind(R.id.flipperEltSpace) ViewFlipper flipperActionInterface;
    private int positionAction = 0;
    @Bind(R.id.voir_galeri) ImageButton btnGallery;
    @Bind(R.id.btnTakePhoto) Button btnCapturePhoto;
    @Bind(R.id.prendre_video) ImageButton btnCaptureVideo;

    @Bind(R.id.envoyer_capture) ImageButton btnEnvoyerPhoto;

    private boolean pendingIntro;
    private int currentState;

    private File photoPath;

    private ImageView btnEnvoiMail = null;
    private ImageView btnEnvoiWhatsApp = null;
    private FloatingActionMenu btnFloatMenu = null;
    private FloatingActionMenu.Builder btnMenuBuild = null;
    private SubActionButton.Builder rLSubBuilder = null;

    // @Bind(R.id.vUpperPanel) ViewSwitcher vUpperPanel;
    // @Bind(R.id.vLowerPanel) ViewSwitcher vLowerPanel;
    // @Bind(R.id.rvFilters) RecyclerView rvFilters;
    // @Bind(R.id.btnTakePhoto) Button btnTakePhoto;

    public static void startCameraFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, TakePhotoMainActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
        // MainActivity.afficheTost(startingActivity, "Bonjour à tous...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        // MainActivity.afficheTost(this, "Disposition des éléments. Vais-je faire une photo?");

        updateStatusBarColor();
        setupRevealBackground(savedInstanceState);
        /*setupPhotoFilters();

        vUpperPanel.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                vUpperPanel.getViewTreeObserver().removeOnPreDrawListener(this);
                pendingIntro = true;
                vUpperPanel.setTranslationY(-vUpperPanel.getHeight());
                vLowerPanel.setTranslationY(vLowerPanel.getHeight());
                return true;
            }
        });*/

        btnEnvoiMail = new ImageView(this);
        btnEnvoiWhatsApp = new ImageView(this);
        btnEnvoiMail.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_email_white_24dp));
        btnEnvoiWhatsApp.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_email_white_24dp));
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
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            vTakePhotoRoot.setVisibility(View.VISIBLE);
            if (pendingIntro) {
                // startIntroAnimation();
            }
        } else {
            vTakePhotoRoot.setVisibility(View.INVISIBLE);
        }
    }

    /*private void startIntroAnimation() {
        vUpperPanel.animate().translationY(0).setDuration(400).setInterpolator(DECELERATE_INTERPOLATOR);
        vLowerPanel.animate().translationY(0).setDuration(400).setInterpolator(DECELERATE_INTERPOLATOR).start();
    }

    private void setupPhotoFilters() {
        PhotoFiltersAdapter photoFiltersAdapter = new PhotoFiltersAdapter(this);
        rvFilters.setHasFixedSize(true);
        rvFilters.setAdapter(photoFiltersAdapter);
        rvFilters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }*/

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

    @OnClick(R.id.envoyer_capture)
    public void onAcceptClick() {
        // PublishMainActivity.openWithPhotoUri(this, Uri.fromFile(photoPath));
        // btnCapturePhoto.setEnabled(false);
        // btnEnvoyerPhoto.

        MainActivity.afficheTost(this, "Je veux enrégistrer la photo prise...");
        // Il s'agit ici d'un click prolongé qui a été effectué
        /*
        private FloatingActionMenu btnFloatMenu = null;
        private FloatingActionMenu.Builder btnMenuBuild = null;
        private SubActionButton.Builder rLSubBuilder = null;
         */
        if(btnMenuBuild == null){
            // Si le menu n'a pas encore été constitué
            rLSubBuilder = new SubActionButton.Builder(this);
            // Build the menu with default options: light theme, 90 degrees, 72dp radius. Set 3 default SubActionButtons
            btnMenuBuild = new FloatingActionMenu.Builder(this);
            btnEnvoyerPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_white_24dp));
            btnMenuBuild.addSubActionView(rLSubBuilder.setContentView(btnEnvoiMail).build());
            btnMenuBuild.addSubActionView(rLSubBuilder.setContentView(btnEnvoiWhatsApp).build());
            btnMenuBuild.attachTo(btnEnvoyerPhoto);
            btnFloatMenu = btnMenuBuild.build();
        }else{
            // Si un menu a déjàe été constitué
            btnFloatMenu.close(true);
            // Suppression de tous les éléments du menu précédent
            btnMenuBuild.removeSubActionView();
            rLSubBuilder = null;
            btnMenuBuild = null;
            // Déstruction de la variable du menu flottant
            btnFloatMenu = null;
            btnEnvoyerPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_24dp));
        }
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
        }
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