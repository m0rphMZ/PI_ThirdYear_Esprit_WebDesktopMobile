/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Rediction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;

/**
 *
 * @author sbekr
 */
public class Redictionservice implements iRediction{
        Connection cnx;

    public Redictionservice() {
        cnx =MyDB.getInstance().getCnx();
    }

    @Override
    public boolean ajouterduction(Rediction r) throws SQLException {
    String request = "INSERT INTO redection (coder, valr) VALUES (?,?)";
 
    
    PreparedStatement pst = cnx.prepareStatement(request);
    
    pst.setString(1, r.getCoder());
    pst.setFloat(2, r.getValr());

    pst.executeUpdate();
    System.out.println("Reduction added");
    return true;    }

    @Override
    public boolean updaterduction(Rediction r) throws SQLException {

    String request = "UPDATE redection SET  valr=? WHERE coder=?";
    PreparedStatement pst = cnx.prepareStatement(request);
    pst.setString(2, r.getCoder());
    pst.setDouble(1, r.getValr());
    
    pst.executeUpdate();
    System.out.println("Reduction Updated");
    return true;
    }

    @Override
    public boolean suprimerductiont(Rediction r) throws SQLException {
    String request = "DELETE FROM redection WHERE coder=?";
    PreparedStatement pst = cnx.prepareStatement(request);
    pst.setString(1, r.getCoder());
    pst.executeUpdate();
    System.out.println("Reduction deleted");
    return true;    }

    @Override
    public List<Rediction> getAll() throws SQLException {
            List<Rediction> reductions = new ArrayList<>();
    String query = "SELECT * FROM redection";
    Statement statement = cnx.createStatement();
    ResultSet resultSet = statement.executeQuery(query);
    while (resultSet.next()) {
        Rediction reduction = new Rediction();
       
        reduction.setCoder(resultSet.getString("coder"));
        reduction.setValr(resultSet.getFloat("valr"));
        reductions.add(reduction);
    }
    return reductions;
    }
    public boolean checkCodeExists(String code) throws SQLException {
    String request = "SELECT * FROM redection WHERE coder = ?";
    PreparedStatement pst = cnx.prepareStatement(request);
    pst.setString(1,code);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
        
        return true;
        
    } else {
        return false;
    }


    }
        public float getrductiontcode(String code) throws SQLException {
    String request = "SELECT valr FROM redection WHERE coder = ?";
    PreparedStatement pst = cnx.prepareStatement(request);
    pst.setString(1, code);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
        return rs.getFloat("valr");
    } else {
        return -1; // or throw an exception, depending on your needs
    }
}
            public boolean suprimerbycode(String coder) throws SQLException {
    String request = "DELETE FROM redection WHERE coder=?";
    PreparedStatement pst = cnx.prepareStatement(request);
    pst.setString(1,coder);
    pst.executeUpdate();
    System.out.println("Reduction deleted");
    return true;    }
}
