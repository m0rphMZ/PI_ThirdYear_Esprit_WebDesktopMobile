/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.Local;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class LocalController implements Initializable {

    @FXML
    private ImageView imgv;
    @FXML
    private Label desc;
    @FXML
    public Label lieu;
    @FXML
    private Label surf;
    @FXML
    private Label nbper;
    @FXML
    public Button louer;
    @FXML
    public Label numloc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    
    
    public void setData(Local l) {
          numloc.setText(String.valueOf(l.getNum()));
           desc.setText(String.valueOf(l.getDescript()));
           lieu.setText(l.getLieu());
           surf.setText(String.valueOf(l.getSurface()));
           nbper.setText(String.valueOf(l.getNbper()));
           Image image = new Image(new ByteArrayInputStream(l.getImage()));
           imgv.setImage(image);
        
    
    }

    @FXML
    private void louer(ActionEvent event) {
    }
    
}
