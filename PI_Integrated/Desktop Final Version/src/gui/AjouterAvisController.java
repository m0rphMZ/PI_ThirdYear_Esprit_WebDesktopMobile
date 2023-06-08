/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entites.Avis;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.AvisService;

/**
 * FXML Controller class
 *
 * @author Monta
 */
public class AjouterAvisController implements Initializable {

    @FXML
    private TextField idEvField;
    @FXML
    private TextArea contenuField;
    
    AvisService AS=new AvisService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterAvis(ActionEvent event) {
        
           Avis a = new Avis();
        a.setContenu(contenuField.getText());
        a.setIdProd(Integer.valueOf(idEvField.getText()));
         
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("avis add");
            alert.setContentText("avis added successfully!");
            alert.showAndWait();      
        try {
            AS.ajouterAvis(a);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }      
    }
    
     public void setIdevent(int idevent){
        idEvField.setText(String.valueOf(idevent));
    }
    
}
