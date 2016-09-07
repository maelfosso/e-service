package org.pasteur_yaounde.e_service.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pasteur_yaounde.e_service.OMPaymentActivity;
import org.pasteur_yaounde.e_service.R;
import org.pasteur_yaounde.e_service.adapter.CartListAdapter;
import org.pasteur_yaounde.e_service.data.GlobalVariable;
import org.pasteur_yaounde.e_service.model.Exam;
import org.pasteur_yaounde.e_service.widget.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private GlobalVariable global;
    public CartListAdapter adapter;
    private TextView examTotal, priceTotal;
    private LinearLayout lyt_notfound;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        this.getActivity().setTitle("Cart");
        global = (GlobalVariable) getActivity().getApplication();

        examTotal = (TextView) view.findViewById(R.id.exam_total);
        priceTotal = (TextView) view.findViewById(R.id.price_total);
        lyt_notfound = (LinearLayout) view.findViewById(R.id.lyt_notfound);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        //set data and list adapter
        adapter = new CartListAdapter(getActivity(), global.getCart());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Exam obj) {
                // dialogCartAction(obj, position);
            }
        });

        ((Button) view.findViewById(R.id.checkout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getItemCount() != 0) {

                    dialogPaymentMode();
                }
            }
        });

        setTotalPrice();
        if (adapter.getItemCount() == 0) {
            lyt_notfound.setVisibility(View.VISIBLE);
        } else {
            lyt_notfound.setVisibility(View.GONE);
        }

        return view;
    }

    private void setTotalPrice() {
        examTotal.setText(" - " + global.getCartItemTotal() + " Exams");
        priceTotal.setText(global.getCartPriceTotal() + " FCFA");
    }

    private void dialogPaymentMode() {
        final Dialog dialog = new Dialog(this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_payment_means);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((ImageView)dialog.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setHasFixedSize(true);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_large);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));

        ImageAdapter adapter = new ImageAdapter(getContext());
        recyclerView.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment_mode, null);
            ImageAdapter.ViewHolder holder = new ImageAdapter.ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
            Picasso.with(mContext)
                    .load(getItem(position))
                    .resize(150, 75)
                    .into(holder.image);

            holder.image.setTag(getItem(position));
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer id = (Integer) view.getTag();

                    switch (id) {
                        case R.drawable.orange_money:
                            Intent intent = new Intent(mContext, OMPaymentActivity.class);
                            mContext.startActivity(intent);

                            break;
                        case R.drawable.visa:

                            break;
                        case R.drawable.pp_logo_h_200x51:

                            break;
                        case R.drawable.mtn_mobile_money:

                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mThumbIds.length;
        }

        public Integer getItem(int position) {
            return mThumbIds[position];
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView image;

            public ViewHolder(View view) {
                super(view);

                image = (ImageView) view.findViewById(R.id.image);
            }

        }

        private Integer[] mThumbIds = {
                R.drawable.orange_money, R.drawable.visa,
                R.drawable.pp_logo_h_200x51, R.drawable.mtn_mobile_money
        };
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;
        private int headerNum;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int headerNum) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
            this.headerNum = headerNum;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view) - headerNum; // item position

            if (position >= 0) {
                int column = position % spanCount; // item column

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing; // item top
                    }
                }
            } else {
                outRect.left = 0;
                outRect.right = 0;
                outRect.top = 0;
                outRect.bottom = 0;
            }
        }
    }

}
