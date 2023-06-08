/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categorie;
import entities.Categorie.UniteCategorie;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import utils.MyDB;


/**
 *
 * @author Monta
 */
public  class CategorieService implements ICategorieService<Categorie> {
    
         Connection cnx;
           public Statement ste;
           public PreparedStatement pst;
           
//           private TableView<UniteCategorie> tableView = new TableView<>();
//    private ObservableList<UniteCategorie> data = FXCollections.observableArrayList();
 

     {
        cnx = MyDB.getInstance().getCnx();

}

    @Override
    public void ajouterCat(Categorie t) throws SQLException {
        String requete = "INSERT INTO categorie (`libcategorie`)"
                + "VALUES (?);";

           try {
            Categorie c = new Categorie() ;
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            
            pst.setString(1,t.getLibCat());
                    
            pst.executeUpdate();
            System.out.println("Categorie " + c.getLibCat() + " added successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
 @Override
    public void modifierCat(Categorie t) throws SQLException {
        String req = "UPDATE categorie SET libCategorie = ? where idCategorie = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        
        ps.setString(1, t.getLibCat());
         ps.setInt(2, t.getId());
        ps.executeUpdate();
          System.out.println("produit " + t.getId() + " modified successfully");
    }
     @Override

    public void supprimerCat(Categorie t) throws SQLException {
       
         String req = "delete from categorie where idCategorie = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getId());
        ps.executeUpdate();
        System.out.println("event with id= " + t.getId() + "  is deleted successfully");
        
    }
    
     @Override

    public List recupererCat() throws SQLException {
             List<Categorie> Categorie = new ArrayList<>();    
       String s = "select * from categorie";
        Statement st = cnx.createStatement();
        ResultSet rs =  st.executeQuery(s);
         while(rs.next()){
             
            
           //   String libUnite = rs.getString("libUnite");
          ///      String libCategorie = rs.getString("libCategorie");
            //    data.add(new UniteCategorie(libUnite, libCategorie));
         
//             String libUnite = rs.getString("libUnite");
//                String libCategorie = rs.getString("libCategorie");
//                System.out.println(libUnite + " - " + libCategorie);
            Categorie p = new Categorie();
//            unite u = new unite();
          //    p.setId(rs.getInt("idCategorie"));
           p.setLibCat(rs.getString("libcategorie"));
            p.setId(rs.getInt("idCategorie"));
          //  u.setLibUntie(rs.getString("libUnite"));
          
           Categorie.add(p);
            
        }
        return Categorie;
    }
}
