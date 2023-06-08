/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Commentaire;
import entities.Event;
import entities.User;
import static gui.LoginController.UserConnected;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.zone.ZoneRulesProvider.refresh;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.CommentaireService;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class CommentaireController implements Initializable {

    @FXML
    private TextField cmtntext;
    @FXML
    private Button valider;
    @FXML
    private Button annuler;
    @FXML
    private GridPane gridcmnt;
    Connection cnx;
    
    CommentaireService cs = new CommentaireService();
    Commentaire t=new Commentaire();
    private Event eventInfo;
    
    @FXML
    private Button retbt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User UserConnected = LoginController.UserConnected;
        
    }    

    public CommentaireController() {
        cnx = MyDB.getInstance().getCnx();
    }

    
    
    @FXML
    private void commenter(ActionEvent event) throws SQLException, IOException {
        
    t.setId_event(eventInfo.getEvent_id());
    t.setId_user(UserConnected.getId());
    t.setCommentaire(cmtntext.getText());

    String req = "INSERT INTO commentaire (id_user, id_event, commentaire) VALUES (?, ?, ?)";
    PreparedStatement stmt = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
    stmt.setInt(1, t.getId_user());
    stmt.setInt(2, t.getId_event());
    stmt.setString(3, t.getCommentaire());
    stmt.executeUpdate();
    ResultSet generatedKeys = stmt.getGeneratedKeys();
    if (generatedKeys.next()) {
        int id = generatedKeys.getInt(1);
        System.out.println(id);
       FXMLLoader loader = new FXMLLoader(getClass().getResource("UserCommentaire.fxml"));
           AnchorPane pane = loader.load();
        UserCommentaireController controller = new UserCommentaireController();
        controller = loader.getController();
        controller.setidcmnt(id);
        
       String nom = cs.NomUser(UserConnected.getId());
        
     



        
        
        
        
        cmtntext.setText("");
       sendInfo(eventInfo);
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
}

    @FXML
    private void annuler(ActionEvent event) {
        
       
        cmtntext.setText("");
       
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheUser.fxml"));
           Parent root = loader.load();
//      
//        
      Scene scene = new Scene(root);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setTitle("Affiche Users");
       stage.setScene(scene);
      stage.show();
    }

    void sendInfo(Event eventInfoStore) {
        eventInfo = eventInfoStore;
        
        
        
         try {
            // TODO
            List<Commentaire> cnmts = cs.recuperer();
            
            System.out.println(cnmts);
       int row = 0;
        int column = 0;
            for (int i = 0; i < cnmts.size(); i++){
                
                if ((cnmts.get(i).getId_event()==eventInfo.getEvent_id())){
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserCommentaire.fxml"));
        AnchorPane pane = loader.load();
        UserCommentaireController controller = loader.getController();
            Commentaire cnmt=cnmts.get(i);
            
            controller.setcmnt(cnmt);
            
            gridcmnt.add(pane, column, row);
                row++;
                if (column > 0) {
                    column = 0;
                    row++;
            
            
                }
            
            }
            
            
            }    
        } catch (SQLException|IOException ex) {
            
            
            System.out.println(ex.getMessage());
            
            
            
        }
    }

        
        
        
        
        
    }
       

