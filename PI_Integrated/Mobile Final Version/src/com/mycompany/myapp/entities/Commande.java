/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author ashre
 */
public class Commande {
    private int id_c;
    private String date;
    private int user;
    private String statue;
    private boolean livraison;
    
 

    public boolean isLivraison() {
        return livraison;
    }

    public void setLivraison(boolean livraison) {
        this.livraison = livraison;
    }

    public Commande(int id_c, String date, int user, String statue) {
        this.id_c = id_c;
        this.date = date;
        this.user = user;
        this.statue = statue;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public String getDate() {
        return date;
    }

    
    public void setDate(String date) {
        this.date = date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }


    public Commande() {
    }

    
    public Commande(int id_c, String date, int user, String statue, double remise) {
        this.id_c = id_c;
        this.date = date;
        this.user = user;
        this.statue = statue;
        
    }

    @Override
    public String toString() {
        return "Commande{" + "id_c=" + id_c + ", date=" + date + ", user=" + user + ", statue=" + statue + ", livraison=" + livraison + '}';
    }

   
    
    
    
}
