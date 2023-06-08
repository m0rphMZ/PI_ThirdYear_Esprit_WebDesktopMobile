/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;


//import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import entities.Event;
import entities.Ticket;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.EventService;
import services.TicketService;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class TicketPaymentController implements Initializable {
    
    private Event ev;
    TicketService ts = new TicketService();
    EventService es = new EventService();

    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField cvvField;
    @FXML
    private Button PurchaseBtn;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField exprMonthField;
    @FXML
    private TextField exprYearField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Stripe.apiKey = "sk_test_51MgtelLLCNeC0hr0mCf88VawiUWkhHUT31HrUOJieGeZXcph8jTHoXYvULZB8micyWQVupMyHx5meCHom3D80TVA00roslqe44";
    }    
    
    
    @FXML
    private void handlePayment(ActionEvent event) {
        
        String cardNumber = cardNumberField.getText();
        String expMonth = exprMonthField.getText();
        String expYear = exprYearField.getText();
        String cvv = cvvField.getText();
            
        if (cardNumber.isEmpty() || expMonth.isEmpty() || expYear.isEmpty() || cvv.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        
        // create a Stripe Charge object with the card info and total price
        Map<String, Object> chargeParams = new HashMap<>();
        int Price = Math.round(ev.getTicketPrice() * 100 / 10) * 10;
        chargeParams.put("amount", Price); // convert to cents
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "Ticket Purchase");
        chargeParams.put("source", createCardToken(cardNumber, expMonth, expYear, cvv));
        Charge charge;
        try {
            charge = Charge.create(chargeParams);
        } catch (StripeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error processing payment.");
            alert.showAndWait();
            return;
        }

        // show confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment successful! Charge ID: " + charge.getId());
        alert.showAndWait();
        try {
            ticketMaker();
        } catch (WriterException | IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
    }
    
    private String createCardToken(String cardNumber, String expMonth, String expYear, String cvv) {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expMonth);
        cardParams.put("exp_year", expYear);
        cardParams.put("cvc", cvv);

        try {
            Map<String, Object> tokenParams = new HashMap<>();
            tokenParams.put("card", cardParams);
            return Token.create(tokenParams).getId();
        } catch (StripeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error creating card token.");
            alert.showAndWait();
            return null;
        }
    }

    void sendInfo(Event eventInfoStore) {
        ev = eventInfoStore;
        System.out.println(ev.getTicketPrice());
    }
    
    public void ticketMaker() throws WriterException, IOException, SQLException {
        String qrImgName = "";
        Ticket t = new Ticket();
        t.setEvent_id(ev.getEvent_id());
        t.setPrice(ev.getTicketPrice());
        t.setUser_id(LoginController.UserConnected.getId());
                
        //QR Code Generator
        String str = "Ticket number: "+ev.getTicketCount()+" | event: "+t.getEvent_id()+" | User: "+t.getUser_id();
        qrImgName = "t"+ev.getTicketCount()+"e"+t.getEvent_id()+"u"+t.getUser_id();
        String path = "src/assets/images/"+qrImgName+".png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();  
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        generateQRcode(str, path, charset, hashMap, 200, 200);

        t.setQrCodeImg(path);
        ts.ajouter(t);
        //Update TicketCount
        int newTicketCount = (ev.getTicketCount())-1;
        ev.setTicketCount(newTicketCount);
        es.modifier(ev);

        //Extra: Refresh content pane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventInfo.fxml"));
        Parent root = loader.load();
        MCCSaver.mcc.setContent(root);
        EventInfoController controller = loader.getController();
        controller.sendEvent(ev);

        // Show QR code image
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Congratulation!");
        successAlert.setHeaderText("Ticket was successfully bought!");
        successAlert.setContentText("Here's your Ticket QR Code, please scan it!");

        // Load the image from the saved file
        File imageFile = new File(t.getQrCodeImg());
        Image image = new Image(imageFile.toURI().toString());

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200); // Set the width of the image view
        imageView.setFitHeight(200); // Set the height of the image view

        // Set the graphic of the alert to the image view
        successAlert.setGraphic(imageView);
        successAlert.showAndWait();
    }
    
    public void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException  {  
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);  
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));  
    } 
}  
