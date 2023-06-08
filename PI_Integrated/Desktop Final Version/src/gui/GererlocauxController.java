/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entities.Local;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class GererlocauxController implements Initializable {

    @FXML
    private TextField numField;
    @FXML
    private TextField descripField;
    @FXML
    private TextField lieuField;
    @FXML
    private Button ajouterFielld;
    @FXML
    private Button annn;
  

    public void setNumField(TextField numField) {
        this.numField = numField;
    }

    public void setDescripField(TextField descripField) {
        this.descripField = descripField;
    }

    public void setLieuField(TextField lieuField) {
        this.lieuField = lieuField;
    }

    public void setSurfaceField(TextField surfaceField) {
        this.surfaceField = surfaceField;
    }

    public void setNbPersField(TextField nbPersField) {
        this.nbPersField = nbPersField;
    }

    public void setCmbx(ComboBox<String> cmbx) {
        this.cmbx = cmbx;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextField getNumField() {
        return numField;
    }

    public TextField getDescripField() {
        return descripField;
    }

    public TextField getLieuField() {
        return lieuField;
    }

    public TextField getSurfaceField() {
        return surfaceField;
    }

    public TextField getNbPersField() {
        return nbPersField;
    }

    public ComboBox<String> getCmbx() {
        return cmbx;
    }

    public Label getDescript() {
        return descript;
    }

    public ImageView getImageView() {
        return imageView;
    }
    @FXML
    private TextField surfaceField;
    @FXML
    private TextField nbPersField;
    @FXML
    private ComboBox<String> cmbx;
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
    LocalService ls=new LocalService();
    byte[]  imageData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addcombox(ActionEvent event) throws Exception {
    
     Connection cnx = MyDB.getInstance().getCnx();
        String req = "select * from categorie_loc  ";
        Statement ps = cnx.createStatement();
        ResultSet rs = ps.executeQuery(req);
        ObservableList data = FXCollections.observableArrayList();
        while (rs.next()) {

            data.add(rs.getString("codeC_loc"));

        }
        cmbx.setItems(data);
    }

    @FXML
    private void afficherLocal(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherLOCAL.fxml"));
            Parent root = loader.load();
       
            numField.getScene().setRoot(root);
           

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    
    
    
    @FXML
    private void parcourir(ActionEvent event) throws IOException {
        
        Stage primaryStage=new Stage();
        FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
if (selectedFile != null) {
    String path = selectedFile.toURI().toString();
    Image image = new Image(path);
    imageView.setImage(image);
       
    
    
    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteArrayOut);
    imageData = byteArrayOut.toByteArray();
     
    
        
}  
        
    }

    @FXML
    private void ajouterLocal(MouseEvent event) {
        
        
        
            boolean isValid = true;
    if (numField.getText().isEmpty()) {
       num.setText("Le numero est requis.");
        isValid = false;
    } else {
        num.setText("");
    }

    if (descripField.getText().isEmpty()) {
        descript.setText("la descript  est requise.");
        isValid = false;
    } 
    
    else {
        descript.setText("");
    }
    
    
    
    if (lieuField.getText().isEmpty()) {
        lieu.setText("L'emplacement est requis.");
        isValid = false;
    }else {
        lieu.setText("");
    }
    
    if (surfaceField.getText().isEmpty()) {
        sur.setText("la surface  est requise.");
        isValid = false;
    } 
else {
        sur.setText("");
    }
    
    
    
    if (nbPersField.getText().isEmpty()) {
        nb.setText("nbre a supporter est requis.");
        isValid = false;
    } 
else {
        nb.setText("");
    }
    if (isValid) {
        // Les entrées utilisateur sont valides, faites quelque chose ici
        
        Local l = new Local();
            l.setNum(Integer.parseInt(numField.getText()));
            l.setDescript(descripField.getText());
            l.setLieu(lieuField.getText());
            l.setSurface(Float.parseFloat(surfaceField.getText()));
            l.setNbper(Integer.parseInt(nbPersField.getText()));
            l.setCodec(Integer.parseInt(cmbx.getValue()));
            l.setImage(imageData);
            try {
                ls.ajouter(l);
            } catch (SQLException ex) {
                Logger.getLogger(GererlocauxController.class.getName()).log(Level.SEVERE, null, ex);
            }
   
            reset();
            
            
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                
                alert.setHeaderText(null);
                alert.setContentText("Ajout avec succes");
                
                alert.show(); 
    }
        
        
        
        
        
        
        
        
        
    }
    
    
    
    
    
    
     private void reset() {
        numField.setText("");
        descripField.setText("");
        lieuField.setText("");
        surfaceField.setText("");
        nbPersField.setText("");

    }

    
    

    @FXML
    private void annulerLocal(MouseEvent event) {
        
       reset();
        
    }

   /* private void retour(MouseEvent event) {
        
         try{
    Parent root=FXMLLoader.load(getClass().getResource("../gui/GESTIONLAUCAUX.fxml"));
   retour.getScene().setRoot(root);
    } 
    catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }*/

    @FXML
    private void ret(MouseEvent event) {
        
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GESTIONLOCAUX.fxml"));
            Parent root = loader.load();
       
            numField.getScene().setRoot(root);
           

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

   
    
    
}
