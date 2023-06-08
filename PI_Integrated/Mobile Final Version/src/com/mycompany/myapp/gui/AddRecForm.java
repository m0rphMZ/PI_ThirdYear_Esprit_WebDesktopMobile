/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ServiceReclamation;
import com.codename1.ui.ComboBox;
import com.codename1.ui.TextArea;
import java.util.Date;

/**
 *
 * @author bhk
 */
public class AddRecForm extends Form{

    public AddRecForm(Form previous) {
        setTitle("Ajouter une nouvelle réclamation");
        setLayout(BoxLayout.y());
        
        TextField recName = new TextField("","Nom de la réclamation");
        ComboBox<String> recType = new ComboBox<>("Type de réclamation", "User", "Ticket", "Evénement", "Autre aide");
        TextArea recDesc = new TextArea(5, 20);
        recDesc.setHint("Merci de décrire votre problème");
        Button btnValider = new Button("Ajoutez votre nouvelle réclamation");


            btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            if (recName.getText().length() < 5) 
                Dialog.show("Alerte", "Le nom doit comporter au moins 5 caractères", new Command("OK"));
            else if (recDesc.getText().length() < 10) 
                Dialog.show("Alerte", "La description doit comporter au moins 10 caractères", new Command("OK"));
            else if (recName.getText().length() == 0 || recType.getSelectedIndex() == 0 || recDesc.getText().length() == 0) 
                Dialog.show("Alerte", "Veuillez remplir tous les champs", new Command("OK"));
                else
                {
                    try {
                        Reclamation r = new Reclamation();
                        
                        r.setTitre_rec(recName.getText());
                        String selectedType = (String) recType.getSelectedItem();
                        r.setType(selectedType);
//                        r.setType(recType.getText());
                        r.setDescription(recDesc.getText());
                        r.setDate_creation(new Date());
                        r.setUser_id(LoginForm.getUserConnected().getId());
                        
                        
                        int userId = LoginForm.getUserConnected().getId();
  

                        if( ServiceReclamation.getInstance().addReclamation(r, userId))
                        {
                           Dialog.show("Succès","Votre réclamation a été ajoutée",new Command("OK"));
                           new ListReclamationsUserForm(LoginForm.getUserConnected().getId()).show();
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });

     
        addAll(recName,recType,recDesc,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
    
}
