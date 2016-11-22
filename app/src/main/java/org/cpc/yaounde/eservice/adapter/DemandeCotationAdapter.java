package org.cpc.yaounde.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import org.cpc.yaounde.eservice.web_interface.ServiceReturnData;
import org.cpc.yaounde.eservice.R;
import org.cpc.yaounde.eservice.data.GlobalVariable;
import org.cpc.yaounde.eservice.model.DemandeCotation;
import org.cpc.yaounde.eservice.model.User;

/**
 * Created by malko on 26/08/16
 */
public class DemandeCotationAdapter extends RecyclerView.Adapter<DemandeCotationAdapter.DemandeCotationViewHolder> {

    public static final String TAG = DemandeCotationAdapter.class.getSimpleName();

    private ArrayList<DemandeCotation> original_items = new ArrayList<>(0);
    private ArrayList<DemandeCotation> filtered_items = new ArrayList<>(0);
    // private ItemFilter mFilter = new ItemFilter();

    private Context context;
    // private OnExamClickListener onExamClickListener;

    private GlobalVariable global;

    private  static Context tcontext;
    private int layoutIdDemandeCotation;
    private LayoutInflater inflaterDemandeCotation;
    private ArrayList<DemandeCotation> dataDemandeCotation;
    private static DemandeCotationClickListener demandeCotationClickListener;

    public DemandeCotationAdapter(Context context) {
        inflaterDemandeCotation = LayoutInflater.from(context);
    }

    /**
     * Constructeur par initialisation de nore classe
     * @param context
     * @param donnee
     */
    public DemandeCotationAdapter(Context context, ArrayList<DemandeCotation> donnee) {
        super();
        this.tcontext = context;
        this.inflaterDemandeCotation = LayoutInflater.from(context);
        this.layoutIdDemandeCotation = 0;
        this.dataDemandeCotation = donnee;
    }

    /**
     * Constructeur par initialisation de nore classe
     * @param context
     * @param identifiant
     * @param donnee
     */
    public DemandeCotationAdapter(Context context, int identifiant, ArrayList<DemandeCotation> donnee) {
        super();
        this.inflaterDemandeCotation = LayoutInflater.from(context);
        this.layoutIdDemandeCotation = identifiant;
        this.dataDemandeCotation = donnee;
    }

    @Override
    public int getItemCount() {
        return dataDemandeCotation.size();
    }

    @Override
    public void onBindViewHolder(DemandeCotationViewHolder demandeViewHolder, int i) {
        // Dans tous les cas, on récupère l'activité concernée
        DemandeCotation element = dataDemandeCotation.get(i);
        if(element != null){
            demandeViewHolder.nameCreatorEvent.setText(element.getId());
            demandeViewHolder.description.setText(element.getDescription());
            User utilisateur = ServiceReturnData.getUserByIdDemandeCotationOfJson(
                                                        ServiceReturnData.getListeUserOfJson(tcontext), element);
            Log.d(getClass().getName(), utilisateur.toString());
            Picasso.with(tcontext)
                    //.load(tcontext.getResources().getIdentifier(utilisateur.getPhoto(), "drawable", tcontext.getPackageName()))
                    .load(tcontext.getResources().getIdentifier(utilisateur.getPhoto(), null, tcontext.getPackageName()))
                    .resize(50, 50)
                    .into(demandeViewHolder.imagUser);
            Picasso.with(tcontext)
                    // .load(tcontext.getResources().getIdentifier(element.getImage_demande(), "drawable", tcontext.getPackageName()))
                    .load(tcontext.getResources().getIdentifier(element.getImage_demande(), null, tcontext.getPackageName()))
                    .resize(50, 50)
                    .into(demandeViewHolder.imageDemandeCotation);

            /*if(element.getEtat_traitement().equals("oui"))
                timeLineViewHolder.imageBoosterEvent.setTextColor(tcontext.getResources().getColor(R.color.color_blue_white));
            else    timeLineViewHolder.imageBoosterEvent.setTextColor(tcontext.getResources().getColor(R.color.colorPrimaryDark));*/
        }
    }

    @Override
    public DemandeCotationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ask_cotation, viewGroup, false);
        return new DemandeCotationViewHolder(itemView);
    }

    /**
     *
     */
    public static class DemandeCotationViewHolder extends RecyclerView.ViewHolder {
        protected TextView nameCreatorEvent;
        protected ImageView imagUser;
        protected ImageView imageDemandeCotation;
        protected TextView description;

        /**
         * Contructeur permettant de récupérer les identifiants des widgets
         * @param itemView
         */
        public DemandeCotationViewHolder(View itemView) {
            super(itemView);
            this.nameCreatorEvent = (TextView) itemView.findViewById(R.id.name_poster_event);
            this.imagUser = (ImageView) itemView.findViewById(R.id.photo_profil_user_connected);
            this.imageDemandeCotation = (ImageView) itemView.findViewById(R.id.capture_dmde_cotation);
            this.description = (TextView) itemView.findViewById(R.id.description_demande_cotation);

            this.nameCreatorEvent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.name_poster_event){
                        if (demandeCotationClickListener != null)
                            demandeCotationClickListener.itemClicked(v, getAdapterPosition(), 0);
                    }
                }
            });
            this.imagUser.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.photo_profil_user_connected){
                        if (demandeCotationClickListener != null)
                            demandeCotationClickListener.itemClicked(v, getAdapterPosition(), 1);
                    }
                }
            });
            this.imageDemandeCotation.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.capture_dmde_cotation){
                        if (demandeCotationClickListener != null)
                            demandeCotationClickListener.itemClicked(v, getAdapterPosition(), 2);
                    }
                }
            });
        }
    }

    public interface DemandeCotationClickListener {
        public void itemClicked(View v, int position, int action);
    }

    /**
     *
     * @param clickListener
     */
    public void setClickListener(DemandeCotationClickListener clickListener) {
        this.demandeCotationClickListener = clickListener;
    }
}