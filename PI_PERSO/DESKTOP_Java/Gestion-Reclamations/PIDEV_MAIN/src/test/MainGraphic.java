/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package test;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGraphic extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        try {
        // Interface Utilisateur normale
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ChoisirReclamationType.fxml"));
        
        //Interface ADMIN
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AdminRecPanel.fxml"));
        
        
        Parent root = loader.load();

        // new scene avec root node
        Scene scene = new Scene(root,1280,800);
        
        primaryStage.setResizable(false);

        // titre stage
        primaryStage.setTitle("Touskieart");

        // set scene pour stage
        primaryStage.setScene(scene);

        // Show stage
        primaryStage.show();
    }
        catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
