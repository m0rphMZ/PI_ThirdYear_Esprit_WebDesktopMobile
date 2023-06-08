/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Theto
 */
public interface RepInterface<T> {
    public void ajouter(T t) throws SQLException;
    public List<T>recupererParRecId(int rec_id) throws SQLException;
    public void supprimerParRecId(int rec_id) throws SQLException;
}
