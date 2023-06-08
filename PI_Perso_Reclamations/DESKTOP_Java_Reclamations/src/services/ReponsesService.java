 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Reponses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;
/**
 *
 * @author Theto
 */
public class ReponsesService implements RepInterface<Reponses>{
    
    Connection cnx;
    PreparedStatement pst;
    
    public ReponsesService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    //ajouter nouvelle reponse
    public void ajouter(Reponses rep) throws SQLException {
            String query = "INSERT INTO reponses (rec_id, user_id, admin_id, rep_description, `date_rep`) VALUES (?, ?, ?, ?, ?)";
            pst = cnx.prepareStatement(query);
            pst.setInt(1, rep.getRec_id());
            pst.setInt(2, rep.getUser_id());
            pst.setInt(3, rep.getAdmin_id());
            pst.setString(4, rep.getRep_desc());
            pst.setDate(5, new java.sql.Date(rep.getDate_rep().getTime()));
            pst.executeUpdate();
        
    }

    
    @Override
    //recuperer reponse par ID de reclamation
    public List<Reponses> recupererParRecId(int rec_id) throws SQLException {
            List<Reponses> reponsesList = new ArrayList<>();
            String query = "SELECT * FROM reponses WHERE rec_id = ?";
            pst = cnx.prepareStatement(query);
            pst.setInt(1, rec_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            Reponses rep = new Reponses();
            rep.setRep_id(rs.getInt("rep_id"));
            rep.setRec_id(rs.getInt("rec_id"));
            rep.setUser_id(rs.getInt("user_id"));
            rep.setAdmin_id(rs.getInt("admin_id"));
            rep.setRep_desc(rs.getString("rep_description"));
            rep.setDate_rep(rs.getDate("date_rep"));
            reponsesList.add(rep);
    }
    return reponsesList;
}

    @Override
    //suprimer reponse lieé a une reclamation donne (rec_id)
    public void supprimerParRecId(int rec_id) throws SQLException {
            String query = "DELETE FROM reponses WHERE rec_id=?";
            pst = cnx.prepareStatement(query);
            pst.setInt(1, rec_id);
            pst.executeUpdate();    }
 
}
