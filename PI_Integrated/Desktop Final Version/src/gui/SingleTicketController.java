/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Event;
import entities.Ticket;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import services.EventService;
import services.TicketService;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class SingleTicketController implements Initializable {

    @FXML
    private Text ticketPriceText;
    @FXML
    private Text eventNameText;
    @FXML
    private Text eventStartDateText;
    @FXML
    private Text eventEndDateText;
    @FXML
    private ImageView qrCodeDisplay;
    
    EventService es = new EventService();
    TicketService ts = new TicketService();
    private com.itextpdf.text.Image ITPImage;
    private Ticket ticketInfo;
    private Event eventInfo;
    @FXML
    private Button printTickBtn;
    @FXML
    private Button DeleteTicketButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!LoginController.UserConnected.getRole().equals("Admin")){
                DeleteTicketButton.setVisible(false);
        }
    }    

    void setData(Ticket t) {
        try {
            ticketInfo = t;
            eventInfo = es.getEvent(t.getEvent_id());
            ticketPriceText.setText(String.valueOf(t.getPrice())+" TND");
            eventNameText.setText(eventInfo.getTitle());
            eventStartDateText.setText(String.valueOf(eventInfo.getStartDate()));
            eventEndDateText.setText(String.valueOf(eventInfo.getEndDate()));
            
            //show QR Code:
            File imageFile = new File(t.getQrCodeImg());
            Image image = new Image(imageFile.toURI().toString());
            qrCodeDisplay.setImage(image);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    @FXML
    private void printTicketHandler(ActionEvent event) throws IOException {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("testpdf.pdf"));
            document.open();
            
            // Add the logo to the document
            ITPImage = ITPImage.getInstance("src/assets/images/LogoBlack.png");
            ITPImage.setAlignment(ITPImage.ALIGN_CENTER);
            ITPImage.scaleToFit(100, 100);
            document.add(ITPImage);

            // Add a congratulatory message to the document
            Paragraph congrats = new Paragraph("Congratulations on buying your ticket!");
            congrats.setAlignment(Paragraph.ALIGN_CENTER);
            congrats.setSpacingBefore(20);
            document.add(congrats);

            // Add the ticket to the document
            PdfPTable ticket = new PdfPTable(new float[]{70, 30});
            ticket.setWidthPercentage(100);
            ticket.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            ticket.setSpacingBefore(20);
            PdfPCell leftCell = new PdfPCell();
            leftCell.setBorderWidth(1);
            leftCell.setPadding(10);
            leftCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            leftCell.addElement(new Paragraph("Event Name: "+eventInfo.getTitle()));
            leftCell.addElement(new Paragraph("Start date: "+eventInfo.getStartDate()));
            leftCell.addElement(new Paragraph("End date: "+eventInfo.getEndDate()));
            leftCell.addElement(new Paragraph("Price: "+ticketInfo.getPrice()+" TND."));
            PdfPCell rightCell = new PdfPCell();
            rightCell.setBorderWidth(1);
            rightCell.setPadding(10);
            rightCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            ITPImage = ITPImage.getInstance(ticketInfo.getQrCodeImg());
            ITPImage.setAlignment(ITPImage.ALIGN_CENTER);
            rightCell.addElement(ITPImage);
            rightCell.addElement(new Paragraph("Scan to check in"));
            ticket.addCell(leftCell);
            ticket.addCell(rightCell);
            document.add(ticket);
            
            document.close();
        } catch (FileNotFoundException | DocumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void deleteTicketHandler(ActionEvent event) {
        try {
            ts.supprimer(ticketInfo);
            //Refresh back to ticket list
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketList.fxml"));
            Parent root = loader.load();
            MCCSaver.mcc.setContent(root);
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
