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
public class Livraison {

    private int id;
    private String adresse;
    private String date;
    private int idCommande;
    private String confirmer;
    private String email;

    public Livraison() {
    }

    public Livraison(String adresse, String date, int idCommande, String confirmer, String email) {
        this.adresse = adresse;
        this.date = date;
        this.idCommande = idCommande;
        this.confirmer = confirmer;
        this.email = email;
    }

    public Livraison(int id, String adresse, String date, int idCommande, String confirmer, String email) {
        this.id = id;
        this.adresse = adresse;
        this.date = date;
        this.idCommande = idCommande;
        this.confirmer = confirmer;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Livraison{" + "id=" + id + ", adresse=" + adresse + ", date=" + date + ", idCommande=" + idCommande + ", confirmer=" + confirmer + ", email=" + email + '}';
    }

}

