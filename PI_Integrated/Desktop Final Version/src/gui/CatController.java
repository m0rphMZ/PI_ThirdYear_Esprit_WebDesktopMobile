/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Categorie;
import entities.Categorie.UniteCategorie;
import entities.produit;
import entites.unite;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import services.CategorieService;
import services.UniteService;

/**
 * FXML Controller class
 *
 * @author Monta
 */
public class CatController implements Initializable {

    @FXML
    private TextField NomCatFid;
    private TextField UniteCatFid;

    @FXML
    private Button ajouter_cat_unite;
    @FXML
    private Button suprimerr_cat_unite;
    @FXML
    private Button modifier_cat_unite;
    CategorieService ps = new CategorieService();
    UniteService un = new UniteService();
    CategorieService cs = new CategorieService();

    @FXML
    private TableView<Categorie> tableCat;
    @FXML
    private TableColumn<Categorie, String> nomCatTv;
    private TextField idCatField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getEvent();
    }

    @FXML
    private void AjouterCat(ActionEvent event) {
        Categorie p = new Categorie();
        p.setLibCat(NomCatFid.getText());
        try {
            ps.ajouterCat(p);  
            getEvent();
            // reset();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void SupprimerCat(ActionEvent event) {
        
            Categorie e = tableCat.getItems().get(tableCat.getSelectionModel().getSelectedIndex());
      
        try {
            ps.supprimerCat(e);
        } catch (SQLException ex) {
            Logger.getLogger(GereController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("Event delete");
        alert.setContentText("Event deleted successfully!");
        alert.showAndWait();
        getEvent();
        getEvent();
    }

    @FXML
    private void ModiferCat(ActionEvent event) throws SQLException {
            Categorie e = tableCat.getItems().get(tableCat.getSelectionModel().getSelectedIndex());
        
      
//         String ring2 = UniteFid.getValue().toString();
     
        e.setLibCat(NomCatFid.getText());
              
        ps.modifierCat(e);
        getEvent();
    }

    public void getEvent() {

        try {
            List<Categorie> evenements = cs.recupererCat();
            ObservableList<Categorie> olp = FXCollections.observableArrayList(evenements);

            tableCat.setItems(olp);
            nomCatTv.setCellValueFactory(new PropertyValueFactory("libcategorie"));

        } catch (SQLException ex) {

            System.out.println("error" + ex.getMessage());
        }

    }

    @FXML
    private void choisirCat(MouseEvent event) {

        Categorie e = tableCat.getItems().get(tableCat.getSelectionModel().getSelectedIndex());

        NomCatFid.setText(String.valueOf(e.getLibCat()));
       //idCatField.setText(String.valueOf(e.getId()));

    }

}
