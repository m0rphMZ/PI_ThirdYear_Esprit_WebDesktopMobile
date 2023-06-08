/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.CategorieLocal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class CategorieController implements Initializable {

    @FXML
    private Label code;
    @FXML
    private Label lib;
    @FXML
    public Button no;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
    public void setData(CategorieLocal l) {
     
        code.setText(String.valueOf(l.getCode()));
          lib.setText(l.getLibelle());
          
      
    
    
    }
    
    
}
