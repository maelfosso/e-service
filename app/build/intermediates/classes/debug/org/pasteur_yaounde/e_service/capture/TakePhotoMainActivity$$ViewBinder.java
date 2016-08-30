// Generated code from Butter Knife. Do not modify!
package org.pasteur_yaounde.e_service.capture;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TakePhotoMainActivity$$ViewBinder<T extends org.pasteur_yaounde.e_service.capture.TakePhotoMainActivity> extends org.pasteur_yaounde.e_service.capture.BaseActivity$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131624144, "field 'vRevealBackground'");
    target.vRevealBackground = finder.castView(view, 2131624144, "field 'vRevealBackground'");
    view = finder.findRequiredView(source, 2131624145, "field 'btnAnnuler'");
    target.btnAnnuler = finder.castView(view, 2131624145, "field 'btnAnnuler'");
    view = finder.findRequiredView(source, 2131624146, "field 'btnGridOn'");
    target.btnGridOn = finder.castView(view, 2131624146, "field 'btnGridOn'");
    view = finder.findRequiredView(source, 2131624147, "field 'btnCamera'");
    target.btnCamera = finder.castView(view, 2131624147, "field 'btnCamera'");
    view = finder.findRequiredView(source, 2131624148, "field 'vTakePhotoRoot'");
    target.vTakePhotoRoot = view;
    view = finder.findRequiredView(source, 2131624150, "field 'ivTakenPhoto'");
    target.ivTakenPhoto = finder.castView(view, 2131624150, "field 'ivTakenPhoto'");
    view = finder.findRequiredView(source, 2131624149, "field 'cameraView'");
    target.cameraView = finder.castView(view, 2131624149, "field 'cameraView'");
    view = finder.findRequiredView(source, 2131624151, "field 'vShutter'");
    target.vShutter = view;
    view = finder.findRequiredView(source, 2131624152, "field 'flipperActionInterface'");
    target.flipperActionInterface = finder.castView(view, 2131624152, "field 'flipperActionInterface'");
    view = finder.findRequiredView(source, 2131624153, "field 'btnGallery'");
    target.btnGallery = finder.castView(view, 2131624153, "field 'btnGallery'");
    view = finder.findRequiredView(source, 2131624154, "field 'btnCapturePhoto' and method 'onTakePhotoClick'");
    target.btnCapturePhoto = finder.castView(view, 2131624154, "field 'btnCapturePhoto'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onTakePhotoClick();
        }
      });
    view = finder.findRequiredView(source, 2131624155, "field 'btnCaptureVideo'");
    target.btnCaptureVideo = finder.castView(view, 2131624155, "field 'btnCaptureVideo'");
    view = finder.findRequiredView(source, 2131624156, "field 'btnEnvoyerMail'");
    target.btnEnvoyerMail = finder.castView(view, 2131624156, "field 'btnEnvoyerMail'");
    view = finder.findRequiredView(source, 2131624157, "field 'btnEnvoyerWhatsApp'");
    target.btnEnvoyerWhatsApp = finder.castView(view, 2131624157, "field 'btnEnvoyerWhatsApp'");
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.vRevealBackground = null;
    target.btnAnnuler = null;
    target.btnGridOn = null;
    target.btnCamera = null;
    target.vTakePhotoRoot = null;
    target.ivTakenPhoto = null;
    target.cameraView = null;
    target.vShutter = null;
    target.flipperActionInterface = null;
    target.btnGallery = null;
    target.btnCapturePhoto = null;
    target.btnCaptureVideo = null;
    target.btnEnvoyerMail = null;
    target.btnEnvoyerWhatsApp = null;
  }
}
