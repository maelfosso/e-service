// Generated code from Butter Knife. Do not modify!
package org.pasteur_yaounde.e_service.capture;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PublishMainActivity$$ViewBinder<T extends org.pasteur_yaounde.e_service.capture.PublishMainActivity> extends org.pasteur_yaounde.e_service.capture.BaseActivity$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131624138, "field 'tbFollowers' and method 'onFollowersCheckedChange'");
    target.tbFollowers = finder.castView(view, 2131624138, "field 'tbFollowers'");
    ((android.widget.CompoundButton) view).setOnCheckedChangeListener(
      new android.widget.CompoundButton.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(
          android.widget.CompoundButton p0,
          boolean p1
        ) {
          target.onFollowersCheckedChange(p1);
        }
      });
    view = finder.findRequiredView(source, 2131624139, "field 'tbDirect' and method 'onDirectCheckedChange'");
    target.tbDirect = finder.castView(view, 2131624139, "field 'tbDirect'");
    ((android.widget.CompoundButton) view).setOnCheckedChangeListener(
      new android.widget.CompoundButton.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(
          android.widget.CompoundButton p0,
          boolean p1
        ) {
          target.onDirectCheckedChange(p1);
        }
      });
    view = finder.findRequiredView(source, 2131624141, "field 'ivPhoto'");
    target.ivPhoto = finder.castView(view, 2131624141, "field 'ivPhoto'");
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.tbFollowers = null;
    target.tbDirect = null;
    target.ivPhoto = null;
  }
}
