// Generated code from Butter Knife. Do not modify!
package org.pasteur_yaounde.e_service.capture;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BaseActivity$$ViewBinder<T extends org.pasteur_yaounde.e_service.capture.BaseActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131624134, null);
    target.toolbar = finder.castView(view, 2131624134, "field 'toolbar'");
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
  }
}
