/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;
import java.util.Date; 

/**
 *
 * @author Theto
 */

public class Reclamation {
    private int rec_id;
    private int user_id;
    private String titre_rec;
    private String type;
    private String description;
    private Date date_creation;
    private Date date_fin;
    private String status = "Ouvert";

    public Reclamation() {
    }

    public Reclamation(int user_id, String titre_rec, String type, String description, String status) {
        this.user_id = user_id;
        this.titre_rec = titre_rec;
        this.type = type;
        this.description = description;
        Date date_deb = new Date();
        this.date_creation = date_deb;
        this.status = status;
    }
        public Reclamation(int rec_id, int user_id, String titre_rec, String type, String description, String status) {
        this.rec_id = rec_id;
        this.user_id = user_id;
        this.titre_rec = titre_rec;
        this.type = type;
        this.description = description;
        Date date_deb = new Date();
        this.date_creation = date_deb;
        this.status = status;
    }

    public Reclamation(int i) {
    }

    public Reclamation(int rec_id, int user_id, String titre_rec, String type, String description, String status, Date date_creation, Date date_fin) {
        this.rec_id = rec_id;
        this.user_id = user_id;
        this.titre_rec = titre_rec;
        this.type = type;
        this.description = description;
        this.date_creation = date_creation;
        this.date_fin = date_fin;
        this.status = status;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitre_rec() {
        return titre_rec;
    }

    public void setTitre_rec(String titre_rec) {
        this.titre_rec = titre_rec;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "rec_id=" + rec_id + ", user_id=" + user_id + ", titre_rec=" + titre_rec + ", type=" + type + ", description=" + description + ", date_creation=" + date_creation + ", date_fin=" + date_fin + ", status=" + status + '}';
    }
    
    
    
    
    
    
    
    
}
