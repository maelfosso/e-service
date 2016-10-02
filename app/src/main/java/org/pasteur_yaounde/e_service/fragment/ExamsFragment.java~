package org.pasteur_yaounde.e_service.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pasteur_yaounde.e_service.ExamDetailActivity;
import org.pasteur_yaounde.e_service.R;
import org.pasteur_yaounde.e_service.adapter.ExamsListAdapter;
import org.pasteur_yaounde.e_service.capture.TakePhotoMainActivity;
import org.pasteur_yaounde.e_service.data.Constant;
import org.pasteur_yaounde.e_service.data.GlobalVariable;
import org.pasteur_yaounde.e_service.model.Exam;
import org.pasteur_yaounde.e_service.widget.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamsFragment extends Fragment implements ExamsListAdapter.OnExamClickListener {

    private FloatingActionButton takePhoto;
    private RecyclerView recyclerView;

    private ExamsListAdapter adapter;
    private GlobalVariable global;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

    @Override
    public void onExamClick(View view, Exam exam, int position) {
        ExamDetailActivity.navigate(getActivity(), view.findViewById(R.id.lyt_parent), exam);
    }

    @Override
    public void onHandleExamClick(View view, Exam exam, int position) {


    }
}
