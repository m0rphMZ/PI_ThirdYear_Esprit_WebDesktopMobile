package gui;

import entities.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.ReclamationService;

public class AjouterReclamationController implements Initializable {

    @FXML
    private Label labelTypeReclamation;
    @FXML
    private Button BtnRecSend;
    @FXML
    private TextField TitreRec;
    @FXML
    private TextArea DescriptionRec;
    @FXML
    private Label labelTitreRec;
    @FXML
    private Label labelTitreDescription;
    
    private int userId;
    
    ReclamationService rs = new ReclamationService();
    @FXML
    private ImageView GoBackBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    // A public method to update the label text with the selected reclamation type
    void setReclamationType(String reclamationType) {
        String ReclamationTypeTXT = "Type de Reclamation : " + reclamationType;
        labelTypeReclamation.setText(ReclamationTypeTXT);
    }
        
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void reset() {
        TitreRec.setText("");
        DescriptionRec.setText("");
    }

    @FXML
    private void AjouterRec(ActionEvent event) {      
        // verification des champs
        if (TitreRec.getText().isEmpty() || DescriptionRec.getText().isEmpty()) {
            // message pop_up
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ(s) vide(s)");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs avant de continuer.");
            alert.showAndWait();
            return;
        }

        // Créer un nouvel objet Reclamation
        Reclamation r = new Reclamation();
        r.setUser_id(userId);
        r.setTitre_rec(TitreRec.getText());
        r.setDescription(DescriptionRec.getText());
        r.setDate_creation(new Date());

        // Set the selected reclamation type
        String reclamationType = labelTypeReclamation.getText().substring(21);
        r.setType(reclamationType);

        try {
            // Insérer la Réclamation dans la base de données
            rs.ajouter(r);

            // Afficher un message pop_up pour informer l'utilisateur de la réussite de l'insertion
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout de reclamation");
            alert.setHeaderText(null);
            alert.setContentText("La réclamation a été ajoutée avec succès !");
            alert.showAndWait();

            // Reset the text fields
            reset();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void GoBk(MouseEvent event) throws IOException {
        // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChoisirReclamationType.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            GoBackBtn.getScene().setRoot(root);
        
    }
}
