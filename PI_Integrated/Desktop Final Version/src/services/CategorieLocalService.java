/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Entities.CategorieLocal;
import Entities.Local;
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
 * @author Administrateur
 */
public class CategorieLocalService implements Iservice1<CategorieLocal> {
Connection cnx;
    public CategorieLocalService() {  cnx = MyDB.getInstance().getCnx();
    }
    
  
    
     
    public void ajouter(CategorieLocal c) throws SQLException{
    String req="insert into categorie_loc (codeC_loc,libelleC_loc) values(?,?)";
    PreparedStatement  ps=cnx.prepareStatement(req);
     ps.setInt(1,c.getCode());
      ps.setString(2,c.getLibelle());
    
    ps.executeUpdate();
    ps.close();
    
    }
 public void modifier(CategorieLocal c) throws SQLException{
    
     String req= "UPDATE categorie_loc SET libelleC_loc = ? where codeC_loc = ?" ;
     PreparedStatement ps = cnx.prepareStatement(req);//envoie d une requette sans parametres
        ps.setString(1,c.getLibelle());
        ps.setInt(2, c.getCode());
       
        ps.executeUpdate();
    
   ps.close();
    
    }
    public boolean supprimer(CategorieLocal c) throws SQLException{
   /*
         String req=" update location set  code_Catg=0 where code_Catg=?"; //supprimer du table fille 
       
         PreparedStatement ps = cnx.prepareStatement(req);
       
          ps.setInt(1,c.getCode());
       ps.executeUpdate();
      */  
        
    String req2=" delete from categorie_loc where codeC_loc=? ";
    PreparedStatement ps2 = cnx.prepareStatement(req2);
      ps2.setInt(1, c.getCode());
       ps2.executeUpdate();
       ps2.close();
       return true;
    
    }
    
    
    public List< CategorieLocal> recuperer() throws SQLException{
    
    List< CategorieLocal> categories=new ArrayList();
    String req ="select * from categorie_loc ";
     Statement ps = cnx.createStatement();
     ResultSet rs=ps.executeQuery(req);
     
     while(rs.next()){
          CategorieLocal c =new CategorieLocal();
          c.setCode(rs.getInt("codeC_loc"));
          c.setLibelle(rs.getString("libelleC_loc"));
          categories.add(c);
     }
             
    System.out.println( categories);
    return categories;
     
    }
    
    
    
      
    //fonction recuprer une liste dont code entrée
     public List<CategorieLocal>  recupererCode(int code) throws SQLException{
    List< CategorieLocal> categories=new ArrayList();
    String req="SELECT * FROM categorie_loc WHERE codeC_loc=?";
    PreparedStatement ps = cnx.prepareStatement(req);
       ps.setInt(1,code);
      
    ResultSet rs=ps.executeQuery();
     
     while(rs.next()){
          CategorieLocal c =new CategorieLocal();
          c.setCode(rs.getInt("codeC_loc"));
          c.setLibelle(rs.getString("libelleC_loc"));
          categories.add(c);
     }
             
    System.out.println( categories);
    return categories;
     
        
    }
    
    public List<CategorieLocal>  triCode() throws SQLException{
    List< CategorieLocal> categories=new ArrayList();
    String req="SELECT * FROM categorie_loc ORDER BY codeC_loc DESC"  ;
    PreparedStatement ps = cnx.prepareStatement(req);
       //ps.setInt(1,code);
      
    ResultSet rs=ps.executeQuery();
     
     while(rs.next()){
          CategorieLocal c =new CategorieLocal();
          c.setCode(rs.getInt("codeC_loc"));
          c.setLibelle(rs.getString("libelleC_loc"));
          categories.add(c);
     }
             
    System.out.println( categories);
    return categories;
     
        
    }
     
    
    
    
    public List<CategorieLocal>  triLibelle() throws SQLException{
    List< CategorieLocal> categories=new ArrayList();
    String req="SELECT * FROM categorie_loc ORDER BY libelleC_loc "  ;
    PreparedStatement ps = cnx.prepareStatement(req);
       //ps.setInt(1,code);
      
    ResultSet rs=ps.executeQuery();
     
     while(rs.next()){
          CategorieLocal c =new CategorieLocal();
          c.setCode(rs.getInt("codeC_loc"));
          c.setLibelle(rs.getString("libelleC_loc"));
          categories.add(c);
     }
             
    System.out.println( categories);
    return categories;
     
        
    }
     
     
     
}