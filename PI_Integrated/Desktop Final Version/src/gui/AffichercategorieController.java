/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.CategorieLocal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.CategorieLocalService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AffichercategorieController implements Initializable {

    @FXML
    private AnchorPane pan;
    @FXML
    private VBox vb;

    CategorieLocalService ls = new CategorieLocalService();
    CategorieLocalService cs = new CategorieLocalService();
    @FXML
    private TextField cherchertxtF;
    @FXML
    private ComboBox<String> cmbtrie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            List<CategorieLocal> personnes = ls.recuperer();

            for (int i = 0; i < personnes.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Categorie.fxml"));
                AnchorPane pane = loader.load();

                //passage de parametres
                CategorieController controller = loader.getController();
                controller.setData(personnes.get(i));

                vb.getChildren().add(pane);

                // Button btnSupprimer = new Button("Supprimer");
                // vb.getChildren().add(btnSupprimer);
                controller.no.setOnAction(event -> {
                    // Récupération de l'élément à supprimer
                    VBox parent = (VBox) controller.no.getParent().getParent();
                    int index = parent.getChildren().indexOf(controller.no.getParent());
                    CategorieLocal categorie = personnes.get(index);

                    try {
                        // Suppression de l'élément de la base de données
                        if (cs.supprimer(categorie)) {
                            parent.getChildren().remove(index);
                        }

                        // Suppression de l'élément de la VBox
                    } catch (SQLException ex) {
                        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO
        
        
        
        cherchertxtF.textProperty().addListener((observable, oldValue, newValue) -> {
    // Appeler la fonction searchByCode avec la nouvelle valeur du texte
    searchByCode(newValue);
});
        
          String[] data ={"code","libelle"};
        cmbtrie.getItems().addAll(data);
        
       cmbtrie.setOnAction(this::trieCode);
       //if(cmbtrie.getValue()=="code"){
        
      // cherchertxtF.setText(cmbtrie.getValue());
        
       
    }
    
    
    
    
    private void trieCode(ActionEvent ev){
       if(cmbtrie.getValue().equals("code"))
       
       {
       try{
            List<CategorieLocal> categories = ls.triCode();

        // Clear the VBox
        vb.getChildren().clear();

        // Iterate through the list of categories and add the matching elements to the VBox
        for (CategorieLocal categorie : categories) {
           // if (categorie.getCode() == Integer.parseInt(code)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Categorie.fxml"));
                AnchorPane pane = loader.load();

                // Pass the category data to the CategorieController
                CategorieController controller = loader.getController();
                controller.setData(categorie);

                // Add the AnchorPane to the VBox
                vb.getChildren().add(pane);
            //}
        }
    } catch (SQLException | IOException ex) {
        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
    }
       
       }
       else if(cmbtrie.getValue().equals("libelle")){
       
       
        try{
            List<CategorieLocal> categories = ls.triLibelle();

        // Clear the VBox
        vb.getChildren().clear();

        // Iterate through the list of categories and add the matching elements to the VBox
        for (CategorieLocal categorie : categories) {
           // if (categorie.getCode() == Integer.parseInt(code)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Categorie.fxml"));
                AnchorPane pane = loader.load();

                // Pass the category data to the CategorieController
                CategorieController controller = loader.getController();
                controller.setData(categorie);

                // Add the AnchorPane to the VBox
                vb.getChildren().add(pane);
            //}
        }
    } catch (SQLException | IOException ex) {
        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
    }
       
       
       
       }
       
    }
    
    
    

    @FXML
    private void retour(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gerercategories.fxml"));
            Parent root = loader.load();

            pan.getScene().setRoot(root);

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }

    }

    
    
         
            
      public void searchByCode(String code) {
    try {
        // Retrieve the list of categories from the database using the "recuperer" method of the "ls" object
        List<CategorieLocal> categories = ls.recuperer();

        // Clear the VBox
        vb.getChildren().clear();

        // Iterate through the list of categories and add the matching elements to the VBox
        for (CategorieLocal categorie : categories) {
            if (categorie.getCode() == Integer.parseInt(code)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Categorie.fxml"));
                AnchorPane pane = loader.load();

                // Pass the category data to the CategorieController
                CategorieController controller = loader.getController();
                controller.setData(categorie);

                // Add the AnchorPane to the VBox
                vb.getChildren().add(pane);
            }
        }
    } catch (SQLException | IOException ex) {
        Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    


    @FXML
    private void chercher(KeyEvent event) { 
    }


  
    
}
/*private void Chercher(int id)
    
    {
    List<Node> matchingNodes = new ArrayList<>();

    // Parcourir les enfants de la VBox pour trouver les locaux correspondants
    for (Node node : vb.getChildren()) {
        if (node instanceof AnchorPane) {
            CategorieController controller = (CategorieController) ((AnchorPane) node).getProperties().get("controller");
            CategorieLocal local = controller.getData();

            // Vérifier si le code correspond
            if (local.getCode().equals(code)) {
                matchingNodes.add(node);
            }
        }
    }

    // Ajouter les locaux correspondants à la VBox
    vb.getChildren().clear();
    vb.getChildren().addAll(matchingNodes);
    
    
    }*/
   /* @FXML
    private void chercher(KeyEvent eventt) throws Exception {
        String c = cherchertxtF.getText();

        if (c != null && !c.isEmpty()) {
            int code = Integer.valueOf(c);
            List<CategorieLocal> personnecode = ls.recupererCode(code);
            vb.getChildren().clear();

            try {

                for (int i = 0; i < personnecode.size(); i++) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Categorie.fxml"));
                    AnchorPane pane = loader.load();

                    //passage de parametres
                    CategorieController controller = loader.getController();
                    controller.setData(personnecode.get(i));

                    vb.getChildren().add(pane);

                }
            } catch (IOException ex) {
                Logger.getLogger(AffichercategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    
    
    
    
    
    
    
    
    
    
    
   */ 