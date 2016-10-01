package org.pasteur_yaounde.e_service.web_interface;

import android.content.Context;

import org.pasteur_yaounde.e_service.data.Constant;
import org.pasteur_yaounde.e_service.model.DemandeCotation;
import org.pasteur_yaounde.e_service.model.User;

import java.util.ArrayList;

/**
 * Created by Franck on 10/05/2016.
 */
public class ServiceReturnData {
    /**
     * Constructeur par défaut de notre classe
     */
    public ServiceReturnData() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param context
     * @return
     */
    public static ArrayList<DemandeCotation> getListeDemandeCotationOfJson(Context context){
        // Récupération des évènements dans le fichier Json
        ArrayList<DemandeCotation> demande = Constant.getDemandeCotationData(context);
        return demande;
    }

    /**
     *
     * @param categorie
     * @param ident
     * @return
     */
    public static DemandeCotation getDemandeCotationByIdOfJson(ArrayList<DemandeCotation> categorie, String ident) {
        for(int i = 0; i < categorie.size(); i++){
            if(categorie.get(i).getId().equals(ident))    return categorie.get(i);
        }
        return null;
    }

    /**
     *
     * @param context
     * @return
     */
    public static ArrayList<User> getListeUserOfJson(Context context){
        // Récupération des utilisateur dans le fichier Json
        ArrayList<User> users = Constant.getUserData(context);
        return users;
    }

    /**
     *
     * @param users
     * @param ident
     * @return
     */
    public static User getUserByIdOfJson(ArrayList<User> users, String ident) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId().equals(ident))    return users.get(i);
        }
        return null;
    }

    /**
     *
     * @param users
     * @param demande
     * @return
     */
    public static User getUserByIdDemandeCotationOfJson(ArrayList<User> users, DemandeCotation demande) {
        for(int i = 0; i < users.size(); i++){
            for(int j = 0; j < users.get(i).getId_demande_cotation().length; j++){
                if(users.get(i).getOneId_demande_cotation(j).equals(demande.getId()))    return users.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param users
     * @param login
     * @return
     */
    public static User getUserByLoginOfJson(ArrayList<User> users, String login) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getLogin().equals(login))    return users.get(i);
        }
        return null;
    }
}