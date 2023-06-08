/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Event;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class SingleEventController implements Initializable {

    @FXML
    private ImageView afficheIv;
    @FXML
    private Text titleText;
    @FXML
    private Text startDateText;
    @FXML
    private Button moreInfoBtn;
    
    private Event eventInfo;
    @FXML
    private Text typeText;
    @FXML
    private Text endDateText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficheIv.maxWidth(202);
        afficheIv.maxHeight(114);
    }

    public void setData(Event e){
        titleText.setText(e.getTitle());
        typeText.setText(e.getType());
        startDateText.setText(String.valueOf(e.getStartDate()));
        endDateText.setText(String.valueOf(e.getEndDate()));
        
        File imageFile = new File(e.getAffiche());
        Image image = new Image(imageFile.toURI().toString());
        afficheIv.setImage(image);
        afficheIv.setPreserveRatio(true);
        //Save event Id:
        eventInfo = e;
    }

    @FXML
    private void afficherMoreInfo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventInfo.fxml"));
            Parent root = loader.load();
            EventInfoController controller = loader.getController();
            controller.sendEvent(eventInfo);
            MCCSaver.mcc.setContent(root);
              
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
