package org.pasteur_yaounde.e_service.model;

/**
 * Created by malko on 26/08/16.
 */
public class DemandeCotation {
    public String id;
    public String description;
    public String etat_traitement;
    public String image_demande;
    /**
     *
     */
    public DemandeCotation() {
        super();
        this.id = "";
        this.description = "";
        this.etat_traitement = "";
        this.image_demande = "";
    }

    /**
     *
     * @param id
     * @param description
     * @param etat
     * @param image
     */
    public DemandeCotation(String id, String description, String etat, String image) {
        super();
        this.id = id;
        this.description = description;
        this.etat_traitement = etat;
        this.image_demande = image;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @param descripte
     */
    public void setDescription(String descripte) {
        this.description = descripte;
    }

    /**
     *
     * @return
     */
    public String getEtat_traitement() {
        return this.etat_traitement;
    }

    /**
     *
     * @param etat
     */
    public void setEtat_traitement(String etat) {
        this.etat_traitement = etat;
    }

    /**
     *
     * @return
     */
    public String getImage_demande() {
        return this.image_demande;
    }

    /**
     *
     * @param image
     */
    public void setImage_demande(String image) {
        this.image_demande = image;
    }
}