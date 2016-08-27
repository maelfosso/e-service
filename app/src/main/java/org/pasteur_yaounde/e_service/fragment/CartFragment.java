package org.pasteur_yaounde.e_service.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private CartListAdapter adapter;
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
                    checkoutConfirmation();
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

    private void checkoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Checkout Confirmation");
        builder.setMessage("Are you sure continue to checkout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                global.clearCart();
                adapter.notifyDataSetChanged();
                Snackbar.make(view, "Checkout success", Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
