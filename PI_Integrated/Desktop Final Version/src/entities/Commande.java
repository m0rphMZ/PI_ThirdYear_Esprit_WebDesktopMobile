/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.util.Date;
/**
 *
 * @author ashre
 */
public class Commande {

    
    	private int commande_id ; 
        private int id_user ; 
        private String date_commande ;
        private  String rue;
        private  String ville;
        private  String code_postal;
        private  String tel;
        private String nom ; 
        private String prenom ; 
        private int id_panier;
        private int prix ; 

    public Commande(int id_user, String date_commande, String rue, String ville, String code_postal, String tel, String nom, String prenom, int id_panier, int prix) {
        this.id_user = id_user;
        this.date_commande = date_commande;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
        this.id_panier = id_panier;
        this.prix = prix;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Commande(int commande_id, int id_user, String date_commande, String rue, String ville, String code_postal, String tel, String nom, String prenom, int id_panier, int prix) {
        this.commande_id = commande_id;
        this.id_user = id_user;
        this.date_commande = date_commande;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
        this.id_panier = id_panier;
        this.prix = prix;
    }

    public Commande(int id_user, String date_commande, String rue, String ville, String code_postal, String tel, String nom, String prenom, int id_panier) {
        this.id_user = id_user;
        this.date_commande = date_commande;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
        this.id_panier = id_panier;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public Commande(int commande_id, int id_user, String date_commande, String rue, String ville, String code_postal, String tel, String nom, String prenom, int id_panier) {
        this.commande_id = commande_id;
        this.id_user = id_user;
        this.date_commande = date_commande;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
        this.id_panier = id_panier;
    }

    @Override
    public String toString() {
        return "Commande{" + "commande_id=" + commande_id + ", id_user=" + id_user + ", date_commande=" + date_commande + ", rue=" + rue + ", ville=" + ville + ", code_postal=" + code_postal + ", tel=" + tel + ", nom=" + nom + ", prenom=" + prenom + '}';
    }

    public Commande(int commande_id, int id_user, String date_commande, String rue, String ville, String code_postal, String tel, String nom, String prenom) {
        this.commande_id = commande_id;
        this.id_user = id_user;
        this.date_commande = date_commande;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
    }
        
        

    public Commande(int id_user, String date_commande, String rue, String ville, String code_postal, String tel, String nom, String prenom) {
        this.id_user = id_user;
        this.date_commande = date_commande;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
    }

   
  

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Commande() {
    }
    public int getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public int getUser_id() {
        return id_user;
    }

    public void setUser_id(int user_id) {
        this.id_user = user_id;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }


    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }


    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
