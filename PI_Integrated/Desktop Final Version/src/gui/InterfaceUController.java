/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.CategorieLocal;
import Entities.Local;
import entities.mail;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.LocalService;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class InterfaceUController implements Initializable {

    @FXML
    private GridPane grid;
LocalService ls=new LocalService();
List <Local>personnes=new ArrayList();
Connection cnx= cnx = MyDB.getInstance().getCnx();
 String to="";
  mail m=new mail();
    @FXML
    private TextField cherchertext;
    @FXML
    private Button goBackBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        try {
            //
//        try {
//            
//            try {
//                personnes = ls.recuperer();
//            } catch (SQLException ex) {
//                Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        int column = 0;
//        int row = 1;
//       
//            for (int i = 0; i < personnes.size(); i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("../gui/Local.fxml"));
//                AnchorPane anchorPane = fxmlLoader.load();
//
//                LocalController controller = fxmlLoader.getController();
//                controller.setData(personnes.get(i));
//
//                if (column == 3) {
//                    column = 0;
//                    row++;
//                }
//
//                grid.add(anchorPane, column++, row); //(child,column,row)
//                //set grid width
//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//                //set grid height
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMaxHeight(Region.USE_PREF_SIZE);
//
//             GridPane.setMargin(anchorPane, new Insets(10));
//
//
//
//
//             
//              controller.louer.setOnAction(event -> {
//                    // Récupération de l'admin
//    /*               
// try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("gerercategories.fxml"));
//            Parent root = loader.load();
//
//            grid.getScene().setRoot(root);
//
//        } catch (IOException ex) {
//            System.out.println("error" + ex.getMessage());
//        }*/
//    
//    try{
//  //  String r=null;   
//    String req=" select email from user ";
//    //PreparedStatement ps = cnx.prepareStatement(req);
//     Statement cs = cnx.createStatement(); 
//    ResultSet rs =  cs.executeQuery(req);
//    //cs.setString(1,"admin");
//    //cs.executeUpdate();
//    
//    //jai bcp d'admin et non pas un seul
//    while(rs.next()){
//        if(to.equals("")){to+=rs.getString("email");}
//        
//        else {
//        
//        to+=","+rs.getString("email");
//        }
//        
//    }
//   
//    m.send(to, "Client", "le client est intersse à louer", "saws55457@gmail.com", "dptkvofegptwzirs");
//
//
//    
//     } catch (SQLException ex) {
//                        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//
//
//
//                });
//
//
//
//
//
//             
//            }
//        } 
//     catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
////
//        
//       cherchertext.setOnKeyReleased(event -> {
//    try {
//        searchByCode(cherchertext.getText());
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//});

afficher();
//searchByCode("");
        } catch (Exception ex) {
            Logger.getLogger(InterfaceUController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       cherchertext.textProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.trim().isEmpty()) {
         try {afficher();
           // searchByCode(cherchertext.getText());
        } catch (Exception ex) {
            Logger.getLogger(InterfaceUController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    else{
        try {
            searchByCode(cherchertext.getText());
        } catch (Exception ex) {
            Logger.getLogger(InterfaceUController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
});

        
        
    }
    
    
    private void afficher(){
    
    
    
    
        try {
            
            try {
                personnes = ls.recuperer();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        int column = 0;
        int row = 1;
       
            for (int i = 0; i < personnes.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../gui/Local.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                LocalController controller = fxmlLoader.getController();
                controller.setData(personnes.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

             GridPane.setMargin(anchorPane, new Insets(10));
             
             
             
             
             
              controller.louer.setOnAction(event -> {
                    // Récupération de l'admin
    /*               
 try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gerercategories.fxml"));
            Parent root = loader.load();

            grid.getScene().setRoot(root);

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }*/
    
    try{
  //  String r=null;   
    String req=" select email from user ";
    //PreparedStatement ps = cnx.prepareStatement(req);
     Statement cs = cnx.createStatement(); 
    ResultSet rs =  cs.executeQuery(req);
    //cs.setString(1,"admin");
    //cs.executeUpdate();
    
    //jai bcp d'admin et non pas un seul
    while(rs.next()){
        if(to.equals("")){to+=rs.getString("email");}
        
        else {
        
        to+=","+rs.getString("email");
        }
        
    }
    String s=controller.numloc.getText();
   String msg="le client est interssé à louer le local dont num ="+s;
    m.send(to, "Client", msg, "saws55457@gmail.com", "dptkvofegptwzirs");
    
    
    
     } catch (SQLException ex) {
                        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
                    }
    
    
    

                });
             
             
             
             
             
             
            }
        } 
     catch (IOException e) {
            e.printStackTrace();
        }
        
    
    
    
    
    
    
    
    
    
    }
    
    
        
        
        public void searchByCode(String code) throws Exception {
    try {code=cherchertext.getText();
        
        List<Local> locals = ls.recuperer();

        
        grid.getChildren().clear();
        int column = 0;
        int row = 1;

       
        for (Local local : locals) {
            if (local.getLieu().equals(code)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Local.fxml"));
                AnchorPane pane = loader.load();

                // Pass the local data to the LocalController
                LocalController controller = loader.getController();
                controller.setData(local);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));
                
                
                
                
              controller.louer.setOnAction(event -> {
                    // Récupération de l'admin
    /*               
 try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gerercategories.fxml"));
            Parent root = loader.load();

            grid.getScene().setRoot(root);

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }*/
    
    try{
  //  String r=null;   
    String req=" select email from user ";
    //PreparedStatement ps = cnx.prepareStatement(req);
     Statement cs = cnx.createStatement(); 
    ResultSet rs =  cs.executeQuery(req);
    //cs.setString(1,"admin");
    //cs.executeUpdate();
    
    //jai bcp d'admin et non pas un seul
    while(rs.next()){
        if(to.equals("")){to+=rs.getString("email");}
        
        else {
        
        to+=","+rs.getString("email");
        }
        
    }
   String s=controller.numloc.getText();
   String msg="le client est intersse à louer LE LOCAL à"+s;
    m.send(to, "Client", msg, "saws55457@gmail.com", "dptkvofegptwzirs");
    
    
    
     } catch (SQLException ex) {
                        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
                    }
    
    
    

                });
                
                
            }
        }
    } catch (SQLException | IOException ex) {
        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @FXML
    private void goBackHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContainer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            //Load CSS
            String css = this.getClass().getResource("../assets/css/app.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
  }

        
        
   