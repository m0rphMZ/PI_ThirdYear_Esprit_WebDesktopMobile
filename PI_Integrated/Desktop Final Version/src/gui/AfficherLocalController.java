/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.Local;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.LocalService;

/*
import Entities.Local;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import services.LocalService;
 */
/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AfficherLocalController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private TableView<Local> locTv;
    @FXML
    private TableColumn<Local, Integer> numTv;
    @FXML
    private TableColumn<Local, String> descriptTv;
    @FXML
    private TableColumn<Local, String> lieuTv;
    @FXML
    private TableColumn<Local, Float> surfaceTv;
    @FXML
    private TableColumn<Local, Integer> nbTv;
    @FXML
    private TableColumn<Local, Integer> codeTv;
    @FXML
    private TableColumn<Local, String> imgTv;
    @FXML
    private TableColumn<Local, Void> action;
    @FXML
    private TableColumn<Local, Void> action2;

    Connection cnx = null;

    //LocalService local = new LocalService();
    ObservableList<Local> olp = FXCollections.observableArrayList();
    LocalService ls = new LocalService();
    @FXML
    private Label lab;
    @FXML
    private ImageView excel;
    @FXML
    private ImageView statistique;


    /* @FXML
    private AnchorPane pane;
    @FXML
    private TableView<Local> locTv;
    @FXML
    private TableColumn<Local, Integer> numTv;
    @FXML
    private TableColumn<Local, String> descriptTv;
    @FXML
    private TableColumn<Local, String> lieuTv;
    @FXML
    private TableColumn<Local, Float> surfaceTv;
    @FXML
    private TableColumn<Local,Integer> nbTv;
    @FXML
    private TableColumn<Local, Integer> codeTv;
    @FXML
    private TableColumn<Local, Void> action;
    @FXML
    private TableColumn<Local, Void> action2;
    @FXML
    private TableColumn<Local, ?> imgTv;
  /*Connection cnx = null;

   LocalService local = new LocalService();
    ObservableList<Local> olp = FXCollections.observableArrayList();
       LocalService ls = new LocalService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            List<Local> personnes = ls.recuperer();
            System.out.println("1111");
            ObservableList<Local> olp = FXCollections.observableArrayList(personnes);
            locTv.setItems(olp);
            numTv.setCellValueFactory(new PropertyValueFactory<Local, Integer>("num"));
            descriptTv.setCellValueFactory(new PropertyValueFactory<Local, String>("descript"));
            lieuTv.setCellValueFactory(new PropertyValueFactory<Local, String>("lieu"));
            surfaceTv.setCellValueFactory(new PropertyValueFactory<Local, Float>("surface"));
            nbTv.setCellValueFactory(new PropertyValueFactory<Local, Integer>("nbper"));
            codeTv.setCellValueFactory(new PropertyValueFactory<Local, Integer>("codec"));

            imgTv.setCellValueFactory(new PropertyValueFactory("image"));

            action.setCellFactory((param) -> {
                return new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        setGraphic(null);
                        if (!empty) {
                            Button b = new Button("delete");
                            b.setOnAction((event) -> {
                                try {
                                    if (ls.supprimer(locTv.getItems().get(getIndex()))) {
                                        locTv.getItems().remove(getIndex());
                                        locTv.refresh();

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Information Dialog");

                                        alert.setHeaderText(null);
                                        alert.setContentText("suppression avec succes");

                                        alert.show();

                                    } else {

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Information Dialog");

                                        alert.setHeaderText(null);
                                        alert.setContentText("suppression a echouee");

                                        alert.show();

                                    }

                                } catch (SQLException ex) {
                                    System.out.println("erreor:" + ex.getMessage());

                                }

                            });
                            setGraphic(b);

                        }
                    }
                };

            });

            action2.setCellFactory((param) -> {
                return new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        setGraphic(null);
                        if (!empty) {
                            Button b2 = new Button("update");
                            b2.setOnAction((event) -> {

                                try {
                                  //Local l =new Local(888881,"aa","eee",52.1f,15,999);
                                    //Parent root = FXMLLoader.load(getClass().getResource("../gui/gererlaucax.fxml"));
                                   // pane.getScene().setRoot(root);
                                
                                     
                                    
                                     TableView<Local> tableView = (TableView<Local>) getTableView();
                                    Local selectedLocal = tableView.getSelectionModel().getSelectedItem();
                                   if(selectedLocal !=null)
                                   { Local newLocal = new Local(selectedLocal.getNum(), selectedLocal.getDescript(), selectedLocal.getLieu(), selectedLocal.getSurface(), selectedLocal.getNbper(), selectedLocal.getCodec(),selectedLocal.getImage());
                                   
                                   
                                   FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/modifierlocal.fxml"));
                                  Parent root = (Parent)loader.load();
                                  ModifierlocalController controller= loader.getController();
                                 controller.getModif(newLocal);
                                  Scene scene=new Scene(root,870,560);
                                  
                                  
           
            
                                   Stage stage = new Stage(); 
                                   stage.setTitle("modification");
                                     stage.setScene(scene);
                                     stage.show();
                                   }
                                    /*  
                                    Local at = locTv.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/modifierlocal.fxml"));
        AnchorPane pane = loader.load();
        ModifierlocalController controller = loader.getController();
        controller.getModif(at);
        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.showAndWait();
                                       
                                    
                                    
                                    
                                    Local at=locTv.getSelectionModel().getSelectedItem();
                                    
                                 FXMLLoader loader = FXMLLoader.load(getClass().getResource("../gui/modifierlocal.fxml"));
                                 AnchorPane pane = loader.load();
                                ModifierlocalController controller=loader.load();
                                     controller.getModif(at);
                                 grid.add(pane,0,1);
                                    
                                       /*  
                                    Parent root = FXMLLoader.load(getClass().getResource("../gui/gererlaucax.fxml"));
                                    pane.getScene().setRoot(root);
                                    
                                /*    
                                    GererlocauxController controller= new GererlocauxController();
                                    controller.setNumField("7");
                                    
                                  */  
                                    
                                    
                             

                                    
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }

                            });
                            setGraphic(b2);

                        }
                    }
                };

            });

        } catch (SQLException ex) {
            Logger.getLogger(AfficherLocalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void retour(MouseEvent event) {
         
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gererlaucax.fxml"));
            Parent root = loader.load();
       
            pane.getScene().setRoot(root);
           

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
        
        
    }

    @FXML
    private void handleImageViewClick(MouseEvent event) {
        
         try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier Excel");
           fileChooser.setInitialFileName(".xlsx");
            File file = fileChooser.showSaveDialog(null);
            
            if (file != null) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet =  workbook.createSheet("locaux");
                
                // écrire les en-têtes de colonne
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("NumLocal");
                headerRow.createCell(1).setCellValue("Description");
                headerRow.createCell(2).setCellValue("LieuLocal");
                headerRow.createCell(3).setCellValue("Surface en metre");
                headerRow.createCell(4).setCellValue("Code categorie");
                
        
        
         // écrire les données de TableView dans la feuille de calcul
                ObservableList<Local> locaux = locTv.getItems();
                for (int i = 0; i < locaux.size(); i++) {
                    Local local = locaux.get(i);
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(local.getNum());
                    row.createCell(1).setCellValue(local.getDescript());
                    row.createCell(2).setCellValue(local.getLieu());
                    row.createCell(3).setCellValue(local.getNbper());
                    row.createCell(4).setCellValue(local.getCodec());
                }
                
                FileOutputStream fos = new FileOutputStream(file);
                workbook.write(fos);
                workbook.close();
                fos.close();
                
        
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Enregistrement réussi");
                alert.setHeaderText("Le fichier Excel a été enregistré avec succès.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
   
        
        
        
        
        
        
        
        
    }

    @FXML
    private void statistique(MouseEvent event) {
        
        
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("statistiquelocal.fxml"));
            Parent root = loader.load();
       
            pane.getScene().setRoot(root);
           

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
        
    }

}
