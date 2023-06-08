/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entites;

/**
 *
 * @author Monta
 */
public class Avis {
    
    private int idAvis;
    private int idProd;
    private String contenu;

    public Avis() {
    }

    public Avis(int idAvis, int idProd, String contenu) {
        this.idAvis = idAvis;
        this.idProd = idProd;
        this.contenu = contenu;
    }
    public Avis( int idProd, String contenu) {
        
        this.idProd = idProd;
        this.contenu = contenu;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    
    
    
}
