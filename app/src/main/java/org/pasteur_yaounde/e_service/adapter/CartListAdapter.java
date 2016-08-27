package org.pasteur_yaounde.e_service.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import org.pasteur_yaounde.e_service.R;
import org.pasteur_yaounde.e_service.model.Exam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maelfosso on 8/26/16.
 */
public class CartListAdapter  extends RecyclerView.Adapter<CartListAdapter.ViewHolder> implements Filterable {

    private final int mBackground;
    private List<Exam> original_items = new ArrayList<>();
    private List<Exam> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();

    private final TypedValue mTypedValue = new TypedValue();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Exam obj);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView description;
        public TextView price;
        public MaterialRippleLayout lyt_parent;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            description = (TextView) v.findViewById(R.id.description);
            price = (TextView) v.findViewById(R.id.price);

            lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CartListAdapter(Context ctx, List<Exam> items) {
        this.ctx = ctx;
        original_items = items;
        filtered_items = items;
        ctx.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        v.setBackgroundResource(mBackground);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Exam exam = filtered_items.get(position);
        holder.name.setText(exam.getName());
        holder.description.setText(exam.getDescription());
        holder.price.setText(String.valueOf(exam.getPrice()));
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position, exam);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<Exam> list = original_items;
            final List<Exam> result_list = new ArrayList<>(list.size());

            for (int i = 0; i < list.size(); i++) {
                String str_title = list.get(i).getName();
                // String str_cat = list.get(i).getCategory();
                if (str_title.toLowerCase().contains(query)) {
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            filtered_items = (List<Exam>) results.values;
            notifyDataSetChanged();
        }

    }
}