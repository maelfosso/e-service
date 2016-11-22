package org.cpc.yaounde.eservice.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.cpc.yaounde.eservice.MainActivity;
import org.cpc.yaounde.eservice.MessageDetailsActivity;
import org.cpc.yaounde.eservice.adapter.MessagesListAdapter;
import org.cpc.yaounde.eservice.data.Constant;
import org.cpc.yaounde.eservice.model.Message;
import org.cpc.yaounde.eservice.widget.DividerItemDecoration;
import org.cpc.yaounde.eservice.R;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment {
    public static final String TAG = MessageFragment.class.getName();

    public RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    public MessagesListAdapter mAdapter;
    private ProgressBar progressBar;
    private ActionMode actionMode;
    private List<Message> items = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_message, container, false);
        getActivity().setTitle("Messages");
        // activate fragment menu
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        items = Constant.getMessagesData(getActivity());

        // specify an adapter (see also next example)
        mAdapter = new MessagesListAdapter(getActivity(), items);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MessagesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Message obj, int position) {
                if (actionMode != null) {
                    myToggleSelection(position);
                    return;
                }
                Log.d(TAG, obj.toString());
                MessageDetailsActivity.navigate((MainActivity) getActivity(), v.findViewById(R.id.lyt_parent), obj.getUser(), obj.getSnippet());
            }
        });

        mAdapter.setOnItemLongClickListener(new MessagesListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemClick(View view, Message obj, int position) {
                actionMode = getActivity().startActionMode(modeCallBack);
                myToggleSelection(position);
            }
        });

        bindView();

        return view;
    }

    private void dialogDeleteMessageConfirm(final int count) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Confirmation");
        builder.setMessage("All chat from " + count + " selected item will be deleted?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAdapter.removeSelectedItem();
                mAdapter.notifyDataSetChanged();
                Snackbar.make(view, "Delete "+count+" items success", Snackbar.LENGTH_SHORT).show();
                modeCallBack.onDestroyActionMode(actionMode);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void bindView() {
        try {
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }

    }

    public void onRefreshLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void myToggleSelection(int idx) {
        mAdapter.toggleSelection(idx);
        String title = mAdapter.getSelectedItemCount() + " selected";
        actionMode.setTitle(title);
    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.menu_multiple_select, menu);
            // ((MainActivity)getActivity()).setVisibilityAppBar(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if(menuItem.getItemId() == R.id.action_delete && mAdapter.getSelectedItemCount()>0){
                dialogDeleteMessageConfirm(mAdapter.getSelectedItemCount());
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode act) {
            actionMode.finish();
            actionMode = null;
            mAdapter.clearSelections();
            // ((MainActivity)getActivity()).setVisibilityAppBar(true);
        }
    };
}
