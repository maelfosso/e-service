package org.pasteur_yaounde.e_service.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import java.util.List;

/**
 * Created by maelfosso on 8/4/16.
 */
public class PageAdapter  extends FragmentPagerAdapter {

    List<Fragment> fragments;
    Context context;

    public PageAdapter(Context ctx, List<Fragment> fragments, FragmentManager fm) {
        super(fm);

        this.context = ctx;
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

        /*switch (position) {
            case 0:
                return WizardFragment.newInstance("@drawable/post_run",
                        context.getResources().getString(R.string.post_needs),
                        context.getResources().getString(R.string.post_needs_description));

            case 1:
                return WizardFragment.newInstance("@drawable/post_run",
                        context.getResources().getString(R.string.make_run),
                        context.getResources().getString(R.string.make_run_description));

            case 2:
                return WizardFragment.newInstance("@drawable/post_run",
                        context.getResources().getString(R.string.win_money),
                        context.getResources().getString(R.string.win_money_description));
        }

        return null;*/
    }

    public class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {

            // cupdateWizardIndicators(position);
        }
    }
}