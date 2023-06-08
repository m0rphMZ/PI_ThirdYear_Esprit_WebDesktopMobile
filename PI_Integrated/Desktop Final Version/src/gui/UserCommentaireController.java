/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Commentaire;
import entities.Event;
import entities.Like;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.CommentaireService;
import services.LikeService;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class UserCommentaireController implements Initializable {

    @FXML
    private Label user;
    @FXML
    private Label role;
    @FXML
    private Label comment;
  User UserConnected = LoginController.UserConnected;
    @FXML
    private Button modb1;
    @FXML
    private Button suppb;
    @FXML
    private Button modbt2;
    @FXML
    private TextField zone;
    private Commentaire c2=new Commentaire();
    CommentaireService cs = new CommentaireService();
    List<User> l = new ArrayList<>();
    @FXML
    private Button jaimebtn;
    @FXML
    private Button dislikebtn;
    private int likenbr;
    @FXML
    private Label nbr;
    @FXML
    private ImageView up;
    @FXML
    private ImageView down;
    LikeService ls = new LikeService();
    Boolean verif;
    @FXML
    private AnchorPane anchre;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }  
    
    
    
    
    
    public void setcmnt(Commentaire c) throws SQLException{
        
        
        
            verif=ls.verif_like(LoginController.UserConnected.getId(),c.getId_com());
            if (verif==true){
            
            jaimebtn.setDisable(true);
          dislikebtn.setDisable(false);
            
            }
            
            
            if(verif==false){
              dislikebtn.setDisable(true);
         jaimebtn.setDisable(false);
            }
        
        
       
       if (LoginController.UserConnected.getId()==c.getId_user()){
       
       user.setText(UserConnected.getNom());
       user.setText(UserConnected.getRole());
       
       } 
       List<User> cnmts = cs.recuperer_nom_role(c.getId_event());
        for (int i = 0; i < cnmts.size(); i++){
        if (c.getId_user()==cnmts.get(i).getId()){
        
        user.setText(cnmts.get(i).getNom());
        role.setText(cnmts.get(i).getRole());
        
        }
        
        }
       comment.setText(c.getCommentaire());
       nbr.setText(String.valueOf(cs.nbrlike(c)));
    c2=c;
    

    
    
    }
    
    
    public void setidcmnt(int id){
        c2.setId_com(id);
    
    }

    @FXML
    private void modifier(ActionEvent event) {
        comment.setVisible(false);
        zone.setText(comment.getText());
        zone.setVisible(true);
        modbt2.setVisible(true);
        modb1.setVisible(false);
        
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
//     int i = c2.getId_event();
//      Event ev = new Event();
//      ev.setEvent_id(i);
       cs.supprimer(c2);
       System.out.println(c2);
       anchre.getChildren().clear();
       anchre.requestLayout();
       anchre.layout();
//      FXMLLoader loader = new FXMLLoader(getClass().getResource("Commentaire.fxml"));
//     CommentaireController controller = loader.getController();
//      controller.sendInfo(ev);
    }

    @FXML
    private void modif2(ActionEvent event) throws SQLException {
        
        c2.setCommentaire(zone.getText());
        cs.modifier(c2);
        zone.setVisible(false);
        comment.setText(c2.getCommentaire());
        modbt2.setVisible(false);
        modb1.setVisible(true);
        comment.setVisible(true);
        
        
        
    }

    @FXML
    private void likeee(ActionEvent event) throws SQLException {
       
            Like like = new Like();
        like.setId_user(LoginController.UserConnected.getId());
        like.setId_com(c2.getId_com());
        like.setEtat(Boolean.TRUE);
        
    List<Like> likes = ls.affiche_like(like);
    if (likes.size()==0){   
        ls.ajouter(like);
        likenbr=cs.nbrlike(c2);
        c2.setLikeCount(likenbr+1);
       
        cs.modifier(c2);
          jaimebtn.setDisable(true);
          dislikebtn.setDisable(false);
          nbr.setText(String.valueOf(cs.nbrlike(c2)));
    }
    
    
    else{
    
    ls.ModifLike(like);
    likenbr=cs.nbrlike(c2);
        c2.setLikeCount(likenbr+1);
       
        cs.modifier(c2);
          jaimebtn.setDisable(true);
          dislikebtn.setDisable(false);
          nbr.setText(String.valueOf(cs.nbrlike(c2)));
    
    }
         
        
        
        
    
      
    }

    @FXML
    private void dislike(ActionEvent event) throws SQLException {
        //c2.getId_user()     
        Like like = new Like();
        like.setId_user(LoginController.UserConnected.getId());
        like.setId_com(c2.getId_com());
        like.setEtat(Boolean.FALSE);
        List<Like> likes = ls.affiche_like(like);
        
        if (likes.size()==0){ 
        
        ls.ajouter(like);
        
        
        
        
        likenbr=cs.nbrlike(c2);
        c2.setLikeCount(likenbr-1);
        cs.modifier(c2);
         dislikebtn.setDisable(true);
         jaimebtn.setDisable(false);
        nbr.setText(String.valueOf(cs.nbrlike(c2))); }
        
        
        else{       
        
        ls.ModifLike(like);
        
         likenbr=cs.nbrlike(c2);
        c2.setLikeCount(likenbr-1);
        cs.modifier(c2);
         dislikebtn.setDisable(true);
         jaimebtn.setDisable(false);
        nbr.setText(String.valueOf(cs.nbrlike(c2)));
        
        
        
        
        
        }
        
    }

    
    
    
    
    
    
}
