package gui;

import entities.Reponses;
import entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ReponseController implements Initializable {

    @FXML
    private Label RepOwn;
    @FXML
    private Label Rep;
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //setData depuis AffichReclamOneController, pour passer le user ID lieé au reponse
    void setData(Reponses rep) {
       if (rep.getAdmin_id()==1) {
            RepOwn.setText("Reponse Admin :" );
            Rep.setText(rep.getRep_desc());
        } else {
            RepOwn.setText("Utilisateur avec Id " + rep.getUser_id() + ":");
            Rep.setText(rep.getRep_desc());
        }
    }
    
    //setAdminData depuis AfficherReclamOneAdminController TODEBUG
    void setAdminData(Reponses rep) {
        if (rep.getAdmin_id()==1) {
            RepOwn.setText("Reponse Admin :" );
            Rep.setText(rep.getRep_desc());
        } else {
            RepOwn.setText("Utilisateur avec ID: ");
            Rep.setText(rep.getRep_desc());
        }
    }

}
