package entities;

import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Panier {
        int id_panier;
        int id_produit;
        int id_user;
        int quantite;

    public Panier(int id_panier) {
        this.id_panier = id_panier;
    }

    @Override
    public String toString() {
        return "Panier{" + "id_panier=" + id_panier + "}";
    }

    public Panier(int id_panier, int id_produit, int id_user, int quantite) {
        this.id_panier = id_panier;
        this.id_produit = id_produit;
        this.id_user = id_user;
        this.quantite = quantite;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Panier(int produit_id, int id_user) {
        this.id_produit = produit_id;
        this.id_user = id_user;
    }

    public Panier(int id_panier, int produit_id, int id_user) {
        this.id_panier = id_panier;
        this.id_produit = produit_id;
        this.id_user = id_user;
    }

    public Panier() {
    }

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public int getProduit_id() {
        return id_produit;
    }

    public void setProduit_id(int produit_id) {
        this.id_produit = produit_id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

}