/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Commande;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import services.CommandeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ashre
 */
public class AdminCommandeController implements Initializable {

    @FXML
    private ChoiceBox<String> trichoix;
    @FXML
    private FlowPane flowp;

    CommandeService CS = new CommandeService();
    @FXML
    private AnchorPane affichfinale;
    @FXML
    private ImageView backback;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        try {
            List<Commande> CM = CS.recupererCommande();
            for (Commande r : CM) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SousAdminCommande.fxml"));
                Pane commandeNode = loader.load();
                SousAdminCommandeController CMController = loader.getController();
                CMController.SetCommande(r);
                flowp.getChildren().add(commandeNode);
            }
        } catch (IOException ex) {
            System.err.println("erreur: " + ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(AdminCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        trichoix.setValue("Trié par");
        trichoix.getItems().add("Date");
        trichoix.getItems().add("UserID");
        
        trichoix.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                List<Commande> filteredCM;
                if (newValue.equals("Date")) {
                    filteredCM = CS.recupererCommandeOrderByDate();
                } else if ((newValue.equals("UserID"))){
                filteredCM = CS.recupererCommandeOrderByUserID();
                } 
                
                else {
                    filteredCM = CS.recupererCommande();
                }
                flowp.getChildren().clear();
                for (Commande r : filteredCM) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("SousAdminCommande.fxml"));
                    Pane commandeNode = loader.load();
                    SousAdminCommandeController CMController = loader.getController();
                    CMController.SetCommande(r);
                    flowp.getChildren().add(commandeNode);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminCommandeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AdminCommandeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void backback(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContainer.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            backback.getScene().setRoot(root);
        
        
    }

}





