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
public class Reponses {
    private int rep_id;
    private int rec_id;
    private int user_id;
    private int admin_id;
    private String rep_desc;
    private Date date_rep;
    private boolean isAdminReponse;

    public Reponses() {
    }

    public Reponses(int rep_id, int rec_id, int user_id, String rep_desc) {
        this.rep_id = rep_id;
        this.rec_id = rec_id;
        this.user_id = user_id;
        this.rep_desc = rep_desc;
        Date daterep = new Date();
        this.date_rep = daterep;
    }
    public Reponses(int rep_id, int rec_id, int user_id,int admin_id, String rep_desc) {
        this.rep_id = rep_id;
        this.rec_id = rec_id;
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.rep_desc = rep_desc;
        Date daterep = new Date();
        this.date_rep = daterep;
    }
    public Reponses(int rep_id, int rec_id, int user_id,int admin_id, String rep_desc,boolean isAdminReponse) {
        this.rep_id = rep_id;
        this.rec_id = rec_id;
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.rep_desc = rep_desc;
        Date daterep = new Date();
        this.date_rep = daterep;
        this.isAdminReponse = isAdminReponse;
    }

    public boolean isIsAdminReponse() {
        return isAdminReponse;
    }

    public void setIsAdminReponse(boolean isAdminReponse) {
        this.isAdminReponse = isAdminReponse;
    }


    public Reponses(int user_id) {
        
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
    

    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
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

    public String getRep_desc() {
        return rep_desc;
    }

    public void setRep_desc(String rep_desc) {
        this.rep_desc = rep_desc;
    }

    public Date getDate_rep() {
        return date_rep;
    }

    public void setDate_rep(Date date_rep) {
        this.date_rep = date_rep;
    }

    @Override
    public String toString() {
        return "Reponses{" + "rep_id=" + rep_id + ", rec_id=" + rec_id + ", user_id=" + user_id + ", rep_desc=" + rep_desc + ", date_rep=" + date_rep + '}';
    }
    
    
    
    
    
}
