/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class HomeSpaceController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void usersclick(MouseEvent event) {
    }

    @FXML
    private void eventsclick(MouseEvent event) {
    }

    @FXML
    private void productclick(MouseEvent event) {
    }

    @FXML
    private void locationclik(MouseEvent event) {
        loadPage("GestionLocauux");
    }
    
    private void loadPage(String page){
    Parent root=null;
    try{
    root=FXMLLoader.load(getClass().getResource(page+".fxml"));
    } 
    catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

     bp.setCenter(root);
    }
    
}
