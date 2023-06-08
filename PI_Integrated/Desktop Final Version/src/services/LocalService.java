/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Entities.Local;
import java.io.ByteArrayInputStream;
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
public class LocalService{
    
     Connection cnx;

    public LocalService() {
        
      cnx = MyDB.getInstance().getCnx();
    }
     
   
    
    public void ajouter(Local l) throws SQLException{
    
     String req = "INSERT INTO location(num_loc,descipt_loc,lieu_loc,surface_loc,nb_pers_loc,code_catg,image) VALUES(?,?,?,?,?,?,?)";
              
        
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, l.getNum());
        ps.setString(2,l.getDescript());
        ps.setString(3, l.getLieu());
        ps.setFloat(4, l.getSurface());
         ps.setInt(5, l.getNbper());
         ps.setInt(6, l.getCodec());
      ps.setBinaryStream(7, new ByteArrayInputStream(l.getImage()), (l.getImage()).length);
        ps.executeUpdate();
       // System.out.println("local "+ l. getNum()+"est ajouté");
ps.close();
       
       
    }
    public void modifier(Local l) throws SQLException{
    String req= "UPDATE location SET descipt_loc = ?,lieu_loc = ?,surface_loc = ?,nb_pers_loc = ?,code_catg = ?,image = ? where num_loc = ?" ;
     PreparedStatement ps = cnx.prepareStatement(req);//envoie d une requette sans parametres
        ps.setString(1,l.getDescript());
        ps.setString(2, l.getLieu());
        ps.setFloat(3, l.getSurface());
         ps.setInt(4, l.getNbper());
         ps.setInt(5, l.getCodec());
        ps.setBinaryStream(6,  new ByteArrayInputStream(l.getImage()), (l.getImage()).length);
          ps.setInt(7, l.getNum());
        ps.executeUpdate();
    
   ps.close();

    
    }
    public boolean supprimer(Local l) throws SQLException{
    
    String req=" delete from location where num_loc=? ";
    PreparedStatement ps = cnx.prepareStatement(req);
     ps.setInt(1, l.getNum());
       ps.executeUpdate();
       ps.close();
       return true;
    
    }
   
    
    public List<Local>  recuperer() throws SQLException{
    
    
    List<Local> personnes = new ArrayList<>();
        String req = "select * from location";
        Statement cs = cnx.createStatement();  
        ResultSet rs =  cs.executeQuery(req); 
        while(rs.next()){
            Local p = new Local();
            p.setNum(rs.getInt("num_loc"));
            p.setDescript(rs.getString("descipt_loc"));
            p.setLieu(rs.getString("lieu_loc"));
            p.setSurface(rs.getFloat("surface_loc"));
            p.setNbper(rs.getInt("nb_pers_loc"));
             p.setCodec(rs.getInt("code_catg"));
             
             
             byte[] ImageBytes = rs.getBlob("image").getBytes(1l, (int)rs.getBlob("image").length());
              p.setImage(ImageBytes);
             
                   //ps.setBinaryStream(7, new ByteArrayInputStream(l.getImage()), (l.getImage()).length);

             
            
            personnes.add(p);
                  


        }
         System.out.println( personnes);
         return personnes;
        //return personnes;
         
       
        
    }

   public String getLieu(int id) throws SQLException {
      String req = "SELECT lieu_loc FROM location WHERE num_loc = ?";
      PreparedStatement st = cnx.prepareStatement(req);
      st.setInt(1, id);
      ResultSet rs = st.executeQuery();
      String lieu = "";
      if (rs.next()) {
        lieu = rs.getString("lieu_loc");
      }
      return lieu;
    }
    
   
    
}