/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;


import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.media.MediaPlayer;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ProduitService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.CategorieService;
import entities.Categorie;
import entities.produit;
import entites.unite;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.controlsfx.control.Notifications;
import services.UniteService;


/**yu
 * FXML Controller class
 *
 * @author Monta
 */
public class GereController implements Initializable {

    @FXML
    private TextField nompFid;
    @FXML
    private TextField QTFid;
    @FXML
    private TextField QTMFid;
    @FXML
    private TextArea DisFid;
    @FXML
    private TextField PrixFid;
    @FXML
    private ComboBox<Categorie> CatFid;
//    private void addcombox (ActionEvent event) throws SQLException {
//    Connection cnx = MyDB.getInstance().getCnx();
//    String  req = "select * from categorie";
//    Statement ps = cnx.createStatement();
//    ResultSet rs = ps.executeQuery(req);
//    ObservableList data = FXCollections.observableArrayList();
//    while(rs.next()){
//    
//    data.add(rs.getString("libCategorie"));
//    }
//    CatFid.setItems(data);
//    }
    
    
    @FXML
    private ComboBox<unite> UniteFid;
    produit prod =new produit();
    ProduitService ps = new ProduitService();
    CategorieService cs = new CategorieService();
    UniteService un = new UniteService();
    @FXML
    private TextField imageFid;
    private TextField tryIDunite;
    private TextField tryCat;

    @FXML
    private TableView<produit> tableProduit;
    @FXML
    private TableColumn<produit, String> nomTV;
    @FXML
    private TableColumn<produit, Integer> QMaxTV;
    @FXML
    private TableColumn<produit, Integer> QMINTV;
    private TableColumn<produit, Integer> prixTV;
    @FXML
    private TableColumn<produit, Integer> catTV;
    @FXML
    private TableColumn<produit, Integer> iduntitTV;
    private TableColumn<produit,String>  descriptionTV;
    @FXML
    private TableColumn<produit, String> imageTV;
    @FXML
    private TextField idmodifierField;
    @FXML
    private ImageView imageview;
    @FXML
    private Button AjoutFid;
    @FXML
    private Button Modiferbtn;
    @FXML
    private Button supPrd;
    @FXML
    private Button AfficherFid;
    private TableColumn<?, ?> desc;
    @FXML
    private TextField unitF;
    
    ProduitService Ev = new ProduitService();
    @FXML
    private ImageView QrCode;
    @FXML
    private Button backID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
     
        getEvents();
       
        
         
