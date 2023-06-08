/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class ChangementMdpController implements Initializable {

    @FXML
    private Button valider;
    @FXML
    private PasswordField mdp1;
    @FXML
    private PasswordField mdp2;
    String email2;
    UserService us=new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     public void updateMdp(String email){
    email2=email;
    
    }
    
    @FXML
    private void Update_password(ActionEvent event) throws SQLException, IOException {
        if(mdp1.getText().equals(mdp2.getText())){
       // us.ModifMDP(email2, mdp1.getText());
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("mot de passe");
       alert.setHeaderText(null);
       alert.setContentText("votre mot de passe a été changé avec succés");
       alert.show();
       
       FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
       
        
        }
        
        else {
        
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
       alert.setTitle("mot de passe");
       alert.setHeaderText(null);
       alert.setContentText("champ vide ou les mot de passe ne sont pas identiques");
       alert.show();
        
        
        }
        
        
       
        
        
    }
    
    
    
   
    
}
