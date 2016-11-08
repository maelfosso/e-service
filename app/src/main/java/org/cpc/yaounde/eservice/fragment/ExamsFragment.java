package org.cpc.yaounde.eservice.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cpc.yaounde.eservice.data.Constant;
import org.cpc.yaounde.eservice.model.Exam;
import org.cpc.yaounde.eservice.ExamDetailActivity;
import org.yaounde.eservice.R;
import org.cpc.yaounde.eservice.adapter.ExamsListAdapter;
import org.cpc.yaounde.eservice.capture.TakePhotoMainActivity;
import org.cpc.yaounde.eservice.data.GlobalVariable;
import org.cpc.yaounde.eservice.widget.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamsFragment extends Fragment implements ExamsListAdapter.OnExamClickListener {

    private FloatingActionButton takePhoto;
    private RecyclerView recyclerView;

    public ExamsListAdapter adapter;
    private GlobalVariable global;

    private boolean isSearch = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exams, container, false);
        this.getActivity().setTitle("Accueil");
        global = (GlobalVariable) getActivity().getApplication();


        takePhoto = (FloatingActionButton) view.findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTakePhoto();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        adapter = new ExamsListAdapter(getActivity(), Constant.getExamsData(getActivity()), global); //global.getCart());
        recyclerView.setAdapter(adapter);
        adapter.setOnExamClickListener(this);
    }


    public void goTakePhoto() {
        int[] startingLocation = new int[2];
        takePhoto.getLocationOnScreen(startingLocation);
        startingLocation[0] += takePhoto.getWidth() / 2;

        TakePhotoMainActivity.startCameraFromLocation(startingLocation, getActivity());
        getActivity().overridePendingTransition(0, 0);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // MenuInflater inflater = getMenuInflater();
        inflater.inflate(isSearch ? R.menu.menu_search_toolbar : R.menu.menu_main, menu);

        if (isSearch) {
            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setIconified(false);
            search.setQueryHint("Search exams...");
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return true;
                }
            });
            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    closeSearch();
                    return true;
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search: {
                isSearch = true;
                searchToolbar.setVisibility(View.VISIBLE);
                prepareActionBar(searchToolbar);
                supportInvalidateOptionsMenu();
                return true;
            }
            case R.id.action_cart: {
                Snackbar.make(parentView, "Cart Clicked", Snackbar.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_filter_category: {
                Snackbar.make(parentView, "Filter by Category Clicked", Snackbar.LENGTH_SHORT).show();
            }
            case android.R.id.home:
                closeSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    private void closeSearch() {
        if (isSearch) {
            isSearch = false;
            prepareActionBar(toolbar);
            searchToolbar.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
        }
    }
    }*/

    @Override
    public void onExamClick(View view, Exam exam, int position) {
        ExamDetailActivity.navigate(getActivity(), view.findViewById(R.id.lyt_parent), exam);
    }

    @Override
    public void onHandleExamClick(View view, Exam exam, int position) {


    }
}