        try {
            List<Categorie> categories = cs.recupererCat();
                         ObservableList<Categorie> olc = FXCollections.observableArrayList(categories);
                       CatFid.setItems(olc);
                    
                       
            List<unite> unite = un.recupererUnite();
                         ObservableList<unite> olc1 = FXCollections.observableArrayList(unite);
                       UniteFid.setItems(olc1);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
    
    
     String path = "C:\\Users\\Monta\\Downloads\\fur-elise-piano-version.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
   
    @FXML
    private void ajouterProduit(ActionEvent event) throws SQLException {
           produit p = new produit();
           
           
//               if ((QTFid.getText().length() == 0) || (nompFid.getText().length() == 0) || (QTMFid.getText().length() == 0) || (PrixFid.getText().length() == 0)|| (tryCat.getText().length() == 0)|| (tryIDunite.getText().length() == 0)) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error ");
//            alert.setHeaderText("Error!");
//            alert.setContentText("Fields cannot be empty");
//            alert.showAndWait();
//        }
            //   else {
                   
                   String ring = CatFid.getValue().toString();
                   // String ring2 = UniteFid.getValue().toString();
                   p.setQteStock(Integer.parseInt(QTFid.getText()));
                   p.setCodeProduit(nompFid.getText());
                   p.setQteMin(Integer.parseInt(QTMFid.getText()));
                   p.setPrixUnite(Integer.parseInt(PrixFid.getText()));
                   p.setIdCat(ring);
                   p.setIdUnite(unitF.getText());
                   p.setDesgination(DisFid.getText());
                   p.setImage(imageFid.getText());


          List<Categorie> Categorie  = cs.recupererCat();
             ObservableList<Categorie> 
            olc = FXCollections.observableArrayList(Categorie);
                       CatFid.setItems(olc);
        try {
            ps.ajouter(p);
            getEvents();
        reset();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       // }
    }

    @FXML
    private void modifierProduit(ActionEvent event) throws SQLException {
        produit e = new produit();
        
         String ring = CatFid.getValue().toString();
//         String ring2 = UniteFid.getValue().toString();
        e.setId(Integer.valueOf(idmodifierField.getText()));
        e.setCodeProduit(nompFid.getText());
        e.setDesgination(DisFid.getText());
        e.setImage(imageFid.getText());
        e.setIdCat(ring);
        e.setPrixUnite(Float.valueOf(PrixFid.getText()));
        //e.setIdUnite(ring2);
        e.setIdUnite(unitF.getText());
        e.setQteMin(Integer.valueOf(QTMFid.getText()));
        e.setQteStock(Integer.valueOf(QTFid.getText()));            
        ps.modifier(e);
        getEvents();
       
    }

    @FXML
    private void supProduit(ActionEvent event) {
        
        
           produit e = tableProduit.getItems().get(tableProduit.getSelectionModel().getSelectedIndex());
      
        try {
            ps.supprimer(e);
        } catch (SQLException ex) {
            Logger.getLogger(GereController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("Event delete");
        alert.setContentText("Event deleted successfully!");
        alert.showAndWait();
        getEvents();
        
    }

    @FXML
    private void afficherProduit(ActionEvent event) {
        
        
          try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("afficherProduit.fxml"));
            nompFid.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
    }

    @FXML
    private void choisirProduit(MouseEvent event) throws IOException {
        produit e = tableProduit.getItems().get(tableProduit.getSelectionModel().getSelectedIndex());
    
        idmodifierField.setText(String.valueOf(e.getId()));
        //idLabel.setText(String.valueOf(e.getId_event()));
        QTFid.setText(String.valueOf(e.getQteStock()));
        nompFid.setText(e.getCodeProduit());
        QTMFid.setText(String.valueOf(e.getQteMin()));
        PrixFid.setText(String.valueOf(e.getPrixUnite()));
        DisFid.setText(e.getDesgination());
        unitF.setText(e.getIdUnite());
       //dateEventField.setValue((e.getDate()));
      
       //DisFid.setText((e.getDesgination()));
       imageFid.setText((e.getImage()));
        
        String path = e.getImage();
               File file=new File(path);
              Image img = new Image(file.toURI().toString());
                imageview.setImage(img);
             
                
                //////////::
                 String filename = Ev.GenerateQrEvent(e);
            System.out.println("filename lenaaa " + filename);
            String path1="C:\\Users\\Monta\\Documents\\NetBeansProjects\\GestionProduits\\imgQr"+filename;
             File file1=new File(path1);
              Image img1 = new Image(file1.toURI().toString());
              //Image image = new Image(getClass().getResourceAsStream("src/utils/img/" + filename));
            QrCode.setImage(img1);  
          

    }
     
   public void getEvents() {  

         try {
            // TODO    
            List<produit> evenements = ps.recuperer();
            ObservableList<produit> olp = FXCollections.observableArrayList(evenements);
            tableProduit.setItems(olp);
            nomTV.setCellValueFactory(new PropertyValueFactory("CodeProduit"));
        //    desc.setCellValueFactory(new PropertyValueFactory("Des"));
            QMaxTV.setCellValueFactory(new PropertyValueFactory("QteStock"));
            QMINTV.setCellValueFactory(new PropertyValueFactory("QteMin"));
         //   prixTV.setCellValueFactory(new PropertyValueFactory("PrixUnitaire"));
            catTV.setCellValueFactory(new PropertyValueFactory("idCat"));
            iduntitTV.setCellValueFactory(new PropertyValueFactory("idUnite"));
             imageTV.setCellValueFactory(new PropertyValueFactory("image"));
          

        } catch (SQLException ex) {
       
            System.out.println("error" + ex.getMessage());
        }

    }//get events

    @FXML
    private void uploadImage(ActionEvent event)throws FileNotFoundException, IOException  {
        
               Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\\\xampp\\\\htdocs\\\\imageP\\\\"  + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path=file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            imageview.setImage(img);
           /* File File1 = new File(DBPath);
            Image img = new Image(File1.getAbsolutePath());
            image_event.setImage(img);*/
            imageFid.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();
            
        } else {
            System.out.println("error");

        }
    }
        
    
   
private void reset() {
        QTFid.setText("");
        QTMFid.setText("");
        DisFid.setText("");
        nompFid.setText("");
        PrixFid.setText("");
        unitF.setText("");

    

    }

    @FXML
    private void switchToCategorie(ActionEvent event) {
        
     

        try {
          Parent root = FXMLLoader.load(getClass().getResource("Cat.fxml"));
                    Scene sence = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(sence);
                    stage.show();
            
        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
        
    }

    @FXML
    private void playMusic(ActionEvent event) {
          mediaPlayer.play();
      //  Image img = new Image("C:\\xampp\\htdocs\\chy.png");
  Notifications notificationBuilder = Notifications.create()
                .title("Musique")
                .text("      Musique demarreeerrr");
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    @FXML
    private void pauseMusic(ActionEvent event) {
         mediaPlayer.pause();
        //Image img = new Image("fllogo.png");
        Notifications notificationBuilder = Notifications.create()
                .title("Musique")
                .text("      Musique Arrêtée");
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    @FXML
    private void excelEvent(ActionEvent event) {
        try{
String filename="C:\\xampp\\htdocs\\fichierExcelJava\\dataEvent.xls" ;
    HSSFWorkbook hwb=new HSSFWorkbook();
    HSSFSheet sheet =  hwb.createSheet("new sheet");
    HSSFRow rowhead=   sheet.createRow((short)0);
rowhead.createCell((short) 0).setCellValue("nom evenement");
rowhead.createCell((short) 1).setCellValue("type d'evenement");
rowhead.createCell((short) 2).setCellValue("description ");
List<produit> evenements = Ev.recuperer();
  for (int i = 0; i < evenements.size(); i++) {          
HSSFRow row=   sheet.createRow((short)i);
row.createCell((short) 0).setCellValue(evenements.get(i).getCodeProduit());
row.createCell((short) 1).setCellValue(evenements.get(i).getPrixUnite());
row.createCell((short) 2).setCellValue(evenements.get(i).getQteStock());
row.createCell((short) 2).setCellValue(evenements.get(i).getIdCat());
//row.createCell((short) 3).setCellValue((evenements.get(i).getDate()));
i++;
            }
int i=1;
    FileOutputStream fileOut =  new FileOutputStream(filename);
hwb.write(fileOut);
fileOut.close();
System.out.println("Your excel file has been generated!");
 File file = new File(filename);
        if (file.exists()){ 
        if(Desktop.isDesktopSupported()){
            Desktop.getDesktop().open(file);
        }}       
} catch ( Exception ex ) {
    System.out.println(ex);
}
    }

    @FXML
    private void pdfEvent(ActionEvent event) throws IOException {
          // evenement tab_Recselected = evenementTv.getSelectionModel().getSelectedItem();
               long millis = System.currentTimeMillis();
        java.sql.Date DateRapport = new java.sql.Date(millis);

        String DateLyoum = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(DateRapport);//yyyyMMddHHmmss
        System.out.println("Date d'aujourdhui : " + DateLyoum);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));//yyyy-MM-dd
            document.open();
            Paragraph ph1 = new Paragraph("Voici un rapport détaillé de notre application qui contient tous les Produit . Pour chaque événement, nous fournissons des informations telles que la date d'aujourdhui :" + DateRapport );
            Paragraph ph2 = new Paragraph(".");
            PdfPTable table = new PdfPTable(3);
            //On créer l'objet cellule.
            PdfPCell cell;
            //contenu du tableau.
            table.addCell("nomProduit");
            table.addCell("Prit");
            table.addCell("Quantite");
            table.addCell("Categorie");
            produit r = new produit();
            Ev.recuperer().forEach(e
                    -> {
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(String.valueOf(e.getCodeProduit()));
              //  table.addCell(String.valueOf(e.getCategorie()));
                table.addCell(String.valueOf(e.getPrixUnite()));    
                 table.addCell(String.valueOf(e.getQteStock()));
                  table.addCell(String.valueOf(e.getIdCat()));
            }
            );
            document.add(ph1);
            document.add(ph2);
            document.add(table);
             } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

        ///Open FilePdf
        File file = new File(DateLyoum + ".pdf");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) //checks file exists or not  
        {
            desktop.open(file); //opens the specified file   
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainContainer.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            backID.getScene().setRoot(root);
    }

}
   
    
   

