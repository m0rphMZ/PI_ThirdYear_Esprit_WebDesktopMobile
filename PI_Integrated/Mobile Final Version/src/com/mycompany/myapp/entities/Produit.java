/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author ashre
 */
public class Produit {
    private int id; 
    private String Codeproduit;
    private String Des;
    private int QteStock;
    private int QteMin;
    private int PrixUnitaire;
    private String idUnite;
    private String image;
    private String idcat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeproduit() {
        return Codeproduit;
    }

    public void setCodeproduit(String Codeproduit) {
        this.Codeproduit = Codeproduit;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String Des) {
        this.Des = Des;
    }

    public int getQteStock() {
        return QteStock;
    }

    public void setQteStock(int QteStock) {
        this.QteStock = QteStock;
    }

    public int getQteMin() {
        return QteMin;
    }

    public void setQteMin(int QteMin) {
        this.QteMin = QteMin;
    }

    public int getPrixUnitaire() {
        return PrixUnitaire;
    }

    public void setPrixUnitaire(int PrixUnitaire) {
        this.PrixUnitaire = PrixUnitaire;
    }

    public String getIdUnite() {
        return idUnite;
    }

    public void setIdUnite(String idUnite) {
        this.idUnite = idUnite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdcat() {
        return idcat;
    }

    public void setIdcat(String idcat) {
        this.idcat = idcat;
    }
    
}
