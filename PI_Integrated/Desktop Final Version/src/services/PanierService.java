/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Panier;
import entities.produit;
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
 * @author ashre
 */
public class PanierService  {

    Connection cnx;
    public PanierService() {
        cnx = MyDB.getInstance().getCnx();
    }

//    public void ajouterPanier(Panier p) throws SQLException  {
//
//        
//            String req = "INSERT INTO panier (id_produit,id_user,quantite) VALUES (?,?,?)";
//            PreparedStatement pst = cnx.prepareStatement(req);
//            /*pst.setInt(1,t.getIdp());
//            pst.setInt(2, t.getCategory_id());*/
//            pst.setInt(1, p.getProduit_id());
//            pst.setInt(2, p.getId_user());
//            pst.setInt(3, p.getQuantite());
//
//            pst.executeUpdate();
//            
//    ResultSet generatedKeys = pst.getGeneratedKeys();
//    if (generatedKeys.next()) {
//        int id = generatedKeys.getInt(1);
//        System.out.println(id);
//        } 
//
//    }
//    
    
    public void ajouterPanier(Panier p) throws SQLException {
    
    String req = "INSERT INTO panier (id_produit,id_user,quantite,id_panier) VALUES (?,?,?,?)";
    PreparedStatement pst = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);

    pst.setInt(1, p.getProduit_id());
    pst.setInt(2, p.getId_user());
    pst.setInt(3, p.getQuantite());
    pst.setInt(4,p.getId_panier());

    pst.executeUpdate();

    ResultSet generatedKeys = pst.getGeneratedKeys();
    if (generatedKeys.next()) {
        int id = generatedKeys.getInt(1);
        System.out.println(id);
    }

}

  
    public void recupererlastPanier(int id_user) {
    try {
        String req = "SELECT MAX(id_panier) FROM panier WHERE id_user = ? ;" ;
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, id_user);
        ResultSet rs = pst.executeQuery();
        
        // Traitez le résultat de la requête ici
        
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

    
    
 public void supprimerProduitParId(int id_produit) {
        try {
            
            String req = "DELETE FROM panier WHERE id_produit=? " ;
            PreparedStatement pst = cnx.prepareStatement(req);

            
            pst.setInt(1, id_produit);

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

 public List<produit> afficherPanier(int id_user) {
        List<produit> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM panier where id_user=" + id_user;
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                produit p;
                p = this.recupererProduitParId(rs.getInt(1));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }



public produit recupererProduitParId(int id_produit) {
        produit p = new produit();

        try {
            Statement st = cnx.createStatement();
            String query = "select * FROM produit WHERE id=" + id_produit ;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                p.setId(rs.getInt(1));
                p.setCodeProduit(rs.getString(2));
                p.setDesgination(rs.getString(3));
                p.setQteStock(rs.getInt(4));
                p.setQteMin(rs.getInt(5));
                p.setPrixUnite(rs.getInt(6));
                p.setIdUnite(rs.getString(7));
                p.setImage(rs.getString(8));
                p.setIdCat(rs.getString(9));
            }

        } catch (SQLException ex) {
            System.out.println("erreur get IdOBJ pour suivi");
            System.out.println(ex);
        }

        return p;

    }


 public Boolean productExist(Panier p, int id_user) {
        System.out.println(p.getId_produit()+"user:"+id_user);

        try {
            Statement st = cnx.createStatement();
            String query = "SELECT * FROM panier WHERE id_produit="+p.getId_produit()+" and id_user="+id_user ;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

              return true;

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return false;

    }
    
 
 public List<Integer> recuperer() throws SQLException {
        List<Integer> p = new ArrayList<>(); 
        String req="SELECT id_panier FROM panier ORDER BY id_panier";
   Statement st = cnx.createStatement();
   ResultSet rs =  st.executeQuery(req);
      while(rs.next()){
   
  int c= rs.getInt("id_panier");
       p.add(c);
      }
 
   return p;
    }
 
 

    public void supprimerPanier(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



}