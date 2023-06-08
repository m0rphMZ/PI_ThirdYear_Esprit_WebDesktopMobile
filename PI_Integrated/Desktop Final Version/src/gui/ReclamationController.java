/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Reclamation;
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

/**
 * FXML Controller class
 *
 * @author Theto
 */
public class ReclamationController implements Initializable {

    @FXML
    private Label TypRec;
    @FXML
    private Label StatusRec;
    @FXML
    private Button BtnOpnRec;
    private int rec_id;
    private int userId;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //setUserId depuis AfficherReclamationsController passé pour OpenRec() bech nraja3ha maa el goback to fetsh recs for user x
     public void setUserId(int userId) {
        this.userId = userId;
    }
    
    // Titre , type set
    public void SetReclamation(Reclamation r, int rec_id) {
    TypRec.setText(r.getType());
    StatusRec.setText(r.getStatus());
    this.rec_id = rec_id;
    System.out.println("DEBUG CONSOLE MSG Rec id = " + rec_id); //debugging
}


    @FXML
    //yloadi el page reclamation pour User mou3ayen and rec_id mou3ayen
    private void OpenRec(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichReclamOne.fxml"));
        Parent root = loader.load();
        AffichReclamOneController controller = loader.getController();
        //passage de params
        controller.AfficherReponses(rec_id);
        controller.setUserId(userId);
        BtnOpnRec.getScene().setRoot(root);
}
  
}
