/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.zxing.qrcode.encoder.QRCode;
import com.sun.javafx.iio.ImageStorage.ImageType;
import entities.produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import entities.Categorie;
import entities.produit;

/**
 *
 * @author Monta
 */
public class ProduitService implements IProduitService<produit> {

    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public ProduitService() {
        cnx = MyDB.getInstance().getCnx();

    }

    @Override

    public void ajouter(produit e) throws SQLException {
//         String req = "INSERT INTO personne(CodeProduit,Designation`,`idUnite`,`idcat`,`image`,`QteMin`,`QteStock`,`PrixUnitaire`) VALUES("
//                + "'" + e.getCodeproduit() + "','" + e.getDesgination() + "'," + e.getIdUnite() + "','" + e.getIdCat() + "','" + e.getImage() + "','" +  e.getQteMin()) + "','" + e.getDesgination() + ")";
//        Statement st = cnx.createStatement();
//        st.executeUpdate(req);

        String requete = "INSERT INTO produit (CodeProduit,`Des`,`idUnite`,`idCat`,`image`,`QteMin`,`QteStock`,`PrixUnitaire`) "
                + "VALUES (?,?,?,?,?,?,?,?);";

        try {
            Categorie c = new Categorie();
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setString(1, e.getCodeProduit());
            pst.setString(2, e.getDesgination());
            pst.setString(3, e.getIdUnite());
            pst.setString(4, e.getIdCat());
            pst.setString(5, e.getImage());
            pst.setInt(6, e.getQteMin());
            pst.setInt(7, e.getQteStock());
            pst.setFloat(8, e.getPrixUnite());
            pst.executeUpdate();
            System.out.println("produit " + e.getCodeProduit() + " added successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override

    public void modifier(produit t) throws SQLException {
        String req = "UPDATE produit SET CodeProduit = ?,Des = ?,idUnite = ?,idcat=?,image=?,QteMin=?,QteStock= ?,PrixUnitaire =? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getCodeProduit());
        ps.setString(2, t.getDesgination());
        ps.setString(3, t.getIdUnite());
        ps.setString(4, t.getIdCat());
        ps.setString(5, t.getImage());
        ps.setInt(6, t.getQteMin());
        ps.setInt(7, t.getQteStock());
        ps.setFloat(8, t.getPrixUnite());
        ps.setInt(9, t.getId());
        ps.executeUpdate();
        System.out.println("produit " + t.getCodeProduit() + " modified successfully");
    }

    @Override
    public void supprimer(produit p) throws SQLException {

        String req = "delete from produit where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId());
        ps.executeUpdate();
        System.out.println("event with id= " + p.getId() + "  is deleted successfully");

//             PreparedStatement ps;
//
//        String query = "DELETE * FROM `produit` WHERE id = ?" + t.getId();
//
//       try {
//      
//           ps = cnx.prepareStatement(query);
//
//            ps.execute();
//        } catch (SQLException e) {
//           System.out.println(e);
//       }
    }

    @Override
    public List<produit> recuperer() throws SQLException {

        List<produit> produits = new ArrayList<>();
        String s = "select * from produit";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            produit p = new produit();

            p.setCodeProduit(rs.getString("CodeProduit"));
            p.setDesgination(rs.getString("Des"));
            p.setImage(rs.getString("image"));
            p.setQteStock(rs.getInt("QteStock"));
            p.setPrixUnite(rs.getInt("PrixUnitaire"));
            p.setIdCat(rs.getString("idCat"));
            p.setQteMin(rs.getInt("QteMin"));
            p.setIdUnite(rs.getString("idUnite"));
            p.setId(rs.getInt("id"));

            produits.add(p);

        }
        return produits;
    }

    public List<produit> trierEvent() throws SQLException {
        List<produit> produits = new ArrayList<>();
        String s = "select * from produit order by CodeProduit ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            produit p = new produit();

            p.setCodeProduit(rs.getString("CodeProduit"));
            p.setDesgination(rs.getString("Des"));
            p.setImage(rs.getString("image"));
            p.setQteStock(rs.getInt("QteStock"));
            p.setPrixUnite(rs.getInt("PrixUnitaire"));
            p.setIdCat(rs.getString("idCat"));
            p.setQteMin(rs.getInt("QteMin"));
            p.setIdUnite(rs.getString("idUnite"));
            p.setId(rs.getInt("id"));

            produits.add(p);
        }
        return produits;
    }

    public ObservableList<produit> chercherEvent(String chaine)throws SQLException {
        String sql = "SELECT * FROM produit WHERE (CodeProduit LIKE ? or idcat  LIKE ?  ) order by CodeProduit ";
        //Connection cnx= Maconnexion.getInstance().getCnx();
        String ch = "%" + chaine + "%";
        ObservableList<produit> produits = FXCollections.observableArrayList();
        try {

            Statement ste = cnx.createStatement();
            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee = cnx.prepareStatement(sql);
            stee.setString(1, ch);
            stee.setString(2, ch);

            ResultSet rs = stee.executeQuery();
            while (rs.next()) {
                produit p = new produit();

            p.setCodeProduit(rs.getString("CodeProduit"));
            p.setDesgination(rs.getString("Des"));
            p.setImage(rs.getString("image"));
            p.setQteStock(rs.getInt("QteStock"));
            p.setPrixUnite(rs.getInt("PrixUnitaire"));
            p.setIdCat(rs.getString("idCat"));
            p.setQteMin(rs.getInt("QteMin"));
            p.setIdUnite(rs.getString("idUnite"));
            p.setId(rs.getInt("id"));

            produits.add(p);
                System.out.println("event trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }
    
    public String GenerateQrEvent(produit event) throws FileNotFoundException, IOException {
    
    String eventName = "event name: " + event.getCodeProduit() + "\n" + "event date: " + event.getDesgination() + "\n" + "event description: " + event.getIdUnite() + "\n";
        ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(eventName).to(net.glxn.qrgen.image.ImageType.JPG).stream();
        String filename = event.getCodeProduit() + "_QrCode.jpg";
        //File f = new File("src\\utils\\img\\" + filename);
                File f = new File("C:\\Users\\Monta\\Documents\\NetBeansProjects\\GestionProduits\\imgQr" + filename);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(out.toByteArray());
        fos.flush();
       
        System.out.println("qr yemshi");
        return filename;
}
}
