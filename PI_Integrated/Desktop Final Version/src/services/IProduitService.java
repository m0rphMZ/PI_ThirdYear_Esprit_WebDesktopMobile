/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;
import entities.produit;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Monta
 */
public interface IProduitService<T> {
    
        public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(T t) throws SQLException;
    public List<T> recuperer() throws SQLException;
    
}
