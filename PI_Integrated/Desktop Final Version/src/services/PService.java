/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ashre
 * @param <T>
 */
public interface PService<T> {
    public T recupererProduitParId(int produit_id) throws SQLException;
}
