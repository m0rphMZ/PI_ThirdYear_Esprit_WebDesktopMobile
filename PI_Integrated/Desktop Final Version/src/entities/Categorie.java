/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;
import java.util.Objects;

/**
 *
 * @author Monta
 */
public class Categorie {
    private int id ;
    private String libCat;

    public Categorie(int id, String libCat) {
        this.id = id;
        this.libCat = libCat;
    }

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibCat() {
        return libCat;
    }

    public void setLibCat(String libCat) {
        this.libCat = libCat;
    }

    @Override
    public String toString() {
        return libCat ;
    }
    
     public static class UniteCategorie {
        private String libUnite;
        private String libCategorie;
        
        public UniteCategorie(String libUnite, String libCategorie) {
            this.libUnite = libUnite;
            this.libCategorie = libCategorie;
        }
    
}
}
