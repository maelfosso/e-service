package org.pasteur_yaounde.e_service;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.pasteur_yaounde.e_service.adapter.PageAdapter;
import org.pasteur_yaounde.e_service.fragment.om.OMConfirmationFragment;
import org.pasteur_yaounde.e_service.fragment.om.OMPaymentFragment;
import org.pasteur_yaounde.e_service.fragment.om.OMTraitmentFragment;

import java.util.ArrayList;
import java.util.List;

public class OMPaymentActivity extends AppCompatActivity {

    RelativeLayout mainView;

    Toolbar toolbar;
    ImageView stepNumber;
    TextView stepName;
    ImageView cancel;

    ViewPager pager;
    PageAdapter adapter;
    LinearLayout first, second, third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ompayment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initComponents();
        setupToolbar();
        setupViewPager();
    }

    private void initComponents() {
        mainView = (RelativeLayout) findViewById(R.id.main_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pager = (ViewPager) findViewById(R.id.pager);

        first = (LinearLayout) findViewById(R.id.first);
        second = (LinearLayout) findViewById(R.id.second);
        third = (LinearLayout) findViewById(R.id.third);
    }

    private void setupToolbar() {
        /*stepNumber = (ImageView) toolbar.findViewById(R.id.step_number);
        stepName = (TextView) toolbar.findViewById(R.id.step_name);*/
        cancel = (ImageView) toolbar.findViewById(R.id.cancel_action);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(mainView, "cancel clicked....", Snackbar.LENGTH_LONG);
                finish();
            }
        });
    }

    private void setupViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OMPaymentFragment.newInstance("", ""));
        fragments.add(OMTraitmentFragment.newInstance("", ""));
        fragments.add(OMConfirmationFragment.newInstance("", ""));

        adapter = new PageAdapter(this, fragments, getSupportFragmentManager());
        pager.setAdapter(adapter);

        updateStepIndicators(0);
    }

    private void updateStepIndicators(int position) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        switch (position) {
            case 0:
                first.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                second.setBackgroundColor(getResources().getColor(R.color.grey_hard));
                third.setBackgroundColor(getResources().getColor(R.color.grey_hard));

                break;
            case 1:
                first.setBackgroundColor(getResources().getColor(R.color.grey_hard));
                second.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                third.setBackgroundColor(getResources().getColor(R.color.grey_hard));

                break;
            case 2:
                first.setBackgroundColor(getResources().getColor(R.color.grey_hard));
                second.setBackgroundColor(getResources().getColor(R.color.grey_hard));
                third.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                break;
        }
    }


    private void closeConfirmation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Etes vous sur de vouloir abandonner le paiement via Orange Money ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // global.clearCart();
                // mAdapter.notifyDataSetChanged();
                // Snackbar.make(view, "Checkout success", Snackbar.LENGTH_SHORT).show();

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }



}
