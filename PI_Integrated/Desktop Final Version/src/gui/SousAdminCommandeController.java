/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Commande;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.xdevapi.Statement;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author ashre
 */
public class SousAdminCommandeController implements Initializable {

    private Label nom;
    @FXML
    private Label commande_id;
    @FXML
    private Label user_id;
    @FXML
    private Label commande_date;
    @FXML
    private Button imprimer;
    @FXML
    private Label panier_id;
    @FXML
    private Label numft;
    @FXML
    private Label prixtotft;
    @FXML
    private Label nomft;
    @FXML
    private Label prenomft;
    @FXML
    private Label rueft;
    @FXML
    private Label villeft;
    @FXML
    private Label codeposft;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void SetCommande(Commande r) {
        
       user_id.setText(Integer.toString(r.getUser_id()) );
       commande_id.setText(Integer.toString(r.getCommande_id()));
       commande_date.setText(r.getDate_commande());
       panier_id.setText(Integer.toString(r.getId_panier()));
       numft.setText(r.getTel());
       nomft.setText(r.getNom());
       prenomft.setText(r.getPrenom());
       rueft.setText(r.getRue());
       villeft.setText(r.getVille());
       codeposft.setText(r.getCode_postal());
       prixtotft.setText(Integer.toString(r.getPrix()));
    }

    @FXML
    
        private void imprimerFacture(MouseEvent event) throws SQLException {
            Document document = new Document();
    
    try {
        // Utilisez PdfWriter pour écrire dans un fichier PDF
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\ashre\\OneDrive\\Desktop\\PidevDesk\\src\\gui\\factures\\commande.pdf"));
        
        // Ouvrez le document
        document.open();
        
        // Récupérez les valeurs des différents éléments de l'interface JavaFX
        String commandeID = commande_id.getText();
        String userID = user_id.getText();
        String panierID = panier_id.getText();
        String dateCommande = commande_date.getText();
        String rue = rueft.getText();
        String ville = villeft.getText();
        String nom = nomft.getText();
        String prenom = prenomft.getText();
        String num = numft.getText();
        String codepos = codeposft.getText();
        String prix = prixtotft.getText();
        
       
        
        
        // Écrivez ces valeurs dans le fichier PDF
        document.add(new Paragraph("Commande ID : " + commandeID));
        document.add(new Paragraph("User ID : " + userID));
        document.add(new Paragraph("Panier ID : " + panierID));
        document.add(new Paragraph("Prix Total : " + prix));
        document.add(new Paragraph("Nom : " + nom));
        document.add(new Paragraph("Prénom : " + prenom));
        document.add(new Paragraph("Rue : " + rue));
        document.add(new Paragraph("Ville : " + ville));
        document.add(new Paragraph("Code postale : " + codepos));
        document.add(new Paragraph("Numéro de téléphone : " + num));
        document.add(new Paragraph("Date Commande : " + dateCommande));
        
        // Fermez le document
        document.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
        }
}