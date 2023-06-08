/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Administrateur
 */
public class Local {
     private int num;
    private String descript;
   
    private String lieu;
     private String equipements;
      private String disponibilite;
 private String date ;
 
 private float surface;
     private float prix;
    private int nbper;
    private int codec;
    private int userid;
 

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    

    public void setUserid(int userid) {
        this.userid = userid;
    }
    private String image;
    

    public Local(int num, String descript, String lieu, float surface, int codec, String image) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.codec = codec;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Local() {
    }

    /*public Local(int num, String descript, String lieu, float surface, int nbper, int codec) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.nbper = nbper;
        this.codec = codec;
    }*/

    public Local(int num, String descript, String lieu, float surface, int nbper, int codec, String image) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.nbper = nbper;
        this.codec = codec;
        this.image = image;
    }

    public int getUserid() {
        return userid;
    }

    public int getNum() {
        return num;
    }

    public String getDescript() {
        return descript;
    }

    public String getLieu() {
        return lieu;
    }

    public float getSurface() {
        return surface;
    }

    public int getNbper() {
        return nbper;
    }

    public int getCodec() {
        return codec;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public void setNbper(int nbper) {
        this.nbper = nbper;
    }

    public void setCodec(int codec) {
        this.codec = codec;
    }

    public String getEquipements() {
        return equipements;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public float getPrix() {
        return prix;
    }

    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    
    
    
    
    
    
    

    @Override
    public String toString() {
        return "Local{" + "num=" + num + ", descript=" + descript + ", lieu=" + lieu + ", surface=" + surface + ", nbper=" + nbper + ", codec=" + codec + ", image=" + image + '}';
    }

   
    
}
