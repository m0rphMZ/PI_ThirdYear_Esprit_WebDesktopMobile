/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.CategorieLocal;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.CategorieLocalService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class GererCategoriesController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private TextField codeCat;
    @FXML
    private TextField libCat;
    @FXML
    private Label code;
    @FXML
    private Label lib;
    int codd;
    CategorieLocalService cs=new CategorieLocalService (); 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void afficher(ActionEvent event) {
             
    
       
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Affichercategorie.fxml"));
            Parent root;
        try {
            root = loader.load();
            codeCat.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(GererCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
           
        
    }


    @FXML
    private void ajouterCategorie(MouseEvent event) {
        
        
        
    
    boolean isValid=true;
     if (codeCat.getText().isEmpty()) {
        code.setText("le code  est requis.");
        isValid = false;
    } 
else {
        code.setText("");
    }
    
    
    
    if (libCat.getText().isEmpty()) {
        lib.setText("libelle est requise.");
        isValid = false;
    } 
else {
        lib.setText("");
    }
    if (isValid) {
        // Les entrées utilisateur sont valides, faites quelque chose ici
        CategorieLocal c=new CategorieLocal();
       codd=Integer.parseInt(codeCat.getText());
        c.setCode(codd);
        c.setLibelle(libCat.getText());
        
     System.out.println(c.getCode());
     System.out.println(c.getLibelle());
        try {
            cs.ajouter(c);
            reset();
            
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                
                alert.setHeaderText(null);
                alert.setContentText("Ajout categorie avec succes");
                
                alert.show(); 
        } catch (SQLException ex) {
            Logger.getLogger(GererCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
    
    }
        
       
        
    }

    
    
    
     
     private void reset() {
        codeCat.setText("");
        libCat.setText("");
      

    }
    
    @FXML
    private void annulerCategorie(MouseEvent event) {
        
        reset();
        
    }

    @FXML
    private void retour(MouseEvent event) {
           try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GESTIONLOCAUX.fxml"));
            Parent root = loader.load();
       
            codeCat.getScene().setRoot(root);
           

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
        
    }
    
}
