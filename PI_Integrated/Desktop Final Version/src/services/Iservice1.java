/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrateur
 * @param <T>
 */
public interface Iservice1 <T> {
    
    
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public boolean supprimer(T t) throws SQLException;
    public List<T> recuperer() throws SQLException;

    
    
}