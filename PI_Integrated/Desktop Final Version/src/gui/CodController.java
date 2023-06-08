/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.produit;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import services.Redictionservice;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Monta
 */
public class CodController implements Initializable {

    @FXML
    private Text newprix;
    @FXML
    private Text description;
    @FXML
    private TextField addcode;
Redictionservice rs = new Redictionservice();
    @FXML
    private Text proddd_chyyy;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

              public void setprix(float prix) {
        
newprix.setText(Float.toString(prix));

    }
                   public void setdescription(String  des) {
        
description.setText(des);

    }
    @FXML
    private void chekproduit(ActionEvent event) {
        float a =Float.parseFloat(newprix.getText());
         
        try {
        boolean exists =rs.checkCodeExists(addcode.getText());
        float f=rs.getrductiontcode(addcode.getText())/100;
        float prix =Float.parseFloat(newprix.getText())-a*f;
                if(exists)
        {
            newprix.setText(Float.toString(prix));
            rs.suprimerbycode(addcode.getText());
        
        }else {proddd_chyyy.setText("sere9");}
        
           } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
    }

}
