package gui;

import entities.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import services.ReclamationService;

public class AfficherReclamationsController implements Initializable {
    
    @FXML
    private FlowPane flpRec;

    ReclamationService rs = new ReclamationService();
    
    @FXML
    private ImageView GoBackBtn;

    public int userId;
    @FXML
    private Label UsrNbr;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label NoRecFound;
     
    public void setNewUserId(int userId) {
        this.userId = userId;
        UsrNbr.setText(String.valueOf(userId));
        System.out.println("userId = " + userId);
        try {
            List<Reclamation> reclamations = rs.recupererParUtilisateur(userId);
            System.out.println("DEBUG CONSOLE MSG : nbr reclamations pour user " + userId + ": " + reclamations.size());
            flpRec.getChildren().clear(); // clear the previous reclamations from the view
            for (Reclamation reclamation : reclamations) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Reclamation.fxml"));
                AnchorPane pane = loader.load();

                ReclamationController controller = loader.getController();
                controller.SetReclamation(reclamation, reclamation.getRec_id());
                controller.setUserId(userId);
                flpRec.getChildren().add(pane);
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        } if (flpRec.getChildren().isEmpty()) {
        NoRecFound.setText("Vous n'avez créé aucune réclamation");
        NoRecFound.setVisible(true);
    } else {
        NoRecFound.setVisible(false);
    }
        
    }   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // do nothing
    }

    @FXML
    private void GoBk(MouseEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChoisirReclamationType.fxml"));
        Parent root = loader.load();

        // Set the root of the current scene to the new FXML file
        GoBackBtn.getScene().setRoot(root);
    } 

        @FXML
        private void Search(KeyEvent event) {
            String searchTerm = SearchBar.getText();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                try {
                    List<Reclamation> reclamations = rs.rechercherParMotCle(userId, searchTerm);
                    flpRec.getChildren().clear();
                    for (Reclamation reclamation : reclamations) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reclamation.fxml"));
                        AnchorPane pane = loader.load();

                        ReclamationController controller = loader.getController();
                        controller.SetReclamation(reclamation, reclamation.getRec_id());
                        controller.setUserId(userId);
                        flpRec.getChildren().add(pane);
                    }
                } catch (SQLException | IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                // Si la barre de recherche est vide, reload toutes les réclamations
                setNewUserId(userId);
    }
            if (flpRec.getChildren().isEmpty()) {
        NoRecFound.setText("Aucune réclamation trouvée avec le terme recherché");
        NoRecFound.setVisible(true);
    } else {
        NoRecFound.setVisible(false);
    }
}
            
}


