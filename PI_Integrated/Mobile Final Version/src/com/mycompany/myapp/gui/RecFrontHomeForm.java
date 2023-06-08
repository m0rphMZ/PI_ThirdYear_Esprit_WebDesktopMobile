/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author bhk
 */
public class RecFrontHomeForm extends Form{

    public RecFrontHomeForm(Form previous) {
        
        setTitle("Reclamations");
        setLayout(BoxLayout.y());
        
        add(new Label("Choisis une option"));
        Button btnAddRec = new Button("Ajouter une nouvelle réclamation");
        Button btnListRec = new Button("Voir vos réclamations");
        
        btnAddRec.addActionListener(e-> new AddRecForm(this).show());
        
        btnListRec.addActionListener(e -> {
        ListReclamationsUserForm listRecForm = new ListReclamationsUserForm(this, LoginForm.getUserConnected().getId());
        listRecForm.show();
    });
        
getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        addAll(btnAddRec,btnListRec);
        
        
    }
    
    
}
