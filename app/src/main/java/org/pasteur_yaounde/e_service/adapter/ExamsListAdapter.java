package org.pasteur_yaounde.e_service.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pasteur_yaounde.e_service.R;
import org.pasteur_yaounde.e_service.data.GlobalVariable;
import org.pasteur_yaounde.e_service.model.Exam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maelfosso on 8/26/16.
 */
public class ExamsListAdapter extends RecyclerView.Adapter<ExamsListAdapter.ViewHolder> implements Filterable {

    public static final String TAG = ExamsListAdapter.class.getSimpleName();

    private List<Exam> original_items = new ArrayList<>();
    private List<Exam> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();

    private Context context;
    private OnExamClickListener onExamClickListener;

    private GlobalVariable global;

    public interface OnExamClickListener {
        void onExamClick(View view, Exam exam, int position);
        void onHandleExamClick(View view, Exam exam, int position);
    }

    public ExamsListAdapter(Context context, List<Exam> exams, GlobalVariable application) {
        this.context = context;

        original_items = exams;
        filtered_items = exams;

        global = application;
    }

    public void setOnExamClickListener(final OnExamClickListener onExamClickListener) {
        this.onExamClickListener = onExamClickListener;
    }

    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public ExamsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_exams, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Exam e = filtered_items.get(position);
        holder.name.setText(e.getName());
        holder.description.setText(e.getDescription());
        holder.price.setText(String.valueOf(e.getPrice() + " Fcfa"));
        holder.duration.setText(String.valueOf(e.getDuration() + " j"));

        if (global.isCartExist(e)) {
            holder.handle.setImageResource(R.drawable.ic_remove_shopping_cart_white_18dp);
        } else {
            holder.handle.setImageResource(R.drawable.ic_add_shopping_cart_white_18dp);
        }

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onExamClickListener != null) {
                    onExamClickListener.onExamClick(view, e, position);
                }
            }
        });
        holder.handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onExamClickListener != null) {
                    // onExamClickListener.onHandleExamClick(view, e, position);
                    if(!global.isCartExist(e)){ // !inCart
                        global.addCart(e);

                        holder.handle.setImageResource(R.drawable.ic_remove_shopping_cart_white_18dp);
                        Snackbar.make(view, "Added to Cart", Snackbar.LENGTH_SHORT).show();
                    }else{
                        global.removeCart(e);

                        holder.handle.setImageResource(R.drawable.ic_add_shopping_cart_white_18dp);
                        Snackbar.make(view, "Removed from Cart", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public Exam getItem(int position){
        return filtered_items.get(position);
    }

    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView description;
        public TextView price;
        public TextView duration;
        public ImageButton handle;
        public LinearLayout lyt_parent;

        public ViewHolder(View v) {
            super(v);

            name = (TextView) v.findViewById(R.id.name);
            description = (TextView) v.findViewById(R.id.description);
            price = (TextView) v.findViewById(R.id.price);
            duration = (TextView) v.findViewById(R.id.duration);

            handle = (ImageButton) v.findViewById(R.id.handle);
            lyt_parent = (LinearLayout) v.findViewById(R.id.lyt_parent);
        }
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<Exam> list = original_items;
            final List<Exam> result_list = new ArrayList<>(list.size());

            for (int i = 0; i < list.size(); i++) {
                Exam exam = list.get(i);
                // String str_title = list.get(i).getName();
                // String str_desription =
                if (exam.getName().toLowerCase().contains(query) ||
                        exam.getDescription().toLowerCase().contains(query)) {
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filtered_items = (List<Exam>) results.values;
            notifyDataSetChanged();
        }
    }
}
