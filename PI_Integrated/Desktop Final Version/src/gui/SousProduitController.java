/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.produit;
import entities.Panier;
import java.io.IOException;
import static java.lang.System.load;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import static javafx.fxml.FXMLLoader.load;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import services.PanierService;
import gui.PanierController;
import javafx.scene.Parent;


/**
 * FXML Controller class
 *
 * @author ashre
 */
public class SousProduitController implements Initializable {

    @FXML
    private Label codeNom;
    @FXML
    private Label produitprix;
    @FXML
    private TextField caltotal;
    private Panier pp=new Panier();
    PanierService ps =new PanierService();
    
    private produit po=new produit();
    int q;
    @FXML
    private Text sstotal;
    @FXML
    private AnchorPane anchorpane;

    
    private int i ;
    private List<Integer> L = new ArrayList(); 
    @FXML
    private Button ajoutprod;
    @FXML
    private Button supprod;
    @FXML
    private Label sostot;
    private double cc;
    private double xd;
   
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        try {
            L=ps.recuperer();
        } catch (SQLException ex) {
            Logger.getLogger(SousProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         i = L.get(L.size()-1);
         System.out.println(i);
    }    

    void setProd(produit p) {
        
//        codeNom.setText(p.getCodeProduit());
        produitprix.setText(Float.toString(p.getPrixUnite()));
        caltotal.setText(Integer.toString(pp.getQuantite()));
        
        pp.setId_produit(p.getId());
        pp.setId_user(LoginController.UserConnected.getId());

    
}

    
    
    @FXML
    private void calculsom(KeyEvent event) throws SQLException {
        q = Integer.parseInt(caltotal.getText()) ; 
        System.out.println(q);
        int p = Integer.parseInt(produitprix.getText());
        int s = p*q ; 
        sstotal.setText(String.valueOf(s));
        pp.setQuantite(q);
      
    }
    


    @FXML
    private void ajoutProd(ActionEvent event) throws SQLException, IOException {
 pp.setQuantite(Integer.parseInt(caltotal.getText()));

    if(pp.getQuantite() == 0) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText("La quantité ne doit pas être égale à zéro.");
        alert.setContentText("Veuillez saisir une quantité valide.");
        alert.showAndWait();
    } else {
        pp.setId_panier(i+1);
        System.out.println(pp.toString());
        ps.ajouterPanier(pp);
        this.xd = Double.parseDouble(produitprix.getText());
        this.cc = Double.parseDouble(caltotal.getText());
        double sostotValue = cc * xd;
        sostot.setText(String.valueOf(sostotValue));
        PanierController.totalpanier+= sostotValue ;
        System.out.println(PanierController.totalpanier);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Panier.fxml"));
        AnchorPane pane = loader.load();
        PanierController controller = loader.getController();
        controller.setSostot(PanierController.totalpanier);
        
        
    }
    }

    @FXML
    private void suppProd(ActionEvent event) {
        anchorpane.getChildren().clear();
        anchorpane.requestLayout();
anchorpane.layout();
        ps.supprimerProduitParId(1);
    }
    
    
    
  //  public void setidprod(int id){
    
    
   // }
}


