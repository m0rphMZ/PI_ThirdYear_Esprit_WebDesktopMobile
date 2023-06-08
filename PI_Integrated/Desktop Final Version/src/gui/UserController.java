/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.User;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class UserController implements Initializable {

    @FXML
    private Text nom;
    @FXML
    private Text email;
    @FXML
    private ImageView pdp;
    @FXML
    private Button btinfos;
private User u;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Circle clip = new Circle();
clip.setCenterX(pdp.getFitWidth() / 2);
clip.setCenterY(pdp.getFitHeight() / 2);
clip.setRadius(Math.min(pdp.getFitWidth(), pdp.getFitHeight()) / 2);


pdp.setClip(clip);


pdp.fitWidthProperty().bind(clip.radiusProperty().multiply(2));
pdp.fitHeightProperty().bind(clip.radiusProperty().multiply(2));
        
        
        
        
        
        
        
    }    
    
    
    
    
   public void setUser(User p){   
   nom.setText(p.getPrenom()+" "+p.getNom());
   email.setText(p.getEmail());
  
//  ByteArrayInputStream inputStream = new ByteArrayInputStream(p.getImage());
//       Image image = new Image(inputStream);
//       pdp.setImage(image);
File imageFile = new File(p.getImage());
          Image image = new Image(imageFile.toURI().toString());
       pdp.setImage(image);
        
   
   
   
   
   u=p;
   }

    @FXML
    private void infos(ActionEvent event) throws IOException{
        
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            Parent root = loader.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
        
        EditController controller = loader.getController();
        controller.senduser(u);
        
    }
    
    
    
       
        
   
   
  
    
}
