/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Skander
 * @param <T>
 */
public interface RecInterface<T> {
    
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void ModifierEtat(int rec_id) throws SQLException;
    public void supprimer(T t) throws SQLException;
    public void supprimerParRecId(int rec_id) throws SQLException;
    public List<T> recupererParUtilisateur(int userId) throws SQLException;
    public T recupererParId(int id) throws SQLException;
    public List<T> recuperer() throws SQLException;
    
}
