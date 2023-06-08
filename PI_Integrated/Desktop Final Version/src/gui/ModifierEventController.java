/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.Local;
import entities.Event;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EventService;
import services.LocalService;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class ModifierEventController implements Initializable {

    @FXML
    private TextField titleTf;
    @FXML
    private ChoiceBox<String> typeTf;
    @FXML
    private TextArea descTa;
    @FXML
    private DatePicker startDateDp;
    @FXML
    private DatePicker endDateDp;
    @FXML
    private TextField ticketCountTf;
    @FXML
    private Button uploadImgBtn;
    private ChoiceBox<String> statusCBox;
    @FXML
    private Button updateBtn;
    
    private Event eventInfo;
    
    private String imageData;
    
    EventService es = new EventService();
    LocalService locS = new LocalService();
    @FXML
    private ImageView imagePreview;
    @FXML
    private Button cancelBtn;
    @FXML
    private ChoiceBox<String> locationCBox;
    private String selectedLoc;
    private int selectedLocId;
    @FXML
    private TextField ticketPriceTf;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Event Type choice box
            typeTf.setValue("Plein air");
            typeTf.getItems().addAll("Plein air", "A l'intérieur", "Cérémonie");
            typeTf.setOnAction(event -> {
                String selected = typeTf.getValue();
        });
        
        //Set location choice box values
        try {
            List<Local> locations = locS.recuperer();
            locationCBox.setValue(locations.get(0).getLieu());
            for (int i = 0; i < locations.size(); i++){
                locationCBox.getItems().add(locations.get(i).getLieu());
            }
            locationCBox.setOnAction(event -> {
                selectedLoc = locationCBox.getValue();
                for (Local location : locations) {
                    if (location.getLieu().equals(selectedLoc)) {
                        selectedLocId = location.getNum();
                        break;
                    }
                }
            });
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void eventReceiver(Event eventInfoStore) {
        try {
            eventInfo = eventInfoStore;
            titleTf.setText(eventInfoStore.getTitle());
            typeTf.setValue(eventInfoStore.getType());
            descTa.setText(eventInfoStore.getDescription());
            startDateDp.setValue((eventInfoStore.getStartDate()).toLocalDate());
            endDateDp.setValue((eventInfoStore.getEndDate()).toLocalDate());
            ticketCountTf.setText(String.valueOf(eventInfoStore.getTicketCount()));
            imageData = eventInfoStore.getAffiche();
            locationCBox.setValue(locS.getLieu(eventInfoStore.getLocation_id()));
            ticketPriceTf.setText(Float.toString(eventInfoStore.getTicketPrice()));
            File imageFile = new File(eventInfo.getAffiche());
            Image image = new Image(imageFile.toURI().toString());
            imagePreview.setImage(image);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imageName = selectedFile.getName();
            imageData = "src/assets/images/affiches/"+imageName;
            File imageFile = new File(imageData);
            Image image = new Image(imageFile.toURI().toString());
            imagePreview.setImage(image);
        }
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        try {
            String verifTicketCount = ticketCountTf.getText();
            String verifTicketPrice = ticketPriceTf.getText();
            if (!verifTicketCount.matches("\\d+")){
                Alert warn = new Alert(Alert.AlertType.INFORMATION);
                warn.setTitle("Invalid Input");
                warn.setContentText("The ticket count value is not valid.");
                warn.setHeaderText(null);
                warn.showAndWait();
            } else if (!verifTicketPrice.matches("\\d+(\\.\\d+)?")) {
                Alert warn = new Alert(Alert.AlertType.INFORMATION);
                warn.setTitle("Invalid Input");
                warn.setContentText("The ticket price value is not valid.");
                warn.setHeaderText(null);
                warn.showAndWait();
            } else if (titleTf.getText().isEmpty() || typeTf.getValue().isEmpty() || descTa.getText().isEmpty() || startDateDp.getValue()==null || endDateDp.getValue()==null) {
                Alert warn = new Alert(Alert.AlertType.INFORMATION);
                warn.setTitle("Invalid Input");
                warn.setContentText("Please enter values in all fields.");
                warn.setHeaderText(null);
                warn.showAndWait();
            } else if (endDateDp.getValue().isBefore(startDateDp.getValue())) {
                Alert warn = new Alert(Alert.AlertType.INFORMATION);
                warn.setTitle("Invalid Input");
                warn.setContentText("End date cannot be before start date.");
                warn.setHeaderText(null);
                warn.showAndWait();
            } else {
            Event e = new Event();
            e.setEvent_id(eventInfo.getEvent_id());
            e.setTitle(titleTf.getText());
            e.setType(typeTf.getValue());
            e.setDescription(descTa.getText());
            e.setStartDate(java.sql.Date.valueOf(startDateDp.getValue())); //Gives a local date so I converted to Date
            e.setEndDate(java.sql.Date.valueOf(endDateDp.getValue()));
            e.setTicketCount(Integer.parseInt(ticketCountTf.getText()));
            e.setLocation_id(selectedLocId);
            e.setAffiche(imageData);
            e.setTicketPrice(Float.parseFloat(ticketPriceTf.getText()));
            es.modifier(e);
            eventInfo = e;
            //Redirect back to event info page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventInfo.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
            EventInfoController controller = loader.getController();
            controller.sendEvent(eventInfo);
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        try {
            //Redirect back to event info page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventInfo.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
            EventInfoController controller = loader.getController();
            controller.sendEvent(eventInfo);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
}
