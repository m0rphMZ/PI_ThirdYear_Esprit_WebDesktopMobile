/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Reclamation;
import entities.Reponses;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.ReclamationService;
import services.ReponsesService;

/**
 * FXML Controller class
 *
 * @author Theto
 */
public class AffichReclamOneController implements Initializable {

    @FXML
    private Label TitreRec;
    @FXML
    private Label TypeRec;
    @FXML
    private Label DescRec;
    @FXML
    private Label DateCreation;
    @FXML
    private Label StatusRec;

    private ReclamationService rs = new ReclamationService();
    private ReponsesService Reps = new ReponsesService();

    private int rec_id;
    @FXML
    private TextArea RepText;
    @FXML
    private ImageView GoBckBtn;
    private int userId;

    @FXML
    private Button RemRec;
    @FXML
    private Button AddRep1;
    @FXML
    private VBox DescRep;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    //setUserId mab3outha men aand ReclamationController
    public void setUserId(int userId) {
    this.userId = userId;
}
   
    

    @FXML
    public void AfficherReponses(int rec_id) throws SQLException, IOException {
    this.rec_id = rec_id;

    // Get the reclamation with the specified ID from the database
    Reclamation r = rs.recupererParId(rec_id);
    List<Reponses> reponses = Reps.recupererParRecId(rec_id);

    // Set the labels to display the reclamation data
    DescRep.getChildren().clear();
    int numRep = 1;
    for (Reponses rep : reponses) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reponse.fxml"));
        AnchorPane reponseNode = loader.load();
        ReponseController reponseController = loader.getController();
        reponseController.setData(rep);
        DescRep.getChildren().add(reponseNode);
        numRep++;
    }
    TypeRec.setText("Type de Reclamation: " + r.getType());
    TitreRec.setText(r.getTitre_rec());
    DescRec.setText(r.getDescription());
    DateCreation.setText(r.getDate_creation().toString());
    StatusRec.setText("Status: " + r.getStatus());
    
    // Check the status of the reclamation and hide the buttons if the status is "Closed"
    if (r.getStatus().equals("Fermé")) {
    AddRep1.setDisable(true);
    RepText.setDisable(true);
    }
}






@FXML
private void AjoutRep(ActionEvent event) throws SQLException, IOException {
    // Get the response description from the text area
    String repDesc = RepText.getText();

    // Check that the response description is not empty
    if (repDesc.trim().isEmpty()) {
        // Show a popup error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer une réponse.");
        alert.showAndWait();
        return;
    }

    // Create a new Reponses object with the rec_id, user_id, and response description
    Reponses rep = new Reponses();
    rep.setRec_id(rec_id);
    rep.setUser_id(userId);
    rep.setRep_desc(repDesc);
    rep.setDate_rep(new Date());

    // Call the ajouter method of ReponsesService and pass the new Reponses object to it
    Reps.ajouter(rep);

    // Show a popup message to notify the user that the reply has been added
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Succès");
    alert.setHeaderText(null);
    alert.setContentText("Votre réponse a été ajoutée.");
    alert.showAndWait();

    // Clear the reply text area
    RepText.clear();
    // Refresh the response page with the newly added response
    AfficherReponses(rec_id);

}



    @FXML
private void GoBck(MouseEvent event) throws IOException {
    // Load the new FXML file
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReclamations.fxml"));
    Parent root = loader.load();

    // Set the root of the current scene to the new FXML file
    AfficherReclamationsController controller = loader.getController();
    controller.setNewUserId(userId);

    GoBckBtn.getScene().setRoot(root);
}

    @FXML
    private void RemoveRec(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cette réclamation ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                
                Reps.supprimerParRecId(rec_id);
                rs.supprimerParRecId(rec_id);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReclamations.fxml"));
                Parent root = loader.load();
                AfficherReclamationsController controller = loader.getController();
                controller.setNewUserId(userId);
                RemRec.getScene().setRoot(root);
                
                
            } catch (SQLException ex) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText(null);
                alertError.setContentText("Une erreur s'est produite lors de la suppression de la récupération.");
                alertError.showAndWait();
            } catch (IOException ex) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText(null);
                alertError.setContentText("Une erreur s'est produite lors du chargement de la liste des réclamations.");
                alertError.showAndWait();
            }
        }
}


}
