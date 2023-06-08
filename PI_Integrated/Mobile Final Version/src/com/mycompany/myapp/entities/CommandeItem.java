/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author ashre
 */
public class CommandeItem {

    private int id;
    private int qantity;
    private String NomProduit;

    public CommandeItem() {
    }

    public CommandeItem(String NomProduit, int qantity) {
        this.NomProduit = NomProduit;
        this.qantity = qantity;
    }

    public CommandeItem(int id, String NomProduit, int qantity) {
        this.id = id;
        this.NomProduit = NomProduit;
        this.qantity = qantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQantity() {
        return qantity;
    }

    public void setQantity(int qantity) {
        this.qantity = qantity;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public void setNomProduit(String NomProduit) {
        this.NomProduit = NomProduit;
    }

    @Override
    public String toString() {
        return "CommandeItem{" + "id=" + id + ", qantity=" + qantity + ", NomProduit=" + NomProduit + '}';
    }

}
