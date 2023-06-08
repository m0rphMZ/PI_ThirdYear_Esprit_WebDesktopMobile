/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import services.ReclamationService;

/**
 * FXML Controller class
 *
 * @author Theto
 */

    
public class AdminRecPanelController implements Initializable {
    
    

    @FXML
    //fxid FlowPane
    private FlowPane AllRecFlow;
    
    //initiation Service Reclamation
    private ReclamationService rs = new ReclamationService();
    
    @FXML
    private ComboBox<String> ChooseStts;
    @FXML
    private ImageView StatsBtn;
    @FXML
    private ImageView GobackAdmin;

    /**
     * Initializes the controller class.
     */
    
    @Override
public void initialize(URL url, ResourceBundle rb) {
    try {
        // Créer une Observable-List avec les valeurs souhaitées
        ObservableList<String> statusList = FXCollections.observableArrayList("Ouvert", "Fermé");
        // Définissez les éléments du ComboBox sur la OBservableList
        ChooseStts.setItems(statusList);

        // Recuperer toutes les reclam
        List<Reclamation> reclamations = rs.recuperer();
        //Créez une nouvelle instance de ReclamationController pour chaque récupération et ajoutez-la au FlowPane
        for (Reclamation r : reclamations) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationAdmin.fxml"));
            Pane reclamationNode = loader.load();
            // Récupérer le contrôleur ReclamationController
            ReclamationAdminController recAdController = loader.getController();


            // Définir les données de la réclamation, recid, admin_id dans le contrôleur
            recAdController.SetReclamation(r, r.getRec_id());

            // Ajouter le nœud de réclamation à FlowPane
            AllRecFlow.getChildren().add(reclamationNode);
        }

        // action listener sur ChooseStts combo box
        ChooseStts.setOnAction((event) -> {
            try {
                // get status selectionner
                String selectedStatus = ChooseStts.getSelectionModel().getSelectedItem();
                // Retrieve the reclamations with the selected status
                List<Reclamation> filteredReclamations = rs.recupererByStatus(selectedStatus);
                // Clear the FlowPane
                AllRecFlow.getChildren().clear();
                // Add the filtered reclamations to the FlowPane
                for (Reclamation r : filteredReclamations) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationAdmin.fxml"));
                    Pane reclamationNode = loader.load();
                    // Récupérer le contrôleur ReclamationController
                    ReclamationAdminController recAdController = loader.getController();

                    // Définir les données de la réclamation, recid, admin_id dans le contrôleur
                    recAdController.SetReclamation(r, r.getRec_id());

                    // Ajouter le nœud de réclamation à FlowPane
                    AllRecFlow.getChildren().add(reclamationNode);
                }
            } catch (IOException ex) {
                System.err.println("erreur: " + ex.getMessage());

            } catch (SQLException ex) {
                System.err.println("erreur BD: " + ex.getMessage());
            }
        });
    } catch (IOException ex) {
        System.err.println("erreur: " + ex.getMessage());

    } catch (SQLException ex) {
        System.err.println("erreur BD: " + ex.getMessage());
    }
}

    @FXML
    private void GoToStatistics(MouseEvent event) throws IOException {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticsAdmin.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            StatsBtn.getScene().setRoot(root);
    }

    @FXML
    private void GoBackAdmin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContainer.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            GobackAdmin.getScene().setRoot(root);
    }

    
}
