/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Theto
 */
public class NotificationController implements Initializable {

    @FXML
    private Label recId;
    @FXML
    private Label RepNbr;
    @FXML
    private Label RecType;

    @FXML
    private Button RmvNotif;
    
    private NotifPanelUserController parentController;
    @FXML
    private Label LabelrecId;
    @FXML
    private Label LabelRecType;
    @FXML
    private Label LabelRepNbr;
    @FXML
    private Label LabelNvlRep;
    @FXML
    private Label LabelClosedRecId;
    @FXML
    private Label recIdClosed;
    @FXML
    private Label LabelTypeClosed;
    @FXML
    private Label RecTypeClosed;
    @FXML
    private Label LabelMaintenantClosed;
    @FXML
    private Label IsClosed;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setParentController(NotifPanelUserController parentController) {
    this.parentController = parentController;
}



    void setReclamation(int rec_id, Date date_creation, String type, int newResponseCount, String status) {
       if ("Fermé".equals(status)){ 
       
       
       LabelrecId.setVisible(false);
           recId.setVisible(false);
           LabelRecType.setVisible(false);
           RecType.setVisible(false);
           LabelRepNbr.setVisible(false);
           RepNbr.setVisible(false);
           LabelNvlRep.setVisible(false);
           
           //Closed affichage
           LabelClosedRecId.setVisible(true);
           recIdClosed.setVisible(true);
           recIdClosed.setText( "''" + String.valueOf(rec_id)+ "''");
           LabelTypeClosed.setVisible(true);
           RecTypeClosed.setVisible(true);
           RecTypeClosed.setText("''" + String.valueOf(type)+ "''");
           LabelMaintenantClosed.setVisible(true);
           IsClosed.setVisible(true);
           IsClosed.setText(status);
       
       } else {
           RepNbr.setText("''" + String.valueOf(newResponseCount)+ "''" );
       recId.setText( "''" + String.valueOf(rec_id)+ "''");
       RecType.setText("''" + String.valueOf(type)+ "''");
           
          
      
       }
    }

    @FXML
    void removeNotification(ActionEvent event) {
        parentController.removeNotification(event);
    }

}

