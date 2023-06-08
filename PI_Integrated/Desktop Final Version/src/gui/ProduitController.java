/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import entities.produit;
import java.io.File;
import services.PanierService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monta
 */
public class ProduitController implements Initializable {

    @FXML
    private Label nomProduirLabel;
    @FXML
    private Label QunatiteLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button participerEventButton;
    @FXML
    private Label prixlabel;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField idProdF;
    produit p=new produit();
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idProdF.setVisible(false);
    }    

    @FXML
    private void participerEvent(MouseEvent event) {
    }
    
      public void setProduit(produit e) {
        p=e;
        nomProduirLabel.setText(e.getCodeProduit());
        QunatiteLabel.setText(String.valueOf(e.getQteStock()));
        descriptionLabel.setText(e.getDesgination());
        prixlabel.setText(String.valueOf(e.getPrixUnite()));
        idProdF.setText(String.valueOf(e.getId()));
         String path = e.getImage();
               File file=new File(path);
              Image img = new Image(file.toURI().toString());
                imageview.setImage(img);
       

    }

    @FXML
    private void go_cod(MouseEvent event) {
try {
                            
                            
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("cod.fxml"));
        Parent root = loader.load();
          CodController destController = loader.getController();
//          destController.prodredcu(ps.selectProduitById(ps.getProductIdByName(nomProduirLabel.getText())));
          
         destController.setprix(Float.parseFloat(prixlabel.getText()));
         destController.setdescription(descriptionLabel.getText());
         
                    Scene sence = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(sence);
                    stage.show();
            
        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    @FXML
    private void go_avis(MouseEvent event) {
        
        try {                       
                            
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterAvis.fxml"));
        Parent root = loader.load();
          AjouterAvisController destController = loader.getController();
//          destController.prodredcu(ps.selectProduitById(ps.getProductIdByName(nomProduirLabel.getText())));
          
         destController.setIdevent(Integer.parseInt(idProdF.getText()));
                    Scene sence = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(sence);
                    stage.show();
            
        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
        
    }

    @FXML
    private void ajoutPanier(ActionEvent event) throws IOException {
        SousProduitController SSC = new SousProduitController () ;
        produit p=new produit();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SousProduit.fxml"));
           AnchorPane pane = loader.load();
        SousProduitController controller = new SousProduitController();
        controller = loader.getController();
        SSC.setProd(p);
    } 
    }
    

