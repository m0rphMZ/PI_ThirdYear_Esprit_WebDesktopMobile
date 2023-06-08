/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;
import entities.Categorie;
import entities.produit;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Monta
 */

 public interface ICategorieService<T> {
    
        public void ajouterCat(Categorie t) throws SQLException;
    public void modifierCat(Categorie t) throws SQLException;
    public void supprimerCat(Categorie t) throws SQLException;
    public List<T> recupererCat() throws SQLException;
    
}   

