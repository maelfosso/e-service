package org.cpc.yaounde.eservice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.cpc.yaounde.eservice.adapter.CartListAdapter;
import org.cpc.yaounde.eservice.data.GlobalVariable;
import org.cpc.yaounde.eservice.model.Exam;
import org.cpc.yaounde.eservice.widget.DividerItemDecoration;
import org.cpc.yaounde.eservice.R;

public class ExamDetailActivity extends AppCompatActivity {
    public static final String EXTRA_OBJCT = "org.pasteur_yaounde.e_service.EXAM";

    Toolbar toolbar;
    FloatingActionButton fab;

    TextView description;
    TextView price;
    TextView duration;

    GlobalVariable global;
    Exam exam;
    boolean inCart = false;

    public static void navigate(Activity activity, View transitionImage, Exam obj) {
        Intent intent = new Intent(activity, ExamDetailActivity.class);
        intent.putExtra(EXTRA_OBJCT, obj);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_OBJCT);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);

        global = (GlobalVariable) getApplication();
        exam = (Exam) getIntent().getSerializableExtra(EXTRA_OBJCT);

        initComponents();
        initToolbar();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.price);
        duration = (TextView) findViewById(R.id.duration);
        Log.d("INIT", exam.toString());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        toolbar.setTitle(exam.getName());
        description.setText(exam.getDescription());
        price.setText(String.valueOf(exam.getPrice() + " Fcfa"));
        duration.setText(String.valueOf(exam.getDuration() + " j"));


        if (global.isCartExist(exam)) {
            fab.setImageResource(R.drawable.ic_remove_shopping_cart_white_18dp);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!global.isCartExist(exam)) { //if(!exam.isInCart()){ // !inCart
                    global.addCart(exam);

                    fab.setImageResource(R.drawable.ic_remove_shopping_cart_white_18dp);
                    Snackbar.make(view, "Added to Cart", Snackbar.LENGTH_SHORT).show();
                }else{
                    global.removeCart(exam);

                    fab.setImageResource(R.drawable.ic_add_shopping_cart_white_18dp);
                    Snackbar.make(view, "Removed from Cart", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exam_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_cart:
                dialogCartDetails();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void dialogCartDetails() {

        final Dialog dialog = new Dialog(ExamDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_cart_detail);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LinearLayout lyt_notfound = (LinearLayout) dialog.findViewById(R.id.lyt_notfound);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //set data and list adapter
        CartListAdapter mAdapter = new CartListAdapter(this, global.getCart());
        recyclerView.setAdapter(mAdapter);
        ((TextView)dialog.findViewById(R.id.exam_total)).setText(" - " + global.getCartItemTotal() + " Exams");
        ((TextView)dialog.findViewById(R.id.price_total)).setText(global.getCartPriceTotal() + " Fcfa");
        ((ImageView)dialog.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if(mAdapter.getItemCount()==0){
            lyt_notfound.setVisibility(View.VISIBLE);
        }else{
            lyt_notfound.setVisibility(View.GONE);
        }

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
