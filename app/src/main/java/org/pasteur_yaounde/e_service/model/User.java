package org.pasteur_yaounde.e_service.model;

import java.util.Arrays;

/**
 * Created by Mael FOSSO on 10/03/2016.
 */
public class User {
    public String id;
    public String name;
    public String photo;
    public String login;
    public String password;
    public String[] id_demande_cotation;
    public String connexion;
    /**
     *
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
        this.id = "";
        this.name = "";
        this.photo = "";
        this.login = "";
        this.password = "";
        this.id_demande_cotation = null;
        this.connexion = "";
    }
    /**
     * @param id
     * @param name
     * @param photo
     * @param login
     * @param password
     * @param connexion
     */
    public User(String id, String name, String photo, String login, String password, String[] demande_id, String connexion) {
        super();
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.login = login;
        this.password = password;
        this.id_demande_cotation = demande_id;
        this.connexion = connexion;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }
    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }
    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the connexion
     */
    public String getConnexion() {
        return connexion;
    }
    /**
     * @param connexion the connexion to set
     */
    public void setConnexion(String connexion) {
        this.connexion = connexion;
    }
    /**
     * @return the attenders
     */
    public String[] getId_demande_cotation() {
        return this.id_demande_cotation;
    }

    /**
     * @return the attenders
     */
    public String getOneId_demande_cotation(int i) {
        return id_demande_cotation[i];
    }

    /**
     * @return the attenders
     */
    public int getTailleId_demande_cotation() {
        return id_demande_cotation.length;
    }

    /**
     * @param attenders the attenders to set
     */
    public void setId_demande_cotation(String[] attenders) {
        this.id_demande_cotation = attenders;
    }

    /**
     * @param attender the attenders to set
     * @param i
     */
    public void setOneId_demande_cotation(String attender, int i) {
        this.id_demande_cotation[i] = attender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", id_demande_cotation=" + Arrays.toString(id_demande_cotation) +
                ", connexion='" + connexion + '\'' +
                '}';
    }
}
