package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Theto
 */
public class ChoisirReclamationTypeController implements Initializable {

    @FXML
    private Button BtnUsrHelp;
    @FXML
    private Button BtnTcktHelp;
    @FXML
    private Button BtnEvntHelp;
    @FXML
    private Button BtnOthrHelp;
    @FXML
    private Button BtnViewOwnRec;
    
    @FXML
    private Button NotifBtn;
    @FXML
    private Label UserName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserName.setText(LoginController.UserConnected.getPrenom()); 
    }    

    @FXML
    
private void GoToRec(ActionEvent event) throws IOException {
    String fxmlFile = "";
    String reclamationType = "";

    if (event.getSource() == BtnUsrHelp) {
        fxmlFile = "AjouterReclamation.fxml";
        reclamationType = "User";
    } else if (event.getSource() == BtnTcktHelp) {
        fxmlFile = "AjouterReclamation.fxml";
        reclamationType = "Ticket";
    } else if (event.getSource() == BtnEvntHelp) {
        fxmlFile = "AjouterReclamation.fxml";
        reclamationType = "Evénement";
    } else if (event.getSource() == BtnOthrHelp) {
        fxmlFile = "AjouterReclamation.fxml";
        reclamationType = "Autre aide";
    }

    // Load the selected FXML file
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    Parent root = loader.load();

    // Get the controller of the loaded FXML file
    AjouterReclamationController controller = loader.getController();
    // Extract the user ID from the IdUser label
    int userId = LoginController.UserConnected.getId();

    // Set the user ID in the AjouterReclamationController
    controller.setUserId(userId);
    // Set the selected reclamation type
    controller.setReclamationType(reclamationType);

    // Set the loaded FXML file as the scene root
    BtnUsrHelp.getScene().setRoot(root);
}

   @FXML
    private void GoToViewRec(ActionEvent event) throws IOException {
        // Load the AfficherReclamations.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReclamations.fxml"));
        Parent root = loader.load();
        AfficherReclamationsController controller = loader.getController();
        int userId = LoginController.UserConnected.getId();
        System.out.println("User ID selected: " + userId);
        controller.setNewUserId(userId);

        // Set the loaded FXML file as the scene root
        BtnViewOwnRec.getScene().setRoot(root);
}

    @FXML
    private void ShowNotifPanel(ActionEvent event) throws IOException, SQLException {
        // Load the AfficherReclamations.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NotifPanelUser.fxml"));
        Parent root = loader.load();
        NotifPanelUserController controller = loader.getController();
        int userId = LoginController.UserConnected.getId();
        System.out.println("User ID selected: " + userId);
        controller.setNewUserId(userId);

        // Set the loaded FXML file as the scene root
        BtnViewOwnRec.getScene().setRoot(root);
    }



    
}