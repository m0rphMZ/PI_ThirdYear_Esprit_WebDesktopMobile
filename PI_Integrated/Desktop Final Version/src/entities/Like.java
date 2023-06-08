/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Mohamed
 */
public class Like {
    
    private int id_user;
    private int id_com;
    private Boolean etat;

    public Like(int id_user, int id_com, Boolean etat) {
        this.id_user = id_user;
        this.id_com = id_com;
        this.etat = etat;
    }

    public Like() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Like{" + "id_user=" + id_user + ", id_com=" + id_com + ", etat=" + etat + '}';
    }
    
    
    
    
    
}
