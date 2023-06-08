/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Event;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.EventService;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class AfficherListeEventController implements Initializable {

    @FXML
    private ScrollPane eListScrollPane;
    @FXML
    private GridPane eventListGP;
    
    EventService es = new EventService();
    @FXML
    private Button addEventBtn;
    @FXML
    private TextField searchBarTf;
    @FXML
    private ChoiceBox<String> sortBoxCB;
    
    private String sortCriteria = "Start Date";;
    private boolean showOnlyActiveEvents = false;
    private boolean showOnlyEndedEvents = false;
    @FXML
    private RadioButton showOnlyActiveRb;
    @FXML
    private RadioButton showOnlyEndedRb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Metier Filtre
            ToggleGroup group = new ToggleGroup();
            showOnlyActiveRb.setToggleGroup(group);
            showOnlyEndedRb.setToggleGroup(group);
            showOnlyActiveRb.setOnAction(event -> {
                showOnlyActiveEvents = showOnlyActiveRb.isSelected();
                showOnlyEndedEvents = showOnlyEndedRb.isSelected();
                searchSort(searchBarTf.getText(), sortCriteria);
            });
            showOnlyEndedRb.setOnAction(event -> {
                showOnlyActiveEvents = showOnlyActiveRb.isSelected();
                showOnlyEndedEvents = showOnlyEndedRb.isSelected();
                searchSort(searchBarTf.getText(), sortCriteria);
            });
            
            
            eListScrollPane.setFitToWidth(true);
            List<Event> events = es.recuperer();
            int row = 1;
            int column = 0;
            for (int i = 0; i < events.size(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleEvent.fxml"));
                AnchorPane pane = loader.load();
                SingleEventController controller = loader.getController();
                controller.setData(events.get(i));
                eventListGP.add(pane, column, row);
                    row++;       
            }
            //Tri criterias:
            sortBoxCB.setValue("Start Date");
            sortBoxCB.getItems().addAll("Title", "Type", "Start Date");
            sortBoxCB.setOnAction(event -> {
                sortCriteria = sortBoxCB.getValue();
            });
            //Search
            searchBarTf.textProperty().addListener((observable, oldValue, newValue) -> {
                searchSort(newValue, sortCriteria);
            });
            
            //Sort
            sortBoxCB.valueProperty().addListener((observable, oldValue, newValue) -> {
                searchSort(searchBarTf.getText(), newValue);
            });
            
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }    

    @FXML
    private void addEventRedirect(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterEvent.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void searchSort(String search, String sortBy){
        try {
                List<Event> filteredEvents = es.rechercher(search, sortBy);
                if (showOnlyActiveEvents) {
                    filteredEvents = filteredEvents.stream()
                    .filter(event -> event.getEndDate().after(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())))
                    .collect(Collectors.toList());
                } else if (showOnlyEndedEvents) {
                    filteredEvents = filteredEvents.stream()
                    .filter(event -> event.getEndDate().before(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())))
                    .collect(Collectors.toList());
                }
                eventListGP.getChildren().clear();
                int rowS = 1;
                int columnS = 0;
                for (int i = 0; i < filteredEvents.size(); i++){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleEvent.fxml"));
                    AnchorPane pane = loader.load();
                    SingleEventController controller = loader.getController();
                    controller.setData(filteredEvents.get(i));
                    eventListGP.add(pane, columnS, rowS);
                    rowS++;
                }
            } catch (SQLException | IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
}
