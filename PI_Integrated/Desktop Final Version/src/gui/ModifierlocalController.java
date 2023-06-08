/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.Local;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import services.LocalService;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ModifierlocalController implements Initializable {

  /*  private TextField numField;
    private TextField descripFieldd;
    private TextField lieuField;
    private TextField surfaceField;
    private TextField nbPersField;*/
    @FXML
    private Label num;
    @FXML
    private Label descript;
    @FXML
    private Label lieu;
    @FXML
    private Label sur;
    @FXML
    private Label nb;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField numF;
    @FXML
    private TextField descripF;
    @FXML
    private TextField lieuF;
    @FXML
    private TextField surfaceF;
    @FXML
    private TextField nbPersF;
    @FXML
    private ComboBox<String> cmbxx;
    byte[] imageData;
LocalService ls=new LocalService();
    @FXML
    private Button b;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    
    
    
    public void getModif(Local l){ 
       numF.setText(String.valueOf(l.getNum()));
       descripF.setText(l.getDescript()); 
        lieuF.setText(l.getLieu());
       surfaceF.setText(String.valueOf(l.getSurface()));
        nbPersF.setText(String.valueOf(l.getNbper()));
       Image image = new Image(new ByteArrayInputStream(l.getImage()));
        imageView.setImage(image);
        cmbxx.setValue(String.valueOf(l.getCodec()));
        
       
       //  
        
        
        
    
    }

    @FXML
    private void addcombox(ActionEvent event) throws Exception{
        Connection cnx = MyDB.getInstance().getCnx();
        String req = "select * from categorie_loc  ";
        Statement ps = cnx.createStatement();
        ResultSet rs = ps.executeQuery(req);
        ObservableList data = FXCollections.observableArrayList();
        while (rs.next()) {

            data.add(rs.getString("codeC_loc"));

        }
        cmbxx.setItems(data);
    }

    @FXML
    private void enregister(ActionEvent event)  throws Exception{
        
      
          Local l = new Local();
            l.setNum(Integer.parseInt(numF.getText()));
            l.setDescript(descripF.getText());
            l.setLieu(lieuF.getText());
            l.setSurface(Float.parseFloat(surfaceF.getText()));
            l.setNbper(Integer.parseInt(nbPersF.getText()));
            l.setCodec(Integer.parseInt(cmbxx.getValue()));
            
           // imageView.getImage();
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
            ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage() , null), "png", byteArrayOut);
             imageData = byteArrayOut.toByteArray();
            
            
        // if(b.setOnAction((event)))//   b.setOnAction((eventt) -> {
              
          l.setImage(imageData);
     
                  try {
                ls.modifier(l);
                
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                
                alert.setHeaderText(null);
                alert.setContentText("Mofication avec succes");
                
                alert.show(); 
    
            } catch (SQLException ex) {
                Logger.getLogger(GererlocauxController.class.getName()).log(Level.SEVERE, null, ex);
            }
   
          //  reset();
            
    }
        
     
        
         
    

    @FXML
   
    
    private void parcourir(ActionEvent event) throws IOException {
       /*  Stage primaryStage=new Stage();
        FileChooser fileChooser = new FileChooser();
       fileChooser.setInitialDirectory(new File("C:\\Users\\arbey\\Imagepidev"));
      
File selectedFile = fileChooser.showOpenDialog(primaryStage);
if (selectedFile != null) {
    String path = selectedFile.toURI().toString();
    Image image = new Image(path);
    imageView.setImage(image);
       
    
    
   ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteArrayOut);
    imageData = byteArrayOut.toByteArray();;
     
    
        
    }*/
        Stage primaryStage=new Stage();
        FileChooser fileChooser = new FileChooser();
       fileChooser.setInitialDirectory(new File("C:\\Users\\arbey\\Imagepidev"));
      
File selectedFile = fileChooser.showOpenDialog(primaryStage);
if (selectedFile != null) {
    String path = selectedFile.toURI().toString();
    Image image = new Image(path);
    imageView.setImage(image);
       
    
    
    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteArrayOut);
    imageData = byteArrayOut.toByteArray();
     
    
       
                     }     
    
    }
    
}
