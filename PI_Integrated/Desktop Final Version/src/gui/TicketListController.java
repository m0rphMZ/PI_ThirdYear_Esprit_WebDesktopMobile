/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Ticket;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import services.TicketService;

/**
 * FXML Controller class
 *
 * @author Aymen
 */
public class TicketListController implements Initializable {

    @FXML
    private ScrollPane ticketListScrollPane;
    @FXML
    private GridPane ticketGridPane;
    TicketService ts = new TicketService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ticketListScrollPane.setFitToWidth(true);
            List<Ticket> tickets = ts.recuperer();
            int row = 1;
            int column = 0;
            for (int i = 0; i < tickets.size(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleTicket.fxml"));
                AnchorPane pane = loader.load();
                SingleTicketController controller = loader.getController();
                controller.setData(tickets.get(i));
                ticketGridPane.add(pane, column, row);
                    row++;       
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }    
    
}
