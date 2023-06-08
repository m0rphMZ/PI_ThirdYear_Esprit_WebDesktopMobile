/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Commande;
import entities.produit;
import entities.Panier;
import entities.SMSSender;
import java.sql.SQLException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.CommandeService;
import services.PanierService;
//import services.ProduitService;

/**
 * FXML Controller class
 *
 * @author ashre
 */
public class PanierController implements Initializable {

    
    PanierService PS = new PanierService() ;
    CommandeService CS = new CommandeService();
    
    @FXML
    private FlowPane flowp;
    @FXML
    private Button viderPanier;
    @FXML
    private TextField numtelft;
    @FXML
    private TextField nomft;
    @FXML
    private TextField prenomft;
    @FXML
    private TextField rueft;
    @FXML
    private TextField codeposft;
    @FXML
    private TextField villeft;
    @FXML
    private Button enrecomft;
    @FXML
    private Button passCommande;
    private Label totals;
    @FXML
    private Label totpanier;
    @FXML
    private Label totals1;
    private double pp;
    private double cc;
    private double sostot;
    private double sostotValue;
       static double totalpanier;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
    List<produit> produits = new ArrayList<>();
//    produits.add(PS.recupererProduitParId(1));
    

flowp.setHgap(10.0);

// Set the preferred wrap length to a large value to avoid wrapping
flowp.setPrefWrapLength(Double.MAX_VALUE);

// Iterate over the produits and add them to the FlowPane
for (produit produit : produits) {
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("SousProduit.fxml"));
    AnchorPane pane = loader.load();
    
    SousProduitController controller = loader.getController();
    controller.setProd(produit);
    flowp.getChildren().add(pane);
   
    
}
} catch (IOException ex) {
    System.out.println("Erreur d'entrée/sortie : " + ex.getMessage());
}
    }    
    
    @FXML
    private void supPanier(ActionEvent event) {
        flowp.getChildren().clear(); //supprimer les produits de l'affichage
        PS.supprimerPanier(4);
    }

    @FXML
    private void insereCommande(ActionEvent event) {
        String prenom=prenomft.getText();
        String nom=nomft.getText();
        String tel=numtelft.getText();
        String rue=rueft.getText();
        String ville=villeft.getText();
        String codepostal=codeposft.getText();
        
        
        if (!prenom.matches("[a-zA-Z]+")) {
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("Information Dialog");
       alert.setHeaderText(null);
       alert.setContentText("format prenom non valide!");
       alert.show();  }
        
       else if (!nom.matches("[a-zA-Z ]+")) {
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("Information Dialog");
       alert.setHeaderText(null);
       alert.setContentText("format nom non valide!");
       alert.show();  }
       
       else if (!tel.matches("\\d{8}")) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Format numéro de téléphone non valide !");
    alert.show();
}
       
       else if (!rue.matches("[a-zA-Z0-9 ]+")) {
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("Information Dialog");
       alert.setHeaderText(null);
       alert.setContentText("format rue non valide!");
       alert.show();  
       }
        
       else if (!ville.matches("[a-zA-Z]+")) {
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("Information Dialog");
       alert.setHeaderText(null);
       alert.setContentText("format ville non valide!");
       alert.show();  
       }
        
        else if (!codepostal.matches("[0-9]+")) {
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("Information Dialog");
       alert.setHeaderText(null);
       alert.setContentText("format Code Postal non valide!");
       alert.show();  
        }
        
        else  { 
           try {
        Commande p = new Commande(); 
        p.setPrenom(prenomft.getText());
        p.setNom(nomft.getText());
        p.setTel(numtelft.getText());
        p.setRue(rueft.getText());
        p.setVille(villeft.getText());
        p.setCode_postal(codeposft.getText());
        p.setUser_id(4);
        CS.ajouterCommande(p,4);
         
        }
        
        catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
        
       }
    }

    @FXML
    private void passerCommande(ActionEvent event) throws SQLException {
        try {
        // Charger la nouvelle vue depuis le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalisationCommande.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        // Récupérer la scène actuelle à partir de l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Définir la nouvelle scène et l'afficher
        stage.setScene(scene);
        stage.show();
        SMSSender SS = new SMSSender() ; 
        SS.SMSSender(numtelft.getText());
            System.out.println(SS);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void setSostot(double i) {
        
     totpanier.setText(String.valueOf(i));
}

}
    
    

    


