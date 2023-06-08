/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.User;
import static gui.LoginController.UserConnected;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class UpdateController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField Tel;
    @FXML
    private TextField mdp;
    @FXML
    private ChoiceBox<String> role;
    @FXML
    private Button modb;
    @FXML
    private Button annb;
private User user;
private User user_test;
UserService us = new UserService();
    @FXML
    private ImageView pdp;
    @FXML
    private Button upbtn;
    private String imageData;
    @FXML
    private ImageView backbt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
         role.getItems().add("Artiste");
        role.getItems().add("simple utilisateur");
        role.getSelectionModel().select("Artiste");
        
        
    }    

    
    public void senduser(User p){
    user_test=p;
    user=p;
//    ByteArrayInputStream inputStream = new ByteArrayInputStream(user.getImage());
//       Image image = new Image(inputStream);

File imageFile = new File(user.getImage());
          Image image = new Image(imageFile.toURI().toString());
       pdp.setImage(image);
        
    
    }
    
    
    
    
    @FXML
    private void modifier(ActionEvent event) throws SQLException , NumberFormatException, IOException {
        
        
                
                
        if (!email.getText().isEmpty()) {
    user.setEmail(email.getText());
} 

if (!nom.getText().isEmpty()) {
    user.setNom(nom.getText());
} 

if (!prenom.getText().isEmpty()) {
    user.setPrenom(prenom.getText());
}

if (!Tel.getText().isEmpty()) {
    
        user.setTel(Integer.parseInt(Tel.getText()));
    

}

if (!mdp.getText().isEmpty()) {
    user.setMdp(mdp.getText());
}

if (role.getValue() != null) {
    user.setRole(role.getValue());
} 

if (imageData != null ) {
user.setImage(imageData);
        
}
        us.modifier(user);
        
      
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
       
       alert.setHeaderText(null);
       alert.setContentText("Modification réussie !!");
       alert.show();  
       
       FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            Parent root = loader.load();
        EditController controller = loader.getController();
        controller.senduser(user);
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit");
        stage.setScene(scene);
        stage.show();
               
      
        
        
        
    }

    @FXML
    private void reset(ActionEvent event) {
    }


    @FXML
    private void uploadImgBtn(ActionEvent event) {
        
        
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            //imageData = Files.readAllBytes(selectedFile.toPath());
            imageData=selectedFile.getPath();
        }
        
        
        
        
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            Parent root = loader.load();
        EditController controller = loader.getController();
        controller.senduser(user);
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit");
        stage.setScene(scene);
        stage.show();
        
    }

    
    
}
