/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.User;
import entities.mail;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class LoginController implements Initializable {

    @FXML
    private Button cnbt;
    @FXML
    private Button cnbt1;
    @FXML
  
    private TextField email;
    @FXML
    private PasswordField mdp;
    
    
    public static User UserConnected;
    UserService us= new UserService();
    @FXML
    private Button icibt;
    @FXML
    private ImageView logo;
    @FXML
    private Text slogan;
    @FXML
    private Text bien;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), bien);
    rotate.setByAngle(360);
    rotate.setCycleCount(3);
    rotate.play();
        
        
    
TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), slogan);
translateTransition.setFromX(-slogan.getBoundsInParent().getWidth());
translateTransition.setToX(logo.getBoundsInParent().getWidth());
translateTransition.setCycleCount(Timeline.INDEFINITE);
translateTransition.play();    
        
        
        
        
    }    

    @FXML
    private void connect(ActionEvent event) throws SQLException, IOException {
        
        if (email.getText().isEmpty()||mdp.getText().isEmpty()){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("champ vide");
                alert.setHeaderText(null);
                alert.setContentText("remplir les champs vides!");
                alert.show();
        }
        
        
        
        else if (!email.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
     Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setTitle("Email non valide");
       alert.setHeaderText(null);
       alert.setContentText("format email non valide!");
       alert.show();  }
        
        else {
         Boolean verif =false;
        List<User> users = us.recuperer();
        
        for (int i = 0; i < users.size(); i++){
        
        if (users.get(i).getEmail().equals(email.getText()) && users.get(i).getMdp().equals(mdp.getText()))
        {        
        UserConnected=users.get(i);
        verif=true;
        break;
        }   
        }
            
            
            if (verif==true){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
   
       alert.setHeaderText(null);
       alert.setContentText("Welcome"+" "+UserConnected.getNom()+" "+UserConnected.getPrenom());
       alert.show();
            
            
            
            }
            else{
            
             Alert alert = new Alert(Alert.AlertType.ERROR);
       
       alert.setHeaderText(null);
       alert.setContentText("Utilisateur inexistant!");
       alert.show();
            
            }
            
            
            if(UserConnected != null){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContainer.fxml"));
            Parent root = loader.load();


        Scene scene = new Scene(root);
        //Load CSS
            String css = this.getClass().getResource("../assets/css/app.css").toExternalForm();
            scene.getStylesheets().add(css);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("TouskieArt");
        stage.setScene(scene);
        stage.show();


            }
        }
    
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void inscrire(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("InscriptionUser.fxml"));
            Parent root = loader.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.show();
        
        
        
        
        
        
        
    }

    @FXML
    private void mdp_ob(MouseEvent event) {
    }

    @FXML
    private void passwrd(ActionEvent event) throws IOException {
        
        
        
       FXMLLoader loader = new FXMLLoader(getClass().getResource("email_check.fxml"));
            Parent root = loader.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("changement de mot de passe");
        stage.setScene(scene);
        stage.show();
        
        
    }

  

       

   


    

  
}
