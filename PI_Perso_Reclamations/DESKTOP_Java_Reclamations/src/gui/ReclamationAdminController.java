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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Theto
 */
public class ReclamationAdminController implements Initializable {

    @FXML
    private Label TypRecAdmin;
    @FXML
    private Label StatusRecAdmin;
    @FXML
    private Button BtnOpnRecAdmin;
    @FXML
    private Label DateRecAdmin;
    private int rec_id;
    private Reclamation reclamation;
    @FXML
    private Label DateCreationOrClosure;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OpenRecAdmin(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReclamOneAdmin.fxml"));
            Parent root = loader.load();
            AfficherReclamOneAdminController controller = loader.getController();
            controller.SetReclamation(this.rec_id, this.reclamation);

            BtnOpnRecAdmin.getScene().setRoot(root);
        } catch (IOException e)
        {
            System.out.println(e.toString());
    }
}


    public void SetReclamation(Reclamation r, int rec_id) {
    TypRecAdmin.setText(r.getType());
    StatusRecAdmin.setText(r.getStatus());
    this.rec_id = rec_id;
    this.reclamation = r; 
    System.out.println("Rec id = " + rec_id); //debugging
    System.out.println(r.toString()); //
    if (r.getStatus() == "Fermé") {
    DateCreationOrClosure.setText("Date de Fermeture");
    DateRecAdmin.setText(r.getDate_fin().toString());
    } else {
    DateRecAdmin.setText(r.getDate_creation().toString());
    }
}
    
}
