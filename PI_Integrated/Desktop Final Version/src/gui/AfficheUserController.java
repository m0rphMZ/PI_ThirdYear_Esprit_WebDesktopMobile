/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class AfficheUserController implements Initializable {

    @FXML
    private GridPane grid;
    UserService us = new UserService();
    @FXML
    private Button ajouttbn;
    @FXML
    private Button retourbtn;
    @FXML
    private TextField cher;
    @FXML
    private Button stat;

    /**
     * Initializes the controller class.
     */
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            

            List<User> users = us.recuperer();
        int row = 0;
        int column = 0;
            
            
            
            System.out.println(users);
            
            
        for (int i = 0; i < users.size(); i++){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
        AnchorPane pane = loader.load();
        UserController controller = loader.getController();
        if(users.get(i).getRole().equals("Artiste")||users.get(i).getRole().equals("simple utilisateur")){
         User user=users.get(i);
     
        controller.setUser(user);
        
       
         grid.add(pane, column, row);
                row++;
                if (column > 0) {
                    column = 0;
                    row++;
        }
        
        }
        }
            
            
            
            
            
            
            
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        
        
        
    }    
    
}

    
    
    
    
    
    
    
    
    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutUser.fxml"));
            Parent root = loader.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ajouter utilisateur");
        stage.setScene(scene);
        stage.show();
        
        
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContainer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }

   

    @FXML
    private void chercherkey(KeyEvent event) throws IOException {
        String nom = cher.getText();
    try {
        List<User> users_ch = us.rechercherParNom(nom);
        grid.getChildren().clear();
        int row = 0;
        int column = 0;
        for (int i = 0; i < users_ch.size(); i++){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
            AnchorPane pane = loader.load();
            UserController controller = loader.getController();
            if(users_ch.get(i).getRole().equals("Artiste") || users_ch.get(i).getRole().equals("simple utilisateur")){
                User user=users_ch.get(i);
                controller.setUser(user);
                grid.add(pane, column, row);
                row++;
                if (column > 0) {
                    column = 0;
                    row++;
                }
            }
        }
    } catch (SQLException | IOException ex) {
        System.out.println(ex.getMessage());
    }
        
        
        
        
        
    }

    @FXML
    private void stats(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StatUser.fxml"));
            Parent root = loader.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Statistique");
        stage.setScene(scene);
        stage.show();
        
        
        
    }
        
        
    
}
