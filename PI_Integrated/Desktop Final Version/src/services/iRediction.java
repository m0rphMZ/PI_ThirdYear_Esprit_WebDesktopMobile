/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import entities.Rediction;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sbekr
 */
public interface iRediction {
            public boolean ajouterduction(Rediction r)throws SQLException;
        public boolean updaterduction(Rediction r)throws SQLException;
    public boolean suprimerductiont(Rediction r)throws SQLException;
    public List<Rediction> getAll()throws SQLException;
    
}
