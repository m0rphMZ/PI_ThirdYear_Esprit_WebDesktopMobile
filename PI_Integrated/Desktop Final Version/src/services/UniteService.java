/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categorie;
import entities.produit;
import entites.unite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;

/**
 *
 * @author Monta
 */
public class UniteService {
    
    
    
           Connection cnx;
           public Statement ste;
           public PreparedStatement pst;
 

     {
        cnx = MyDB.getInstance().getCnx();
   

}
     public void ajouter(unite e) throws SQLException{
     
     String requete = "INSERT INTO unite (`libUnite`) "
                + "VALUES (?);";
      try {
            //Categorie c = new Categorie() ;
            pst = (PreparedStatement) cnx.prepareStatement(requete);
         
            pst.setString(1, e.getLibUntie());
                     
            pst.executeUpdate();
            System.out.println("Unite " + e.getLibUntie() + " added successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     }
     
     
      public void modifierUnite(unite t) throws SQLException {
        String req = "UPDATE unite SET 	libUnite = ? where idUnite = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
         ps.setInt(1, t.getIdUnite());
        ps.setString(2, t.getLibUntie());
        ps.executeUpdate();
          System.out.println("unite " + t.getLibUntie() + " modified successfully");
    }
      
         public void supprimerUnite(unite t) throws SQLException {
       
         String req = "delete from unite where idUnite = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getIdUnite());
        ps.executeUpdate();
        System.out.println("unite with id= " + t.getLibUntie() + "  is deleted successfully");
        
    }
         public List recupererUnite() throws SQLException {
             List<unite> unite = new ArrayList<>();    
       String s = "select * from  unite";
        Statement st = cnx.createStatement();
        ResultSet rs =  st.executeQuery(s);
         while(rs.next()){
            unite p = new unite();
          //  Categorie c = new Categorie();
            //  p.setId(rs.getInt("idCategorie"));
            p.setLibUntie(rs.getString("libUnite"));
          //    c.setLibCat(rs.getString("libCategorie"));
          
            unite.add(p);
            
        }
        return unite;
    }
    
}
