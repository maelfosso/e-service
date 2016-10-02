package org.pasteur_yaounde.e_service.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import org.pasteur_yaounde.e_service.MainActivity;
import org.pasteur_yaounde.e_service.R;
import org.pasteur_yaounde.e_service.adapter.DemandeCotationAdapter;
import org.pasteur_yaounde.e_service.data.Constant;
import org.pasteur_yaounde.e_service.data.GlobalVariable;
import org.pasteur_yaounde.e_service.model.DemandeCotation;

import java.util.ArrayList;

/**
 * Created by malko on 30/08/16.
 */
public class AskCotationFragment extends Fragment implements DemandeCotationAdapter.DemandeCotationClickListener {
    private GlobalVariable global;
    private LinearLayoutManager recyclerManager = null;
    private RecyclerView recyclerView = null;

    private DemandeCotationAdapter askAdapter = null;
    // Variables permettant de contenir les objets
    private ArrayList<DemandeCotation> listContentDemandeCotation = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ask_cotation, container, false);
        this.getActivity().setTitle("Taitment des demandes");
        global = (GlobalVariable)getActivity().getApplication();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initRecyclerView();

        return view;
    }

    /**
     *
     */
    private void initRecyclerView() {
        // Utiliser un gestionnaire de layout linéaire
        recyclerManager = new LinearLayoutManager(getActivity());
        recyclerManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(recyclerManager);
        // Amélioration des performances pour améliorer la pprésentation du RecyclerView
        recyclerView.setHasFixedSize(true);

        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // recyclerView.setHasFixedSize(true);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        // listContentDemandeCotation = new ArrayList<DemandeCotation>(0);
        // listContentDemandeCotation = Constant.getDemandeCotationData(getActivity());
        // askAdapter = new DemandeCotationAdapter(getActivity(), listContentDemandeCotation);
        askAdapter = new DemandeCotationAdapter(getActivity(), Constant.getDemandeCotationData(getActivity()));
        askAdapter.setClickListener(this);
        recyclerView.setAdapter(askAdapter);
    }

    /**
     *
     */
    private class DemandeCotationClickListener implements View.OnClickListener{
        private final Context context;

        private DemandeCotationClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            //
        }
    }

    /**
     *
     * @param v
     * @param position
     * @param actionClick
     */
    @Override
    public void itemClicked(View v, int position, int actionClick) {
        actionWidgetEvent(v, position, actionClick);
    }

    /**
     *
     * @param v
     * @param itemPosition
     * @param actionClick
     */
    public void actionWidgetEvent(View v, final int itemPosition, int actionClick) {
        switch(actionClick){
            case 0 :    MainActivity.afficheTost(getActivity(), "Photo de l'utilisateur...");    break;
            case 1 :    MainActivity.afficheTost(getActivity(), "Nom de l'utilisateur...");    break;
            case 2 :    MainActivity.afficheTost(getActivity(), "Affichage de le demande de cotation...");    break;
        }
    }
}