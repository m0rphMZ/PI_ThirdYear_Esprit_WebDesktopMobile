/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import entities.produit;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import services.ProduitService;

/**
 * FXML Controller class
 *
 * @author Monta
 */
public class AfficherProduitController implements Initializable {

    @FXML
    private GridPane gridProduit;

    
    ProduitService ev = new ProduitService();
    @FXML
    private TextField chercherProduitF;
    @FXML
    private Button Retour;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                try {
            List<produit> evenements = ev.recuperer();
            int row = 0;
            int column = 0;
            for (int i = 0; i < evenements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Produit.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                ProduitController controller = loader.getController();
                controller.setProduit(evenements.get(i));
              //  controller.setIdevent(evenements.get(i).getId_event());
                gridProduit.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }    

    @FXML
    private void rechercherProd(KeyEvent event) {
        

        
             try {
            List<produit> evenements = ev.chercherEvent(chercherProduitF.getText());
            gridProduit.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < evenements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Produit.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                ProduitController controller = loader.getController();
                controller.setProduit(evenements.get(i));
              //  controller.setIdevent(evenements.get(i).getId_event());
                gridProduit.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }

       
    }

    @FXML
    private void trierProd(ActionEvent event) {
       
             try {
            List<produit> evenements = ev.trierEvent();
            gridProduit.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < evenements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Produit.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                ProduitController controller = loader.getController();
                controller.setProduit(evenements.get(i));
              //  controller.setIdevent(evenements.get(i).getId_event());
                gridProduit.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }

        
    }

    @FXML
    private void retoue(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContainer.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            Retour.getScene().setRoot(root);
    }
    
}
