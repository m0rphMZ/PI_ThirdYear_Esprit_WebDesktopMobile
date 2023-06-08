/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import entities.Event;
import entities.EventReaction;
import entities.Ticket;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.EventReactionService;
import services.EventService;
import services.LocalService;
import services.TicketService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class EventInfoController implements Initializable {

    private Text eventIdText;
    
    private Event eventInfoStore;
    
    EventService es = new EventService();
    UserService us = new UserService();
    LocalService locS = new LocalService();
    TicketService ts = new TicketService();
    EventReactionService ers = new EventReactionService();
    
    @FXML
    private Text titleText;
    @FXML
    private Text startDateText;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button backBtn;
    @FXML
    private ImageView afficheIv;
    @FXML
    private Text typeText;
    @FXML
    private Text endDateText;
    @FXML
    private Text locationAddressText;
    @FXML
    private Text descText;
    @FXML
    private Text hostNameText;
    @FXML
    private Text ticketCountText;
    @FXML
    private ScrollPane sPane;
    @FXML
    private Button buyBtn;
    @FXML
    private Button likeButton;
    @FXML
    private Button dislikeButton;
    @FXML
    private AnchorPane commentPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sPane.setFitToWidth(true);
    }    

    void sendEvent(Event eventInfo) {
        try {
            eventInfoStore = eventInfo;
            titleText.setText(eventInfo.getTitle());
            typeText.setText(eventInfo.getType());
            startDateText.setText(String.valueOf(eventInfo.getStartDate()));
            endDateText.setText(String.valueOf(eventInfo.getEndDate()));
            locationAddressText.setText(locS.getLieu(eventInfo.getLocation_id()));
            hostNameText.setText(us.getNom(eventInfo.getHost_id()));
            ticketCountText.setText(String.valueOf(eventInfo.getTicketCount()));
            descText.setText(eventInfo.getDescription());
            File imageFile = new File(eventInfo.getAffiche());
            Image image = new Image(imageFile.toURI().toString());
            afficheIv.setImage(image);
            buyBtn.setText("Obtenez votre ticket! "+eventInfo.getTicketPrice()+" dt.");
            likeButton.setText("LIKE: "+ers.reactionCount(eventInfoStore.getEvent_id(), EventReaction.Reaction.like));
            dislikeButton.setText("DISLIKE: "+ers.reactionCount(eventInfoStore.getEvent_id(), EventReaction.Reaction.dislike));
            //Check if Admin:
            if(!LoginController.UserConnected.getRole().equals("Admin")){
                updateBtn.setVisible(false);
                deleteBtn.setVisible(false);
            }
            //Check if
            if(eventInfoStore.getEndDate().before(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) || LoginController.UserConnected.getRole().equals("Admin")){
                buyBtn.setVisible(false);
            }
            
            
            //Fill up comment pane
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource("Commentaire.fxml"));
            AnchorPane content = contentLoader.load();
            CommentaireController controller = contentLoader.getController();
            controller.sendInfo(eventInfoStore);
            
            commentPane.getChildren().setAll(content);
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void deleteEvent(ActionEvent event) {
        try {
            es.supprimer(eventInfoStore);
            //Redirect back to event list
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherListeEvent.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEvent.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
            ModifierEventController controller = loader.getController();
            controller.eventReceiver(eventInfoStore);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goBackToList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherListeEvent.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handlePopup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicketPayment.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            TicketPaymentController controller = fxmlLoader.getController();
            controller.sendInfo(eventInfoStore);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("../assets/css/app.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Buy a Ticket!");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void likeEventHandler(ActionEvent event) {
        try {
            //Create reaction
            EventReaction er = new EventReaction();
            er.setEvent_id(eventInfoStore.getEvent_id());
            er.setUser_id(LoginController.UserConnected.getId());
            er.setReaction(EventReaction.Reaction.like);
            
            //check if user reacted
            boolean alreadyReacted = ers.alreadyReacted(eventInfoStore.getEvent_id(), LoginController.UserConnected.getId());
            if (alreadyReacted == true) {
                ers.modifier(er);
            } else {
                ers.ajouter(er);
            }
            likeButton.setText("LIKE: "+ers.reactionCount(eventInfoStore.getEvent_id(), EventReaction.Reaction.like));
            dislikeButton.setText("DISLIKE: "+ers.reactionCount(eventInfoStore.getEvent_id(), EventReaction.Reaction.dislike));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void dislikeEventHandler(ActionEvent event) {
        try {
            //Create reaction
            EventReaction er = new EventReaction();
            er.setEvent_id(eventInfoStore.getEvent_id());
            er.setUser_id(LoginController.UserConnected.getId());
            er.setReaction(EventReaction.Reaction.dislike);
            
            //check if user reacted
            boolean alreadyReacted = ers.alreadyReacted(eventInfoStore.getEvent_id(), LoginController.UserConnected.getId());
            if (alreadyReacted == true) {
                ers.modifier(er);
            } else {
                ers.ajouter(er);
            }
            likeButton.setText("LIKE: "+ers.reactionCount(eventInfoStore.getEvent_id(), EventReaction.Reaction.like));
            dislikeButton.setText("DISLIKE: "+ers.reactionCount(eventInfoStore.getEvent_id(), EventReaction.Reaction.dislike));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
