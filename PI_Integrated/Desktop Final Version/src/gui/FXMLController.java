/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class FXMLController implements Initializable {

    @FXML
    private AnchorPane mainform;
    @FXML
    private BarChart<String, Integer> charbar;

    /**
     * Initializes the controller class.
     */
    Connection cnx = MyDB.getInstance().getCnx();
    @FXML
    private ImageView retour;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chart();
    }  
    
   
    
     private void chart(){//"SELECT categorieloc.nom_catg, COUNT(*) as nb_locations FROM location INNER JOIN categorieloc ON location.id_catg = categorieloc.id_catg GROUP BY categorieloc.nom_catg";
         //String sqlchart ="select libelleC_loc, count(*)as nb_location from location inner join categorie_loc on num_loc = codeC_loc group by libelleC_loc " ;
    String sqlchart="SELECT libelleC_loc, COUNT(*) as nb_locations FROM location INNER JOIN categorie_loc ON code_catg = codeC_loc GROUP BY libelleC_loc";
    try{
    XYChart.Series<String, Integer> series = new XYChart.Series<>();
    PreparedStatement  ps=cnx.prepareStatement(sqlchart);
    ResultSet rs=ps.executeQuery();
    while (rs.next()) {
        String nom_catg = rs.getString("libelleC_loc");
        int nb_locations = rs.getInt("nb_locations");
        series.getData().add(new XYChart.Data<>(nom_catg, nb_locations));
    }
charbar.getData().add(series);
     rs.close();
    
    }
    catch(Exception e){
    e.printStackTrace();
    }
    
    
    } 

    @FXML
    private void retour(MouseEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherLOCAL.fxml"));
            Parent root = loader.load();
       
            mainform.getScene().setRoot(root);
           

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
    

}
