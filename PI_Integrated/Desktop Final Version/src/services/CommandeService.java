/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entities.Commande;
import entities.SMSSender;
import static entities.SMSSender.ACCOUNT_SID;
import static entities.SMSSender.AUTH_TOKEN;
import entities.produit;
import gui.SousProduitController;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.L;
import utils.MyDB;

/**
 *
 * @author ashre
 */
public class CommandeService  {
    Connection cnx ; 
    
    public CommandeService() { 
       cnx = MyDB.getInstance().getCnx(); 
    }
    
    
    
    public void ajouterCommande(Commande c , int id_user) throws SQLException {
        int i;
       PanierService ps = new PanierService() ; 
        String req1 = "INSERT INTO commande (id_user, date_commande, rue, ville, code_postal, tel, nom, prenom,id_panier) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";


try (PreparedStatement ps1 = cnx.prepareStatement(req1)) {
    ps1.setInt(1, id_user);
    
    
    // Obtenir le timestamp actuel
    long timestamp = new Date().getTime();
    
    // Convertir le timestamp en une chaîne de caractères dans le format souhaité
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateCommande = dateFormat.format(new Date(timestamp));
    
    ps1.setString(2, dateCommande);
    ps1.setString(3, c.getRue());
    ps1.setString(4, c.getVille());
    ps1.setString(5, c.getCode_postal());
    ps1.setString(6, c.getTel());
    ps1.setString(7, c.getNom());
    ps1.setString(8, c.getPrenom());
           List<Integer> l=ps.recuperer();
            i = l.get(l.size()-1);  
    ps1.setInt(9, i);
    ps1.executeUpdate();
}
 
}
    
   public List<Commande> recupererCommande() throws SQLException {
    List<Commande> CM = new ArrayList<>();
    String query = "SELECT * FROM commande";
    PreparedStatement pst = cnx.prepareStatement(query);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
        int commande_id = rs.getInt(1); // 1 represents the first column
        int id_user = rs.getInt("id_user");
        String date_commande = rs.getString("date_commande");
        String rue = rs.getString("rue");
        String ville = rs.getString("ville");
        String code_postal = rs.getString("code_postal");
        String tel = rs.getString("tel");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        int panier_id = rs.getInt(10);
        Commande c = new Commande(commande_id,id_user, date_commande, rue,ville,code_postal, tel, nom,prenom,panier_id);
        CM.add(c);
    }
    return CM;
}
   
   
   
   
   public List<Commande> recupererCommandeOrderByDate() throws SQLException {
List<Commande> CM = new ArrayList<>();
String query = "SELECT * FROM commande ORDER BY date_commande";
PreparedStatement pst = cnx.prepareStatement(query);
ResultSet rs = pst.executeQuery();
while (rs.next()) {
int commande_id = rs.getInt(1); // 1 represents the first column
int id_user = rs.getInt("id_user");
String date_commande = rs.getString("date_commande");
String rue = rs.getString("rue");
String ville = rs.getString("ville");
String code_postal = rs.getString("code_postal");
String tel = rs.getString("tel");
String nom = rs.getString("nom");
String prenom = rs.getString("prenom");
int panier_id = rs.getInt(10);
Commande c = new Commande(commande_id,id_user, date_commande, rue,ville,code_postal, tel, nom,prenom,panier_id);
CM.add(c);
}
return CM;
}
   
   
      public List<Commande> recupererCommandeOrderByUserID() throws SQLException {
List<Commande> CM = new ArrayList<>();
String query = "SELECT * FROM commande ORDER BY id_user";
PreparedStatement pst = cnx.prepareStatement(query);
ResultSet rs = pst.executeQuery();
while (rs.next()) {
int commande_id = rs.getInt(1); // 1 represents the first column
int id_user = rs.getInt("id_user");
String date_commande = rs.getString("date_commande");
String rue = rs.getString("rue");
String ville = rs.getString("ville");
String code_postal = rs.getString("code_postal");
String tel = rs.getString("tel");
String nom = rs.getString("nom");
String prenom = rs.getString("prenom");
int panier_id = rs.getInt(10);
Commande c = new Commande(commande_id,id_user, date_commande, rue,ville,code_postal, tel, nom,prenom,panier_id);
CM.add(c);
}
return CM;
}
      
      

    
    public void supprime(int id_user) throws SQLException {
        try {
            
            String req = "DELETE FROM commande WHERE id_user=? " ;
            PreparedStatement pst = cnx.prepareStatement(req);

            
            pst.setInt(1, id_user);

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }      
      
//    public void SMSSender(int id_user) {
//    // Remplacez les informations de compte et de numéro de téléphone par les vôtres
//    String accountSid = "ACd9f8163e8cb8fa99b07dfb9ccfc8a1f4";
//    String authToken = "70143dfba0b45b403f1bc05d8e941aca";
//    
//    try {
//        // Récupérer le numéro de téléphone de l'utilisateur à partir de la base de données
//        String clientPhoneNumber = recupererUserPhone(id_user);
//        
//        Twilio.init(accountSid, authToken);
//        Message message = Message.creator(
//            new PhoneNumber(clientPhoneNumber),
//            new PhoneNumber("+15674122488"),
//            "Votre commande a été bien reçue"
//        ).create();
//        
//        System.out.println("SID du message : " + message.getSid());
//    } catch (Exception ex) {
//        System.out.println("Erreur : " + ex.getMessage());
//    }
//}
//
public String recupererUserPhone(int id_user) throws SQLException {
    try {
        String req = "SELECT tel FROM commande WHERE id_user=? " ;
        PreparedStatement pst = cnx.prepareStatement(req);

        pst.setInt(1, id_user);
        ResultSet rs = pst.executeQuery();
        
        // Si le numéro de téléphone existe, renvoyer sa valeur
        if (rs.next()) {
            return rs.getString("tel");
        } else {
            throw new SQLException("Numéro de téléphone introuvable pour l'utilisateur avec l'ID " + id_user);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération du numéro de téléphone de l'utilisateur : " + ex.getMessage());
        throw ex;
    }
}

}
    
      


